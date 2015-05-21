package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Controller.Game;
import client.Controller.Game.DevCard;

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
	private UserPanel userPanel;

	public static final String[] labels = { "<html>Year of Plenty</html>",
			"<html>Monopoly</html>", "<html>Knight</html>",
			"<html>Victory</html>", "<html>Road Builder</html>" };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549121935124223208L;

	public DevCardSelectorFrame(Game game, int[] devCardCounts,
			UserPanel userPanel) {
		super();
		this.game = game;
		this.userPanel = userPanel;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.devCardCounts = devCardCounts;

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				handleCloseEvent();

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

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

		handleCloseEvent();
		
		this.game.processPlayDevCardClick(DevCard.values()[index]);

		

	}

	protected void handleCloseEvent() {
		this.userPanel.reEnableUserPanel();
		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object
	}
}
