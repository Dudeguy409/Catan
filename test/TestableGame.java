import java.awt.Color;
import java.util.LinkedList;
import java.util.Set;
import client.Controller.Game;
import client.Controller.IDice;
import client.GUI.IBoardRenderer;
import client.GUI.IUserPanel;

public class TestableGame extends Game {

	protected boolean victoryTriggered = false;
	private LinkedList<Resource> monopolySelections;
	private LinkedList<Resource> yearOfPlentySelections;
	private LinkedList<Integer> playerStealSelections;
	private LinkedList<Integer> playerTradeSelections;
	private LinkedList<Integer> resourceToStealSelections;
	private LinkedList<Integer> robberMoveSelections;
	public int sameHexRobberErrorCount = 0;

	public TestableGame(Color[] pColors, Resource[] hexResources, IDice dice,
			int startingPlayer, IUserPanel userPanel, IBoardRenderer board,
			int[] randomNumberArray, LinkedList<DevCard> devCardDeck,
			Resource[] portTypes) {
		super(pColors, hexResources, dice, startingPlayer, userPanel, board,
				randomNumberArray, devCardDeck, portTypes);
	}

	protected void configureTestableGame(
			LinkedList<Resource> monopolySelections,
			LinkedList<Resource> yearOfPlentySelections,
			LinkedList<Integer> playerStealSelections,
			LinkedList<Integer> playerTradeSelections,
			LinkedList<Integer> robberMoveSelections,
			LinkedList<Integer> resourceToStealSelections) {

		this.monopolySelections = monopolySelections;
		this.yearOfPlentySelections = yearOfPlentySelections;
		this.playerStealSelections = playerStealSelections;
		this.playerTradeSelections = playerTradeSelections;
		this.resourceToStealSelections = resourceToStealSelections;
		this.robberMoveSelections = robberMoveSelections;

	}

	@Override
	protected void displayVictory() {
		this.victoryTriggered = true;
	}

	@Override
	protected Resource selectResourceForMonopoly() {
		return this.monopolySelections.poll();
	}

	@Override
	public Resource selectResourceForYearOfPlenty(boolean first) {
		return this.yearOfPlentySelections.poll();
	}

	@Override
	public int selectPlayerToStealFrom(Set<Integer> possiblePlayers) {
		return this.playerStealSelections.poll();
	}

	@Override
	public int selectPlayerToTradeWith() {
		return this.playerTradeSelections.poll();
	}

	@Override
	protected void createPlayerTradeFrame(int rslt) {
		// does nothing. Tests that wish to implement trading should call
		// game.adjustCardsForPlayer(...) just like a playerFrame would.
	}

	@Override
	protected void displayDiscardFrame(int i) {
		// does nothing. Tests that wish to implement discarding should call
		// game.adjustCardsForPlayer(...) just like a DiscardFrame would.
	}

	// TODO since these two methods are void, these methods could be called in
	// the test rather than here, if preferred.
	@Override
	protected void displayMoveRobberMessage() {
		setRobberLocation(this.robberMoveSelections.poll());
	}

	@Override
	protected void drawRandomCardFromOpponent(int playerToStealFrom) {
		System.out.println(this.resourceToStealSelections);
		int r = this.resourceToStealSelections.poll();
		this.players[playerToStealFrom].adjustCards(r, -1);
		this.players[this.currentPlayer].adjustCards(r, 1);
	}

	@Override
	protected void displayDrawnDevCard(DevCard d) {
		// does nothing.
	}

	@Override
	protected void displayCardStolen(Resource resource, int player) {
		// does nothing.

	}

	@Override
	protected void displayRobberErrorMessage() {
		this.sameHexRobberErrorCount++;
	}

	@Override
	protected void displayPlayDevCardError() {
		// does nothing.
	}

}
