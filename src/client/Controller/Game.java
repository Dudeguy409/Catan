package client.Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JOptionPane;

import client.GUI.HexComponent;
import client.GUI.HexComponent.RoadPosition;
import client.GUI.HexComponent.StructurePosition;
import client.GUI.IBoardRenderer;
import client.GUI.IUserPanel;
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

	public static enum Resource {
		wood(0), brick(1), sheep(2), ore(3), wheat(4), desert(-1);

		private int numVal;

		Resource(int numVal) {
			this.numVal = numVal;
		}

		public int getNumVal() {
			return numVal;
		}
	}

	public static enum TurnPhase {
		preroll, pretrade, trade, prebuild, build, end
	}

	// development cards aren't built, they are drawn, so they aren't included
	// in this.
	public static enum BuildType {
		road, settlement, city, none
	}

	private IBoardRenderer board;
	private IUserPanel userPanel;
	private TurnPhase currentTurnPhase = TurnPhase.build;
	private BuildType currentBuildType = BuildType.none;
	private int currentPlayer;
	private Player[] players;
	private Color[] colorArray;
	private int numberOfPlayers;
	private RoadManager roadMgr;
	private HexManager hexMgr;
	private StructureManager structMgr;
	private IDice dice;
	private Queue<Integer> startingTurnsQueue;
	private int maxRoadLength = 0;
	private int playerWithLongestRoad = -1;
	private boolean preGameMode = true;
	private boolean hasBuiltRoad = false;
	private ArrayList<Hex> hexArray;

	/**
	 * The number of hexes on the field.
	 */
	public static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static Game.Resource[] randomColorArray = new Game.Resource[boardSize];

	public Game(Color[] pColors, Resource[] hexResources, IDice dice,
			int startingPlayer, IUserPanel userPanel, IBoardRenderer board, int[] randomNumberArray ) {

		this.currentPlayer = startingPlayer;
		this.randomColorArray = hexResources;
		this.dice = dice;
		this.colorArray = pColors;
		this.numberOfPlayers = pColors.length;

		generateStartingTurnsQueue();

		

		this.hexArray = new ArrayList<Hex>();
		for (int j = 0; j < boardSize; j++) {
			hexArray.add(new Hex(randomNumberArray[j], randomColorArray[j], j));
		}

		this.players = new Player[this.numberOfPlayers];
		for (int i = 0; i < this.numberOfPlayers; i++) {
			this.players[i] = new Player();
		}
		this.roadMgr = new RoadManager(this.numberOfPlayers);
		this.hexMgr = new HexManager();
		this.structMgr = new StructureManager(this.numberOfPlayers);
		this.userPanel= userPanel;
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

	public boolean addRoad(int playerIndex, int hexId,
			HexComponent.RoadPosition pos) {
		// TODO throw exceptions
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
		}

		// TODO keep track of longest road
		if (roadMgr.findLongestRoadForPlayer(playerIndex) > maxRoadLength) {
			maxRoadLength = roadMgr.findLongestRoadForPlayer(playerIndex);
			playerWithLongestRoad = playerIndex;
		}
		return roadAdded;
	}

	private boolean isValidBeginnerRoad(int hexId, RoadPosition pos) {
		HexComponent.StructurePosition[] structPos = getAdjacentStructurePositionsForRoad(pos);
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
		//System.out.println("current Player: "+(playerIndex+1));
		// TODO throw exceptions

		// Check for adjacent road not returned by
		// getAdjacentRoadPositionsForStructure

		int structureId = this.hexMgr.getStructureId(hexId, pos);

		boolean isAdjacent = false;

		HexComponent.RoadPosition[] adjRoads = this
				.getAdjacentRoadPositionsForStructure(pos);

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
				} else {
					this.structMgr.updateStructure(playerIndex, structureId);
					this.board.addBuilding(hexId, pos,
							this.colorArray[playerIndex], BuildType.city);
				}

				return true;

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return false;
	}

	public void roll() {
		int[] rolls = this.dice.rollDice();
		this.userPanel.setRolls(rolls);

		int roll = rolls[0];
		ArrayList<Hex> rolledHexes = findRolledHexes(roll);

		for (int i = 0; i < rolledHexes.size(); i++) {
			Hex hex = rolledHexes.get(i);
			int hexId = hex.getHexID();
			Resource type = hex.getResource();

			int[] structPositions = {
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.east),
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.northeast),
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.northwest),
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.southeast),
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.southwest),
					this.hexMgr.getStructureId(hexId,
							HexComponent.StructurePosition.west) };

			for (int j = 0; j < structPositions.length; j++) {

				StructurePiece p = this.structMgr
						.getStructurePiece(structPositions[j]);

				if (p != null) {
//					System.out.println("Structure: player "
//							+ (1 + p.getPlayerIndex()) + ", buildType: "
//							+ p.getBuildType() + "resource: "
//							+ hex.getResource() + ", structure id: "
//							+ p.getStructureId() + ", hex id: "
//							+ hex.getHexID() + ", hex roll number: "
//							+ hex.getRollNumber());
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
		}

		updateUserPanelCards();

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
		// TODO Auto-generated method stub

	}

	public void setBuildType(BuildType type) {
		this.currentBuildType = type;
	}

	public void endTurn() {
		this.currentPlayer = (this.currentPlayer + 1) % this.numberOfPlayers;
		this.userPanel.setCurrentPlayer(this.currentPlayer);
		this.userPanel.setTurnPhase(TurnPhase.preroll);
		this.updateUserPanelCards();

		if (checkVictory() >= 0) {
			JOptionPane.showMessageDialog(null, "Congratulations! Player "
					+ checkVictory() + 1 + " has won the game!", "Game Over",
					JOptionPane.NO_OPTION);
			// add one to this since player names start with 1 in GUI

			System.exit(0);
		}
	}

	public Color[] getPlayerColors() {
		return this.colorArray;
	}

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

	public HexComponent.RoadPosition[] getAdjacentRoadPositionsForStructure(
			StructurePosition pos) {
		switch (pos) {
		case west:
			HexComponent.RoadPosition[] arrayToReturn = {
					HexComponent.RoadPosition.northwest,
					HexComponent.RoadPosition.southwest };
			return arrayToReturn;
		case northwest:
			HexComponent.RoadPosition[] arrayToReturn2 = {
					HexComponent.RoadPosition.northwest,
					HexComponent.RoadPosition.north };
			return arrayToReturn2;
		case northeast:
			HexComponent.RoadPosition[] arrayToReturn3 = {
					HexComponent.RoadPosition.north,
					HexComponent.RoadPosition.northeast };
			return arrayToReturn3;
		case east:
			HexComponent.RoadPosition[] arrayToReturn4 = {
					HexComponent.RoadPosition.northeast,
					HexComponent.RoadPosition.southeast };
			return arrayToReturn4;
		case southeast:
			HexComponent.RoadPosition[] arrayToReturn5 = {
					HexComponent.RoadPosition.south,
					HexComponent.RoadPosition.southeast };
			return arrayToReturn5;
		case southwest:
			HexComponent.RoadPosition[] arrayToReturn6 = {
					HexComponent.RoadPosition.south,
					HexComponent.RoadPosition.southwest };
			return arrayToReturn6;
		default:
			return null;
		}

	}

	private StructurePosition[] getAdjacentStructurePositionsForRoad(
			RoadPosition pos) {
		switch (pos) {
		case north:
			HexComponent.StructurePosition[] arrayToReturn = {
					HexComponent.StructurePosition.northwest,
					HexComponent.StructurePosition.northeast };
			return arrayToReturn;
		case northwest:
			HexComponent.StructurePosition[] arrayToReturn2 = {
					HexComponent.StructurePosition.northwest,
					HexComponent.StructurePosition.west };
			return arrayToReturn2;
		case northeast:
			HexComponent.StructurePosition[] arrayToReturn3 = {
					HexComponent.StructurePosition.east,
					HexComponent.StructurePosition.northeast };
			return arrayToReturn3;
		case south:
			HexComponent.StructurePosition[] arrayToReturn4 = {
					HexComponent.StructurePosition.southwest,
					HexComponent.StructurePosition.southeast };
			return arrayToReturn4;
		case southeast:
			HexComponent.StructurePosition[] arrayToReturn5 = {
					HexComponent.StructurePosition.east,
					HexComponent.StructurePosition.southeast };
			return arrayToReturn5;
		case southwest:
			HexComponent.StructurePosition[] arrayToReturn6 = {
					HexComponent.StructurePosition.west,
					HexComponent.StructurePosition.southwest };
			return arrayToReturn6;
		default:
			return null;
		}

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
			points+=2;
		}
		points += structMgr
				.calculateStructureVictoyPointsForPlayer(playerNumber);
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
					int[] delta = { -1, -1, -1, -1, 0, 0, 0 };
					cp.adjustCards(delta);
					addBuilding(this.currentPlayer, hexID, pos);
				}
			} else {
				if (cp.getCards()[0] >= 2 && cp.getCards()[4] >= 3) {
					int[] delta = { -2, 0, 0, 0, -3, 0, 0 };
					cp.adjustCards(delta);
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
			if (cp.getCards()[1] >= 1 && cp.getCards()[3] >= 1) {
				int[] delta = { 0, -1, 0, -1, 0, 0, 0 };
				cp.adjustCards(delta);
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

}
