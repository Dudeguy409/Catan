package client.Model;

import java.util.HashSet;
import client.Controller.Game;

/**
 * this class manages all of the individual player's data.
 * 
 * @author Andrew Davidson. Created May 28, 2010.
 */
public class Player {
	private int wheatCount = 0;
	private int woodCount = 0;
	private int woolCount = 0;
	private int brickCount = 0;
	private int oreCount = 0;
	private int devVictoryPoints = 0;
	private int totalResourceCards = 0;
	private int yearOfPlenty = 0;
	private int monopoly = 0;
	private int knight = 0;
	private int knightsPlayed = 0;
	private int roadBuilder = 0;
	private int victory = 0;
	private HashSet<Game.Resource> ports = new HashSet<Game.Resource>();

	/**
	 * returns all of the player's cards in a convenient array.
	 * 
	 * @return array of player's cards
	 */
	public int[] getCards() {
		int[] cards = { this.wheatCount, this.woodCount, this.woolCount,
				this.brickCount, this.oreCount, knightsPlayed,
				this.totalResourceCards };
		return cards;
	}

	public int getDevCardVPs() {
		return this.devVictoryPoints;
	}

	public void incrementDevVps() {
		this.devVictoryPoints++;
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
		this.updateTotal();
	}

	public int getCard(Game.Resource type) {
		switch (type) {
		case wood:
			return this.woodCount;
		case wheat:
			return this.wheatCount;
		case brick:
			return this.brickCount;
		case ore:
			return this.oreCount;
		case sheep:
			return this.woolCount;
		default:
			return -1;
		}
	}

	public int getCard(int type) {
		switch (type) {
		case 0:
			return this.wheatCount;
		case 1:
			return this.woodCount;
		case 2:
			return this.woolCount;
		case 3:
			return this.brickCount;
		case 4:
			return this.oreCount;

		default:
			return -1;
		}
	}

	public void adjustCards(Game.Resource type, int numberToAdd) {
		switch (type) {
		case wood:
			this.woodCount += numberToAdd;
			break;
		case wheat:
			this.wheatCount += numberToAdd;
			break;
		case brick:
			this.brickCount += numberToAdd;
			break;
		case ore:
			this.oreCount += numberToAdd;
			break;
		case sheep:
			this.woolCount += numberToAdd;
			break;
		default:
			break;
		}
		this.updateTotal();
	}

	public void adjustCards(int type, int numberToAdd) {
		switch (type) {
		case 0:
			this.wheatCount += numberToAdd;
			break;
		case 1:
			this.woodCount += numberToAdd;
			break;
		case 2:
			this.woolCount += numberToAdd;
			break;
		case 3:
			this.brickCount += numberToAdd;
			break;
		case 4:
			this.oreCount += numberToAdd;
			break;
		default:
			break;
		}
		this.updateTotal();
	}

	/**
	 * calculates the total number of cards that the player holds, primarily to
	 * determine how many cards will be lost if a seven is rolled.
	 * 
	 */
	private void updateTotal() {
		this.totalResourceCards = this.wheatCount + this.woodCount
				+ this.woolCount + this.brickCount + this.oreCount;
	}

	// /**
	// * tells panel how many victory points the player has. The player wins at
	// * 10.
	// *
	// * @return number of victory points
	// */
	// public int getVPs() {
	// return this.devVictoryPoints;
	// }
	//
	// private void addVP() {
	// this.devVictoryPoints++;
	// }

	public void changeDevCardCount(Game.DevCard devCard, int delta) {
		switch (devCard) {
		case yearOfPlenty:
			this.yearOfPlenty += delta;
			break;
		case knight:
			this.knight += delta;
			break;
		case monopoly:
			this.monopoly += delta;
			break;
		case roadBuilder:
			this.roadBuilder += delta;
			break;
		case victory:
			this.victory += delta;
			// addVP();
			break;
		default:
			break;

		}
	}

	public int getDevCard(Game.DevCard devCard) {
		switch (devCard) {
		case yearOfPlenty:
			return this.yearOfPlenty;
		case knight:
			return this.knight;
		case monopoly:
			return this.monopoly;
		case roadBuilder:
			return this.roadBuilder;
		case victory:
			return this.victory;
		default:
			return -1;
		}
	}

	public void addPort(Game.Resource portResource) {
		this.ports.add(portResource);
	}

	public boolean hasPort(Game.Resource resource) {
		return this.ports.contains(resource);
	}

	public void incrementKnightsPlayed() {
		this.knightsPlayed++;
	}

	public int getKnightsPlayed() {
		return this.knightsPlayed;
	}

	public int[] getDevCardCounts() {
		int[] rslt = { yearOfPlenty, monopoly, knight, victory, roadBuilder };
		return rslt;
	}
}
