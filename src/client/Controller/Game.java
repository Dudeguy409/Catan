package client.Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import client.Controller.Game.BuildType;
import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.UserPanel;
import client.Model.Player;

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

	/**
	 * The number of hexes on the field.
	 */
	public static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static Game.Resource[] randomColorArray = new Game.Resource[boardSize];

	public Game() {

		// This array contains all of the roll numbers in the order that they
		// are always supposed to appear. These are placed in a clockwise inward
		// spiral starting at the bottom hex.
		int[] rollNumberArray = { 5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4,
				5, 6, 3, 11 };

		// makes an array of the different resources for the hexes.
		ArrayList<Resource> colorNumberArray = new ArrayList<Game.Resource>();
		colorNumberArray.add(Resource.desert);
		colorNumberArray.add(Resource.wood);
		colorNumberArray.add(Resource.wood);
		colorNumberArray.add(Resource.wood);
		colorNumberArray.add(Resource.wood);
		colorNumberArray.add(Resource.brick);
		colorNumberArray.add(Resource.brick);
		colorNumberArray.add(Resource.brick);
		colorNumberArray.add(Resource.ore);
		colorNumberArray.add(Resource.ore);
		colorNumberArray.add(Resource.ore);
		colorNumberArray.add(Resource.wheat);
		colorNumberArray.add(Resource.wheat);
		colorNumberArray.add(Resource.wheat);
		colorNumberArray.add(Resource.wheat);
		colorNumberArray.add(Resource.sheep);
		colorNumberArray.add(Resource.sheep);
		colorNumberArray.add(Resource.sheep);
		colorNumberArray.add(Resource.sheep);

		// randomly chooses one element each from the fixed arrays above, copies
		// it to a new array, and resets the element copied to zero. If the
		// element has already been drawn, it will repeat.

		Random generator = new Random();
		int desertColorIndex = -1;
		for (int i = 0; i < boardSize; i++) {
			int readValue = generator.nextInt(colorNumberArray.size());
			randomColorArray[i] = colorNumberArray.get(readValue);
			colorNumberArray.remove(readValue);

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

		configureNumberOfPlayers();
		configurePlayerColors();
		this.roadMgr = new RoadManager(this.numberOfPlayers);
		this.hexMgr = new HexManager();

	}

	private void configureNumberOfPlayers() {
		// TODO exitOnClose with warning window
		Object[] options1 = { "2", "3", "4" };
		this.numberOfPlayers = JOptionPane.showOptionDialog(null,
				"How many players are there?", "Setup",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options1, options1[1]) + 2;
	}

	private void configurePlayerColors() {
		// TODO exitOnClose with warningWindow
		Object[] options2 = { "Red", "Blue", "White", "Magenta", "Orange",
				"Cyan" };
		this.players = new Player[this.numberOfPlayers];
		this.colorArray = new Color[this.numberOfPlayers];
		for (int i = 0; i < this.numberOfPlayers; i++) {
			boolean diffColor = true;
			while (diffColor) {
				int color = JOptionPane.showOptionDialog(null,
						"What color would you like, Player " + (i + 1) + "?",
						"Setup", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options2, null);
				diffColor = false;
				switch (color) {
				case 0:
					this.colorArray[i] = Color.red;
					break;
				case 1:
					this.colorArray[i] = Color.blue;
					break;
				case 2:
					this.colorArray[i] = Color.white;
					break;
				case 3:
					this.colorArray[i] = Color.magenta;
					break;
				case 4:
					this.colorArray[i] = Color.orange;
					break;
				case 5:
					this.colorArray[i] = Color.cyan;
					break;
				case 6:
					this.colorArray[i] = Color.black;
					break;
				case 7:
					this.colorArray[i] = Color.red;
					break;
				}
				for (int j = 0; j < i; j++) {
					if (this.colorArray[i] == this.colorArray[j])
						diffColor = true;
					// verifies that the user selects a color that hasn't
					// already been chosen.
				}

			}

			this.players[i] = new Player();
		}

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

	public BuildType getBuildType() {
		return this.currentBuildType;
	}

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void addRoad(int playerIndex, int hexId,
			HexComponent.RoadPosition pos) {
		// TODO do stuff
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
	}

	public void addBuilding(int playerIndex, int hexId,
			HexComponent.StructurePosition pos) {
		// TODO do stuff
		// TODO throw exceptions
		if (this.currentBuildType == BuildType.settlement) {
			this.board.addBuilding(hexId, pos, this.colorArray[playerIndex],
					BuildType.settlement);
		} else {
			this.board.addBuilding(hexId, pos, this.colorArray[playerIndex],
					BuildType.city);
		}
	}

	public void roll() {
		int[] rolls = new Dice().rollDice();
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
}
