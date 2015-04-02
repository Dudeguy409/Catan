package client.Controller;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JFrame;

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
		JFrame frame = new JFrame();
		frame.setSize(SIZE);
		frame.setTitle("Settlers of Catan");
		frame.setLayout(new FlowLayout());
		Game game = new Game();
		UserPanel myPanel = new UserPanel(game);
		frame.add(myPanel, FlowLayout.LEFT);
		BoardRenderer myBoard = new BoardRenderer(game);
		frame.add(myBoard, FlowLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}