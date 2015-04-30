import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import client.Controller.Game;
import client.Controller.Game.Resource;
import client.Controller.Main;
import client.GUI.HexComponent;

public class GameTradeTest {

	private Game game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;

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

		this.userPanel = new FakeUserPanel();
		this.board = new FakeBoardRenderer();

		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 0,
				this.userPanel, this.board,
				Main.configureRandomNumberArray(resources));

		// gets the game out of the Pre-game set-up phase
		game.setBuildType(Game.BuildType.road);
		game.processBuildRoadClick(12, HexComponent.RoadPosition.south);
		game.setBuildType(Game.BuildType.settlement);
		game.processBuildStructureClick(12,
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
	public void testInsufficientBalanceBounces() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,1,0};
		int[] request = {0,1,0,0,0};
		boolean rslt = game.trade(1, offer, request);
		assertEquals(rslt, false);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}

	@Test
	public void testCantTradeNothing() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,0,0};
		int[] request = {0,1,0,0,0};
		boolean rslt = game.trade(1,offer, request);
		assertEquals(rslt, false);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}

	@Test
	public void testCantTradeForNothing() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,1,0};
		int[] request = {0,0,0,0,0};
		boolean rslt = game.trade(1, offer, request);
		assertEquals(rslt, false);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}

	@Test
	public void testOneResourceTradeSucceeds() throws Exception {
		setUpGameAndrew();
		int[] offer = { 0, 0, 0, 1, 0 };
		int[] request = { 0, 1, 0, 0, 0 };
		boolean rslt = game.trade(1, offer, request);
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);
		assertEquals(rslt, true);
		
		

	}

	@Test
	public void testCantTradeWithSelf() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,1,0};
		int[] request = {0,1,0,0,0};
		boolean rslt = game.trade(0, offer, request);
		assertEquals(rslt, false);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}

	@Test
	public void testLargerTradeOne() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,1,0};
		int[] request ={0,1,0,0,0};
		boolean rslt = game.trade(1, offer, request);
		assertEquals(rslt, true);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}

	@Test
	public void testLargerTradeTwo() throws Exception {
		setUpGameAndrew();
		int[] offer = {0,0,0,1,0};
		int[] request = {0,1,0,0,0};
		boolean rslt = game.trade(1, offer, request);
		assertEquals(rslt, true);
		
		int[] expected = { 0, 0, 0, 0, 0 };
		assertEquals(
				this.userPanel.resourceCardHistory.get(this.userPanel.resourceCardHistory
						.size() - 1), expected);

	}
}
