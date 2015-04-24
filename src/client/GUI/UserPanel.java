package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import client.Controller.Game;

/**
 * This class creates the user interface to complement the board. It deals with
 * more of the 'behind the scenes' mechanics of the game as opposed to the
 * visual board.
 * 
 * @author Andrew Davidson. Created May 8, 2010.
 */
public class UserPanel extends JPanel {

	/**
	 * 
	 */
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
	private JLabel[] vPLabel;
	private Game game;
	private JButton settlementButton;
	private JButton cityButton;
	private JButton roadButton;
	private JButton devButton;
	private JButton pricesButton;
	private JPanel turnPanel;
	private JButton startButton;

	/**
	 * creates the user panel to go alongside the board.
	 * 
	 */
	public UserPanel(Game game) {
		this.game = game;

		this.setPreferredSize(new Dimension(300, 800));
		this.setBackground(Color.red);

		addCurrentPlayerLabel();
		addPlayerStatsPanel();

		addStartGameButton();

		addTurnPanel();
		addBuildPanel();

		this.dice = new DiceRenderer();
		this.dice.setVisible(false);
		this.add(this.dice);

		addCardButton();
		addCardPanel();

		addPricesButton();

		this.game.setUserPanel(this);

	}

	private void addStartGameButton() {
		this.startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enableGameStart();
			}
		});
		this.add(startButton);
	}

	protected void enableGameStart() {
		this.startButton.setVisible(false);
		this.buildButton.setVisible(true);
		this.setCurrentPlayer(this.game.getCurrentPlayer());
	}

	private void addPricesButton() {
		this.pricesButton = new JButton("Price Guide");
		pricesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReferenceComponent priceRefComponent = new ReferenceComponent();
				JPanel pricePanel = new JPanel();
				pricePanel.add(priceRefComponent);
				JDialog pricesDialog = new JDialog();
				pricesDialog.add(pricePanel);
				pricesDialog.setSize(priceRefComponent.getPreferredSize());
				pricesDialog.setVisible(true);
				displayPriceChartDialog();
			}
		});
		pricesButton.setVisible(false);
		this.add(pricesButton);
	}

	protected void displayPriceChartDialog() {
		// TODO Auto-generated method stub
		
	}

	private void addBuildPanel() {
		// TODO only highlight to build when they have enough cards to build
		// something. Only show the buttons of things that they can build. when
		// they click one, turn them all grey.
		// creates a panel for users to select what they want to build.
		this.buildPanel = new JPanel();
		this.buildPanel.setPreferredSize(new Dimension(300, 70));
		this.buildPanel.setBackground(Color.red);
		this.settlementButton = new JButton("Settlement");
		this.settlementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buildSettlement();
			}
		});
		this.cityButton = new JButton("City ");
		this.cityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buildCity();
			}
		});
		this.roadButton = new JButton("Road");
		this.roadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buildRoad();

			}
		});
		this.devButton = new JButton("Development Card");
		this.devButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawDevCard();
			}
		});

		this.buildPanel.add(this.settlementButton);
		this.buildPanel.add(this.cityButton);
		this.buildPanel.add(this.roadButton);
		this.buildPanel.add(this.devButton);
		this.settlementButton.setVisible(false);
		this.cityButton.setVisible(false);
		this.devButton.setVisible(false);
		this.roadButton.setVisible(false);
		this.add(this.buildPanel);

	}

	public void setCurrentPlayer(int playerIndex) {
		this.currentPlayer = playerIndex;
		for (int i = 0; i < this.numberOfPlayers; i++) {
			if (i == this.currentPlayer) {
				this.playerLabel[i].setFont(new Font("Times New Roman", 3, 25));
			} else {
				this.playerLabel[i].setFont(new Font("Times New Roman", 1, 15));
			}
		}

		this.repaint();

	}

	private void buildCity() {
		this.game.setBuildType(Game.BuildType.city);

	}

	private void buildRoad() {
		this.game.setBuildType(Game.BuildType.road);

	}

	private void buildSettlement() {
		this.game.setBuildType(Game.BuildType.settlement);

	}

	public void setTurnPhase(Game.TurnPhase phase) {
		switch (phase) {
		case preroll:
			this.rollButton.setEnabled(true);
			this.buildButton.setEnabled(false);
			this.endButton.setEnabled(false);
			this.buildPanel.setVisible(false);
			break;
		}
	}

	private void addTurnPanel() {
		// this section creates the panel for the player's actions/choices. You
		// must roll. Afterwards, you can build or end your turn.
		// TODO don't allow people the option to build or end their turn before
		// they roll. Disable build button after they already click it.
		// TODO add trade button.
		this.turnPanel = new JPanel();
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
				UserPanel.this.buildButton.setEnabled(false);
				// the dice are removed to make room for the build/buy options.
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

		this.buildButton.setVisible(false);
		this.rollButton.setVisible(false);
		this.endButton.setVisible(false);
		this.add(turnPanel);

	}

	private void addCardButton() {
		// creates a button which allows the user to hide their cards. It
		// automatically defaults to not being shown.
		this.cardButton = new JButton("vv Show Cards vv");
		this.cardButton.setPreferredSize(new Dimension(300, 30));
		this.cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleCardPanel();
			}
		});

		this.cardButton.setVisible(false);
		this.add(this.cardButton);

	}

	private void addCardPanel() {

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
		for (int i = 0; i < 5; i++) {
			nameLabel[i] = new JLabel(labelName[i]);
			nameLabel[i].setFont(new Font("Times New Roman", 2, 15));
			resourcePanel[i] = new JPanel();
			resourcePanel[i].setPreferredSize(new Dimension(60, 80));
			resourcePanel[i].setBackground(resourceColor[i]);
			resourcePanel[i].add(nameLabel[i]);
			quantityLabel[i] = new JLabel("  X  " + 0);
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

	}

	// this creates the panel at the top which displays all of the players
	// with their victory points and the current selected player in bold.
	private void addPlayerStatsPanel() {
		this.colorArray = this.game.getPlayerColors();
		this.numberOfPlayers = this.game.getNumberOfPlayers();
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
			this.vPLabel[i] = new JLabel("VPs:  " + 0);
			this.vPLabel[i].setFont(new Font("Times New Roman", 1, 15));
			colorPanels[i].add(this.playerLabel[i]);
			colorPanels[i].add(this.vPLabel[i]);
			playerPanel.add(colorPanels[i]);
		}
		this.add(playerPanel);
	}
	
	public void updateVPLabel(int playerNumber, int victoryPoints) {
		this.vPLabel[playerNumber].setText("VPs:  " + victoryPoints);
	}

	private void addCurrentPlayerLabel() {
		JLabel currentPlayerLabel = new JLabel("Current Player:");
		currentPlayerLabel.setFont(new Font("Times New Roman", 1, 30));
		currentPlayerLabel.setHorizontalAlignment(0);
		this.add(currentPlayerLabel);
	}

	private void endTurn() {
		this.game.endTurn();
	}

	private void toggleCardPanel() {
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

	public void setRolls(int[] rolls) {
		this.dice.setDice(rolls[1], rolls[2]);
		this.rollButton.setEnabled(false);
		this.buildButton.setEnabled(true);
		this.endButton.setEnabled(true);
	}

	private void roll() {
		this.game.roll();
	}

	private void drawDevCard() {
		this.game.drawDevCard();
	}

}
