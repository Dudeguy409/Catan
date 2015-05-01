import java.util.ArrayList;

import client.Controller.Game;
import client.Controller.Game.TurnPhase;
import client.GUI.IUserPanel;

public class FakeUserPanel implements IUserPanel {

	protected Game game;
	protected boolean isConfigured = false;
	protected ArrayList<int[]> rollHistory = new ArrayList<>();
	protected int currentPlayer = -1;
	protected int setCurrentPlayerCount = 0;
	protected TurnPhase turnPhase = null;
	protected int setTurnPhaseCount = 0;
	protected int[] vpCounts = { 0, 0, 0, 0 };
	protected boolean hasCalledSetUpNormalGame = false;
	protected boolean hasCalledResetBeginningMode = false;
	protected boolean hasCalledSetBeginningBuildSettlement = false;
	protected ArrayList<int[]> resourceCardHistory = new ArrayList<>();

	@Override
	public void configureUserPanel(Game game) {
		this.game = game;
		this.isConfigured = true;
	}

	@Override
	public void setRolls(int[] rolls) {
		this.rollHistory.add(rolls);
	}

	@Override
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.setCurrentPlayerCount++;
	}

	@Override
	public void setTurnPhase(TurnPhase preroll) {
		this.turnPhase = preroll;
		this.setTurnPhaseCount++;
	}

	@Override
	public void updateVPLabel(int playerNumber, int points) {
		this.vpCounts[playerNumber] = points;
	}

	@Override
	public void setUpNormalGame() {
		this.hasCalledSetUpNormalGame = true;
	}

	@Override
	public void resetBeginningMode() {
		this.hasCalledResetBeginningMode = true;
	}

	@Override
	public void setBeginningBuildSettlement() {
		this.hasCalledSetBeginningBuildSettlement = true;
	}

	@Override
	public void updateResourceCards(int[] cards) {
		this.resourceCardHistory.add(cards);
	}

	@Override
	public void beginRobber() {
		
	}
	
	@Override
	public void endRobber() {
		
	}
}
