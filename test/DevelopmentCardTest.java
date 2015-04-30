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

public class DevelopmentCardTest {
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
	
	@Test
	public void TestBuyYearOfPlenty() throws Exception {
		setUpGameEthan();
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(0);
		cards.add(1);
		game.setUserPanel(new UserPanel(game));
		game.setBoardRenderer(new BoardRenderer(game));
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildYearOfPlenty(cards);
		assertEquals(1, player.getCards()[0]);
		assertEquals(1, player.getCards()[1]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}

}
