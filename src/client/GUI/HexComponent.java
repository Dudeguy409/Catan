package client.GUI;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import client.Controller.Game;

/**
 * manages the information stored about each hexagon on the board.
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */
public class HexComponent {

	public static enum RoadPosition {
		north, northeast, southeast, south, southwest, northwest
	}

	public static enum StructurePosition {
		west, northeast, southeast, east, southwest, northwest
	}

	private double centerX;
	private double centerY;
	private Color resourceColor = new Color(0, 0, 0);
	private Shape hexagon;
	private int rollNumber;
	private int colonyDiameter = 30;
	private Shape rollNumberShape;
	private int rollNumberDiameter = 50;
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

		this.rollNumberShape = new Ellipse2D.Double(this.centerX
				- rollNumberDiameter / 2,
				this.centerY - rollNumberDiameter / 2, rollNumberDiameter,
				rollNumberDiameter);

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

	public Shape makeStructure(StructurePosition position,
			Game.BuildType structureType) {
		Shape shapeToAdd = null;
		double xCoord = 0;
		double yCoord = 0;
		switch (position) {
		case west:
			xCoord = this.centerX - HexComponent.RADIUS - this.colonyDiameter
					/ 2;
			yCoord = this.centerY - this.colonyDiameter / 2;
			break;
		case northwest:
			xCoord = this.centerX - HexComponent.RADIUS / 2
					- this.colonyDiameter / 2;
			yCoord = this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
					- this.colonyDiameter / 2;
			break;
		case northeast:
			xCoord = this.centerX + HexComponent.RADIUS / 2
					- this.colonyDiameter / 2;
			yCoord = this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR
					- this.colonyDiameter / 2;
			break;
		case east:
			xCoord = this.centerX + HexComponent.RADIUS - this.colonyDiameter
					/ 2;
			yCoord = this.centerY - this.colonyDiameter / 2;
			break;
		case southeast:
			xCoord = this.centerX + HexComponent.RADIUS / 2
					- this.colonyDiameter / 2;
			yCoord = this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
					- this.colonyDiameter / 2;
			break;
		case southwest:
			xCoord = this.centerX - HexComponent.RADIUS / 2
					- this.colonyDiameter / 2;
			yCoord = this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR
					- this.colonyDiameter / 2;
			break;
		}

		if (structureType == Game.BuildType.settlement) {
			shapeToAdd = new Ellipse2D.Double(xCoord, yCoord,
					this.colonyDiameter, this.colonyDiameter);
		} else if (structureType == Game.BuildType.city) {
			shapeToAdd = new Rectangle2D.Double(xCoord, yCoord,
					this.colonyDiameter, this.colonyDiameter);
		} else {
			// BAD PARAMS!!!
		}

		return shapeToAdd;

	}

	/**
	 * this method returns the road to be drawn. it is called from the
	 * drawStructure method sice the road-making algorithm was too bulky.
	 * 
	 * @param pos
	 * 
	 */
	public Shape makeRoad(RoadPosition pos) {
		Path2D road = null;
		switch (pos) {
		case northwest:
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
		case north:
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
		case northeast:
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
		case southeast:
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
		case south:
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
		case southwest:
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

	public Color getResourceColor() {
		return resourceColor;
	}

	public Shape getHexShape() {
		return hexagon;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public Shape getRollNumberShape() {
		return rollNumberShape;
	}

}
