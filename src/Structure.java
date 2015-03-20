import java.awt.Color;

/**
 * manages the data of each structure added to the board.
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */
public class Structure {

	private int playerNumber;
	private int position;
	private int hexNumber;
	private int structureType;
	private int neighborA;
	private int neighborB;
	private Color playerColor;

	/**
	 * constructs a new structure and stores all parameters as fields.
	 * 
	 * @param playerNumber
	 * @param position
	 * @param hexNumber
	 * @param structureType
	 * @param neighborB
	 * @param neighborA
	 * @param color
	 * @param neighborC
	 * @param border
	 * 
	 */
	public Structure(int playerNumber, int position, int structureType,
			int hexNumber, int neighborA, int neighborB, Color color) {
		this.playerNumber = playerNumber;
		this.playerColor = color;
		this.position = position;
		this.hexNumber = hexNumber;
		this.structureType = structureType;
		this.neighborA = neighborA;
		this.neighborB = neighborB;

	}

	/**
	 * gets the player number
	 * 
	 * @return player number
	 */
	public int getPlayer() {
		return this.playerNumber;
	}

	/**
	 * gets the index of the hexagon that this piece is built on. Even though
	 * all structures are built upon multiple hexes, they have a main hex for
	 * construction purposes.
	 * 
	 * @return hex index
	 */
	public int getHex() {
		return this.hexNumber;
	}

	/**
	 * gets the structure type
	 * 
	 * @return type of structure
	 */
	public int getType() {
		return this.structureType;
	}

	/**
	 * upgrades a settlement to a city. it is not implemented yet.
	 * 
	 * 
	 */
	public void upgrade() {
		this.structureType = 2;
	}

	/**
	 * gives the position on the hex where the structure is built. positions go
	 * 1,2,3,-1,-2,-3 starting at the negative x-axis and going clockwise.
	 * 
	 * @return position on hex
	 */
	public int getPos() {
		return this.position;
	}

	/**
	 * returns the 'closest' neighbor from where you clicked.  used in the .equals() method.
	 * 
	 * @return neighbor closest to mouse click.
	 */
	public int getNeighborA() {
		return this.neighborA;
	}

	/**
	 * returns the second closest neighbor from where you clicked.  used in the .equals() method.
	 * 
	 * @return neighbor second closest to mouse click.
	 */
	public int getNeighborB() {
		return this.neighborB;
	}

	/**
	 * gives the color of the structure to be drawn
	 * 
	 * @return the color of the structure to be drawn
	 */
	public Color getPlayerColor() {
		return this.playerColor;
	}

}
