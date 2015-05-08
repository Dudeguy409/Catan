import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

import client.Controller.Game;
import client.Controller.HexManager;
import client.Controller.Main;
import client.Controller.StructureManager;
import client.Controller.Game.BuildType;
import client.Controller.Game.Resource;
import client.GUI.HexComponent;
import client.Model.Hex;
import client.Model.Player;

public class GameTest {
	TestableGame game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;
	LinkedList<Game.DevCard> devCards;

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

		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 0, this.userPanel, this.board,
				Main.configureRandomNumberArray(resources), devCards);

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

	public void setUpGameAndrew() throws Exception {
		this.userPanel = new FakeUserPanel();
		this.board = new FakeBoardRenderer();

		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		// int[] randomNumberArray = { 5, 2, 6, 3, 8, 0, 9, 12, 11, 4, 8, 10, 9,
		// 4,
		// 5, 6, 3, 11 };
		int[] randomNumberArray = Main.configureRandomNumberArray(resources);
		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 0, userPanel, board,
				randomNumberArray, this.devCards);
	}

	public void setUpGameAndrewPlayerTwoStart() throws Exception {
		this.userPanel = new FakeUserPanel();
		this.board = new FakeBoardRenderer();

		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		// int[] randomNumberArray = { 5, 2, 6, 3, 8, 0, 9, 12, 11, 4, 8, 10, 9,
		// 4,
		// 5, 6, 3, 11 };
		int[] randomNumberArray = Main.configureRandomNumberArray(resources);
		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 1, userPanel, board,
				randomNumberArray, this.devCards);
	}

	public void setUpGameDavis() throws Exception {
		this.userPanel = new FakeUserPanel();
		this.board = new FakeBoardRenderer();

		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };

		// int[] randomNumberArray = { 5, 2, 6, 3, 8, 0, 9, 12, 11, 4, 8, 10, 9,
		// 4,
		// 5, 6, 3, 11 };

		Resource[] portResources = { Resource.brick, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.desert, Resource.wood };
		
		game = new TestableGame(colors, resources,
				new FakeDice(arrayA, arrayB), 0, userPanel, board,
				Main.configureRandomNumberArray(resources), this.devCards, portResources);

		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,
				HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(14, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(14,
				HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(11, HexComponent.RoadPosition.northeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(11, HexComponent.StructurePosition.east);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(13, HexComponent.RoadPosition.northeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(13, HexComponent.StructurePosition.east);

	}

	@Test
	public void getDiceRollTest() {
		// TODO
	}

	@Test
	public void TestPlayerQueueInitializes() throws Exception {
		setUpGameAndrew();
		Field field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		Queue<Integer> prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values = { 1, 1, 0 };
		Queue<Integer> expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values));

		assertEquals(expectedQueue, prePlayerQueue);
	}

	@Test
	public void getAdjacentRoadsForStructureTest() throws Exception {
		setUpGameDavis();

		List<HexComponent.RoadPosition> poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.west));
		assertTrue(poses.contains(HexComponent.RoadPosition.northwest));
		assertTrue(poses.contains(HexComponent.RoadPosition.southwest));
		assertTrue(poses.size() == 2);

		poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.northwest));
		assertTrue(poses.contains(HexComponent.RoadPosition.northwest));
		assertTrue(poses.contains(HexComponent.RoadPosition.north));
		assertTrue(poses.size() == 2);

		poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.northeast));
		assertTrue(poses.contains(HexComponent.RoadPosition.north));
		assertTrue(poses.contains(HexComponent.RoadPosition.northeast));
		assertTrue(poses.size() == 2);

		poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.east));
		assertTrue(poses.contains(HexComponent.RoadPosition.northeast));
		assertTrue(poses.contains(HexComponent.RoadPosition.southeast));
		assertTrue(poses.size() == 2);

		poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.southeast));
		assertTrue(poses.contains(HexComponent.RoadPosition.southeast));
		assertTrue(poses.contains(HexComponent.RoadPosition.south));
		assertTrue(poses.size() == 2);

		poses = Arrays
				.asList(game
						.getAdjacentRoadPositionsForStructure(HexComponent.StructurePosition.southwest));
		assertTrue(poses.contains(HexComponent.RoadPosition.southwest));
		assertTrue(poses.contains(HexComponent.RoadPosition.south));
		assertTrue(poses.size() == 2);

	}

	@Test
	public void testThatOneRollAddsResources() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field hexfield = Game.class.getDeclaredField("hexArray");
		hexfield.setAccessible(true);

		ArrayList<Hex> hex = (ArrayList<Hex>) hexfield.get(game);

		Player[] players = (Player[]) (field.get(game));
		Player player = players[game.getCurrentPlayer()];

		game.roll();
		assertEquals(2, player.getCards()[4]);
	}

	@Test
	public void testAddResourcesOneCity() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player[] players = (Player[]) (field.get(game));
		Player player = players[game.getCurrentPlayer()];
		int[] delta = { 2, 0, 0, 0, 3, 0 };
		player.adjustCards(delta);

		game.setBuildType(Game.BuildType.city);
		game.processBuildStructureClick(3,
				HexComponent.StructurePosition.southwest);
		game.roll();
		assertEquals(3, player.getCards()[4]);
	}

	@Test
	public void testAddResourcesOneCityAndOneSettlement() {
		// TODO
	}

	@Test
	public void testThatNormalGameStartsAfterPreGame() throws Exception {
		setUpGameAndrewPlayerTwoStart();

		Field field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		Queue<Integer> prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values = { 0, 0, 1 };
		Queue<Integer> expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values));

		assertEquals(expectedQueue, prePlayerQueue);

		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.getCurrentBuildType(), Game.BuildType.none);
		assertEquals(game.isBeginningOfGame(), true);
		assertEquals(game.getVictoryPointsForPlayer(0), 0);
		assertEquals(game.getVictoryPointsForPlayer(1), 0);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.south);
		assertEquals(game.getVictoryPointsForPlayer(1), 0);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,
				HexComponent.StructurePosition.southwest);
		assertEquals(game.getVictoryPointsForPlayer(1), 1);
		assertEquals(game.getCurrentPlayer(), 0);

		field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values2 = { 0, 1 };
		expectedQueue = new LinkedList<Integer>(Arrays.asList(values2));

		assertEquals(expectedQueue, prePlayerQueue);

		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.southeast);
		assertEquals(game.getVictoryPointsForPlayer(0), 0);
		assertEquals(game.getCurrentPlayer(), 0);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,
				HexComponent.StructurePosition.southeast);

		field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values3 = { 1 };
		expectedQueue = new LinkedList<Integer>(Arrays.asList(values3));

		assertEquals(expectedQueue, prePlayerQueue);

		assertEquals(game.getVictoryPointsForPlayer(0), 1);
		assertEquals(game.getCurrentPlayer(), 0);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(0), 1);
		assertEquals(game.getCurrentPlayer(), 0);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(9,
				HexComponent.StructurePosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(0), 2);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);

		field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values4 = {};
		expectedQueue = new LinkedList<Integer>(Arrays.asList(values4));

		assertEquals(expectedQueue, prePlayerQueue);

		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(14, HexComponent.RoadPosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(1), 1);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(14,
				HexComponent.StructurePosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(1), 2);
		assertEquals(game.getVictoryPointsForPlayer(0), 2);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), false);

		field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		prePlayerQueue = (LinkedList<Integer>) (field.get(game));

		assertTrue(prePlayerQueue.isEmpty());

	}

	@Test
	public void testThatOnlyOneRoadCanBeAddedDuringEachTurnForEachPlayerInPreGame()
			throws Exception {
		setUpGameAndrew();

		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.south);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.northwest);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,
				HexComponent.StructurePosition.southwest);
		assertEquals(game.getVictoryPointsForPlayer(0), 1);
		assertEquals(game.getRoadCountForPlayer(0), 1);

		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.southeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,
				HexComponent.StructurePosition.southeast);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.south);
		assertEquals(game.getVictoryPointsForPlayer(0), 1);
		assertEquals(game.getRoadCountForPlayer(0), 2);

	}

	// Tests for buying things
	// TODO add tests for buying development cards
	@Test
	public void testBuyAndBuildRoad() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 0, 1, 0, 1, 0, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.southwest);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[3]);
	}

	@Test
	public void testBuyAndBuildSettlement() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 2, 1, 2, 0, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.southeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(3, HexComponent.StructurePosition.east);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[3]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[0]);
	}

	@Test
	public void testBuyAndBuildCity() throws Exception {
		setUpGameEthan();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 2, 0, 0, 0, 3, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		assertEquals(2, game.getVictoryPointsForPlayer(0));

		game.setBuildType(Game.BuildType.city);
		game.processBuildStructureClick(3,
				HexComponent.StructurePosition.southwest);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[4]);

		assertEquals(3, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testTwoVictoryPointsForLongestRoad() throws Exception {
		setUpGameDavis();
		assertEquals(2, game.getVictoryPointsForPlayer(0));

		game.addRoad(0, 16, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 16, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 16, HexComponent.RoadPosition.south);

		assertEquals(2, game.getVictoryPointsForPlayer(0));

		game.addRoad(0, 17, HexComponent.RoadPosition.northwest);

		assertEquals(4, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testAddOneVictoryPointForOneSettlement() throws Exception {
		setUpGameDavis();

		assertEquals(2, game.getVictoryPointsForPlayer(0));

		game.setBuildType(BuildType.road);
		System.out.println(game.addRoad(0, 16,
				HexComponent.RoadPosition.northeast));
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);

		assertEquals(3, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testAddTwoVictoryPointsForOneCity() throws Exception {
		// TODO
		setUpGameDavis();

		game.setBuildType(BuildType.road);
		System.out.println(game.addRoad(0, 16,
				HexComponent.RoadPosition.northeast));
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);

		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);

		assertEquals(4, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testCalculateVictoryPointsForPlayerFromStructures()
			throws Exception {

		StructureManager sManager = new StructureManager(4);

		sManager.addStructure(0, 30);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 1);

		sManager.addStructure(0, 25);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 2);

		sManager.updateStructure(0, 25);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 3);

		sManager.addStructure(3, 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(3), 1);

		sManager.addStructure(3, 40);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(3), 2);

		sManager.updateStructure(3, 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(3), 3);

		sManager.updateStructure(3, 40);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 3);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(3), 4);
	}

	@Test
	public void testThatGameEnds() {
		// TODO
	}

	@Test
	public void testThatTurnEnds() {
		// TODO
	}

	@Test
	public void testPreGameIteration() {
		// TODO
	}

	@Test
	public void testGetPlayerWithLongestRoadEqualLengths() throws Exception {
		// The player with older road should win if roads are the same length.

		setUpGameDavis();
		assertEquals(0, game.getPlayerWithLongestRoad());

		game.addRoad(1, 14, HexComponent.RoadPosition.northeast);
		assertEquals(1, game.getPlayerWithLongestRoad());

		game.addRoad(0, 16, HexComponent.RoadPosition.northeast);
		assertEquals(1, game.getPlayerWithLongestRoad());
	}

	@Test
	public void testGameEndsAfterPlayerHas10Points() throws Exception {
		setUpGameDavis();

		game.addRoad(0, 16, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 16, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 17, HexComponent.RoadPosition.north);
		game.addRoad(0, 17, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.southwest);

		// assertEquals(4, game.getVictoryPointsForPlayer(0));

		// assertEquals(-1, game.checkVictory());

		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.northwest);
		// assertEquals(5, game.getVictoryPointsForPlayer(0));

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);
		// assertEquals(6, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);
		// assertEquals(7, game.getVictoryPointsForPlayer(0));

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.southwest);
		// assertEquals(8, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.southwest);
		// assertEquals(9, game.getVictoryPointsForPlayer(0));

		assertEquals(-1, game.checkVictory());

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 17, HexComponent.StructurePosition.southwest);
		assertEquals(10, game.getVictoryPointsForPlayer(0));

		assertEquals(0, game.checkVictory());
	}

	@Test
	public void testGameEndsAfterPlayerHas11Points() throws Exception {
		setUpGameDavis();

		game.addRoad(0, 16, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 16, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 17, HexComponent.RoadPosition.north);
		game.addRoad(0, 17, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.southwest);

		// assertEquals(4, game.getVictoryPointsForPlayer(0));

		// assertEquals(-1, game.checkVictory());

		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.northwest);
		// assertEquals(5, game.getVictoryPointsForPlayer(0));

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);
		// assertEquals(6, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.east);
		// assertEquals(7, game.getVictoryPointsForPlayer(0));

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 16, HexComponent.StructurePosition.southwest);
		// assertEquals(8, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 16, HexComponent.StructurePosition.southwest);
		// assertEquals(9, game.getVictoryPointsForPlayer(0));

		// assertEquals(-1, game.checkVictory());

		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 17, HexComponent.StructurePosition.southwest);
		// assertEquals(10, game.getVictoryPointsForPlayer(0));

		// assertEquals(0, game.checkVictory());

		game.setBuildType(BuildType.city);
		game.addBuilding(0, 17, HexComponent.StructurePosition.southwest);
		assertEquals(11, game.getVictoryPointsForPlayer(0));

		assertEquals(0, game.checkVictory());
	}

	@Test
	public void testThatRobberPreventsResourceGathering() throws Exception {
		// player index 1 should have only 1 wood because of the robber on hex
		// 14.
		setUpGameDavis();

		game.endTurn(); // Change to player index 1's turn.
		game.setRobberLocation(14);

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Field hexfield = Game.class.getDeclaredField("hexArray");
		hexfield.setAccessible(true);

		ArrayList<Hex> hex = (ArrayList<Hex>) hexfield.get(game);

		Player[] players = (Player[]) (field.get(game));
		Player player = players[game.getCurrentPlayer()];

		game.roll();
		assertEquals(1, player.getCards()[1]);
	}

	@Test
	public void testThatBuildTypeIsSetToNoneAfterRobberIsMoved()
			throws Exception {
		setUpGameDavis();

		game.setRobberLocation(14);

		Field buildTypeField = Game.class.getDeclaredField("currentBuildType");
		buildTypeField.setAccessible(true);

		assertEquals(Game.BuildType.none, buildTypeField.get(game));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatMoveRobberToSameHexThrowsException() throws Exception {
		setUpGameDavis();

		game.setRobberLocation(14);

		game.setRobberLocation(14);
	}
	
	@Test
	public void testGetPlayersToStealFrom() throws Exception {
		setUpGameDavis();
		
		boolean[] players = game.getPlayersToStealFrom(14);
		assertFalse(players[0]);
		assertTrue(players[1]);
		
		players = game.getPlayersToStealFrom(16);
		assertTrue(players[0]);
		assertFalse(players[1]);
	}
	
	@Test
	public void testDrawRandomCardFromOpponent() throws Exception {
		setUpGameDavis();

		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player[] players = (Player[]) (field.get(game));
		int[] delta = { 1, 0, 0, 0, 0};
		players[0].adjustCards(delta);
		players[1].adjustCards(delta);
		
		Player curPlayer = players[game.getCurrentPlayer()];
		
		game.drawRandomCardFromOpponent(1);
		
		
		assertEquals(2, curPlayer.getCards()[0]);
	}
}
