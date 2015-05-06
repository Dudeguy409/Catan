package client.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class CardComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6994122841968897234L;
	private Color color;

	public CardComponent(Color color) {
		this.color = color;
		// this.setSize(DIM);
		// this.setPreferredSize(DIM);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fill(new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));
		g2.setColor(Color.BLACK);
		g2.draw(new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));
	}

}
