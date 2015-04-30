import java.awt.Color;

import org.junit.Test;

import client.Controller.Game;
import client.Controller.Game.Resource;
import client.Controller.Main;


public class GameTradeTest {

	private Game game;


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
		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 0, new FakeUserPanel(), new FakeBoardRenderer(), Main.configureRandomNumberArray(resources));
	}
	
	
	@Test
	public void testInsufficientBalanceBounces() {
		// TODO
		// assertTrue(false);
	}
	
	
}
