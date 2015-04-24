import static org.junit.Assert.*;

import java.awt.FlowLayout;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Generated;
import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import client.Controller.FakeDice;
import client.Controller.Game;
import client.Controller.StructureManager;
import client.Controller.Game.BuildType;
import client.Controller.Game.Resource;
import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.UserPanel;
import client.Model.Player;

public class GameTest {
	Game game;

	public void setUpGameEthan() throws Exception {
		Color[] colors = { new Color(2), new Color(3), new Color(40) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 0);
		Field field = Game.class.getDeclaredField("preGameMode");
		field.setAccessible(true);
		field.set(game, false);
	}

	public void setUpGameAndrew() throws Exception {
		Color[] colors = { new Color(2), new Color(3) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 0);
	}

	public void setUpGameDavis() throws Exception {
		Color[] colors = { new Color(2), new Color(3), new Color(32),
				new Color(55) };
		Game.Resource[] resources = { Resource.desert, Resource.wheat,
				Resource.wood, Resource.ore, Resource.brick, Resource.sheep,
				Resource.wood, Resource.brick, Resource.wheat, Resource.ore,
				Resource.sheep, Resource.wheat, Resource.wood, Resource.wheat,
				Resource.sheep, Resource.ore, Resource.wood, Resource.brick,
				Resource.sheep };
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6 };
		int[] arrayB = { 5, 2, 1, 4, 2, 3, 4, 6, 2, 6, 1, 2, 5, 2, 3, 4, 6, 1 };
		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 2);
	}

	@Test
	public void getDiceRollTest() {
		// TODO
		// assertTrue(false);
	}

	@Test
	public void TestPlayerQueueInitializes() throws Exception {
		setUpGameEthan();
		Field field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		Queue<Integer> prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values = { 1, 2, 3, 3, 2, 1 };
		Queue<Integer> expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values));

		assertEquals(expectedQueue, prePlayerQueue);
	}

	@Test
	public void getAdjacentRoadsForStructureTest() {
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
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 2, 1, 2, 0, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildRoadClick(15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(3, HexComponent.StructurePosition.northwest);
		game.roll();

		assertEquals(1, player.getCards()[4]);
	}

	@Test
	public void testAddResourcesOneCity() throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		
		game.setBoardRenderer(new BoardRenderer(game));
		game.roll();
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 3, 2, 1, 2, 3, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildRoadClick(3, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(3, HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.city);
		game.processBuildStructureClick(3, HexComponent.StructurePosition.northwest);
		game.roll();
		assertEquals(2, player.getCards()[4]);
	}

	@Test
	public void testAddResourcesOneCityAndOneSettlement() {
		// TODO
	}

	@Test
	public void testThatNoSettlementsAddsNoResources()
			throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		game.roll();
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player[] players = (Player[]) (field.get(game));
		Player player = players[game.getCurrentPlayer()];
		assertEquals(0, player.getCards()[0]);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[3]);
		assertEquals(0, player.getCards()[4]);
	}

	@Test
	public void testThatNormalGameStartsAfterPreGame() throws Exception {
		setUpGameAndrew();
		
		Field field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		Queue<Integer> prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values = { 1 , 0, 0 , 1 };
		Queue<Integer> expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values));

		assertEquals(expectedQueue, prePlayerQueue);
		
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.getCurrentBuildType(), Game.BuildType.none);
		assertEquals(game.isBeginningOfGame(), true);
		assertEquals(game.getVictoryPointsForPlayer(0),0);
		assertEquals(game.getVictoryPointsForPlayer(1),0);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.south);
		assertEquals(game.getVictoryPointsForPlayer(1),0);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,HexComponent.StructurePosition.southwest);
		assertEquals(game.getVictoryPointsForPlayer(1),1);
		assertEquals(game.getCurrentPlayer(), 0);
		
	
		 field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		 prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values2 = {  0, 0 , 1 };
		 expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values2));

		assertEquals(expectedQueue, prePlayerQueue);
		
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.southeast);
		assertEquals(game.getVictoryPointsForPlayer(0),0);
		assertEquals(game.getCurrentPlayer(), 0);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,HexComponent.StructurePosition.southeast);
		
		 field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		 prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		Integer[] values3 = { 0 , 1 };
		 expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values3));

		assertEquals(expectedQueue, prePlayerQueue);
		
		assertEquals(game.getVictoryPointsForPlayer(0),1);
		assertEquals(game.getCurrentPlayer(), 0);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(0),1);
		assertEquals(game.getCurrentPlayer(), 0);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(9,HexComponent.StructurePosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(0),2);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);
		
		
		 field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		 prePlayerQueue = (LinkedList<Integer>) (field.get(game));
		 Integer[] values4 = { 1 };
		 expectedQueue = new LinkedList<Integer>(
				Arrays.asList(values4));

		assertEquals(expectedQueue, prePlayerQueue);
		
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(14, HexComponent.RoadPosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(1),1);
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.isBeginningOfGame(), true);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(14,HexComponent.StructurePosition.northwest);
		assertEquals(game.getVictoryPointsForPlayer(1),2);
		assertEquals(game.getVictoryPointsForPlayer(0),2);
		assertEquals(game.getCurrentPlayer(), 0);
		assertEquals(game.isBeginningOfGame(), false);
		
		 field = Game.class.getDeclaredField("startingTurnsQueue");
		field.setAccessible(true);

		prePlayerQueue = (LinkedList<Integer>) (field.get(game));

		assertTrue(prePlayerQueue.isEmpty());
		
		
	

	}
	
	@Test
	public void testThatOnlyOneRoadCanBeAddedDuringEachTurnForEachPlayerInPreGame() throws Exception {
		setUpGameAndrew();
	
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.south);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.northwest);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,HexComponent.StructurePosition.southwest);
		assertEquals(game.getVictoryPointsForPlayer(0),1);
		assertEquals(game.getRoadCountForPlayer(0),1);

		
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(16, HexComponent.RoadPosition.southeast);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(16,HexComponent.StructurePosition.southeast);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northwest);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.north);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.northeast);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.southeast);
		game.processBuildRoadClick(9, HexComponent.RoadPosition.south);
		assertEquals(game.getVictoryPointsForPlayer(0),1);
		assertEquals(game.getRoadCountForPlayer(0),2);

		
		
		
		
	

	}
	
	

	// Tests for buying things
	// TODO add tests for buying development cards
	@Test
	public void testBuyAndBuildRoad() throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 0, 1, 0, 1, 0, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildRoadClick(15, HexComponent.RoadPosition.north);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[3]);
	}

	@Test
	public void testBuyAndBuildSettlement() throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 2, 1, 2, 0, 0 };
		player.adjustCards(delta);
		
		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildRoadClick(15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(15, HexComponent.StructurePosition.northwest);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[3]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[0]);
	}

	@Test
	public void testBuyAndBuildCity() throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		game.roll();
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 3, 2, 1, 2, 3, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildRoadClick(15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(15, HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.city);
		game.processBuildStructureClick(15, HexComponent.StructurePosition.northwest);
		assertEquals(0, player.getCards()[1]);
		assertEquals(0, player.getCards()[3]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[0]);
		assertEquals(0, player.getCards()[4]);
	}

	@Test
	public void testVictoryPointForLongestRoad() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		assertEquals(0, game.getVictoryPointsForPlayer(0));
		
		game.addRoad(0, 18, HexComponent.RoadPosition.north);
		game.addRoad(0, 18, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.south);
		
		assertEquals(0, game.getVictoryPointsForPlayer(0));
		
		game.addRoad(0, 18, HexComponent.RoadPosition.southwest);
		
		assertEquals(2, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testAddOneVictoryPointForOneSettlement() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		game.addRoad(0, 0, HexComponent.RoadPosition.northeast);
		assertEquals(0, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 0, HexComponent.StructurePosition.northeast);
		
		assertEquals(1, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testAddTwoVictoryPointsForOneCity() throws Exception {
		// TODO
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		assertEquals(0, game.getVictoryPointsForPlayer(3));
		
		game.addRoad(3, 3, HexComponent.RoadPosition.northeast);
		game.setBuildType(BuildType.settlement);
		game.addBuilding(3, 3, HexComponent.StructurePosition.northeast);
		assertEquals(1, game.getVictoryPointsForPlayer(3));
		
		game.setBuildType(BuildType.city);
		game.addBuilding(3, 3, HexComponent.StructurePosition.northeast);
		
		assertEquals(2, game.getVictoryPointsForPlayer(3));
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
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		assertEquals(null, game.getPlayerWithLongestRoad());
		
		game.addRoad(0, 0, HexComponent.RoadPosition.south);
		assertEquals(0, game.getPlayerWithLongestRoad());

		game.endTurn();

		game.addRoad(1, 0, HexComponent.RoadPosition.northeast);
		assertEquals(0, game.getPlayerWithLongestRoad());
		game.addRoad(1, 0, HexComponent.RoadPosition.southeast);
		assertEquals(1, game.getPlayerWithLongestRoad());
		
		game.addRoad(0, 0, HexComponent.RoadPosition.southwest);
		assertEquals(1, game.getPlayerWithLongestRoad());
	}
	
	@Test
	public void testGameEndsAfterPlayerHas10Points() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		game.addRoad(0, 18, HexComponent.RoadPosition.north);
		game.addRoad(0, 18, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.south);
		game.addRoad(0, 18, HexComponent.RoadPosition.southwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.north);
		game.addRoad(0, 17, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.southwest);
		game.addRoad(0, 11, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 11, HexComponent.RoadPosition.southwest);
		
		assertEquals(2, game.getVictoryPointsForPlayer(0));
		
		assertEquals(-1, game.checkVictory());
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 18, HexComponent.StructurePosition.northeast);
		assertEquals(3, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 18, HexComponent.StructurePosition.northeast);
		assertEquals(4, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 18, HexComponent.StructurePosition.southwest);
		assertEquals(5, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 18, HexComponent.StructurePosition.southwest);
		assertEquals(6, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 17, HexComponent.StructurePosition.northwest);
		assertEquals(7, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 17, HexComponent.StructurePosition.northwest);
		assertEquals(8, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 11, HexComponent.StructurePosition.northwest);
		assertEquals(9, game.getVictoryPointsForPlayer(0));
		
		assertEquals(-1, game.checkVictory());
		
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 11, HexComponent.StructurePosition.northwest);
		assertEquals(10, game.getVictoryPointsForPlayer(0));
		
		assertEquals(0, game.checkVictory());
	}
	
	@Test
	public void testGameEndsAfterPlayerHas11Points() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		game.addRoad(0, 18, HexComponent.RoadPosition.north);
		game.addRoad(0, 18, HexComponent.RoadPosition.northeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.southeast);
		game.addRoad(0, 18, HexComponent.RoadPosition.south);
		game.addRoad(0, 18, HexComponent.RoadPosition.southwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.north);
		game.addRoad(0, 17, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 17, HexComponent.RoadPosition.southwest);
		game.addRoad(0, 11, HexComponent.RoadPosition.northwest);
		game.addRoad(0, 11, HexComponent.RoadPosition.southwest);
		
		assertEquals(2, game.getVictoryPointsForPlayer(0));
		
		assertEquals(-1, game.checkVictory());
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 18, HexComponent.StructurePosition.northeast);
		assertEquals(3, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 18, HexComponent.StructurePosition.northeast);
		assertEquals(4, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 18, HexComponent.StructurePosition.southwest);
		assertEquals(5, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 18, HexComponent.StructurePosition.southwest);
		assertEquals(6, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 17, HexComponent.StructurePosition.northwest);
		assertEquals(7, game.getVictoryPointsForPlayer(0));
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 17, HexComponent.StructurePosition.northwest);
		assertEquals(8, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 11, HexComponent.StructurePosition.northwest);
		assertEquals(9, game.getVictoryPointsForPlayer(0));
		
		assertEquals(-1, game.checkVictory());
		
		game.setBuildType(BuildType.city);
		game.addBuilding(0, 11, HexComponent.StructurePosition.northwest);
		assertEquals(10, game.getVictoryPointsForPlayer(0));
		
		assertEquals(0, game.checkVictory());
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 11, HexComponent.StructurePosition.southwest);
		assertEquals(11, game.getVictoryPointsForPlayer(0));
		
		assertEquals(0, game.checkVictory());
	}

}
