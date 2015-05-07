package client.GUI;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class PortComponent {

	public static final int PORT_DIAMETER = 40;
	private Line2D.Double lineA;
	private Line2D.Double lineB;
	private Shape square;
	private Color color = Color.white;

	public PortComponent(Line2D.Double lineA, Line2D.Double lineB, Shape square) {
		this.lineA = lineA;
		this.lineB = lineB;
		this.square = square;
	}

	public Line2D.Double getLineA() {
		return this.lineA;
	}

	public Line2D.Double getLineB() {
		return this.lineB;
	}

	public Shape getSquare() {
		return square;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
