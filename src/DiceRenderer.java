import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * manages the rolling and drawing of the dice in the user panel.
 * 
 * @author Andrew Davidson. Created May 23, 2010.
 */
public class DiceRenderer extends JComponent {
	//private int diceHeight = 60;
	private ArrayList<Ellipse2D.Double> diceArray = new ArrayList<Ellipse2D.Double>();
	private int diceArraySize = 0;

	// private int dotWidth = 15;

	/**
	 * sets the dimensions and rolls the dice once before the game starts.
	 * 
	 */
	public DiceRenderer() {
		this.setPreferredSize(new Dimension(150, 70));
		this.rollDice();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Double(0, 10, 60, 60));
		g2.fill(new Rectangle2D.Double(70, 10, 60, 60));
		g2.setColor(Color.black);
		for (Ellipse2D.Double shape : this.diceArray)
			g2.fill(shape);

	}

	/**
	 * first clears the old array of dots to show up on the dice and then
	 * replaces them based on two new randomly-generated simulated rolls.
	 * 
	 * @return roll number
	 */
	public int rollDice() {
		int[] temp = new int[2];
		temp[0] = ((int) (Math.random() * 5 + 1));
		temp[1] = ((int) (Math.random() * 5 + 1));
		this.diceArray.clear();
		this.diceArraySize = 0;
		for (int i = 0; i < 2; i++) {
			int rectShift = 0;
			if (i == 1)
				rectShift = 70;
			switch (temp[i]) {
			case 1:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 30 - 7,
						10 + 30 - 7, 15, 15));
				this.diceArraySize += 1;
				break;
			case 2:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 60 - 13 - 7, 15, 15));
				this.diceArraySize += 2;
				break;
			case 3:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 30 - 7,
						10 + 30 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 60 - 13 - 7, 15, 15));
				this.diceArraySize += 3;
				break;
			case 4:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 13 - 7, 15, 15));
				this.diceArraySize += 4;
				break;
			case 5:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 30 - 7,
						10 + 30 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 13 - 7, 15, 15));
				this.diceArraySize += 5;
				break;
			case 6:
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 60 - 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 13 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(rectShift + 13 - 7,
						10 + 30 - 7, 15, 15));
				this.diceArray.add(new Ellipse2D.Double(
						rectShift + 60 - 13 - 7, 10 + 30 - 7, 15, 15));
				this.diceArraySize += 6;
				break;
			}
		}
		this.repaint();
		return temp[0] + temp[1];
	}
}
