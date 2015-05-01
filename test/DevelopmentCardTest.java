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

import client.Controller.Game;
import client.Controller.Main;
import client.Controller.StructureManager;
import client.Controller.Game.BuildType;
import client.Controller.Game.Resource;
import client.GUI.BoardRenderer;
import client.GUI.HexComponent;
import client.GUI.UserPanel;
import client.Model.Player;

public class DevelopmentCardTest {
	Game game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;

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

		game = new Game(colors, resources, new FakeDice(arrayA, arrayB), 0,
				this.userPanel, this.board,
				Main.configureRandomNumberArray(resources));

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
		game.processBuildDevCard();
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

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildDevCard();
		game.useYearOfPlenty(cards);
		assertEquals(1, player.getCards()[0]);
		assertEquals(1, player.getCards()[1]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}
	
	@Test
	public void TestGetVictoryDevCard() throws Exception {
		setUpGameEthan();
		
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);

		Player[] players = { player, new Player() };
		field.set(game, players);
		game.processBuildDevCard();
		assertEquals(1, player.getVPs());
		assertEquals(1, player.getCards()[0]);
		assertEquals(1, player.getCards()[1]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}
	
	@Test
	public void TestUseMonopolyDevCard() throws Exception {
		setUpGameEthan();
		int card = 0;
		
		Field field = Game.class.getDeclaredField("players");
		field.setAccessible(true);

		Player player = new Player();
		int[] delta = { 1, 0, 1, 0, 1, 0 };
		player.adjustCards(delta);
		
		Player player2 = new Player();
		int[] delta2 = { 1, 0, 0, 0, 0, 0 };
		player2.adjustCards(delta2);

		Player[] players = { player, player2 };
		field.set(game, players);
		game.processBuildDevCard();
		game.useMonopoly(card);
		
		assertEquals(0, player2.getCards()[0]);
		assertEquals(1, player.getCards()[0]);
		assertEquals(0, player.getCards()[2]);
		assertEquals(0, player.getCards()[4]);
	}
}
