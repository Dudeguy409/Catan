package client.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * This class creates a reference table of the prices to buy or build stuff.
 * 
 * @author Andrew Davidson. Created May 28, 2010.
 */
public class ReferenceComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276337706051044624L;

	/**
	 * sets the frame size for the reference table.
	 * 
	 */
	public ReferenceComponent() {
		this.setPreferredSize(new Dimension(330, 250));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(0, 0, 300, 140));
		//makes the labels
		g2.setColor(Color.yellow);
		g2.setFont(new Font("Times New Roman", 1, 18));
		g2.drawString("Settlement", 30, 20);
		g2.drawString("City", 200, 20);
		g2.drawString("Road", 200, 85);
		g2.drawString("Development Card", 10, 85);
		//makes cards for the settlement label
		g2.setColor(new Color(0, 100, 0));
		g2.fill(new Rectangle2D.Double(15, 25, 26, 40));
		g2.setColor(new Color(200, 70, 0));
		g2.fill(new Rectangle2D.Double(15 + 26, 25, 26, 40));
		g2.setColor(Color.yellow);
		g2.fill(new Rectangle2D.Double(15 + 52, 25, 26, 40));
		g2.setColor(Color.green);
		g2.fill(new Rectangle2D.Double(15 + 78, 25, 26, 40));
		//makes cards for the development card label
		g2.setColor(Color.green);
		g2.fill(new Rectangle2D.Double(40, 90, 26, 40));
		g2.setColor(Color.yellow);
		g2.fill(new Rectangle2D.Double(66, 90, 26, 40));
		g2.setColor(Color.gray);
		g2.fill(new Rectangle2D.Double(92, 90, 26, 40));
		//makes cards for the road label
		g2.setColor(new Color(0, 100, 0));
		g2.fill(new Rectangle2D.Double(200, 90, 26, 40));
		g2.setColor(new Color(200, 70, 0));
		g2.fill(new Rectangle2D.Double(226, 90, 26, 40));
//makes cards for the city label
		g2.setColor(Color.yellow);
		g2.fill(new Rectangle2D.Double(160, 25, 26, 40));
		g2.fill(new Rectangle2D.Double(186, 25, 26, 40));
		g2.fill(new Rectangle2D.Double(212, 25, 26, 40));
		g2.setColor(Color.gray);
		g2.fill(new Rectangle2D.Double(238, 25, 26, 40));
		g2.fill(new Rectangle2D.Double(264, 25, 26, 40));
//draws the borders of all the cards
		g2.setColor(Color.black);
		g2.draw(new Rectangle2D.Double(15, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(15 + 26, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(15 + 52, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(15 + 78, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(40, 90, 26, 40));
		g2.draw(new Rectangle2D.Double(66, 90, 26, 40));
		g2.draw(new Rectangle2D.Double(92, 90, 26, 40));
		g2.draw(new Rectangle2D.Double(200, 90, 26, 40));
		g2.draw(new Rectangle2D.Double(226, 90, 26, 40));
		g2.draw(new Rectangle2D.Double(160, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(186, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(212, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(238, 25, 26, 40));
		g2.draw(new Rectangle2D.Double(264, 25, 26, 40));

	}
}
