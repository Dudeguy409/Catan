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

	private BoardRenderer board;
	private UserPanel userPanel;

	/**
	 * The number of hexes on the field.
	 */
	protected static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static Game.Resource[] randomColorArray = new Game.Resource[boardSize];

	public Game() {

		int[] rollNumberArray = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10,
				10, 11, 11, 12 };
		
//		int[] rollNumberArray = { 5, 2, 3, 4, 5, 6, 7, 8,  9, 10,
//				 11,  12, 13, 14, 15, 16, 17, 18 };

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

			// if(i<boardSize-1){
			// if(desertColorIndex<0){
			// randomNumberArray[i]= rollNumberArray[i];
			// }else{
			// randomNumberArray[i+1]= rollNumberArray[i];
			// }
			// }

			// while (randomNumberArray[i] == 0) {
			// readValue = generator.nextInt(boardSize);
			// randomNumberArray[i] = rollNumberArray[readValue];
			// if (randomNumberArray[i] == -1)
			// desertRollIndex = i;
			// rollNumberArray[readValue] = 0;
			// }
		}
		// makes sure that the desert doesn't have a roll number.
		// randomNumberArray[desertRollIndex] =
		// randomNumberArray[desertColorIndex];
		// randomNumberArray[desertColorIndex] = -1;

	}

	public void setUserPanel(UserPanel panel) {
		this.userPanel = panel;
	}

	public void setBoardRenderer(BoardRenderer board) {
		this.board = board;
		this.board.setBoard(randomColorArray, randomNumberArray);
		this.board.setBoard();
	}

	public void processClick(int determineSettlePosition,
			int determineRoadPosition) {
		// TODO Auto-generated method stub

	}

}
