package client.Model;

public class StructurePiece {
	private int key;
	private int[] adjacentStructurePiecesA;
	private int[] adjacentStructurePiecesB;

	public StructurePiece(int key, int[] adjacentStructurePiecesA, int[] adjacentStructurePiecesB) {
		this.key = key;
		this.adjacentStructurePiecesA = adjacentStructurePiecesA;
		this.adjacentStructurePiecesB = adjacentStructurePiecesB;
	}
	
	public int[] getAdjacentStructurePiecesA() {
		return this.adjacentStructurePiecesA;
	}
	
	public int[] getAdjacentStructurePiecesB() {
		return this.adjacentStructurePiecesB;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("( ");
		sb.append(key);
		sb.append(" ( ");
		for (int i = 0; i < this.adjacentStructurePiecesA.length; i++) {
			sb.append(this.adjacentStructurePiecesA[i]);
			sb.append(" ");
		}
		sb.append(") ");
		sb.append(" ( ");
		for (int i = 0; i < this.adjacentStructurePiecesB.length; i++) {
			sb.append(this.adjacentStructurePiecesB[i]);
			sb.append(" ");
		}
		sb.append(") ");
		sb.append(" ) ");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this.getClass() != o.getClass()) {
			return false;
		}
		return this.toString().equals(((StructurePiece) o).toString());
	}

}

