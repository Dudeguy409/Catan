import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;
import javax.swing.JFrame;

/**
 * creates the frame, the board, and the user panel.
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */

public class Main {
	private static final Dimension SIZE = new Dimension(1200, 900);
	/**
	 * The number of hexes on the field.
	 */
	protected static final int boardSize = 19;
	private static int[] randomNumberArray = new int[boardSize];
	private static int[] randomColorArray = new int[boardSize];

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            Command-line arguments, ignored here
	 */
	public static void main(String[] args) {

		// makes the array of roll numbers for the hexes.
		int[] rollNumberArray = new int[boardSize];
		rollNumberArray[0] = 2;
		rollNumberArray[18] = 12;
		rollNumberArray[1] = 3;
		rollNumberArray[2] = 3;
		rollNumberArray[3] = 4;
		rollNumberArray[4] = 4;
		rollNumberArray[5] = 5;
		rollNumberArray[6] = 5;
		rollNumberArray[7] = 6;
		rollNumberArray[8] = 6;
		rollNumberArray[9] = 8;
		rollNumberArray[10] = 8;
		rollNumberArray[11] = 9;
		rollNumberArray[12] = 9;
		rollNumberArray[13] = 10;
		rollNumberArray[14] = 10;
		rollNumberArray[15] = 11;
		rollNumberArray[16] = 11;
		rollNumberArray[17] = -1;

		// mames an array of the different resources for the hexes.
		int[] colorNumberArray = new int[boardSize];
		colorNumberArray[0] = 1;
		colorNumberArray[18] = 6;
		colorNumberArray[1] = 2;
		colorNumberArray[2] = 2;
		colorNumberArray[3] = 2;
		colorNumberArray[4] = 2;
		colorNumberArray[5] = 3;
		colorNumberArray[6] = 3;
		colorNumberArray[7] = 3;
		colorNumberArray[8] = 3;
		colorNumberArray[9] = 4;
		colorNumberArray[10] = 4;
		colorNumberArray[11] = 4;
		colorNumberArray[12] = 4;
		colorNumberArray[13] = 5;
		colorNumberArray[14] = 5;
		colorNumberArray[15] = 5;
		colorNumberArray[16] = 6;
		colorNumberArray[17] = 6;

		// randomly chooses one element each from the fixed arrays above, copies
		// it to a new array, and resets the element copied to zero. If the
		// element ahs already been drawn, it will repeat.

		Random generator = new Random();
		int desertRollIndex = -1;
		int desertColorIndex = -1;
		for (int i = 0; i < boardSize; i++) {
			while (randomColorArray[i] == 0) {
				int readValue = generator.nextInt(boardSize);
				randomColorArray[i] = colorNumberArray[readValue];
				if (randomColorArray[i] == 1)
					desertColorIndex = i;
				colorNumberArray[readValue] = 0;
			}
			while (randomNumberArray[i] == 0) {
				int readValue = generator.nextInt(boardSize);
				randomNumberArray[i] = rollNumberArray[readValue];
				if (randomNumberArray[i] == -1)
					desertRollIndex = i;
				rollNumberArray[readValue] = 0;
			}
		}
		//makes sure that the desert doesn't have a roll number.
		randomNumberArray[desertRollIndex] = randomNumberArray[desertColorIndex];
		randomNumberArray[desertColorIndex] = -1;

		JFrame frame = new JFrame();
		frame.setSize(SIZE);
		frame.setTitle("Settlers of Catan");
		frame.setLayout(new FlowLayout());
		UserPanel myPanel = new UserPanel();
		frame.add(myPanel, FlowLayout.LEFT);
		BoardRenderer myBoard = new BoardRenderer(randomColorArray,
				randomNumberArray, myPanel);
		myBoard.setBoard();
		frame.add(myBoard, FlowLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}