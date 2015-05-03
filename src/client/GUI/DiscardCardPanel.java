package client.GUI;

public class DiscardCardPanel extends CardSelectorPanel {

	public DiscardCardPanel(CardSelectorFrame frame, int[] cardCounts) {
		super(frame, cardCounts);
	}

	protected boolean adjustCards(int resourceIndex, boolean add) {
		return ((DiscardFrame) this.frame).adjustCards(resourceIndex, add);
	}

	protected void updateLabel(int i) {
		this.resourceLabels[i].setText(labels[i]
				+ (this.cardCounts[i] + delta[i]));
	}
}
