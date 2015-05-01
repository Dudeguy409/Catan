package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import client.Controller.Game;

public class DiscardFrame extends JFrame {

	private static final String TOTAL_CARDS = "Total Cards :  ";
	private static final String CARDS_TO_DISCARD = "Cards to discard :  ";
	private static final String[] labels = { "Wheat: ", "Wood: ", "Wool: ",
			"Ore: ", "Brick: " };
	private Game game;
	private int playerIndex;
	private int[] cardCounts;
	private int totalCardCount;
	private int remainingCardCount;
	private int[] delta = { 0, 0, 0, 0, 0 };
	private Font resourceFont = new Font(null, Font.PLAIN, 24);
	private Font buttonFont = new Font(null, Font.PLAIN, 24);
	private JLabel[] resourceLabels = new JLabel[5];
	private JButton[][] buttons;
	private JLabel totalField;
	private JLabel remainingField;
	private JButton okButton;

	public DiscardFrame(Game game, int playerIndex, int[] cardCounts) {
		super();
		this.setResizable(false);

		this.game = game;
		this.playerIndex = playerIndex;
		this.cardCounts = cardCounts;

		String title = "Player " + (this.playerIndex + 1)
				+ ", please discard half of your cards";

		String message = "<html>\tYou have more than seven cards and have been attacked by the robber.\n  You must discard half of your cards.</html>";
		this.setSize(new Dimension(600, 400));
		this.setLocation(700, 250);
		this.setTitle(title);

		this.totalCardCount = cardCounts[6];
		this.remainingCardCount = totalCardCount / 2;
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adjustCards(e);

			}
		};

		this.buttons = new JButton[5][2];

		for (int i = 0; i < 5; i++) {
			this.buttons[i][0] = new JButton("+");
			this.buttons[i][0].setFont(buttonFont);
			this.buttons[i][1] = new JButton("-");
			this.buttons[i][1].setFont(buttonFont);

			this.buttons[i][0].addActionListener(buttonListener);
			this.buttons[i][1].addActionListener(buttonListener);
		}

		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(1, 5));

		for (int i = 0; i < 5; i++) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout(new BorderLayout());
			this.resourceLabels[i] = new JLabel(labels[i] + this.cardCounts[i]);
			this.resourceLabels[i].setFont(resourceFont);
			tempPanel.add(this.resourceLabels[i], BorderLayout.NORTH);
			tempPanel.add(new CardComponent(Game.resourceColors[i]),
					BorderLayout.CENTER);
			JPanel tempButtonPanel = new JPanel();
			tempButtonPanel.setLayout(new FlowLayout());
			tempButtonPanel.add(this.buttons[i][0]);
			tempButtonPanel.add(this.buttons[i][1]);
			tempPanel.add(tempButtonPanel, BorderLayout.SOUTH);
			cardPanel.add(tempPanel);
		}

		String total = TOTAL_CARDS + this.totalCardCount;
		String remaining = CARDS_TO_DISCARD + this.remainingCardCount;
		JLabel messageField = new JLabel(message);
		messageField.setFont(resourceFont);

		this.totalField = new JLabel(total);
		totalField.setFont(resourceFont);
		this.remainingField = new JLabel(remaining);
		remainingField.setFont(resourceFont);

		this.okButton = new JButton("OK");
		this.okButton.setFont(resourceFont);
		this.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitChoice();

			}
		});

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());
		labelPanel.add(totalField);
		labelPanel.add(remainingField);
		labelPanel.add(this.okButton);

		this.setLayout(new BorderLayout());

		this.add(messageField, BorderLayout.NORTH);
		this.add(cardPanel, BorderLayout.CENTER);
		this.add(labelPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	protected void submitChoice() {
		this.game.adjustCardsForPlayer(this.playerIndex, this.delta);
		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object

	}

	protected void adjustCards(ActionEvent e) {
		Object o = e.getSource();

		for (int i = 0; i < 5; i++) {
			if (this.buttons[i][0] == o) {
				adjustCards(i, true);
				break;
			}
			if (this.buttons[i][1] == o) {
				adjustCards(i, false);
				break;
			}
		}

	}

	private void adjustCards(int i, boolean add) {
		System.out.println(i + "  " + add);
		if (add) {
			if (delta[i] > 0) {
				System.out.println("ERROR!!!");
			} else if (delta[i] == 0) {

			} else {
				delta[i] += 1;
				this.remainingCardCount += 1;
				this.totalCardCount += 1;
				this.totalField.setText(TOTAL_CARDS + totalCardCount);
				this.remainingField.setText(CARDS_TO_DISCARD
						+ remainingCardCount);
				this.resourceLabels[i].setText(labels[i]
						+ (this.cardCounts[i] + delta[i]));
			}

		} else {
			if (this.remainingCardCount < 0) {
				System.out.println("ERROR!!!");
			} else if (this.remainingCardCount == 0) {

			} else if ((this.cardCounts[i] + delta[i]) < 0) {
				System.out.println("ERROR!!!");
			} else if ((this.cardCounts[i] + delta[i]) == 0) {

			} else {
				delta[i] -= 1;
				this.remainingCardCount -= 1;
				this.totalCardCount -= 1;
				this.totalField.setText(TOTAL_CARDS + totalCardCount);
				this.remainingField.setText(CARDS_TO_DISCARD
						+ remainingCardCount);
				this.resourceLabels[i].setText(labels[i]
						+ (this.cardCounts[i] + delta[i]));
			}
		}

		if (this.remainingCardCount == 0) {
			this.okButton.setEnabled(true);
		} else {
			this.okButton.setEnabled(false);
		}

	}

}
