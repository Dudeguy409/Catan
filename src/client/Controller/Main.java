package client.Controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.Controller.Game.Resource;
import client.GUI.BoardRenderer;
import client.GUI.UserPanel;

/**
 * creates the frame, the board, and the user panel.
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */

public class Main {

	private static final Dimension SIZE = new Dimension(1200, 900);

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            Command-line arguments, ignored here
	 */
	public static void main(String[] args) {
		int playerCount = configureNumberOfPlayers();
		if (playerCount < 2) {
			System.exit(0);
		}

		Color[] colors = configurePlayerColors(playerCount);

		JFrame frame = new JFrame();
		frame.setSize(SIZE);
		frame.setTitle("Settlers of Catan");
		frame.setLayout(new FlowLayout());
		Game.Resource[] hexResources = HexResourceTypeGenerator
				.getHexColors(new Random().nextLong());
		int[] randomNumberArray = configureRandomNumberArray(hexResources);
		BoardRenderer myBoard = new BoardRenderer(hexResources,randomNumberArray);
		UserPanel myPanel = new UserPanel();

		

		new Game(colors, hexResources, new Dice(),
				new Random().nextInt(colors.length), myPanel, myBoard,
				randomNumberArray);

		frame.add(myPanel, FlowLayout.LEFT);

		frame.add(myBoard, FlowLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static Color[] configurePlayerColors(int numberOfPlayers) {
		Object[] options2 = { "Red", "Blue", "White", "Magenta", "Orange",
				"Cyan" };
		Color[] colorArray = new Color[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			boolean diffColor = false;
			while (!diffColor) {
				int color = JOptionPane.showOptionDialog(null,
						"What color would you like, Player " + (i + 1) + "?",
						"Setup", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options2, null);
				diffColor = true;
				switch (color) {
				case 0:
					colorArray[i] = Color.red;
					break;
				case 1:
					colorArray[i] = Color.blue;
					break;
				case 2:
					colorArray[i] = Color.white;
					break;
				case 3:
					colorArray[i] = Color.magenta;
					break;
				case 4:
					colorArray[i] = Color.orange;
					break;
				case 5:
					colorArray[i] = Color.cyan;
					break;
				case 6:
					colorArray[i] = Color.black;
					break;
				case 7:
					colorArray[i] = Color.red;
					break;
				default:
					int rslt = JOptionPane
							.showConfirmDialog(
									null,
									"You have selected to close the color menu.  This will quit the game.  Do you want to quit?",
									"Exit Program?",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE, null);
					System.out.println(rslt);
					if (rslt != 2) {
						System.exit(0);
					} else {
						diffColor = false;
					}
				}

				// verifies that the user selects a color that hasn't
				// already been chosen.
				for (int j = 0; j < i; j++) {
					if (colorArray[i] == colorArray[j]) {
						diffColor = false;
						JOptionPane
								.showConfirmDialog(
										null,
										"This color has already been chosen by another player.  Please choose a different one.");
						break;
					}

				}

			}
		}
		return colorArray;
	}

	private static int configureNumberOfPlayers() {
		Object[] options1 = { "2", "3", "4" };
		return JOptionPane.showOptionDialog(null,
				"How many players are there?", "Setup",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options1, -1) + 2;
	}

	public static int[] configureRandomNumberArray(Resource[] hexResources) {
		// This array contains all of the roll numbers in the order that they
		// are always supposed to appear. These are placed in a clockwise inward
		// spiral starting at the bottom hex.
		int[] rollNumberArray = { 5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4,
				5, 6, 3, 11 };
		int[] randomNumberArray = new int[hexResources.length];

		// adjusts the rollNumberArray to include a -1 in the right spot to
		// represent a desert.
		int desertColorIndex = -1;
		for (int i = 0; i < hexResources.length; i++) {
			if (hexResources[i] == Resource.desert) {
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

		return randomNumberArray;
	}

}