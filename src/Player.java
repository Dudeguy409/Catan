import java.awt.Color;

/**
 * this class manages all of the individual player's data.
 * 
 * @author Andrew Davidson. Created May 28, 2010.
 */
public class Player {
	// TODO implement an enumeration for resource cards
	// TODO add port and bank trade rates
	private int wheatCount;
	private int woodCount;
	private int woolCount;
	private int brickCount;
	private int oreCount;
	private int playedSoldierCount;
	private int victoryPoints;
	private int totalResourceCards;
	private int playerIndex;
	private int[] unplayedDevCardCount;
	private Color color;

	/**
	 * returns all of the player's cards in a convenient array.
	 * 
	 * @return array of player's cards
	 */
	public int[] getCards() {
		int[] cards = { this.wheatCount, this.woodCount, this.woolCount,
				this.brickCount, this.oreCount, this.playedSoldierCount,
				this.totalResourceCards };
		return cards;
	}

	/**
	 * adjusts, but does NOT replace the old number of cards the player holds.
	 * 
	 * @param delta
	 *            - the number of cards to add or remove from the player's hand
	 */
	public void adjustCards(int[] delta) {
		this.wheatCount += delta[0];
		this.woodCount += delta[1];
		this.woolCount += delta[2];
		this.brickCount += delta[3];
		this.oreCount += delta[4];
		this.playedSoldierCount += delta[5];
		this.updateTotal();
	}

	/**
	 * calculates the total number of cards that the player holds, primarily to
	 * determine how many cards will be lost if a seven is rolled.
	 * 
	 */
	private void updateTotal() {
		this.totalResourceCards = this.wheatCount + this.woodCount + this.woolCount
				+ this.brickCount + this.oreCount;
	}

	/**
	 * tells panel how many victory points the player has. The player wins at
	 * 10.
	 * 
	 * @return number of victory points
	 */
	public int getVPs() {
		return this.victoryPoints;
	}
}
