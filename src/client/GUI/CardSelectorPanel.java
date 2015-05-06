package client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import client.Controller.Game;

public abstract class CardSelectorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3904573915425407859L;

	protected static final String[] labels = { "Wheat: ", "Wood: ", "Wool: ",
			"Brick: ", "Ore: " };

	protected int[] cardCounts;
	private Font mainFont = new Font(null, Font.PLAIN, 24);
	protected JLabel[] resourceLabels = new JLabel[5];
	private JButton[][] buttons;
	protected int[] delta = { 0, 0, 0, 0, 0 };
	protected CardSelectorFrame frame;

	public CardSelectorPanel(CardSelectorFrame frame, int[] cardCounts) {
		super();
		this.frame = frame;
		this.cardCounts = cardCounts;

		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adjustCards(e);

			}
		};

		this.buttons = new JButton[5][2];

		for (int i = 0; i < 5; i++) {
			this.buttons[i][0] = new JButton("+");
			this.buttons[i][0].setFont(mainFont);
			this.buttons[i][1] = new JButton("-");
			this.buttons[i][1].setFont(mainFont);

			this.buttons[i][0].addActionListener(buttonListener);
			this.buttons[i][1].addActionListener(buttonListener);
		}

		this.setLayout(new GridLayout(1, 5));

		for (int i = 0; i < 5; i++) {
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout(new BorderLayout());
			this.resourceLabels[i] = new JLabel();
			this.resourceLabels[i].setFont(mainFont);
			updateLabel(i);
			tempPanel.add(this.resourceLabels[i], BorderLayout.NORTH);
			tempPanel.add(new CardComponent(Game.resourceColors[i]),
					BorderLayout.CENTER);
			JPanel tempButtonPanel = new JPanel();
			tempButtonPanel.setLayout(new FlowLayout());
			tempButtonPanel.add(this.buttons[i][0]);
			tempButtonPanel.add(this.buttons[i][1]);
			tempPanel.add(tempButtonPanel, BorderLayout.SOUTH);
			this.add(tempPanel);
		}

	}

	protected void adjustCards(ActionEvent e) {
		Object o = e.getSource();

		for (int i = 0; i < 5; i++) {
			if (this.buttons[i][0] == o) {
				if (adjustCards(i, true)) {
					delta[i] += 1;
					updateLabel(i);
				}
				break;
			}
			if (this.buttons[i][1] == o) {
				if (adjustCards(i, false)) {
					delta[i] -= 1;
					updateLabel(i);
				}
				break;
			}
		}
	}

	protected abstract boolean adjustCards(int i, boolean b);

	protected abstract void updateLabel(int i);

}
