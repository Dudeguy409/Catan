package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Controller.Game;

public class BankTradeFrame extends TradeFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7407954012206449561L;
	private Game game;
	private Font mainFont = new Font(null, Font.PLAIN, 24);
	private JButton okButton;
	private int[][] delta = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
	private int[][] cardCounts = new int[2][5];

	private TradeCardPanel cardPanelA;
	private TradeCardPanel cardPanelB;
	private int[] tradeOfferCount = { 0, 0 };
	private int currentPlayerIndex;
	private int destPlayerIndex = -1;

	public BankTradeFrame(Game game, int currentPlayerIndex, int[] cardCounts) {
		super();
		this.game = game;
		this.setResizable(false);
		this.currentPlayerIndex = currentPlayerIndex;
		String title = "Player " + (this.currentPlayerIndex + 1)
				+ ", please select your bank trade offer and request.";
		this.cardCounts[0] = cardCounts;
		int[] bankCardCounts = { 50, 50, 50, 50, 50 };
		this.cardCounts[1] = bankCardCounts;

		this.setSize(new Dimension(600, 650));
		this.setLocation(700, 150);
		this.setTitle(title);

		this.okButton = new JButton("OK");
		this.okButton.setFont(mainFont);
		this.okButton.setEnabled(false);
		this.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitChoices();
			}
		});

		String offerMessage = "<html>Your Offer: </html>";
		JLabel offerMessageField = new JLabel(offerMessage);
		offerMessageField.setFont(mainFont);

		String requestMessage = "<html>Your Request: </html>";
		JLabel requestMessageField = new JLabel(requestMessage);
		requestMessageField.setFont(mainFont);

		JPanel panelA = new JPanel();
		panelA.setLayout(new BorderLayout());
		panelA.add(offerMessageField, BorderLayout.NORTH);

		JPanel panelB = new JPanel();
		panelB.setLayout(new BorderLayout());
		panelB.add(requestMessageField, BorderLayout.NORTH);

		this.cardPanelA = new TradeCardPanel(this.currentPlayerIndex, this,
				this.cardCounts[0]);
		this.cardPanelB = new TradeCardPanel(this.destPlayerIndex, this,
				this.cardCounts[1]);

		panelA.add(cardPanelA, BorderLayout.CENTER);
		panelB.add(cardPanelB, BorderLayout.CENTER);

		JPanel labelPanel = new JPanel();
		labelPanel.add(this.okButton);

		JPanel mainCardPanel = new JPanel();
		mainCardPanel.setLayout(new GridLayout(2, 1));
		mainCardPanel.add(panelA);
		mainCardPanel.add(panelB);

		this.setLayout(new BorderLayout());

		this.add(mainCardPanel, BorderLayout.CENTER);
		this.add(labelPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private void submitChoices() {

		if (this.game.tradeWithBank(delta[0], delta[1])) {
			setVisible(false); // you can't see me!
			dispose(); // Destroy the JFrame object
		} else {
			String message = "Player "
					+ (this.currentPlayerIndex + 1)
					+ ", Your trade offer with the bank was not valid.  Perhaps you offered too many or too few resources for your request.";

			JOptionPane.showMessageDialog(null, message);
		}
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
