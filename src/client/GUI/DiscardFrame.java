package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import client.Controller.Game;

public class DiscardFrame extends CardSelectorFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651246352745812504L;
	private static final String TOTAL_CARDS = "Total Cards :  ";
	private static final String CARDS_TO_DISCARD = "Cards to discard :  ";
	private Game game;
	private Font mainFont = new Font(null, Font.PLAIN, 24);
	private JLabel totalField;
	private JLabel remainingField;
	private JButton okButton;
	private int totalCardCount;
	private int remainingCardCount;
	private int playerIndex;
	private int[] delta = { 0, 0, 0, 0, 0 };
	private int[] cardCounts;

	public DiscardFrame(Game game, int playerIndex, int[] cardCounts) {
		super();
		this.game = game;
		this.setResizable(false);
		this.playerIndex = playerIndex;
		String title = "Player " + (playerIndex + 1)
				+ ", please discard half of your cards";
		this.cardCounts = cardCounts;
		this.totalCardCount = cardCounts[6];
		this.remainingCardCount = totalCardCount / 2;

		String message = "<html>\tYou have more than seven cards and have been attacked by the robber.\n  You must discard half of your cards.</html>";
		this.setSize(new Dimension(600, 400));
		this.setLocation(700, 250);
		this.setTitle(title);

		DiscardCardPanel cardPanel = new DiscardCardPanel(this, cardCounts);

		String total = TOTAL_CARDS + this.totalCardCount;
		String remaining = CARDS_TO_DISCARD + this.remainingCardCount;
		JLabel messageField = new JLabel(message);
		messageField.setFont(mainFont);

		this.totalField = new JLabel(total);
		totalField.setFont(mainFont);
		this.remainingField = new JLabel(remaining);
		remainingField.setFont(mainFont);

		this.okButton = new JButton("OK");
		this.okButton.setFont(mainFont);
		this.okButton.setEnabled(false);
		this.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitChoices();

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

	private void submitChoices() {
		this.game.adjustCardsForPlayer(this.playerIndex, this.delta);
		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object
	}

	protected boolean adjustCards(int i, boolean add) {
		boolean succeeded = false;
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
				succeeded = true;

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

				succeeded = true;

			}
		}

		if (this.remainingCardCount == 0) {
			this.okButton.setEnabled(true);
		} else {
			this.okButton.setEnabled(false);
		}

		return succeeded;

	}
}
