import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.Controller.Game;
import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.UserPanel;
import client.Model.Player;

public class GameTest {
	Game game;

	@Before
	public void setUp() throws Exception {
		Color[] colors = {new Color(2), new Color(3)};
		game = new Game(2, colors);
	}

	@Test
	public void getDiceRollTest() {
		// TODO
		// assertTrue(false);
	}
	
	@Test
	public void TestPlayerQueueInitializes() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Game.class.getDeclaredField("prePlayerQueue");
		field.setAccessible(true);

		Queue<Integer> prePlayerQueue = (LinkedList<Integer>)(field.get(game));
		Integer[] values = {1, 2, 3, 3, 2, 1};
		Queue<Integer> expectedQueue = new LinkedList<Integer>(Arrays.asList(values));
		
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
	public void testThatOneRollAddsResources() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
		
		Player[] players = {new Player(), player};
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		//game.roll(2);
		
		assertEquals(1, player.getCards()[2]);
	}
	
	@Test
	public void testAddResourcesOneSettlement() {
		//TODO
	}
	
	@Test
	public void testAddResourcesOneCity() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
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
		
		Player[] players = {new Player(), player};
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		game.setBuildType(Game.BuildType.settlement);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		game.setBuildType(Game.BuildType.city);
		game.addBuilding(1, 15, HexComponent.StructurePosition.northwest);
		//game.roll(2);
		assertEquals(2, player.getCards()[2]);
	}
	
	@Test
	public void testAddResourcesOneCityAndOneSettlement() {
		//TODO
	}
	
	@Test
	public void testThatNoSettlementsAddsNoResources() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		game.setUserPanel(new UserPanel(game));
		game.roll();
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player[] players = (Player[])(field.get(game));
		Player player = players[game.getCurrentPlayer()];
		assertEquals(0, player.getCards()[6]);
	}
	
	@Test
	public void testThatNormalGameStartsAfterPreGame() {
		//TODO
	}
	
	//Tests for buying things
	//TODO add tests for buying development cards
	@Test
	public void testBuyAndBuildRoad() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
		
		Player[] players = {new Player(), player};
		field.set(game, players);
		game.addRoad(1, 15, HexComponent.RoadPosition.north);
		assertEquals(0, woodField.get(player));
		assertEquals(0, brickField.get(player));
	}
	
	@Test
	public void testBuyAndBuildSettlement() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
		
		Player[] players = {new Player(), player};
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
	public void testBuyAndBuildCity() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
		
		Player[] players = {new Player(), player};
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
	
	//TODO Victory Point tests - add more
	@Test
	public void testVictoryPointForLongestRoad() {
		//TODO
	}
	
	@Test
	public void testAddOneVictoryPointForOneSettlement() {
		//TODO
	}
	
	@Test
	public void testAddTwoVictoryPointsForOneCity() {
		//TODO
	}
	
	@Test
	public void testThatGameEnds() {
		//TODO
	}
	
	@Test
	public void testThatTurnEnds() {
		//TODO
	}
	
	@Test
	public void testPreGameIteration() {
		//TODO
	}

}
