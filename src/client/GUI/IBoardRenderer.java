package client.GUI;

import java.awt.Color;

import client.Controller.Game;
import client.Controller.Game.BuildType;
import client.GUI.HexComponent.RoadPosition;
import client.GUI.HexComponent.StructurePosition;

public interface IBoardRenderer {

	void setGame(Game game);

	void setBoard();

	void addRoad(int hexId, RoadPosition pos, Color color, BuildType road);

	void addBuilding(int hexId, StructurePosition pos, Color color,
			BuildType settlement);
	
	void moveRobber(int hexId);

}
