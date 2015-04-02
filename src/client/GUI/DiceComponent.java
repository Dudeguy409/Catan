package client.GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DiceComponent extends JComponent {

	private static final int diceWidth = 60;
	private ArrayList<Shape> diceArray;
	private double horizontalOffset;
	private double verticalOffset;
	private Double middleDot;
	private Double upperLeftDot;
	private Double middleLeftDot;
	private Double lowerLeftDot;
	private Double upperRightDot;
	private Double middleRightDot;
	private Double lowerRightDot;
	private static final int dotWidth = 15;
	private static final int middleDotOffset = 30;
	private static final int upperOrLeftDotOffset = 13;
	private static final int lowOrRightDotOffset = 47;
	private static final int dotCenterShift = dotWidth / 2;

	public DiceComponent(int horizontaloffset, int verticaloffset,
			ArrayList<Shape> squareArray, ArrayList<Shape> dotArray) {
		this.horizontalOffset = horizontaloffset;
		this.verticalOffset = verticaloffset;
		this.diceArray = dotArray;
		squareArray.add(new Rectangle2D.Double(horizontalOffset,
				verticalOffset, diceWidth, diceWidth));

		this.middleDot = new Ellipse2D.Double(horizontalOffset + middleDotOffset
				- dotCenterShift, verticalOffset + middleDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.upperLeftDot = new Ellipse2D.Double(horizontalOffset + upperOrLeftDotOffset
				- dotCenterShift, verticalOffset + upperOrLeftDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.middleLeftDot = new Ellipse2D.Double(horizontalOffset + upperOrLeftDotOffset
				- dotCenterShift, verticalOffset + middleDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.lowerLeftDot = new Ellipse2D.Double(horizontalOffset + upperOrLeftDotOffset
				- dotCenterShift, verticalOffset + lowOrRightDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.upperRightDot = new Ellipse2D.Double(horizontalOffset + lowOrRightDotOffset
				- dotCenterShift, verticalOffset + upperOrLeftDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.middleRightDot = new Ellipse2D.Double(horizontalOffset + lowOrRightDotOffset
				- dotCenterShift, verticalOffset + middleDotOffset - dotCenterShift,
				dotWidth, dotWidth);
		this.lowerRightDot = new Ellipse2D.Double(horizontalOffset + lowOrRightDotOffset
				- dotCenterShift, verticalOffset + lowOrRightDotOffset - dotCenterShift,
				dotWidth, dotWidth);

	}

	// @Override
	// protected void paintComponent(Graphics g) {
	// super.paintComponent(g);
	// Graphics2D g2 = (Graphics2D) g;
	// g2.setColor(Color.white);
	// g2.fill(new Rectangle2D.Double(horizontalOffset, verticalOffset,
	// diceWidth, diceWidth));
	// g2.setColor(Color.black);
	// for (Ellipse2D.Double shape : this.diceArray)
	// g2.fill(shape);
	//
	// }

	/**
	 * first clears the old array of dots to show up on the dice and then
	 * replaces them based on two new randomly-generated simulated rolls.
	 * 
	 */
	public void setRoll(int roll) {
		this.diceArray.clear();
		switch (roll) {
		case 1:
			this.diceArray.add(this.middleDot);
			break;
		case 2:
			this.diceArray.add(this.upperLeftDot);
			this.diceArray.add(this.lowerRightDot);
			break;
		case 3:
			this.diceArray.add(this.upperLeftDot);
			this.diceArray.add(this.lowerRightDot);
			this.diceArray.add(this.middleDot);
			break;
		case 4:
			this.diceArray.add(this.upperLeftDot);
			this.diceArray.add(this.upperRightDot);
			this.diceArray.add(this.lowerRightDot);
			this.diceArray.add(this.lowerLeftDot);
			break;
		case 5:
			this.diceArray.add(this.upperLeftDot);
			this.diceArray.add(this.lowerRightDot);
			this.diceArray.add(this.middleDot);
			this.diceArray.add(this.upperRightDot);
			this.diceArray.add(this.lowerLeftDot);
			break;
		case 6:

			this.diceArray.add(this.upperLeftDot);
			this.diceArray.add(this.lowerRightDot);
			this.diceArray.add(this.upperRightDot);
			this.diceArray.add(this.lowerLeftDot);
			this.diceArray.add(this.middleRightDot);
			this.diceArray.add(this.middleLeftDot);
			break;
		}

		// this.repaint();
	}

}
