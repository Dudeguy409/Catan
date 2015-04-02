package client.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * manages the rolling and drawing of the dice in the user panel.
 * 
 * @author Andrew Davidson. Created May 23, 2010.
 */
public class DiceRenderer extends JComponent {
	private static final int horizontalOffset = 0;
	private static final int horizontalSpacing = 70;
	private static final int verticalOffset = 10;
	private ArrayList<Shape> firstDotArray = new ArrayList<Shape>();
	private ArrayList<Shape> secondDotArray = new ArrayList<Shape>();
	private ArrayList<Shape> squareArray = new ArrayList<Shape>();
	private DiceComponent secondDice;
	private DiceComponent firstDice;

	/**
	 * sets the dimensions and rolls the dice once before the game starts.
	 * 
	 */
	public DiceRenderer() {
		this.setPreferredSize(new Dimension(150, 70));
		this.firstDice = new DiceComponent(horizontalOffset, verticalOffset,
				this.squareArray, this.firstDotArray);
		this.secondDice = new DiceComponent(horizontalOffset
				+ horizontalSpacing, verticalOffset, this.squareArray,
				this.secondDotArray);
		this.setDice(1, 1);
	}

	public void setDice(int firstRoll, int secondRoll) {
		this.firstDice.setRoll(firstRoll);
		this.secondDice.setRoll(secondRoll);
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		for (Shape shape : this.squareArray)
			g2.fill(shape);
		g2.setColor(Color.black);
		for (Shape shape : this.firstDotArray)
			g2.fill(shape);
		for (Shape shape : this.secondDotArray)
			g2.fill(shape);

	}
}
