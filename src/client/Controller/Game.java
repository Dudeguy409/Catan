package client.Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.HexComponent.StructurePosition;
import client.GUI.UserPanel;
import client.Model.Player;
import client.Model.RoadPiece;

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

	private BoardRenderer board;
	private UserPanel userPanel;
	private TurnPhase currentTurnPhase = TurnPhase.build;
	private BuildType currentBuildType = BuildType.city;
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
<<<<<<< HEAD
	private int playerWithLongestRoad = -1;
=======
	private int playerWithLongestRoad;
	private boolean preGameMode = true;
>>>>>>> origin/master

	/**
	 * The number of hexes on the field.
	 */
	public static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static Game.Resource[] randomColorArray = new Game.Resource[boardSize];

	public Game(Color[] pColors, Resource[] hexResources, IDice dice,
			int startingPlayer) {

		this.randomColorArray = hexResources;
		this.dice = dice;

		generateStartingTurnsQueue();

		// This array contains all of the roll numbers in the order that they
		// are always supposed to appear. These are placed in a clockwise inward
		// spiral starting at the bottom hex.
		int[] rollNumberArray = { 5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4,
				5, 6, 3, 11 };

		// adjusts the rollNumberArray to include a -1 in the right spot to
		// represent a desert.
		int desertColorIndex = -1;
		for (int i = 0; i < hexResources.length; i++) {
			if (randomColorArray[i] == Resource.desert) {
				desertColorIndex = i;
				randomNumberArray[i] = -1;
			} else {
				if (desertColorIndex < 0) {
					randomNumberArray[i] = rollNumberArray[i];
				} else {
					randomNumberArray[i] = rollNumberArray[i - 1];
				}
			}
		}

		this.colorArray = pColors;
		this.numberOfPlayers = this.colorArray.length;
		this.players = new Player[this.numberOfPlayers];
		for (int i = 0; i < this.numberOfPlayers; i++) {
			this.players[i] = new Player();
		}
		this.roadMgr = new RoadManager(this.numberOfPlayers);
		this.hexMgr = new HexManager();
		this.structMgr = new StructureManager(this.numberOfPlayers);
	}

	private void generateStartingTurnsQueue() {
		this.startingTurnsQueue = null;

	}

	public void setUserPanel(UserPanel panel) {
		this.userPanel = panel;
		this.userPanel.setCurrentPlayer(this.currentPlayer);
	}

	public void setBoardRenderer(BoardRenderer board) {
		this.board = board;
		this.board.setBoard(randomColorArray, randomNumberArray);
		this.board.setBoard();
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

	public void addRoad(int playerIndex, int hexId,
			HexComponent.RoadPosition pos) {
		// TODO throw exceptions
		int roadId = this.hexMgr.getRoadId(hexId, pos);
		int currentRoadCount = this.roadMgr.getRoadCountForPlayer(playerIndex);
		if (currentRoadCount == 0) {
			this.roadMgr.addRoadPieceAtBeginning(playerIndex, roadId);
		} else {
			this.roadMgr.addRoadPiece(playerIndex, roadId);
		}
		if (this.roadMgr.getRoadCountForPlayer(playerIndex) > currentRoadCount) {
			this.board.addRoad(hexId, pos, this.colorArray[playerIndex],
					BuildType.road);
		}

		// TODO keep track of longest road
		if (roadMgr.findLongestRoadForPlayer(currentPlayer) > maxRoadLength) {
			maxRoadLength = roadMgr.findLongestRoadForPlayer(currentPlayer);
			playerWithLongestRoad = currentPlayer;
		}
	}

	public void addBuilding(int playerIndex, int hexId,
			HexComponent.StructurePosition pos) {
		// TODO do stuff
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

			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	public void roll() {
		int[] rolls = this.dice.rollDice();
		this.userPanel.setRolls(rolls);
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

	}

	public Color[] getPlayerColors() {
		return this.colorArray;
	}

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

	public boolean hasAdjacentRoad(int structureId) {

		return false;
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

	public Object getPlayerWithLongestRoad() {
		if (maxRoadLength <= 0) {
			return null;
		}
		return playerWithLongestRoad;
	}

	public void processStartGameClick() {
		// TODO Auto-generated method stub

	}

	public int getVictoryPointsForPlayer(int playerNumber) {
		int points = 0;
		if (playerWithLongestRoad == playerNumber) {
			points++;
		}
		points += structMgr
				.calculateStructureVictoyPointsForPlayer(playerNumber);
		return points;
	}

	public void processBuildSettlementClick(int hexID,
			HexComponent.StructurePosition pos) {
		Player cp = players[this.currentPlayer];
		if (this.preGameMode != true) {
			if (cp.getCards()[0] >= 1 && cp.getCards()[1] >= 1
					&& cp.getCards()[2] >= 1 && cp.getCards()[3] >= 1) {
				int[] delta = { -1, -1, 0, 0, 0, 0, 0 };
				cp.adjustCards(delta);
				addBuilding(this.currentPlayer, hexID, pos);
			}
		} else 
			addBuilding(this.currentPlayer, hexID, pos);
	}

	public void processBuildCityClick(int hexID,
			HexComponent.StructurePosition pos) {
		Player cp = players[this.currentPlayer];
		if (this.preGameMode != true) {
			if (cp.getCards()[0] >= 2 && cp.getCards()[4] >= 3) {
				int[] delta = { -2, 0, 0, 0, -3, 0, 0 };
				cp.adjustCards(delta);
				addBuilding(this.currentPlayer, hexID, pos);
			}
		}
	}

	public void processBuildRoadClick(int hexID,
			HexComponent.RoadPosition pos) {
		Player cp = players[this.currentPlayer];
		if (this.preGameMode != true) {
			if (cp.getCards()[1] >= 1 && cp.getCards()[3] >= 1) {
				int[] delta = { 0, -1, 0, -1, 0, 0, 0 };
				cp.adjustCards(delta);
				addRoad(this.currentPlayer, hexID, pos);
			}
		} else
			addRoad(this.currentPlayer, hexID, pos);
	}
}
