import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import client.Controller.Game;
import client.Controller.Game.DevCard;
import client.Controller.Main;
import client.Controller.Game.Resource;
import client.Controller.RoadManager;
import client.GUI.HexComponent;
import client.Model.Player;

public class DevelopmentCardTest {
	TestableGame game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;
	private LinkedList<Game.DevCard> devCards;

	public void setUpGameEthan() throws Exception {
		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };

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

		Resource[] portResources = { Resource.brick, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.desert, Resource.wood };

		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 0, this.userPanel, this.board,
				Main.configureRandomNumberArray(resources), devCards,
				portResources);

		LinkedList<Integer> robberMoveSelections = new LinkedList<Integer>();
		robberMoveSelections.add(14);

		LinkedList<Integer> playerStealSelections = new LinkedList<Integer>();
		playerStealSelections.add(1);

		LinkedList<Integer> resourceSelections = new LinkedList<Integer>();
		resourceSelections.add(2);

		game.configureTestableGame(null, null, playerStealSelections, null,
				robberMoveSelections, resourceSelections);

		// gets the game out of the Pre-game set-up phase
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.south);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(3,
				HexComponent.StructurePosition.southwest);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.southeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,
				HexComponent.StructurePosition.southeast);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northwest);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(9,
				HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(14, HexComponent.RoadPosition.northwest);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(14,
				HexComponent.StructurePosition.northwest);
	}

	@Test
	public void TestBuyDevelopmentCard() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.drawDevCard();
		assertEquals(0, player.getCards()[0]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}

	@Test
	public void TestUseYearOfPlenty() throws Exception {
		setUpGameEthan();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(0);
		cards.add(1);

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.yearOfPlenty);

		Player[] players = { player, new Player() };
		field.set(game, players);
		LinkedList<Resource> resources = new LinkedList<Resource>();
		resources.add(Game.Resource.wheat);
		resources.add(Game.Resource.wood);

		game.configureTestableGame(null, resources, null, null, null, null);

		game.drawDevCard();
		game.playYearOfPlenty();
		assertEquals(1, player.getCards()[0]);
		assertEquals(1, player.getCards()[1]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
		assertEquals(0, player.getDevCard(DevCard.yearOfPlenty));
	}

	@Test
	public void TestGetVictoryDevCard() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		assertEquals(2, game.getVictoryPointsForPlayer(0));

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.victory);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.drawDevCard();
		game.processPlayDevCardClick(DevCard.victory);
		assertEquals(3, game.getVictoryPointsForPlayer(0));
		assertEquals(0, player.getCards()[0]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}

	@Test
	public void TestUseMonopolyDevCard() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.monopoly);

		Player player2 = new Player();
		int[] delta2 = { 1, 0, 0, 0, 0, 0 };
		player2.adjustCards(delta2);

		Player[] players = { player, player2 };
		field.set(game, players);

		LinkedList<Resource> resources = new LinkedList<Resource>();
		resources.add(Game.Resource.wheat);
		resources.add(Game.Resource.wood);

		game.configureTestableGame(resources, null, null, null, null, null);

		game.drawDevCard();
		game.playMonopolyDevCard();

		assertEquals(0, player2.getCards()[0]);
		assertEquals(1, player.getCards()[0]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
		assertEquals(0, player.getDevCard(DevCard.monopoly));
	}

	@Test
	public void TestUseRoadBuildDevCard() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Field roadMgrField = Game.class.getDeclaredField("roadMgr");
		roadMgrField.setAccessible(true);

		Field roadBuildField = Game.class.getDeclaredField("roadBuild");
		roadBuildField.setAccessible(true);

		RoadManager roadMgr = (RoadManager) roadMgrField.get(game);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.roadBuilder);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.roadBuilder));

		game.endTurn();

		assertEquals(1, player.getDevCard(Game.DevCard.roadBuilder));

		game.roll();
		game.endTurn();

		game.processPlayDevCardClick(DevCard.roadBuilder);
		int roadBuild = roadBuildField.getInt(game);
		assertEquals(2, roadBuild);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.southwest);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northwest);

		assertEquals(4, roadMgr.getRoadCountForPlayer(0));
		assertEquals(0, player.getDevCard(DevCard.roadBuilder));
	}

	@Test
	public void TestUseRoadBuildDevCardWith14Roads() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Field roadMgrField = Game.class.getDeclaredField("roadMgr");
		roadMgrField.setAccessible(true);

		Field roadBuildField = Game.class.getDeclaredField("roadBuild");
		roadBuildField.setAccessible(true);

		RoadManager roadMgr = (RoadManager) roadMgrField.get(game);

		Player player = new Player();
		int[] delta = { 1, 12, 1, 12, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.roadBuilder);

		Player[] players = { player, new Player() };
		field.set(game, players);

		game.processBuildRoadClick(3, HexComponent.RoadPosition.southwest);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.southwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.south);
		game.processBuildRoadClick(1, HexComponent.RoadPosition.southeast);

		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.roadBuilder));
		game.endTurn();
		assertEquals(1, player.getDevCard(Game.DevCard.roadBuilder));
		
		game.roll();
		game.endTurn();

		game.processPlayDevCardClick(DevCard.roadBuilder);
		int roadBuild = roadBuildField.getInt(game);
		assertEquals(1, roadBuild);
		game.processBuildRoadClick(1, HexComponent.RoadPosition.south);

		assertEquals(15, roadMgr.getRoadCountForPlayer(0));
		assertEquals(0, player.getDevCard(DevCard.roadBuilder));
	}

	@Test
	public void TestUseRoadBuildDevCardWith15Roads() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Field roadMgrField = Game.class.getDeclaredField("roadMgr");
		roadMgrField.setAccessible(true);

		Field roadBuildField = Game.class.getDeclaredField("roadBuild");
		roadBuildField.setAccessible(true);

		RoadManager roadMgr = (RoadManager) roadMgrField.get(game);

		Player player = new Player();
		int[] delta = { 1, 13, 1, 13, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.roadBuilder);

		Player[] players = { player, new Player() };
		field.set(game, players);

		game.processBuildRoadClick(3, HexComponent.RoadPosition.southwest);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.southwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(4, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(2, HexComponent.RoadPosition.south);
		game.processBuildRoadClick(1, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(1, HexComponent.RoadPosition.south);

		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.roadBuilder));
		game.endTurn();
		assertEquals(1, player.getDevCard(Game.DevCard.roadBuilder));
		
		game.roll();
		game.endTurn();

		game.processPlayDevCardClick(DevCard.roadBuilder);
		int roadBuild = roadBuildField.getInt(game);
		assertEquals(0, roadBuild);

		assertEquals(15, roadMgr.getRoadCountForPlayer(0));
		assertEquals(0, player.getDevCard(DevCard.roadBuilder));
	}

	@Test
	public void TestUseKnightDevCard() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.knight);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.knight));
		game.endTurn(); // Change to player index 1's turn.

		assertEquals(1, player.getDevCard(Game.DevCard.knight));

		game.roll();
		game.endTurn();

		assertEquals(1, player.getDevCard(Game.DevCard.knight));

		game.processPlayDevCardClick(DevCard.knight);

		assertEquals(0, player.getDevCard(Game.DevCard.knight));

	}

	@Test
	public void TestLargestArmyVictoryPoints() throws Exception {
		setUpGameEthan();

		LinkedList<Integer> robberMoveSelections = new LinkedList<Integer>();
		robberMoveSelections.add(14);
		robberMoveSelections.add(15);
		robberMoveSelections.add(16);

		LinkedList<Integer> playerStealSelections = new LinkedList<Integer>();
		playerStealSelections.add(1);
		playerStealSelections.add(1);
		playerStealSelections.add(1);

		LinkedList<Integer> resourceSelections = new LinkedList<Integer>();
		resourceSelections.add(2);
		resourceSelections.add(2);
		resourceSelections.add(2);

		game.configureTestableGame(null, null, playerStealSelections, null,
				robberMoveSelections, resourceSelections);

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field devField = Game.class.getDeclaredField("devCardDeck");
		devField.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		Player player2 = new Player();
		int[] delta2 = { 1, 0, 1, 0, 1, 0 };
		player2.adjustCards(delta2);

		LinkedList<Game.DevCard> devCardDeck = (LinkedList<DevCard>) devField
				.get(game);
		devCardDeck.addFirst(Game.DevCard.knight);

		Player[] players = { player, player2 };
		field.set(game, players);

		assertEquals(2, game.getVictoryPointsForPlayer(0));

		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.knight));
		game.endTurn(); // Change to player index 1's turn.
		// assert that the card is only added to your hand after your turn ends
		assertEquals(1, player.getDevCard(Game.DevCard.knight));

		game.roll();
		game.endTurn();
		assertEquals(0, game.getCurrentPlayer());
		assertEquals(1, player.getDevCard(Game.DevCard.knight));
		game.processPlayDevCardClick(DevCard.knight);
		devCardDeck.addFirst(Game.DevCard.knight);
		player.adjustCards(delta);
		assertEquals(0, player.getDevCard(Game.DevCard.knight));

		game.drawDevCard();
		assertEquals(0, player.getDevCard(Game.DevCard.knight));
		game.endTurn(); // Change to player index 1's turn.
		assertEquals(1, player.getDevCard(Game.DevCard.knight));

		game.roll();
		game.endTurn();
		assertEquals(0, game.getCurrentPlayer());

		devCardDeck.addFirst(Game.DevCard.knight);
		player.adjustCards(delta);

		game.drawDevCard();
		game.processPlayDevCardClick(DevCard.knight);
		assertEquals(0, player.getDevCard(Game.DevCard.knight));

		game.endTurn(); // Change to player index 1's turn.

		game.roll();
		game.endTurn();
		assertEquals(0, game.getCurrentPlayer());

		game.processPlayDevCardClick(DevCard.knight);

		game.endTurn();

		assertEquals(4, game.getVictoryPointsForPlayer(0));
	}

}
