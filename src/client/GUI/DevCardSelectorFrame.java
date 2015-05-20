package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Controller.Game;

public class DevCardSelectorFrame extends JFrame {
	private static final Dimension DIMENSION = new Dimension(600, 300);
	private Font mainFont = new Font(null, Font.PLAIN, 24);
	private JButton[] playButtons = new JButton[5];
	private ActionListener playButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			play(e);

		}
	};
	private Game game;
	private int[] devCardCounts;

	public static final String[] labels = { "<html>Year of Plenty</html>",
			"<html>Victory</html>", "<html>Monopoly</html>",
			"<html>Knight</html>", "<html>Road Builder</html>" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549121935124223208L;

	public DevCardSelectorFrame(Game game, int[] devCardCounts) {
		super();
		this.game = game;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.devCardCounts = devCardCounts;

		this.setSize(DIMENSION);
		this.setLocation(700, 300);
		this.setResizable(false);

		this.setLayout(new GridLayout(1, 5));

		for (int i = 0; i < this.playButtons.length; i++) {
			JPanel tempCardPanel = new JPanel();
			tempCardPanel.setLayout(new BorderLayout());
			JLabel tempLabel = new JLabel(labels[i]);
			tempLabel.setFont(mainFont);
			tempCardPanel.add(tempLabel, BorderLayout.NORTH);
			JLabel countLabel = new JLabel(Integer.toString(devCardCounts[i]));
			countLabel.setFont(mainFont);
			tempCardPanel.add(countLabel, BorderLayout.CENTER);
			playButtons[i] = new JButton("Play");
			playButtons[i].setFont(mainFont);
			playButtons[i].addActionListener(this.playButtonListener);
			if (this.devCardCounts[i] <= 0) {
				playButtons[i].setEnabled(false);
			}
			tempCardPanel.add(playButtons[i], BorderLayout.SOUTH);
			this.add(tempCardPanel);
		}

		this.setVisible(true);
	}

	protected void play(ActionEvent e) {
		int index = -1;
		for (int i = 0; i < this.playButtons.length; i++) {
			if (e.getSource() == this.playButtons[i]) {
				index = i;
				break;
			}
		}

		switch (index) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			this.game.playKnight();
			break;
		case 4:
			this.game.playRoadBuilder();
			break;
		default:
			break;
		}

		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object

	}
}
