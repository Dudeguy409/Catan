import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import client.Controller.Game;
import client.Controller.Main;
import client.Controller.Game.Resource;
import client.GUI.HexComponent;

public class GameBankTradeTest {

	private TestableGame game;
	private FakeBoardRenderer board;
	private FakeUserPanel userPanel;
	private LinkedList<Game.DevCard> devCards;

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
				Main.configureRandomNumberArray(resources), this.devCards);

		LinkedList<Integer> playerStealSelections = new LinkedList<Integer>();
		LinkedList<Integer> robberMoveSelections = new LinkedList<Integer>();
		LinkedList<Resource> resourceToStealSelections = new LinkedList<Game.Resource>();

		resourceToStealSelections.add(Resource.sheep);
		robberMoveSelections.add(1);
		playerStealSelections.add(0);

		game.configureTestableGame(null, null, playerStealSelections, null,
				robberMoveSelections, resourceToStealSelections);

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

}
