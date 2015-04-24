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
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		Field woolField = Player.class.getDeclaredField("woolCount");
		Field wheatField = Player.class.getDeclaredField("wheatCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woolField.setAccessible(true);
		wheatField.setAccessible(true);

		woodField.set(player, 2);
		brickField.set(player, 2);
		woolField.set(player, 1);
		wheatField.set(player, 1);

		Player[] players = { new Player(), player };
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		game.roll();

		assertEquals(1, player.getCards()[2]);
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
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		Field woolField = Player.class.getDeclaredField("woolCount");
		Field wheatField = Player.class.getDeclaredField("wheatCount");
		Field oreField = Player.class.getDeclaredField("oreCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woolField.setAccessible(true);
		wheatField.setAccessible(true);
		oreField.setAccessible(true);

		woodField.set(player, 2);
		brickField.set(player, 2);
		woolField.set(player, 1);
		wheatField.set(player, 3);
		oreField.set(player, 3);

		Player[] players = { new Player(), player };
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.city);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		game.roll();
		assertEquals(2, player.getCards()[2]);
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
		assertEquals(0, player.getCards()[6]);
	}

	@Test
	public void testThatNormalGameStartsAfterPreGame() {
		setUpGameAndrew();
		assertEquals(game.getCurrentPlayer(), 1);
		assertEquals(game.getCurrentBuildType(), Game.BuildType.none);
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, );
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,);
		
		
	

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
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woodField.set(player, 1);
		brickField.set(player, 1);

		Player[] players = { new Player(), player };
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		assertEquals(0, woodField.get(player));
		assertEquals(0, brickField.get(player));
	}

	@Test
	public void testBuyAndBuildSettlement() throws Exception {
		setUpGameEthan();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		Field woolField = Player.class.getDeclaredField("woolCount");
		Field wheatField = Player.class.getDeclaredField("wheatCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woolField.setAccessible(true);
		wheatField.setAccessible(true);

		woodField.set(player, 2);
		brickField.set(player, 2);
		woolField.set(player, 1);
		wheatField.set(player, 1);

		Player[] players = { new Player(), player };
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		assertEquals(0, woodField.get(player));
		assertEquals(0, brickField.get(player));
		assertEquals(0, woolField.get(player));
		assertEquals(0, wheatField.get(player));
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
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		Field woolField = Player.class.getDeclaredField("woolCount");
		Field wheatField = Player.class.getDeclaredField("wheatCount");
		Field oreField = Player.class.getDeclaredField("oreCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woolField.setAccessible(true);
		wheatField.setAccessible(true);
		oreField.setAccessible(true);

		woodField.set(player, 2);
		brickField.set(player, 2);
		woolField.set(player, 1);
		wheatField.set(player, 3);
		oreField.set(player, 3);

		Player[] players = { new Player(), player };
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.city);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		assertEquals(0, woodField.get(player));
		assertEquals(0, brickField.get(player));
		assertEquals(0, woolField.get(player));
		assertEquals(0, wheatField.get(player));
		assertEquals(0, oreField.get(player));
	}

	@Test
	public void testVictoryPointForLongestRoad() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		assertEquals(0, game.getVictoryPointsForPlayer(0));
		
		game.addRoad(0, 0, HexComponent.RoadPosition.northeast);
		
		assertEquals(1, game.getVictoryPointsForPlayer(0));
	}

	@Test
	public void testAddOneVictoryPointForOneSettlement() throws Exception {
		setUpGameDavis();
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		
		game.addRoad(0, 0, HexComponent.RoadPosition.northeast);
		assertEquals(1, game.getVictoryPointsForPlayer(0));
		
		game.setBuildType(BuildType.settlement);
		game.addBuilding(0, 0, HexComponent.StructurePosition.northeast);
		
		assertEquals(2, game.getVictoryPointsForPlayer(0));
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

		assertEquals(game.getCurrentPlayer(), 1);

		game.addRoad(1, 0, HexComponent.RoadPosition.northeast);
		assertEquals(0, game.getPlayerWithLongestRoad());
		game.addRoad(1, 0, HexComponent.RoadPosition.southeast);
		assertEquals(1, game.getPlayerWithLongestRoad());
		
		game.addRoad(0, 0, HexComponent.RoadPosition.southwest);
		assertEquals(1, game.getPlayerWithLongestRoad());
		
	}

}
