package client.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import client.Controller.Game.DevCard;

public class RandomDevCardDeckGenerator {

	private static final int knightCardCount = 14;
	private static final int victoryCardCount = 5;
	private static final int monopolyCardCount = 2;
	private static final int roadBuildCardCount = 2;
	private static final int yearOfPlentyCardCount = 2;
	private static final int totalDevCardCount = knightCardCount
			+ victoryCardCount + monopolyCardCount + roadBuildCardCount
			+ yearOfPlentyCardCount;

	public static LinkedList<Game.DevCard> getRandomDeck(long seed) {

		// makes an arraylist with the correct number of each development card.
		ArrayList<Game.DevCard> devCardArray = new ArrayList<Game.DevCard>();

		for (int i = 0; i < knightCardCount; i++) {
			devCardArray.add(DevCard.knight);
		}

		for (int i = 0; i < monopolyCardCount; i++) {
			devCardArray.add(DevCard.monopoly);
		}

		for (int i = 0; i < roadBuildCardCount; i++) {
			devCardArray.add(DevCard.roadBuilder);
		}

		for (int i = 0; i < victoryCardCount; i++) {
			devCardArray.add(DevCard.victory);
		}

		for (int i = 0; i < yearOfPlentyCardCount; i++) {
			devCardArray.add(DevCard.yearOfPlenty);
		}

		// randomly chooses one element from the fixed array above, copies
		// it to the new array, and deletes the element copied.
		LinkedList<DevCard> randomDevCardDeck = new LinkedList<DevCard>();
		Random generator = new Random(seed);

		for (int i = 0; i < totalDevCardCount; i++) {
			int readValue = generator.nextInt(devCardArray.size());
			randomDevCardDeck.add(devCardArray.get(readValue));
			devCardArray.remove(readValue);
		}
		return randomDevCardDeck;
	}

}
