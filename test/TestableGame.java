import java.awt.Color;
import java.util.LinkedList;
import client.Controller.Game;
import client.Controller.IDice;
import client.GUI.IBoardRenderer;
import client.GUI.IUserPanel;

public class TestableGame extends Game {

	public TestableGame(Color[] pColors, Resource[] hexResources, IDice dice,
			int startingPlayer, IUserPanel userPanel, IBoardRenderer board,
			int[] randomNumberArray, LinkedList<DevCard> devCardDeck) {
		super(pColors, hexResources, dice, startingPlayer, userPanel, board,
				randomNumberArray, devCardDeck);
	}

	@Override
	protected void displayVictory() {
		// TODO
	}

	@Override
	protected Resource selectResourceForMonopoly() {
		// TODO
		return null;
	}

	@Override
	public Resource selectResourceForYearOfPlenty(boolean first) {
		// TODO
		return null;

	}

	@Override
	public int selectPlayerToStealFrom() {
		// TODO
		return -1;
	}

	@Override
	public int selectPlayerToTradeWith() {
		// TODO
		return -1;

	}

	@Override
	protected void createPlayerTradeFrame(int rslt) {
		// TODO
	}

	@Override
	protected void displayDiscardFrame(int i) {
		// TODO
	}

	@Override
	protected void displaySevenRolledMessage() {
		// TODO
	}

}
