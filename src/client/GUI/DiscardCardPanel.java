package client.GUI;

public class DiscardCardPanel extends CardSelectorPanel {

	public DiscardCardPanel(TradeFrame frame, int[] cardCounts) {
		super(frame, cardCounts);
	}

	protected boolean adjustCards(int i, boolean b) {
		return ((DiscardFrame) this.frame).adjustCards(i, b);
	}

	protected void updateLabel(int i) {
		this.resourceLabels[i].setText(labels[i]
				+ (this.cardCounts[i] + delta[i]));
	}
}
