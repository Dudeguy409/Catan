package client.GUI;

public abstract class TradeFrame extends CardSelectorFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022828063263523904L;
	
	public abstract boolean adjustCards(int playerIndex, int resourceIndex,
			boolean add);

}
