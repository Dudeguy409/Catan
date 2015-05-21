package client.Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

import client.GUI.BankTradeFrame;
import client.GUI.DiscardFrame;
import client.GUI.HexComponent;
import client.GUI.HexComponent.RoadPosition;
import client.GUI.IBoardRenderer;
import client.GUI.IUserPanel;
import client.GUI.PlayerTradeFrame;
import client.Model.Hex;
import client.Model.Player;
import client.Model.RoadPiece;
import client.Model.StructurePiece;

public class Game {

	public static final Color desertColor = new Color(255, 255, 170);
	public static final Color wheatColor = new Color(255, 255, 0);
	public static final Color sheepColor = new Color(0, 255, 0);
	public static final Color woodColor = new Color(0, 100, 0);
	public static final Color oreColor = new Color(170, 170, 170);
	public static final Color brickColor = new Color(200, 70, 0);

	public static final Color[] resourceColors = { wheatColor, woodColor,
			sheepColor, brickColor, oreColor };

	public static enum Resource {
		wheat(0), wood(1), sheep(2), brick(3), ore(4), desert(-1);

		private int numVal;

		Resource(int numVal) {
			this.numVal = numVal;
		}

		public int getNumVal() {
			return numVal;
		}
	}

	public static enum DevCard {
		yearOfPlenty, monopoly, knight, victory, roadBuilder
	}

	public static enum TurnPhase {
		preroll, pretrade, trade, prebuild, build, end
	}

	// development cards aren't built, they are drawn, so they aren't included
	// in this.
	public static enum BuildType {
		road, settlement, city, none, robber
	}

	private IBoardRenderer board;
	private IUserPanel userPanel;
	private TurnPhase currentTurnPhase = TurnPhase.build;
	private BuildType currentBuildType = BuildType.none;
	protected int currentPlayer;
	protected Player[] players;
	private Color[] colorArray;
	private int numberOfPlayers;
	private RoadManager roadMgr;
	private HexManager hexMgr;
	private StructureManager structMgr;
	private IDice dice;
	private Queue<Integer> startingTurnsQueue;
	private int maxRoadLength = 0;
	private int playerWithLongestRoad = -1;
	private int maxArmySize = -1;
	private int playerWithLargestArmy = -1;
	private boolean preGameMode = true;
	private boolean hasBuiltRoad = false;
	private ArrayList<Hex> hexArray;
	private LinkedList<DevCard> devCardDeck;
	private int robberLocation = -1;
	private int roadBuild = 0;
	private PortManager portMgr;
	private boolean hasPlayedNonVPDevCard = false;
	private ArrayList<DevCard> purchasedDevCards = null;

	/**
	 * The number of hexes on the field.
	 */
	public static final int boardSize = 19;

	public Game(Color[] pColors, Resource[] hexResources, IDice dice,
			int startingPlayer, IUserPanel userPanel, IBoardRenderer board,
			int[] randomNumberArray, LinkedList<DevCard> devCardDeck,
			Resource[] portTypes) {

		this.currentPlayer = startingPlayer;
		this.dice = dice;
		this.colorArray = pColors;
		this.numberOfPlayers = pColors.length;
		this.devCardDeck = devCardDeck;

		generateStartingTurnsQueue();

		this.hexArray = new ArrayList<Hex>();
		for (int j = 0; j < boardSize; j++) {
			hexArray.add(new Hex(randomNumberArray[j], hexResources[j], j));
			if (hexResources[j] == Resource.desert) {
				this.robberLocation = j;
			}
		}

		this.players = new Player[this.numberOfPlayers];
		for (int i = 0; i < this.numberOfPlayers; i++) {
			this.players[i] = new Player();
			// TODO remove
			this.players[i].adjustCards(Resource.sheep, 21);
			this.players[i].adjustCards(Resource.ore, 21);
			this.players[i].adjustCards(Resource.wheat, 21);
		}
		this.roadMgr = new RoadManager(this.numberOfPlayers);
		this.hexMgr = new HexManager();
		this.structMgr = new StructureManager(this.numberOfPlayers);
		this.portMgr = new PortManager(portTypes);
		this.userPanel = userPanel;
		this.userPanel.configureUserPanel(this);
		this.board = board;
		this.board.setGame(this);
		this.board.setBoard();
	}

	private void generateStartingTurnsQueue() {
		this.startingTurnsQueue = new LinkedList<Integer>();
		Stack<Integer> reverseOrder = new Stack<Integer>();
		for (int i = 0; i < this.numberOfPlayers; i++) {
			int index = (i + this.currentPlayer) % this.numberOfPlayers;
			this.startingTurnsQueue.add(index);
			reverseOrder.push(index);
		}

		for (int i = 0; i < this.numberOfPlayers; i++) {
			this.startingTurnsQueue.add(reverseOrder.pop());
		}
		this.startingTurnsQueue.poll();
	}

	public TurnPhase getCurrentTurnPhase() {
		return this.currentTurnPhase;
	}

	public BuildType getCurrentBuildType() {
		return this.currentBuildType;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	public boolean trade(int playerToTradeIndex, int[] resourcesOffered,
			int[] resourcesRequested) {
		if (playerToTradeIndex == this.currentPlayer) {
			return false;
		}

		int[] currentCardsA = this.players[this.currentPlayer].getCards();
		int[] currentCardsB = this.players[playerToTradeIndex].getCards();

		boolean isSomethingOffered = false;
		boolean isSomethingRequested = false;
		for (int i = 0; i < resourcesOffered.length; i++) {
			if (resourcesOffered[i] > currentCardsA[i]
					|| resourcesRequested[i] > currentCardsB[i]) {
				return false;
			}
			if (resourcesOffered[i] > 0) {
				isSomethingOffered = true;
			}
			if (resourcesRequested[i] > 0) {
				isSomethingRequested = true;
			}
		}

		if (!(isSomethingOffered && isSomethingRequested)) {
			return false;
		}

		this.players[this.currentPlayer].adjustCards(resourcesRequested);
		this.players[playerToTradeIndex].adjustCards(resourcesOffered);
		for (int i = 0; i < resourcesOffered.length; i++) {
			resourcesOffered[i] *= -1;
			resourcesRequested[i] *= -1;
		}

		this.players[this.currentPlayer].adjustCards(resourcesOffered);
		this.players[playerToTradeIndex].adjustCards(resourcesRequested);

		updateUserPanelCards();
		return true;
	}

	public boolean addRoad(int playerIndex, int hexId,
			HexComponent.RoadPosition pos) {

		boolean roadAdded = false;
		int roadId = this.hexMgr.getRoadId(hexId, pos);
		int currentRoadCount = this.roadMgr.getRoadCountForPlayer(playerIndex);
		if (currentRoadCount < 2) {
			if (isValidBeginnerRoad(hexId, pos)) {
				this.roadMgr.addRoadPieceAtBeginning(playerIndex, roadId);
			}
		} else {
			this.roadMgr.addRoadPiece(playerIndex, roadId);
		}
		if (this.roadMgr.getRoadCountForPlayer(playerIndex) > currentRoadCount) {
			roadAdded = true;
			this.board.addRoad(hexId, pos, this.colorArray[playerIndex],
					BuildType.road);
			if (!this.preGameMode) {
				int[] delta = { 0, -1, 0, -1, 0, 0, 0 };
				this.players[playerIndex].adjustCards(delta);
			}
			updateUserPanelCards();
		}

		if (roadMgr.findLongestRoadForPlayer(playerIndex) > maxRoadLength) {
			maxRoadLength = roadMgr.findLongestRoadForPlayer(playerIndex);
			playerWithLongestRoad = playerIndex;
		}

		return roadAdded;
	}

	private boolean isValidBeginnerRoad(int hexId, RoadPosition pos) {
		HexComponent.StructurePosition[] structPos = HexComponent
				.getAdjacentStructurePositionsForRoad(pos);
		int a = this.hexMgr.getStructureId(hexId, structPos[0]);
		int b = this.hexMgr.getStructureId(hexId, structPos[1]);
		if ((this.structMgr.isValidBeginningSettlementPosition(a))
				|| (this.structMgr.isValidBeginningSettlementPosition(b))) {
			return true;
		}
		return false;
	}

	public boolean addBuilding(int playerIndex, int hexId,
			HexComponent.StructurePosition pos) {

		int structureId = this.hexMgr.getStructureId(hexId, pos);

		boolean isAdjacent = false;

		HexComponent.RoadPosition[] adjRoads = HexComponent
				.getAdjacentRoadPositionsForStructure(pos);

		// Checks for adjacent road not returned by
		// getAdjacentRoadPositionsForStructure
		ArrayList<Integer> possibleRoads = new ArrayList<Integer>();
		possibleRoads.add(this.hexMgr.getRoadId(hexId, adjRoads[0]));
		possibleRoads.add(this.hexMgr.getRoadId(hexId, adjRoads[1]));

		int[] adjacentRoadsOneA = this.roadMgr.roadDependencyMap.get(
				this.hexMgr.getRoadId(hexId, adjRoads[0])).getAdjacentRoadsA();
		int[] adjacentRoadsOneB = this.roadMgr.roadDependencyMap.get(
				this.hexMgr.getRoadId(hexId, adjRoads[0])).getAdjacentRoadsB();
		int[] adjacentRoadsTwoA = this.roadMgr.roadDependencyMap.get(
				this.hexMgr.getRoadId(hexId, adjRoads[1])).getAdjacentRoadsA();
		int[] adjacentRoadsTwoB = this.roadMgr.roadDependencyMap.get(
				this.hexMgr.getRoadId(hexId, adjRoads[1])).getAdjacentRoadsB();

		int[] adjacentRoadsOne = new int[adjacentRoadsOneA.length
				+ adjacentRoadsOneB.length];
		System.arraycopy(adjacentRoadsOneA, 0, adjacentRoadsOne, 0,
				adjacentRoadsOneA.length);
		System.arraycopy(adjacentRoadsOneB, 0, adjacentRoadsOne,
				adjacentRoadsOneA.length, adjacentRoadsOneB.length);

		int[] adjacentRoadsTwo = new int[adjacentRoadsTwoA.length
				+ adjacentRoadsTwoB.length];
		System.arraycopy(adjacentRoadsTwoA, 0, adjacentRoadsTwo, 0,
				adjacentRoadsTwoA.length);
		System.arraycopy(adjacentRoadsTwoB, 0, adjacentRoadsTwo,
				adjacentRoadsTwoA.length, adjacentRoadsTwoB.length);

		for (int position1 : adjacentRoadsOne) {
			for (int position2 : adjacentRoadsTwo) {
				if (position1 == position2) {
					possibleRoads.add(position1);
				}
			}
		}

		HashMap<Integer, RoadPiece> roadPieceMap = this.roadMgr.roadPieceDependencyMaps
				.get(playerIndex);
		for (int roadPos : possibleRoads) {
			if (roadPieceMap.containsKey(roadPos)) {
				isAdjacent = true;
			}
		}

		if (isAdjacent) {

			try {

				if (this.currentBuildType == BuildType.settlement) {
					this.structMgr.addStructure(playerIndex, structureId);
					this.board.addBuilding(hexId, pos,
							this.colorArray[playerIndex], BuildType.settlement);
					Resource portResource = this.portMgr
							.getPortTypeForStructurePosition(structureId);
					if (portResource != null) {
						this.players[this.currentPlayer].addPort(portResource);
					}
					if (!this.preGameMode) {
						int[] delta = { -1, -1, -1, -1, 0, 0, 0 };
						this.players[playerIndex].adjustCards(delta);
					}

				} else {
					this.structMgr.updateStructure(playerIndex, structureId);
					this.board.addBuilding(hexId, pos,
							this.colorArray[playerIndex], BuildType.city);
					if (!this.preGameMode) {
						int[] delta = { -2, 0, 0, 0, -3, 0, 0 };
						this.players[playerIndex].adjustCards(delta);
					}
				}

				int settleCount = this.structMgr
						.getSettlementCountForPlayer(playerIndex);
				int cityCount = this.structMgr
						.getCityCountForPlayer(playerIndex);

				if (settleCount == 2 && cityCount == 0) {
					addResourcesForSecondSettlement(playerIndex, structureId);
				}
				updateUserPanelCards();
				return true;

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return false;
	}

	private void addResourcesForSecondSettlement(int playerIndex,
			int structureId) {
		ArrayList<Integer> adjacentHexIds = this.hexMgr
				.getAdjacentHexesForSettlement(structureId);

		for (int i = 0; i < adjacentHexIds.size(); i++) {
			this.players[playerIndex].adjustCards(
					this.hexArray.get(adjacentHexIds.get(i)).getResource(), 1);
		}
	}

	public void roll() {
		int[] rolls = this.dice.rollDice();
		this.userPanel.setRolls(rolls);

		int roll = rolls[0];

		if (roll == 7) {

			for (int i = 0; i < this.players.length; i++) {
				if (this.players[i].getCards()[6] > 7) {
					displayDiscardFrame(i);
				}
			}

			startMoveRobber();

		} else {
			ArrayList<Hex> rolledHexes = findRolledHexes(roll);

			for (int i = 0; i < rolledHexes.size(); i++) {
				Hex hex = rolledHexes.get(i);
				int hexId = hex.getHexID();

				if (robberLocation != hexId) {
					Resource type = hex.getResource();

					int[] structIDs = this.hexMgr.getStructureIDsOnHex(hexId);

					for (int j = 0; j < structIDs.length; j++) {

						StructurePiece p = this.structMgr
								.getStructurePiece(structIDs[j]);

						if (p != null) {

							int cardsToAdd = 0;

							if (p.getBuildType() == BuildType.city) {
								cardsToAdd = 2;
							} else if (p.getBuildType() == BuildType.settlement) {
								cardsToAdd = 1;
							} else {
								System.out.println("Critical Error!!!");
							}
							this.players[p.getPlayerIndex()].adjustCards(type,
									cardsToAdd);
						}
					}

					updateUserPanelCards();
				}
			}
		}
	}

	private void startMoveRobber() {
		displayMoveRobberMessage();

		this.userPanel.disableUserPanel();
		this.currentBuildType = BuildType.robber;
	}

	protected void displayMoveRobberMessage() {
		String message = "Player " + (this.currentPlayer + 1)
				+ ", please select a new hex for the robber.";

		JOptionPane.showMessageDialog(null, message);
	}

	protected void displayDiscardFrame(int i) {
		new DiscardFrame(this, i, this.players[i].getCards());
	}

	protected void drawRandomCardFromOpponent(int playerToStealFrom) {

		int[] cards = this.players[playerToStealFrom].getCards();

		ArrayList<Integer> candidateCards = new ArrayList<Integer>();

		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j < cards[i]; j++) {
				candidateCards.add(i);
			}
		}

		int randomResourceNum = candidateCards.get((int) (Math.random()
				* (candidateCards.size() - 1) + 1));

		displayCardStolen(Resource.values()[randomResourceNum],
				playerToStealFrom);

		this.players[playerToStealFrom].adjustCards(randomResourceNum, -1);
		this.players[currentPlayer].adjustCards(randomResourceNum, 1);
		updateUserPanelCards();
	}

	protected void displayCardStolen(Resource resource, int playerToStealFrom) {
		String message = "Player " + (this.currentPlayer + 1)
				+ ", You have just stolen a " + resource.name()
				+ " card from player " + (playerToStealFrom + 1) + ".";

		JOptionPane.showMessageDialog(null, message);

	}

	private ArrayList<Hex> findRolledHexes(int rollNumber) {
		ArrayList<Hex> rolledHexes = new ArrayList<Hex>();
		for (Hex hex : this.hexArray) {
			if (hex.getRollNumber() == rollNumber) {
				rolledHexes.add(hex);
			}
		}
		return rolledHexes;
	}

	public void drawDevCard() {
		int[] cards = players[this.currentPlayer].getCards();
		if (cards[0] >= 1 && cards[2] >= 1 && cards[4] >= 1) {
			DevCard d = this.devCardDeck.poll();

			displayDrawnDevCard(d);

			if (d == DevCard.victory) {
				// add it immediately to their hand.
				this.players[this.currentPlayer].changeDevCardCount(d, 1);
			} else {
				// wait till the end of their turn to add to hand.

				// if they haven't drawn any dev cards yet this turn...
				if (this.purchasedDevCards == null) {
					this.purchasedDevCards = new ArrayList<DevCard>();
				}
				this.purchasedDevCards.add(d);
			}

			int[] delta = { -1, 0, -1, 0, -1 };
			this.players[this.currentPlayer].adjustCards(delta);
			updateUserPanelCards();
		}

	}

	protected void displayDrawnDevCard(DevCard d) {
		String messageA = "Player " + (this.currentPlayer + 1)
				+ ", You have just drawn a " + d.name() + " development card.";
		String messageB;

		if (d == DevCard.victory) {
			messageB = "  It has been immediately added to your hand.";
		} else {
			messageB = "  It will be added to your hand at the end of your turn.";
		}

		String message = messageA + messageB;

		JOptionPane.showMessageDialog(null, message);
	}

	public void playYearOfPlenty() {
		this.userPanel.disableUserPanel();

		players[this.currentPlayer].adjustCards(
				selectResourceForYearOfPlenty(true), 1);
		players[this.currentPlayer].adjustCards(
				selectResourceForYearOfPlenty(false), 1);

		this.userPanel.reEnableUserPanel();
		updateUserPanelCards();
	}

	public void playMonopolyDevCard() {
		this.userPanel.disableUserPanel();
		Resource resource = selectResourceForMonopoly();

		for (int i = 0; i < players.length; i++) {
			if (i != this.currentPlayer) {
				int cardCount = players[i].getCard(resource);
				if (cardCount > 0) {
					players[this.currentPlayer]
							.adjustCards(resource, cardCount);
					players[i].adjustCards(resource, -cardCount);
				}
			}
		}

		this.userPanel.reEnableUserPanel();
		updateUserPanelCards();
	}

	public void setBuildType(BuildType type) {
		this.currentBuildType = type;
	}

	public void endTurn() {
		if (purchasedDevCards != null) {
			for (DevCard d : purchasedDevCards) {
				this.players[this.currentPlayer].changeDevCardCount(d, 1);
			}
			purchasedDevCards = null;
		}
		hasPlayedNonVPDevCard = false;

		this.currentPlayer = (this.currentPlayer + 1) % this.numberOfPlayers;
		this.userPanel.setCurrentPlayer(this.currentPlayer);
		this.userPanel.setTurnPhase(TurnPhase.preroll);
		this.updateUserPanelCards();

		if (checkVictory() >= 0) {
			displayVictory();

			System.exit(0);
		}
	}

	protected void displayVictory() {
		// add one to this since player names start with 1 in GUI
		JOptionPane.showMessageDialog(null, "Congratulations! Player "
				+ checkVictory() + 1 + " has won the game!", "Game Over",
				JOptionPane.NO_OPTION);

	}

	public Color[] getPlayerColors() {
		return this.colorArray;
	}

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

	public Object getPlayerWithLongestRoad() {
		if (maxRoadLength <= 0) {
			return null;
		}
		return playerWithLongestRoad;
	}

	public int getVictoryPointsForPlayer(int playerNumber) {
		int points = 0;
		if (maxRoadLength >= 5 && playerWithLongestRoad == playerNumber) {
			points += 2;
		}
		points += structMgr
				.calculateStructureVictoyPointsForPlayer(playerNumber);

		// add points for knights
		if (players[playerNumber].getKnightsPlayed() >= 3
				&& playerWithLargestArmy == playerNumber) {
			points += 2;
		}

		points += players[playerNumber].getDevCardVPs();

		userPanel.updateVPLabel(playerNumber, points);

		return points;
	}

	public void processBuildStructureClick(int hexID,
			HexComponent.StructurePosition pos) {
		Player cp = players[this.currentPlayer];
		if (this.preGameMode != true) {
			if (this.currentBuildType == BuildType.settlement) {
				if (cp.getCards()[0] >= 1 && cp.getCards()[1] >= 1
						&& cp.getCards()[2] >= 1 && cp.getCards()[3] >= 1) {
					addBuilding(this.currentPlayer, hexID, pos);
				}
			} else {
				if (cp.getCards()[0] >= 2 && cp.getCards()[4] >= 3) {
					addBuilding(this.currentPlayer, hexID, pos);
				}
			}
		} else if (addBuilding(this.currentPlayer, hexID, pos)) {
			proceedWithBeginningPhase();
		}
	}

	private void proceedWithBeginningPhase() {
		if (this.startingTurnsQueue.isEmpty()) {
			this.preGameMode = false;
			this.currentBuildType = BuildType.none;
			this.userPanel.setUpNormalGame();
			this.updateUserPanelCards();
		} else {
			int playerIndex = this.startingTurnsQueue.poll();
			this.hasBuiltRoad = false;
			this.currentBuildType = BuildType.none;
			this.currentPlayer = playerIndex;
			this.userPanel.resetBeginningMode();
		}

	}

	public void processBuildRoadClick(int hexID, HexComponent.RoadPosition pos) {
		Player cp = players[this.currentPlayer];
		if (this.preGameMode != true) {
			if (this.roadBuild > 0) {
				if (addRoad(this.currentPlayer, hexID, pos)) {
					this.roadBuild--;
					this.players[this.currentPlayer].adjustCards(Resource.wood,
							1);
					this.players[this.currentPlayer].adjustCards(
							Resource.brick, 1);
					updateUserPanelCards();
				}
				if (this.roadBuild == 0) {
					this.userPanel.reEnableUserPanel();
				}
			} else if (cp.getCards()[1] >= 1 && cp.getCards()[3] >= 1) {
				addRoad(this.currentPlayer, hexID, pos);
			}

		} else {
			if (hasBuiltRoad) {
				// ERROR!!!
				this.userPanel.setBeginningBuildSettlement();
			} else {

				if (addRoad(this.currentPlayer, hexID, pos)) {
					this.hasBuiltRoad = true;
					this.userPanel.setBeginningBuildSettlement();
				}
			}
		}
	}

	// Returns the number of the winning player. If no player has won yet, it
	// returns -1.
	public int checkVictory() {
		for (int i = 0; i < numberOfPlayers; i++) {
			if (getVictoryPointsForPlayer(i) >= 10) {
				return i;
			}
		}
		return -1;
	}

	public boolean isBeginningOfGame() {
		return this.preGameMode;
	}

	public boolean hasBuiltRoad() {
		return this.hasBuiltRoad;
	}

	public int getRoadCountForPlayer(int playerIndex) {
		return this.roadMgr.getRoadCountForPlayer(playerIndex);
	}

	private void updateUserPanelCards() {
		this.userPanel.updateResourceCards(this.players[this.currentPlayer]
				.getCards());
	}

	public int[] getCardsForPlayer(int playerIndex) {
		return this.players[playerIndex].getCards();
	}

	protected Resource selectResourceForMonopoly() {
		String message = "Please select a resource to monopolize.";

		String title = "Monopoly";

		Resource[] options = { Resource.wheat, Resource.wood, Resource.sheep,
				Resource.ore, Resource.brick };

		int selected = -1;

		while (selected < 0) {
			selected = JOptionPane.showOptionDialog(null, message, title,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, -1);
		}

		return options[selected];
	}

	public Resource selectResourceForYearOfPlenty(boolean first) {
		String message = "Please select your ";
		if (first) {
			message += "first ";
		} else {
			message += "second ";
		}
		message += "resource for your year of plenty.";

		String title = "Year of Plenty";

		Resource[] options = { Resource.wheat, Resource.wood, Resource.sheep,
				Resource.ore, Resource.brick };
		int selected = -1;

		while (selected < 0) {
			selected = JOptionPane.showOptionDialog(null, message, title,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, -1);
		}

		return options[selected];

	}

	public int selectPlayerToStealFrom(Set<Integer> possiblePlayers) {

		String message = "Please select a player to steal from.";
		String title = "Steal";

		String[] options = new String[possiblePlayers.size()];

		int index = 0;
		for (Integer i : possiblePlayers) {
			options[index] = Integer.toString(i + 1);
			index++;
		}

		int selected = -1;

		while (selected < 0) {
			selected = JOptionPane.showOptionDialog(null, message, title,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, -1);
		}

		return selected;
	}

	public int selectPlayerToTradeWith() {
		String message = "Please select a player to trade with.";
		String title = "Trade";

		String[] options = new String[this.numberOfPlayers];

		for (int i = 0, j = 0; i < this.numberOfPlayers; i++) {
			if (i == this.currentPlayer) {

			} else {
				options[j] = Integer.toString(i + 1);
				j++;
			}
		}

		options[options.length - 1] = "Bank";
		int selection = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, -1);

		try {
			if (selection < 0) {
				return selection;
			}
			return Integer.parseInt(options[selection]) - 1;
		} catch (Exception e) {
			// return a special id representing the bank
			return -18;
		}

	}

	public void processTradeClick() {
		int rslt = selectPlayerToTradeWith();

		if (rslt != -1) {
			// a special id representing the bank
			if (rslt == -18) {
				createBankTradeFrame();
			} else {
				createPlayerTradeFrame(rslt);
			}
		}
	}

	private void createBankTradeFrame() {
		new BankTradeFrame(this, this.currentPlayer,
				this.getCardsForPlayer(this.currentPlayer));
	}

	protected void createPlayerTradeFrame(int rslt) {
		new PlayerTradeFrame(this, this.currentPlayer, rslt,
				this.players[this.currentPlayer].getCards(),
				this.players[rslt].getCards());
	}

	public boolean tradeWithBank(int[] rslts, int[] rslts2) {
		int[] currentCardCounts = this.getCardsForPlayer(this.currentPlayer);
		int bankCredits = 0;

		for (int i = 0; i < rslts.length; i++) {
			if (rslts[i] > currentCardCounts[i]) {
				return false;
			}

			int tradeRate;

			if (this.players[this.currentPlayer].hasPort(Resource.values()[i])) {
				tradeRate = 2;
			} else if (this.players[this.currentPlayer]
					.hasPort(Resource.desert)) {
				tradeRate = 3;
			} else {
				tradeRate = 4;
			}

			if (rslts[i] % tradeRate != 0) {
				return false;
			}

			bankCredits += rslts[i] / tradeRate;
		}

		int requestCount = 0;
		for (int i = 0; i < rslts2.length; i++) {
			requestCount += rslts2[i];
		}

		if (requestCount != bankCredits) {
			return false;
		}

		for (int i = 0; i < rslts.length; i++) {
			rslts[i] *= -1;
		}

		this.adjustCardsForPlayer(this.currentPlayer, rslts2);
		this.adjustCardsForPlayer(this.currentPlayer, rslts);

		return true;
	}

	public void adjustCardsForPlayer(int playerIndex, int[] delta) {
		this.players[playerIndex].adjustCards(delta);
		updateUserPanelCards();
	}

	public void setRobberLocation(int robberLoc) {
		if (this.robberLocation == robberLoc) {
			displayRobberErrorMessage();
			return;
		}
		this.robberLocation = robberLoc;
		board.moveRobber(robberLoc);
		this.currentBuildType = BuildType.none;
		this.userPanel.reEnableUserPanel();

		Set<Integer> playersToStealFrom = getPlayersToStealFrom(robberLoc);

		// TODO only let people move the robber to a hex where all players
		// touching it have more than 3 points

		if (playersToStealFrom.size() == 0) {
			// Do nothing.
		} else if (playersToStealFrom.size() == 1) {

			int playerToStealFrom = playersToStealFrom.iterator().next();
			drawRandomCardFromOpponent(playerToStealFrom);
		} else {
			int playerToStealFrom = selectPlayerToStealFrom(playersToStealFrom);
			drawRandomCardFromOpponent(playerToStealFrom);
		}
	}

	protected void displayRobberErrorMessage() {
		String message = "Player " + (this.currentPlayer + 1)
				+ ", you must select a different hex for the robber.";

		JOptionPane.showMessageDialog(null, message);
	}

	public Set<Integer> getPlayersToStealFrom(int robberLocation) {
		Set<Integer> playersToStealFrom = new HashSet<Integer>();
		int[] structIDs = this.hexMgr.getStructureIDsOnHex(robberLocation);

		for (int j = 0; j < structIDs.length; j++) {
			StructurePiece p = this.structMgr.getStructurePiece(structIDs[j]);

			if (p != null) {
				int playerIndex = p.getPlayerIndex();
				if (playerIndex != this.currentPlayer) {
					playersToStealFrom.add(playerIndex);
				}
			}
		}

		return playersToStealFrom;
	}

	public void playRoadBuilder() {

		this.userPanel.disableUserPanel();

		setBuildType(BuildType.road);
		int roadCount = this.roadMgr.getRoadCountForPlayer(this.currentPlayer);
		if (roadCount < 14) {
			this.roadBuild = 2;
		} else if (roadCount == 14) {
			this.roadBuild = 1;
		} else {
			this.userPanel.reEnableUserPanel();
		}
	}

	public void playKnight() {
		startMoveRobber();
		players[this.currentPlayer].incrementKnightsPlayed();

		if (players[this.currentPlayer].getKnightsPlayed() > maxArmySize) {
			maxArmySize = players[this.currentPlayer].getKnightsPlayed();
			playerWithLargestArmy = this.currentPlayer;
		}
	}

	public int[] getDevCardsForCurrentPlayer() {
		return this.players[this.currentPlayer].getDevCardCounts();
	}

	public void processPlayDevCardClick(DevCard devCard) {
		if (this.players[this.currentPlayer].getDevCard(devCard) > 0) {
			if (devCard == DevCard.victory) {
				this.players[this.currentPlayer]
						.changeDevCardCount(devCard, -1);
				this.players[this.currentPlayer].incrementDevVps();
			} else if (this.hasPlayedNonVPDevCard) {
				displayPlayDevCardError();
			} else {
				this.hasPlayedNonVPDevCard = true;
				this.players[this.currentPlayer]
						.changeDevCardCount(devCard, -1);
				switch (devCard) {
				case monopoly:
					playMonopolyDevCard();
					break;
				case roadBuilder:
					playRoadBuilder();
					break;
				case knight:
					playKnight();
					break;
				case yearOfPlenty:
					playYearOfPlenty();
					break;
				default:
					break;
				}
			}

		}

	}

	protected void displayPlayDevCardError() {
		String message = "Player "
				+ (this.currentPlayer + 1)
				+ ", you may only play one development card per turn unless the card played is a victory point card.";

		JOptionPane.showMessageDialog(null, message);
	}

}
