package client.Model;

import client.GUI.HexComponent;
import client.GUI.HexComponent.RoadPosition;

public class LocationKey {

	private int hexIndex;
	private RoadPosition pos;

	public LocationKey(int hexIndex, HexComponent.RoadPosition pos) {
		this.hexIndex = hexIndex;
		this.pos = pos;
	}

	public int getHexIndex() {
		return this.hexIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		if (((LocationKey) o).getHexIndex() != this.hexIndex) {
			return false;
		}
		if (((LocationKey) o).getRoadPosition() != this.pos) {
			return false;
		}
		return true;
	}

	private RoadPosition getRoadPosition() {
		return this.pos;
	}

	@Override
	public int hashCode() {
		return this.hexIndex * 40 + this.pos.hashCode();
	}
}
