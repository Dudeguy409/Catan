import java.awt.Color;

import client.Controller.Game;
import client.Controller.Game.BuildType;
import client.GUI.HexComponent.RoadPosition;
import client.GUI.HexComponent.StructurePosition;
import client.GUI.IBoardRenderer;

public class FakeBoardRenderer implements IBoardRenderer {

	protected int buildingsAddedCount = 0;
	protected int roadsAddedCount = 0;
	protected boolean hasBoardBeenSet = false;
	protected boolean hasGameBeenSet = false;
	protected Game game = null;

	@Override
	public void setGame(Game game) {
		this.game = game;
		this.hasGameBeenSet = true;
	}

	@Override
	public void setBoard() {
		this.hasBoardBeenSet = true;
	}

	@Override
	public void addRoad(int hexId, RoadPosition pos, Color color, BuildType road) {
		this.roadsAddedCount++;
	}

	@Override
	public void addBuilding(int hexId, StructurePosition pos, Color color,
			BuildType settlement) {
		this.buildingsAddedCount++;
	}

	@Override
	public void moveRobber(int hexId){
		
	}
}
