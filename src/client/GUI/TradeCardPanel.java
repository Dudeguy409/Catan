package client.GUI;

public class TradeCardPanel extends CardSelectorPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4153382468845295936L;
	private int playerIndex;

	public TradeCardPanel(CardSelectorFrame frame, int[] cardCounts)
			throws Exception {
		super(frame, cardCounts);
		throw new Exception(
				"TradeCardPanels should pass in their playerIndex!!!");
	}

	public TradeCardPanel(int playerIndex, CardSelectorFrame frame,
			int[] cardCounts) {
		super(frame, cardCounts);
		this.playerIndex = playerIndex;
	}

	@Override
	protected boolean adjustCards(int resourceIndex, boolean add) {
		return ((TradeFrame) this.frame).adjustCards(this.playerIndex,
				resourceIndex, add);
	}

	@Override
	protected void updateLabel(int i) {
		this.resourceLabels[i].setText(labels[i] + delta[i]);
	}

}
