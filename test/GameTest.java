import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import client.Controller.Game;
import client.Controller.StructureManager;
import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.UserPanel;
import client.Model.Player;

public class GameTest {
	Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	@Test
	public void getDiceRollTest() {
		// TODO
		// assertTrue(false);
	}

	@Test
	public void TestPlayerQueueInitializes() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Game.class.getDeclaredField("prePlayerQueue");
		field.setAccessible(true);

		Queue prePlayerQueue = new LinkedList<Integer>();

		field.set(game, prePlayerQueue);
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
		// TODO
	}

	@Test
	public void testAddResourcesOneSettlement() {
		// TODO
	}

	@Test
	public void testAddResourcesOneCity() {
		// TODO
	}

	@Test
	public void testAddResourcesOneCityAndOneSettlement() {
		// TODO
	}

	@Test
	public void testThatNoSettlementsAddsNoResources() {
		// TODO

	}

	@Test
	public void testThatNormalGameStartsAfterPreGame() {
		// TODO
	}

	// Tests for buying things
	// TODO add tests for buying development cards
	@Test
	public void testBuyAndBuildRoad() {
		// TODO
	}

	@Test
	public void testBuyAndBuildSettlement() {
		// TODO
	}

	@Test
	public void testBuyAndBuildCity() {
		// TODO
	}

	// TODO Victory Point tests - add more
	@Test
	public void testVictoryPointForLongestRoad() {
		// TODO
	}

	@Test
	public void testAddOneVictoryPointForOneSettlement() {
		// TODO
	}

	@Test
	public void testAddTwoVictoryPointsForOneCity() {
		// TODO
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
	public void testCalculateVictoryPointsForPlayerFromStructures()
			throws Exception {

		Player player = new Player();
		StructureManager sManager = new StructureManager(3);

		sManager.addStructure(0, 30);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 1);
		
		sManager.addStructure(0,25);
		assertEquals(sManager.calculateStructureVictoyPointsForPlayer(0), 2);

	}

}
