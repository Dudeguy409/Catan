public class Road {
	private int key;
	private int[] adjacentRoadsA;
	private int[] adjacentRoadsB;

	public Road(int key, int[] adjacentRoadsA, int[] adjacentRoadsB) {
		this.key = key;
		this.adjacentRoadsA = adjacentRoadsA;
		this.adjacentRoadsB = adjacentRoadsB;
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
		return this.toString().equals(((Road) o).toString());
	}

}
