public class RoadPiece {

	private int key;
	private int[] adjacentRoadsA;
	private int[] adjacentRoadsB;

	public RoadPiece(int key, int[] adjacentRoadsA, int[] adjacentRoadsB) {
		this.key = key;
		this.adjacentRoadsA = adjacentRoadsA;
		this.adjacentRoadsB = adjacentRoadsB;
	}

	public int[] getOppositeDirectionRoads(int roadKey) {
		if (roadKey < 0) {
			return getAllAdjacentRoads();
		}

		for (int i = 0; i < this.adjacentRoadsA.length; i++) {
			if (this.adjacentRoadsA[i] == roadKey) {
				return this.adjacentRoadsB;
			}
		}
		return this.adjacentRoadsA;
	}

	public int[] getAllAdjacentRoads() {
		int[] allAdjacentRoads = new int[adjacentRoadsA.length
				+ adjacentRoadsB.length];
		int index = 0;

		for (int i = 0; i < this.adjacentRoadsA.length; i++) {
			allAdjacentRoads[index] = this.adjacentRoadsA[i];
			index++;
		}

		for (int i = 0; i < this.adjacentRoadsB.length; i++) {
			allAdjacentRoads[index] = this.adjacentRoadsB[i];
			index++;
		}

		return allAdjacentRoads;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("( ");
		sb.append(key);
		sb.append(" ( ");
		for (int i = 0; i < this.adjacentRoadsA.length; i++) {
			sb.append(this.adjacentRoadsA[i]);
			sb.append(" ");
		}
		sb.append(") ");
		sb.append(" ( ");
		for (int i = 0; i < this.adjacentRoadsB.length; i++) {
			sb.append(this.adjacentRoadsB[i]);
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
		return this.toString().equals(((RoadPiece) o).toString());
	}

	public int getRoadValue() {
		return this.key;
	}

}
