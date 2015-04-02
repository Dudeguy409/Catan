package client.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Controller.Dice;
import client.Controller.Game;
import client.Model.Player;

/**
 * This class creates the user interface to complement the board. It deals with
 * more of the 'behind the scenes' mechanics of the game as opposed to the
 * visual board.
 * 
 * @author Andrew Davidson. Created May 8, 2010.
 */
public class UserPanel extends JPanel {
	// TODO refactor into separate classes, put controller into game class,
	// general cleanup
	private Color[] colorArray;
	private JButton cardButton;
	private JLabel[] playerLabel;
	private boolean cardsShowed = false;
	private JPanel cardPanel;
	private int currentPlayer = 0;
	private JButton rollButton;
	private DiceRenderer dice;
	private JButton endButton;
	private JButton buildButton;
	private int numberOfPlayers;
	private JPanel buildPanel;
	private int buildType = 0;
	private Player[] player;
	private JLabel[] vPLabel;
	private ReferenceComponent referenceComponent;
	private Game game;

	/**
	 * creates the user panel to go alongside the board.
	 * 
	 */
	public UserPanel(Game game) {
		this.setPreferredSize(new Dimension(300, 800));
		this.setBackground(Color.red);
		this.game = game;
		this.game.setUserPanel(this);

		configureNumberOfPlayers();
		configurePlayerColors();

		addCurrentPlayerLabel();
		addPlayerStatsPanel();

		// creates a panel that shows the player's current number of cards. This
		// is a non-working preview.
		this.cardPanel = new JPanel();
		this.cardPanel.setPreferredSize(new Dimension(300, 290));
		this.cardPanel.setBackground(Color.red);
		JPanel[] resourcePanel = new JPanel[6];
		String[] labelName = { "wheat", "wood", "wool", "ore", "brick",
				"soldiers" };
		JLabel[] nameLabel = new JLabel[6];
		JLabel[] quantityLabel = new JLabel[6];
		Color[] resourceColor = { Color.yellow, new Color(0, 100, 0),
				Color.green, Color.gray, new Color(200, 70, 0), Color.white };
		for (int i = 0; i < 6; i++) {
			nameLabel[i] = new JLabel(labelName[i]);
			nameLabel[i].setFont(new Font("Times New Roman", 2, 15));
			resourcePanel[i] = new JPanel();
			resourcePanel[i].setPreferredSize(new Dimension(60, 80));
			resourcePanel[i].setBackground(resourceColor[i]);
			resourcePanel[i].add(nameLabel[i]);
			quantityLabel[i] = new JLabel("  X  " + 21);
			quantityLabel[i].setPreferredSize(new Dimension(80, 80));
			quantityLabel[i].setFont(new Font("Times New Roman", 1, 20));
			this.cardPanel.add(resourcePanel[i]);
			this.cardPanel.add(quantityLabel[i]);
		}
		JLabel totalLabel = new JLabel("Total Cards:  " + 9);
		totalLabel.setFont(new Font("Times New Roman", 2, 25));
		this.cardPanel.add(totalLabel);
		this.cardPanel.setVisible(false);
		this.add(this.cardPanel);

		// creates a button which allows the user to hide their cards. It
		// automatically defaults to not being shown.
		this.cardButton = new JButton("vv Show Cards vv");
		this.cardButton.setPreferredSize(new Dimension(300, 40));
		this.cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleCardPanel();
			}
		});
		this.add(this.cardButton);

		// this section creates the panel for the player's actions/choices. You
		// must roll. Afterwards, you can build or end your turn.
		// TODO don't allow people the option to build or end their turn before
		// they roll. Disable build button after they already click it.
		// TODO add trade button.
		JPanel turnPanel = new JPanel();
		turnPanel.setPreferredSize(new Dimension(300, 30));
		turnPanel.setBackground(Color.red);
		this.rollButton = new JButton("Roll");
		this.rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				roll();
			}
		});
		this.buildButton = new JButton("Build");
		this.buildButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserPanel.this.buildPanel.setVisible(true);
				// the dice are removed to make room for the build/buy options.
				UserPanel.this.dice.setVisible(false);
			}
		});
		this.endButton = new JButton("End Turn");
		this.endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				endTurn();

			}
		});
		turnPanel.add(this.rollButton);
		turnPanel.add(this.buildButton);
		turnPanel.add(this.endButton);
		this.add(turnPanel);

		// TODO only highlight to build when they have enough cards to build
		// something. Only show the buttons of things that they can build. when
		// they click one, turn them all grey.
		// creates a panel for users to select what they want to build.
		this.buildPanel = new JPanel();
		this.buildPanel.setPreferredSize(new Dimension(300, 70));
		this.buildPanel.setBackground(Color.red);
		JButton settlementButton = new JButton("Settlement");
		settlementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserPanel.this.buildType = 1;
			}
		});
		JButton cityButton = new JButton("City ");
		cityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserPanel.this.buildType = 2;
			}
		});
		JButton roadButton = new JButton("Road");
		roadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserPanel.this.buildType = 3;

			}
		});
		JButton devButton = new JButton("Development Card");
		devButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserPanel.this.buildType = 0;
				UserPanel.drawDevCard();
			}
		});

		this.buildPanel.add(settlementButton);
		this.buildPanel.add(cityButton);
		this.buildPanel.add(roadButton);
		this.buildPanel.add(devButton);
		this.buildPanel.setVisible(false);

		this.add(this.buildPanel);

		this.dice = new DiceRenderer();
		this.add(this.dice);
		this.referenceComponent = new ReferenceComponent();
		this.add(this.referenceComponent);

	}

	// this creates the panel at the top which displays all of the players
	// with their victory points and the current selected player in bold.
	private void addPlayerStatsPanel() {
		JPanel playerPanel = new JPanel();
		playerPanel.setBackground(Color.black);
		JPanel[] colorPanels = new JPanel[this.numberOfPlayers];
		this.playerLabel = new JLabel[this.numberOfPlayers];
		this.vPLabel = new JLabel[this.numberOfPlayers];
		for (int i = 0; i < this.numberOfPlayers; i++) {
			colorPanels[i] = new JPanel();
			colorPanels[i].setPreferredSize(new Dimension(
					300 / this.numberOfPlayers - 8, 100));
			colorPanels[i].setBackground(this.colorArray[i]);
			this.playerLabel[i] = new JLabel("Player " + (i + 1));
			this.playerLabel[i].setFont(new Font("Times New Roman", 1, 15));
			this.playerLabel[i].setPreferredSize(new Dimension(
					300 / this.numberOfPlayers - 8, 30));
			this.playerLabel[i].setHorizontalAlignment(0);
			this.vPLabel[i] = new JLabel("VPs:  " + (this.player[i].getVPs()));
			this.vPLabel[i].setFont(new Font("Times New Roman", 1, 15));
			colorPanels[i].add(this.playerLabel[i]);
			colorPanels[i].add(this.vPLabel[i]);
			playerPanel.add(colorPanels[i]);
		}

		this.playerLabel[0].setFont(new Font("Times New Roman", 3, 18));
		this.add(playerPanel);

	}

	private void addCurrentPlayerLabel() {
		JLabel currentPlayerLabel = new JLabel("Current Player:");
		currentPlayerLabel.setFont(new Font("Times New Roman", 1, 30));
		currentPlayerLabel.setHorizontalAlignment(0);
		this.add(currentPlayerLabel);
	}

	protected void endTurn() {
		this.rollButton.setVisible(true);
		this.buildButton.setVisible(false);
		this.endButton.setVisible(false);
		this.playerLabel[this.currentPlayer].setFont(new Font(
				"Times New Roman", 1, 15));
		this.currentPlayer = (this.currentPlayer + 1) % this.numberOfPlayers;
		this.playerLabel[this.currentPlayer].setFont(new Font(
				"Times New Roman", 3, 18));
		this.buildPanel.setVisible(false);
		this.buildType = 0;
		this.dice.setVisible(true);
	}

	private void configurePlayerColors() {
		// TODO exitOnClose with warningWindow
		Object[] options2 = { "Red", "Blue", "White", "Magenta", "Orange",
				"Cyan" };
		this.player = new Player[this.numberOfPlayers];
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

			this.player[i] = new Player();
		}

	}

	private void configureNumberOfPlayers() {
		// TODO exitOnClose with warning window
		Object[] options1 = { "2", "3", "4" };
		this.numberOfPlayers = JOptionPane.showOptionDialog(null,
				"How many players are there?", "Setup",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options1, options1[1]) + 2;
	}

	protected void toggleCardPanel() {
		if (this.cardsShowed) {
			this.cardButton.setText("vv Show Cards vv");
			this.cardPanel.setVisible(false);
			this.cardsShowed = false;
		} else {
			this.cardButton.setText("^^ Hide Cards ^^");
			this.cardsShowed = true;
			this.cardPanel.setVisible(true);
		}
	}

	protected void roll() {
		int[] rolls = new Dice().rollDice();
		this.dice.setDice(rolls[1], rolls[2]);
		this.rollButton.setVisible(false);
		this.buildButton.setVisible(true);
		this.endButton.setVisible(true);

	}

	/**
	 * causes a pane to pop up showing a development card.
	 * 
	 */
	protected static void drawDevCard() {
		// does nothing yet.

	}

	/**
	 * returns the player number to be used in constructing new structures.
	 * 
	 * @return current player
	 */
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * gives the structure type to the board to build new structures.
	 * 
	 * @return current type of structure to build
	 */
	public int getBuildType() {
		return this.buildType;
	}

	/**
	 * gives the player color to the board to build new structures.
	 * 
	 * @return the color of the active player
	 */
	public Color getPlayerColor() {
		return this.colorArray[this.currentPlayer];
	}

}
