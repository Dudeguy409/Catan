import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import client.Controller.Game;
import client.Controller.Main;
import client.Controller.Game.Resource;
import client.GUI.HexComponent;

public class GameBankTradeTest {

	private TestableGame game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;
	private LinkedList<Game.DevCard> devCards;

	public void setUpGameAndrew(int[] arrayA, int[] arrayB) throws Exception {
		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };

		this.userPanel = new FakeUserPanel();
		this.board = new FakeBoardRenderer();

		this.devCards = new LinkedList<Game.DevCard>();
		Random place = new Random();
		for (int i = 0; i < 14; i++) {
			devCards.add(Game.DevCard.knight);
		}

		for (int i = 0; i < 5; i++) {
			devCards.add(place.nextInt(14), Game.DevCard.victory);
		}

		for (int i = 2; i < 2; i++) {
			devCards.add(place.nextInt(19), Game.DevCard.monopoly);
			devCards.add(place.nextInt(19), Game.DevCard.roadBuilder);
			devCards.add(place.nextInt(19), Game.DevCard.yearOfPlenty);
		}

		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 0, this.userPanel, this.board,
				Main.configureRandomNumberArray(resources), this.devCards);

		LinkedList<Integer> playerStealSelections = new LinkedList<Integer>();
		LinkedList<Integer> robberMoveSelections = new LinkedList<Integer>();
		LinkedList<Resource> resourceToStealSelections = new LinkedList<Game.Resource>();

		resourceToStealSelections.add(Resource.sheep);
		robberMoveSelections.add(1);
		playerStealSelections.add(0);

		game.configureTestableGame(null, null, playerStealSelections, null,
				robberMoveSelections, resourceToStealSelections);

		// gets the game out of the Pre-game set-up phase

		// player one builds settlement on a 2 wood hex with a 3:1 port
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.south);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(2,
				HexComponent.StructurePosition.southeast);

		// player 2 builds on a 6 ore hex with ore port
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(3,
				HexComponent.StructurePosition.northeast);

		// player 2 builds on a 5 wheat hex with a brick port
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(1, HexComponent.RoadPosition.south);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(1,
				HexComponent.StructurePosition.southeast);

		// player one builds on a 8 wheat hex with a wood port
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(11, HexComponent.RoadPosition.south);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(11,
				HexComponent.StructurePosition.southwest);
	}

	@Test
	public void testDoesntOfferFour() throws Exception {
		int[] arrayA = { 1, 1, 1, 1, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 4, 4, 4, 3, 4, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer = { 3, 0, 0, 0, 0 };
		int[] request = { 0, 1, 0, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[3, 0, 0, 0, 0, 0, 3]");
	}

	@Test
	public void testOffersFour() throws Exception {
		int[] arrayA = { 1, 1, 1, 1, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 4, 4, 4, 3, 4, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer = { 4, 0, 0, 0, 0 };
		int[] request = { 0, 1, 0, 0, 0 };
		assertEquals(true, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[0, 1, 0, 0, 0, 0, 1]");
	}

	@Test
	public void testOffersTwoWithPort() throws Exception {
		int[] arrayA = { 1, 1, 1, 5, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 1, 1, 1, 1, 1, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();

		int[] offer = { 0, 1, 0, 0, 0 };
		int[] request = { 0, 0, 1, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(0)),
				"[1, 4, 0, 0, 0, 0, 5]");

		int[] offer2 = { 0, 2, 0, 0, 0 };
		int[] request2 = { 0, 0, 1, 0, 0 };
		assertEquals(true, this.game.tradeWithBank(offer2, request2));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(0)),
				"[1, 2, 1, 0, 0, 0, 4]");

	}

	@Test
	public void testDoesntOfferEightForTwo() throws Exception {
		int[] arrayA = { 1, 2, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 4, 4, 4, 4, 4, 4, 4, 6, 3, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer = { 7, 0, 0, 0, 0 };
		int[] request = { 0, 2, 0, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[7, 0, 0, 0, 1, 0, 8]");

	}

	@Test
	public void testOffersEightForTwo() throws Exception {
		int[] arrayA = { 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 4, 4, 4, 4, 4, 4, 4, 6, 3, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer = { 8, 0, 0, 0, 0 };
		int[] request = { 0, 2, 0, 0, 0 };
		assertEquals(true, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[0, 2, 0, 0, 0, 0, 2]");

	}

	@Test
	public void testDoesntOfferThreeWithPort() throws Exception {
		// TODO
		int[] arrayA = { 1, 1, 1, 1, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 1, 1, 1, 1, 1, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

	}

	@Test
	public void testOffersThreeWithPort() throws Exception {
		// TODO
		int[] arrayA = { 1, 1, 1, 1, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 1, 1, 1, 1, 1, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

	}

	@Test
	public void testOffersMoreThanEnough() throws Exception {
		int[] arrayA = { 1, 1, 1, 1, 1, 4, 1, 2, 2, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 4, 4, 4, 4, 4, 4, 4, 6, 3, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer = { 5, 0, 0, 0, 0 };
		int[] request = { 0, 1, 0, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[5, 0, 0, 0, 0, 0, 5]");

		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer2 = { 6, 0, 0, 0, 0 };
		int[] request2 = { 0, 1, 0, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer2, request2));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[6, 0, 0, 0, 0, 0, 6]");

		this.game.endTurn();

		this.game.roll();
		this.game.endTurn();

		this.game.roll();

		int[] offer3 = { 7, 0, 0, 0, 0 };
		int[] request3 = { 0, 1, 0, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer3, request3));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(1)),
				"[7, 0, 0, 0, 0, 0, 7]");
	}

	@Test
	public void testOffersFourForOneWithPort() throws Exception {
		int[] arrayA = { 1, 1, 1, 5, 1, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 1, 1, 1, 1, 1, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		setUpGameAndrew(arrayA, arrayB);

		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();
		this.game.endTurn();
		this.game.roll();

		int[] offer = { 0, 4, 0, 0, 0 };
		int[] request = { 0, 0, 1, 0, 0 };
		assertEquals(false, this.game.tradeWithBank(offer, request));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(0)),
				"[1, 4, 0, 0, 0, 0, 5]");

		int[] offer2 = { 0, 4, 0, 0, 0 };
		int[] request2 = { 0, 0, 2, 0, 0 };
		assertEquals(true, this.game.tradeWithBank(offer2, request2));
		assertEquals(Arrays.toString(this.game.getCardsForPlayer(0)),
				"[1, 0, 2, 0, 0, 0, 3]");

	}

}
