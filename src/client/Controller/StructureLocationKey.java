package client.Controller;

import client.GUI.HexComponent.StructurePosition;

public class StructureLocationKey {

	private StructurePosition pos;
	private int hexIndex;

	public StructureLocationKey(int hexIndex, StructurePosition pos) {
		this.hexIndex = hexIndex;
		this.pos = pos;
	}

	public int getHexIndex() {
		return this.hexIndex;
	}

	private StructurePosition getStructurePosition() {
		return this.pos;
	}

	@Override
	public int hashCode() {
		return this.hexIndex * 40 + this.pos.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		if (((StructureLocationKey) o).getHexIndex() != this.hexIndex) {
			return false;
		}
		if (((StructureLocationKey) o).getStructurePosition() != this.pos) {
			return false;
		}
		return true;
	}

}
