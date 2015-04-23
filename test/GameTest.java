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
	public void testThatOneRollAddsResources() {
		
	}
	
	@Test
	public void testAddResourcesOneSettlement() {
		//TODO
	}
	
	@Test
	public void testAddResourcesOneCity() {
		//TODO
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
		game.roll();
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);
		
		Player player = new Player();
		Field woodField = Player.class.getDeclaredField("woodCount");
		Field brickField = Player.class.getDeclaredField("brickCount");
		woodField.setAccessible(true);
		brickField.setAccessible(true);
		woodField.set(player, 1);
		brickField.set(player, 1);
		
		Player[] players = {new Player(), new Player(), player};
		field.set(game, players);
		game.addRoad(game.getCurrentPlayer(), 15, HexComponent.RoadPosition.north);
		assertEquals(0, woodField);
		assertEquals(0, brickField);
	}
	
	@Test
	public void testBuyAndBuildSettlement() {
		//TODO
	}
	
	@Test
	public void testBuyAndBuildCity() {
		//TODO
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
