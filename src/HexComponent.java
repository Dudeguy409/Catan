import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * manages the information stored about each hexagon on the board.
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */
public class HexComponent {

	private double centerX;
	private double centerY;
	private Color resourceColor = new Color(0, 0, 0);
	private Shape hexagon;
	private int rollNumber;
	private int colonyDiameter = 30;
	/**
	 * (3^.5)/2, or sin of 60 degrees. this multiplied by the diameter gives the
	 * length from top to bottom, used for scaling height multipliers.
	 */
	public final static double Y_SCALAR = Math.sqrt(3) / 2;
	/**
	 * the radius from the center to the farthest point on the hexagon, e.g. any
	 * of the six edges
	 */
	public final static double RADIUS = 70;

	/**
	 * creates a hexagon out of lines given the center point, assigns the roll
	 * number, and chooses a color based on the type of resource.
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param colorSelector
	 * @param rollSelector
	 */
	public HexComponent(double xCoord, double yCoord,
			Game.Resource colorSelector, int rollSelector) {
		this.centerX = xCoord;
		this.centerY = yCoord;
		this.rollNumber = rollSelector;

		setColor(colorSelector);

		createShape();

	}

	private void setColor(Game.Resource colorSelector) {
		switch (colorSelector) {
		case desert:
			this.resourceColor = Game.desertColor;
			break;
		case wheat:
			this.resourceColor = Game.wheatColor;
			break;
		case sheep:
			this.resourceColor = Game.sheepColor;
			break;
		case wood:
			this.resourceColor = Game.woodColor;
			break;
		case ore:
			this.resourceColor = Game.oreColor;
			break;
		case brick:
			this.resourceColor = Game.brickColor;
			break;
		}

	}

	private void createShape() {
		Path2D hexagon = new Path2D.Double();
		hexagon.moveTo(this.centerX - HexComponent.RADIUS, this.centerY);
		hexagon.lineTo(this.centerX - HexComponent.RADIUS / 2, this.centerY
				- HexComponent.RADIUS * HexComponent.Y_SCALAR);
		hexagon.lineTo(this.centerX + HexComponent.RADIUS / 2, this.centerY
				- HexComponent.RADIUS * HexComponent.Y_SCALAR);
		hexagon.lineTo(this.centerX + HexComponent.RADIUS, this.centerY);
		hexagon.lineTo(this.centerX + HexComponent.RADIUS / 2, this.centerY
				+ HexComponent.RADIUS * HexComponent.Y_SCALAR);
		hexagon.lineTo(this.centerX - HexComponent.RADIUS / 2, this.centerY
				+ HexComponent.RADIUS * HexComponent.Y_SCALAR);
		hexagon.lineTo(this.centerX - HexComponent.RADIUS, this.centerY);
		this.hexagon = hexagon;

	}

	/**
	 * This method draws the hexagon on the board with its roll number on top.
	 * 
	 * @param g2
	 */
	public void drawHexComponent(Graphics2D g2) {
		g2.setColor(this.resourceColor);
		g2.fill(this.hexagon);
		g2.setColor(Color.black);
		g2.draw(this.hexagon);
		if (this.rollNumber > 0) {
			g2.setColor(Color.white);
			g2.fill(new Ellipse2D.Double(this.centerX - 15, this.centerY - 15,
					30, 30));
			g2.setColor(Color.black);
			String rollString = Integer.toString(this.rollNumber);
			g2.drawString(rollString, (int) this.centerX - 3,
					(int) this.centerY + 3);

		}
	}
	
	/**
	 * returns the y-value of the center.
	 * 
	 * @return centerY
	 */
	public double getY() {
		return this.centerY;
	}

	/**
	 * returns the x-value of the center.
	 * 
	 * @return centerX
	 */
	public double getX() {
		return this.centerX;
	}

	/**
	 * this method is called numerously to draw each individual structure AFTER
	 * all of the hexes have been drawn.
	 * 
	 * @param g2
	 * @param structure
	 * 
	 */
	public void drawStructures(Graphics2D g2, Structure structure) {
		Shape shapeToAdd = null;
		if (structure.getType() == 1)
			switch (structure.getPos()) {
			case 1:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						- HexComponent.RADIUS - this.colonyDiameter / 2,
						this.centerY - this.colonyDiameter / 2,
						this.colonyDiameter, this.colonyDiameter);
				break;
			case 2:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						- HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY - HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case 3:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						+ HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY - HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case -1:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						+ HexComponent.RADIUS - this.colonyDiameter / 2,
						this.centerY - this.colonyDiameter / 2,
						this.colonyDiameter, this.colonyDiameter);
				break;
			case -2:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						+ HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY + HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case -3:
				shapeToAdd = new Ellipse2D.Double(this.centerX
						- HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY + HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;

			}
		else if (structure.getType() == 2)
			switch (structure.getPos()) {
			case 1:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						- HexComponent.RADIUS - this.colonyDiameter / 2,
						this.centerY - this.colonyDiameter / 2,
						this.colonyDiameter, this.colonyDiameter);
				break;
			case 2:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						- HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY - HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case 3:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						+ HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY - HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case -1:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						+ HexComponent.RADIUS - this.colonyDiameter / 2,
						this.centerY - this.colonyDiameter / 2,
						this.colonyDiameter, this.colonyDiameter);
				break;
			case -3:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						- HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY + HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;
			case -2:
				shapeToAdd = new Rectangle2D.Double(this.centerX
						+ HexComponent.RADIUS / 2 - this.colonyDiameter / 2,
						this.centerY + HexComponent.RADIUS
								* HexComponent.Y_SCALAR - this.colonyDiameter
								/ 2, this.colonyDiameter, this.colonyDiameter);
				break;

			}
		else if (structure.getType() == 3)
			shapeToAdd = this.makeRoad(structure.getPos());
		g2.setColor(structure.getPlayerColor());
		g2.fill(shapeToAdd);
		g2.setColor(Color.black);
		g2.draw(shapeToAdd);

	}

	/**
	 * this method returns the road to be drawn. it is called from the
	 * drawStructure method sice the road-making algorithm was too bulky.
	 * 
	 * @param pos
	 * 
	 */
	private Shape makeRoad(int pos) {
		Path2D road = null;
		switch (pos) {
		case 1:
			road = new Path2D.Double();
			road.moveTo(this.centerX - HexComponent.RADIUS + 5,
					this.centerY - 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2, this.centerY
					- HexComponent.RADIUS * HexComponent.Y_SCALAR + 5);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 - 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			road.lineTo(this.centerX - HexComponent.RADIUS, this.centerY - 5);
			road.lineTo(this.centerX - HexComponent.RADIUS + 5,
					this.centerY - 3);
			break;
		case 2:
			road = new Path2D.Double();
			road.moveTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 - 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 - 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
							- 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
							- 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			break;
		case 3:
			road = new Path2D.Double();
			road.moveTo(this.centerX + HexComponent.RADIUS - 5,
					this.centerY - 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2, this.centerY
					- HexComponent.RADIUS * HexComponent.Y_SCALAR + 5);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 + 3,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			road.lineTo(this.centerX + HexComponent.RADIUS, this.centerY - 5);
			road.lineTo(this.centerX + HexComponent.RADIUS - 5,
					this.centerY - 3);
			break;
		case -1:
			road = new Path2D.Double();
			road.moveTo(this.centerX + HexComponent.RADIUS - 5,
					this.centerY + 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2, this.centerY
					+ HexComponent.RADIUS * HexComponent.Y_SCALAR - 5);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 + 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			road.lineTo(this.centerX + HexComponent.RADIUS, this.centerY + 5);
			road.lineTo(this.centerX + HexComponent.RADIUS - 5,
					this.centerY + 3);
			break;
		case -2:
			road = new Path2D.Double();
			road.moveTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 - 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			road.lineTo(this.centerX + HexComponent.RADIUS / 2 - 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
							- 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
							- 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 + 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
							+ 3);
			break;
		case -3:
			road = new Path2D.Double();
			road.moveTo(this.centerX - HexComponent.RADIUS + 5,
					this.centerY + 3);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2, this.centerY
					+ HexComponent.RADIUS * HexComponent.Y_SCALAR - 5);
			road.lineTo(this.centerX - HexComponent.RADIUS / 2 - 3,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			road.lineTo(this.centerX - HexComponent.RADIUS, this.centerY + 5);
			road.lineTo(this.centerX - HexComponent.RADIUS + 5,
					this.centerY + 3);
			break;
		}
		return road;

	}

	public void drawRobber(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

}