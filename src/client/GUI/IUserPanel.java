package client.GUI;

import client.Controller.Game;
import client.Controller.Game.TurnPhase;

public interface IUserPanel {

	void configureUserPanel(Game game);

	void setRolls(int[] rolls);

	void setCurrentPlayer(int currentPlayer);

	void setTurnPhase(TurnPhase preroll);

	void updateVPLabel(int playerNumber, int points);

	void setUpNormalGame();

	void resetBeginningMode();

	void setBeginningBuildSettlement();

	void updateResourceCards(int[] cards);

}
