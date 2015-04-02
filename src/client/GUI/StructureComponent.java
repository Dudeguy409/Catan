package client.GUI;
import java.awt.Color;
import java.awt.Shape;

public class StructureComponent {
	private Shape shape;
	private Color playerColor;

	public StructureComponent(Shape s, Color playerColor) {
		this.shape = s;
		this.playerColor = playerColor;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public Shape getShape() {
		return this.shape;
	}

}
