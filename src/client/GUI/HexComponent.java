package client.GUI;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Point2D;
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

	public PortComponent makePort(RoadPosition pos) {

		Line2D.Double lineA = null;
		Line2D.Double lineB = null;
		Point2D.Double mergePoint = null;
		Point2D.Double pointA = null;
		Point2D.Double pointB = null;
		switch (pos) {
		case north:
			mergePoint = new Point2D.Double(this.centerX, this.centerY
					- HexComponent.RADIUS * 2 * HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX - HexComponent.RADIUS / 2,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			pointB = new Point2D.Double(this.centerX + HexComponent.RADIUS / 2,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		case south:
			mergePoint = new Point2D.Double(this.centerX, this.centerY
					+ HexComponent.RADIUS * 2 * HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX - HexComponent.RADIUS / 2,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			pointB = new Point2D.Double(this.centerX + HexComponent.RADIUS / 2,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		case northeast:
			mergePoint = new Point2D.Double(this.centerX + HexComponent.RADIUS
					* 1.5, this.centerY - HexComponent.RADIUS
					* HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX + HexComponent.RADIUS,
					this.centerY);
			pointB = new Point2D.Double(this.centerX + HexComponent.RADIUS / 2,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		case southeast:
			mergePoint = new Point2D.Double(this.centerX + HexComponent.RADIUS
					* 1.5, this.centerY + HexComponent.RADIUS
					* HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX + HexComponent.RADIUS,
					this.centerY);
			pointB = new Point2D.Double(this.centerX + HexComponent.RADIUS / 2,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		case northwest:
			mergePoint = new Point2D.Double(this.centerX - HexComponent.RADIUS
					* 1.5, this.centerY - HexComponent.RADIUS
					* HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX - HexComponent.RADIUS,
					this.centerY);
			pointB = new Point2D.Double(this.centerX - HexComponent.RADIUS / 2,
					this.centerY - HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		case southwest:
			mergePoint = new Point2D.Double(this.centerX - HexComponent.RADIUS
					* 1.5, this.centerY + HexComponent.RADIUS
					* HexComponent.Y_SCALAR);
			pointA = new Point2D.Double(this.centerX - HexComponent.RADIUS,
					this.centerY);
			pointB = new Point2D.Double(this.centerX - HexComponent.RADIUS / 2,
					this.centerY + HexComponent.RADIUS * HexComponent.Y_SCALAR);
			break;
		default:
			break;
		}

		Ellipse2D.Double circle = new Ellipse2D.Double(mergePoint.getX()
				- PortComponent.PORT_DIAMETER / 2, mergePoint.getY()
				- PortComponent.PORT_DIAMETER / 2, PortComponent.PORT_DIAMETER,
				PortComponent.PORT_DIAMETER);

		lineA = new Line2D.Double(mergePoint, pointA);
		lineB = new Line2D.Double(mergePoint, pointB);
		return new PortComponent(lineA, lineB, circle);

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
	
	public static StructurePosition[] getAdjacentStructurePositionsForRoad(
			RoadPosition pos) {
		switch (pos) {
		case north:
			HexComponent.StructurePosition[] arrayToReturn = {
					HexComponent.StructurePosition.northwest,
					HexComponent.StructurePosition.northeast };
			return arrayToReturn;
		case northwest:
			HexComponent.StructurePosition[] arrayToReturn2 = {
					HexComponent.StructurePosition.northwest,
					HexComponent.StructurePosition.west };
			return arrayToReturn2;
		case northeast:
			HexComponent.StructurePosition[] arrayToReturn3 = {
					HexComponent.StructurePosition.east,
					HexComponent.StructurePosition.northeast };
			return arrayToReturn3;
		case south:
			HexComponent.StructurePosition[] arrayToReturn4 = {
					HexComponent.StructurePosition.southwest,
					HexComponent.StructurePosition.southeast };
			return arrayToReturn4;
		case southeast:
			HexComponent.StructurePosition[] arrayToReturn5 = {
					HexComponent.StructurePosition.east,
					HexComponent.StructurePosition.southeast };
			return arrayToReturn5;
		case southwest:
			HexComponent.StructurePosition[] arrayToReturn6 = {
					HexComponent.StructurePosition.west,
					HexComponent.StructurePosition.southwest };
			return arrayToReturn6;
		default:
			return null;
		}

	}
	
	public static HexComponent.RoadPosition[] getAdjacentRoadPositionsForStructure(
			StructurePosition pos) {
		switch (pos) {
		case west:
			HexComponent.RoadPosition[] arrayToReturn = {
					HexComponent.RoadPosition.northwest,
					HexComponent.RoadPosition.southwest };
			return arrayToReturn;
		case northwest:
			HexComponent.RoadPosition[] arrayToReturn2 = {
					HexComponent.RoadPosition.northwest,
					HexComponent.RoadPosition.north };
			return arrayToReturn2;
		case northeast:
			HexComponent.RoadPosition[] arrayToReturn3 = {
					HexComponent.RoadPosition.north,
					HexComponent.RoadPosition.northeast };
			return arrayToReturn3;
		case east:
			HexComponent.RoadPosition[] arrayToReturn4 = {
					HexComponent.RoadPosition.northeast,
					HexComponent.RoadPosition.southeast };
			return arrayToReturn4;
		case southeast:
			HexComponent.RoadPosition[] arrayToReturn5 = {
					HexComponent.RoadPosition.south,
					HexComponent.RoadPosition.southeast };
			return arrayToReturn5;
		case southwest:
			HexComponent.RoadPosition[] arrayToReturn6 = {
					HexComponent.RoadPosition.south,
					HexComponent.RoadPosition.southwest };
			return arrayToReturn6;
		default:
			return null;
		}

	}

}
