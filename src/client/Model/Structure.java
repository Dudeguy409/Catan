package client.Model;

public class Structure {

	private int[] adjacentStructures;
	private int id;

	public Structure(int key, int[] adjacentStructures) {
		this.id = key;
		this.adjacentStructures = adjacentStructures;
	}

	public int[] getAdjacentSettlements() {
		return this.adjacentStructures;
	}

	public Object getId() {
		return this.id;
	}
}
