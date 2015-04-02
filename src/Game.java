import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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

	/**
	 * The number of hexes on the field.
	 */
	protected static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static Game.Resource[] randomColorArray = new Game.Resource[boardSize];

	public Game() {

		// int[] rollNumberArray = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10,
		// 10, 11, 11, 12 };

		// int[] rollNumberArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
		// 11, 12, 13, 14, 15, 16, 17, 18 };

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
	}

	public void setUserPanel(UserPanel panel) {
		this.userPanel = panel;
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

	public void addRoad(int currentPlayer2, int hexId,
			HexComponent.RoadPosition pos) {
		// TODO do stuff
		// TODO throw exceptions
		this.board.addRoad(hexId, pos, Color.blue, BuildType.road);
	}

	public void addBuilding(int currentPlayer2, int hexId,
			HexComponent.StructurePosition pos) {
		// TODO do stuff
		// TODO throw exceptions
		if (this.currentBuildType == BuildType.settlement) {
			this.board
					.addBuilding(hexId, pos, Color.blue, BuildType.settlement);
		} else {
			this.board.addBuilding(hexId, pos, Color.blue, BuildType.city);
		}
	}
}
