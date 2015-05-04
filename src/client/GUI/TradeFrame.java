package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Controller.Game;

public class TradeFrame extends CardSelectorFrame {
	private Game game;
	private Font mainFont = new Font(null, Font.PLAIN, 24);
	private JButton okButton;
	private int[][] delta = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
	private int[][] cardCounts = new int[2][5];
	private TradeCardPanel cardPanelA;
	private TradeCardPanel cardPanelB;
	private int[] tradeOfferCount = { 0, 0 };
	private int currentPlayerIndex;
	private int destPlayerIndex;

	public TradeFrame(Game game, int currentPlayerIndex, int destPlayerIndex,
			int[] cardCountsA, int[] cardCountsB) {
		super();
		this.game = game;
		this.setResizable(false);
		this.currentPlayerIndex = currentPlayerIndex;
		this.destPlayerIndex = destPlayerIndex;
		String title = "Player " + (this.currentPlayerIndex + 1)
				+ ", please select your trade offer and request.";
		this.cardCounts[0] = cardCountsA;
		this.cardCounts[1] = cardCountsB;

		String message = "<html>Your Offer: </html>";
		this.setSize(new Dimension(600, 900));
		this.setLocation(700, 150);
		this.setTitle(title);

		this.cardPanelA = new TradeCardPanel(this.currentPlayerIndex, this,
				cardCounts[0]);
		this.cardPanelB = new TradeCardPanel(this.destPlayerIndex, this,
				cardCounts[1]);

		JLabel messageField = new JLabel(message);
		messageField.setFont(mainFont);

		this.okButton = new JButton("OK");
		this.okButton.setFont(mainFont);
		this.okButton.setEnabled(false);
		this.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitChoices();
			}
		});

		// TODO update userPanel when submitted

		JPanel labelPanel = new JPanel();
		labelPanel.add(this.okButton);

		JPanel cardPanels = new JPanel();
		cardPanels.setLayout(new GridLayout(2, 1));
		cardPanels.add(cardPanelA);
		cardPanels.add(cardPanelB);

		this.setLayout(new BorderLayout());

		this.add(messageField, BorderLayout.NORTH);
		this.add(cardPanels, BorderLayout.CENTER);
		this.add(labelPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private void submitChoices() {

		this.game.adjustCardsForPlayer(this.currentPlayerIndex, this.delta[1]);
		this.game.adjustCardsForPlayer(this.destPlayerIndex, this.delta[0]);

		for (int i = 0; i < this.delta[0].length; i++) {
			this.delta[0][i] *= -1;
			this.delta[1][i] *= -1;
		}

		this.game.adjustCardsForPlayer(this.currentPlayerIndex, this.delta[0]);
		this.game.adjustCardsForPlayer(this.destPlayerIndex, this.delta[1]);

		setVisible(false); // you can't see me!
		dispose(); // Destroy the JFrame object
	}

	public boolean adjustCards(int playerIndex, int resourceIndex, boolean add) {

		int arrayIndex = -1;
		if (playerIndex == currentPlayerIndex) {
			arrayIndex = 0;
		} else {
			arrayIndex = 1;
		}

		if (add) {
			if (delta[arrayIndex][resourceIndex] == cardCounts[arrayIndex][resourceIndex]) {
				return false;
			}
			delta[arrayIndex][resourceIndex]++;
			tradeOfferCount[arrayIndex]++;
		} else {
			if (delta[arrayIndex][resourceIndex] == 0) {
				return false;
			}
			delta[arrayIndex][resourceIndex]--;
			tradeOfferCount[arrayIndex]--;
		}

		if (tradeOfferCount[0] > 0 && tradeOfferCount[1] > 0) {
			this.okButton.setEnabled(true);
		} else {
			this.okButton.setEnabled(false);
		}
		return true;
	}
}
