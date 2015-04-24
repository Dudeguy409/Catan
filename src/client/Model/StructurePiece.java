package client.Model;

import client.Controller.Game.BuildType;

public class StructurePiece {

	private BuildType type;
	private int id;
	private int playerIndex;

	public StructurePiece(int id, int playerIndex) {
		this.id = id;
		this.type = BuildType.settlement;
		this.playerIndex=playerIndex;
	}

	public boolean upgrade() {
		if (this.type == BuildType.settlement) {
			this.type = BuildType.city;
			return true;
		}
		return false;
	}

	public BuildType getBuildType() {
		return this.type;
	}

	public int getStructureId() {
		return this.id;
	}
	
	public int getPlayerIndex(){
		return this.playerIndex;
	}

}
