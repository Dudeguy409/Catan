package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

import client.Controller.Game;
import client.Controller.Game.Resource;
import client.GUI.HexComponent.RoadPosition;
import client.GUI.HexComponent.StructurePosition;

/**
 * This class creates the GUI for the board
 * 
 * @author Andrew Davidson. Created May 7, 2010.
 */
public class BoardRenderer extends JComponent implements MouseListener,
		IBoardRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8593240809395368066L;
	private int startingX = 380;
	private int startingY = 640;
	private HexComponent[] boardArray = new HexComponent[Game.boardSize];
	private Game.Resource[] colorNumberArray;
	private int[] rollNumberArray;
	private Point2D.Double[] pointArray = new Point2D.Double[Game.boardSize];
	private ArrayList<StructureComponent> cityArray = new ArrayList<StructureComponent>();
	private ArrayList<StructureComponent> settlementArray = new ArrayList<StructureComponent>();
	private ArrayList<StructureComponent> roadArray = new ArrayList<StructureComponent>();
	private int robberIndex = 4;
	private Game game;
	private Color backgroundColor = new Color(0, 0, 100);
	private Shape background = new Rectangle2D.Double(0, 0, 900, 800);

	// each element's index represents its position in the board's hexArray, and
	// its value represents the hex's index in the Game class
	private int[] hexIndexToGUITranslator = { 0, 11, 10, 1, 12, 17, 9, 2, 13,
			18, 16, 8, 3, 14, 15, 7, 4, 5, 6 };

	// The opposite map: each element's index represents the hex's index in the
	// Game class, and its value represents its position in the board's hexArray
	private int[] hexIndexToGameTranslator = { 0, 3, 7, 12, 16, 17, 18, 15, 11,
			6, 2, 1, 4, 8, 13, 14, 10, 5, 9 };
	private PortComponent[] portArray = new PortComponent[9];
	private Resource[] portResources;

	/**
	 * constructs an empty, randomized board.
	 * 
	 * @param randomColorArray
	 * @param randomNumberArray
	 * @param myPanel
	 */
	public BoardRenderer(Game.Resource[] randomColorArray,
			int[] randomNumberArray, Resource[] portResources) {
		this.setPreferredSize(new Dimension(800, 800));
		this.portResources = portResources;
		this.colorNumberArray = randomColorArray;
		this.rollNumberArray = randomNumberArray;

		// initializes the robber to the desert hex.
		for (int i = 0; i < this.rollNumberArray.length; i++) {
			if (this.rollNumberArray[i] < 0) {
				this.robberIndex = this.hexIndexToGameTranslator[i];
				break;
			}
		}

		this.addMouseListener(this);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(this.backgroundColor);
		g2.fill(this.background);

		for (int i = 0; i < this.boardArray.length; i++) {
			HexComponent hex = this.boardArray[i];
			g2.setColor(hex.getResourceColor());
			g2.fill(hex.getHexShape());
			g2.setColor(Color.black);
			g2.draw(hex.getHexShape());
			int rollNumber = hex.getRollNumber();

			if (rollNumber > 0 || i == this.robberIndex) {
				if (i == this.robberIndex) {
					g2.setColor(Color.cyan);
				} else {
					g2.setColor(Color.white);
				}

				g2.fill(hex.getRollNumberShape());
				g2.setColor(Color.black);
				g2.draw(hex.getRollNumberShape());
			}

			if (rollNumber > 0) {
				if (rollNumber == 8 || rollNumber == 6) {
					g2.setColor(Color.red);
				} else {
					g2.setColor(Color.black);
				}

				String rollString = Integer.toString(rollNumber);
				g2.setFont(new Font("TimesRoman", Font.BOLD, 16));
				g2.drawString(rollString, (int) hex.getX() - 5,
						(int) hex.getY() + 5);
			}

		}

		for (PortComponent p : this.portArray) {
			g2.setColor(Color.white);
			g2.draw(p.getLineA());
			g2.draw(p.getLineB());
			g2.setColor(p.getColor());
			g2.fill(p.getSquare());
			g2.setColor(Color.black);
			g2.draw(p.getSquare());

		}

		for (StructureComponent structure : this.roadArray) {
			g2.setColor(structure.getPlayerColor());
			g2.fill(structure.getShape());
			g2.setColor(Color.black);
			g2.draw(structure.getShape());
		}

		for (StructureComponent structure : this.settlementArray) {
			g2.setColor(structure.getPlayerColor());
			g2.fill(structure.getShape());
			g2.setColor(Color.black);
			g2.draw(structure.getShape());
		}

		for (StructureComponent structure : this.cityArray) {
			g2.setColor(structure.getPlayerColor());
			g2.fill(structure.getShape());
			g2.setColor(Color.black);
			g2.draw(structure.getShape());
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// does nothing
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// does nothing.
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// does nothing.

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int[] nearArray = findNearestHexes(arg0.getX(), arg0.getY());
		if (nearArray[1] != -1) {
			Game.BuildType buildType = this.game.getCurrentBuildType();
			int hexId = nearArray[0];
			if (buildType == Game.BuildType.robber) {
				this.game
						.setRobberLocation(this.hexIndexToGUITranslator[hexId]);
			} else if (buildType != Game.BuildType.none) {
				if (buildType != Game.BuildType.road) {
					HexComponent.StructurePosition pos = this
							.determineStructurePosition(nearArray);
					this.game.processBuildStructureClick(
							this.hexIndexToGUITranslator[hexId], pos);
				} else {
					HexComponent.RoadPosition pos = this
							.determineRoadPosition(nearArray);
					this.game.processBuildRoadClick(
							this.hexIndexToGUITranslator[hexId], pos);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// does nothing.

	}

	public void addBuilding(int hexIdx,
			HexComponent.StructurePosition position, Color playerColor,
			Game.BuildType buildType) {
		int hexIndex = this.hexIndexToGameTranslator[hexIdx];
		Shape s = this.boardArray[hexIndex].makeStructure(position, buildType);
		StructureComponent structure = new StructureComponent(s, playerColor);

		if (buildType == Game.BuildType.city) {
			this.cityArray.add(structure);
		} else {
			this.settlementArray.add(structure);
		}

		this.repaint();
	}

	public void addRoad(int hexIdx, HexComponent.RoadPosition position,
			Color playerColor, Game.BuildType buildType) {
		int hexIndex = this.hexIndexToGameTranslator[hexIdx];
		Shape s = this.boardArray[hexIndex].makeRoad(position);

		StructureComponent structure = new StructureComponent(s, playerColor);
		this.roadArray.add(structure);

		this.repaint();
	}

	public void moveRobber(int hexIndex) {
		this.robberIndex = this.hexIndexToGameTranslator[hexIndex];
		this.repaint();
	}

	/**
	 * creates the randomized hexes on the board.
	 * 
	 */
	public void setBoard() {
		double rightShift = 1.5 * HexComponent.RADIUS;
		double diagonalUpShift = (HexComponent.RADIUS * HexComponent.Y_SCALAR);
		double leftShift = 1.5 * HexComponent.RADIUS;
		double eachUpShift = HexComponent.RADIUS * HexComponent.Y_SCALAR;

		int diagonalSquares = 3;
		int switcher = 1;
		int rightCoeff = 0;
		int startIndex = 0;
		int diagCoeff = 0;

		// creates an array of the 19 hexes on the board.
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < diagonalSquares; j++) {

				this.boardArray[j + startIndex] = new HexComponent(
						this.startingX + rightCoeff * rightShift - j
								* leftShift, this.startingY - diagCoeff
								* diagonalUpShift - j * eachUpShift,
						this.colorNumberArray[hexIndexToGUITranslator[j
								+ startIndex]],
						this.rollNumberArray[hexIndexToGUITranslator[j
								+ startIndex]]);

			}
			rightCoeff++;
			diagCoeff++;
			if (diagonalSquares == 5)
				switcher = -1;
			if (switcher == -1) {
				rightCoeff = 2;
				diagCoeff++;
			}
			startIndex += diagonalSquares;
			diagonalSquares += switcher;
		}
		for (int i = 0; i < Game.boardSize; i++) {
			this.pointArray[i] = new Point2D.Double(this.boardArray[i].getX(),
					this.boardArray[i].getY());
		}

		configurePorts();

	}

	private void configurePorts() {
		this.portArray[0] = this.boardArray[3].makePort(RoadPosition.south);
		this.portArray[1] = this.boardArray[12]
				.makePort(RoadPosition.northeast);
		this.portArray[2] = this.boardArray[17]
				.makePort(RoadPosition.northeast);
		this.portArray[3] = this.boardArray[15]
				.makePort(RoadPosition.northwest);
		this.portArray[4] = this.boardArray[6].makePort(RoadPosition.northwest);
		this.portArray[5] = this.boardArray[1].makePort(RoadPosition.south);

		// These ports are on the far side of the hex (don't border another hex)
		// and are always 3:1 ports.
		this.portArray[6] = this.boardArray[18].makePort(RoadPosition.north);
		this.portArray[7] = this.boardArray[7].makePort(RoadPosition.southeast);
		this.portArray[8] = this.boardArray[2].makePort(RoadPosition.southwest);

		for (int i = 0; i < this.portResources.length; i++) {
			if (this.portResources[i] == Game.Resource.desert) {
				// Don't change the color
			} else {
				this.portArray[i]
						.setColor(Game.resourceColors[this.portResources[i]
								.getNumVal()]);
			}

		}
	}

	// returns an array of: the hex clicked, the second nearest hex, the third
	// nearest, the distance to the center of the second nearest hex from the
	// mouse click, and the same for the third.
	private int[] findNearestHexes(int xCoord, int yCoord) {
		int[] nearArray = new int[5];
		int recordA = -1;
		int recordB = -1;
		int recordC = -1;
		double distanceRecordB = 10 * HexComponent.RADIUS;
		double distanceRecordA = 10 * HexComponent.RADIUS;
		double distanceRecordC = 10 * HexComponent.RADIUS;
		for (int i = 0; i < Game.boardSize; i++) {
			double deltaX = xCoord - this.pointArray[i].getX();
			double deltaY = yCoord - this.pointArray[i].getY();
			double distanceCurrent = Math.sqrt(deltaX * deltaX + deltaY
					* deltaY);

			// cycles through the arraylist of center points of the hexes and
			// compares them to the mouse click. The top three 'record-setters'
			// are kept.
			if (distanceCurrent < distanceRecordC) {
				if (distanceCurrent < distanceRecordB) {
					if (distanceCurrent < distanceRecordA) {
						distanceRecordC = distanceRecordB;
						distanceRecordB = distanceRecordA;
						distanceRecordA = distanceCurrent;
						recordC = recordB;
						recordB = recordA;
						recordA = i;
					} else {
						distanceRecordC = distanceRecordB;
						distanceRecordB = distanceCurrent;
						recordC = recordB;
						recordB = i;
					}
				} else {
					distanceRecordC = distanceCurrent;
					recordC = i;
				}
			}
		}

		nearArray[0] = recordA;
		nearArray[1] = recordB;
		nearArray[2] = recordC;
		nearArray[3] = (int) distanceRecordB;
		nearArray[4] = (int) distanceRecordC;

		// if the mouse click was in the center of the hex, the neighbor is set
		// to -1 to signal that the mouse click was not valid.
		if (distanceRecordA < HexComponent.RADIUS / 2
				&& this.game.getCurrentBuildType() != Game.BuildType.robber) {
			nearArray[1] = -1;
		}
		return nearArray;
	}

	/**
	 * determines which position in a hex a settlement should be built at.
	 * 
	 * @param findNearestHexes
	 * @return position to place structure
	 */
	private HexComponent.StructurePosition determineStructurePosition(
			int[] nearHex) {

		if (nearHex[4] > HexComponent.RADIUS * 1.5) {
			return handleOutlierSettlementPositionCases(nearHex);
		} else {
			return determineNormalSettlementPosition(nearHex);
		}

	}

	// deals with border hexes/positions
	private HexComponent.StructurePosition handleOutlierSettlementPositionCases(
			int[] nearHex) {
		if (nearHex[3] < HexComponent.RADIUS * 1.5) {
			return handleTwoHexCoastSettlementPosition(nearHex);
		} else {

			if (nearHex[4] > HexComponent.RADIUS * 2.25) {
				return handleHexTwoOceanSettlementPosition(nearHex);
			} else {
				return handleOneHexOceanSettlementPosition(nearHex);
			}

		}
	}

	// deals with hexes that have two positions that only border the
	// ocean and the main hex.
	private StructurePosition handleHexTwoOceanSettlementPosition(int[] nearHex) {
		switch (nearHex[0]) {
		case 0:
			if (nearHex[0] > nearHex[1] - 2) {
				return HexComponent.StructurePosition.southwest;

			} else
				return HexComponent.StructurePosition.southeast;

		case 2:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.StructurePosition.southwest;

			} else
				return HexComponent.StructurePosition.west;

		case 7:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.StructurePosition.southeast;

			} else
				return HexComponent.StructurePosition.east;

		case 11:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.StructurePosition.west;

			} else
				return HexComponent.StructurePosition.northwest;

		case 16:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.StructurePosition.east;

			} else
				return HexComponent.StructurePosition.northeast;

		case 18:
			if (nearHex[0] > nearHex[1] + 2) {
				return HexComponent.StructurePosition.northwest;

			} else
				return HexComponent.StructurePosition.northeast;
		default:
			return null;

		}
	}

	// deals with positions that only border the hex and the ocean.
	private StructurePosition handleOneHexOceanSettlementPosition(int[] nearHex) {
		// TODO memoize
		HashMap<Integer, StructurePosition> positionMap = new HashMap<Integer, HexComponent.StructurePosition>();
		positionMap.put(1, HexComponent.StructurePosition.southwest);
		positionMap.put(3, HexComponent.StructurePosition.southeast);
		positionMap.put(6, HexComponent.StructurePosition.west);
		positionMap.put(12, HexComponent.StructurePosition.east);
		positionMap.put(15, HexComponent.StructurePosition.northwest);
		positionMap.put(17, HexComponent.StructurePosition.northeast);

		return positionMap.get(nearHex[0]);

	}

	private HexComponent.StructurePosition handleTwoHexCoastSettlementPosition(
			int[] nearHex) {
		HexComponent.StructurePosition pos = null;
		switch (nearHex[0]) {
		case 1:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southeast;
				break;
			} else
				pos = HexComponent.StructurePosition.west;
			break;
		case 3:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southwest;
				break;
			} else
				pos = HexComponent.StructurePosition.east;
			break;
		case 6:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southwest;
				break;
			} else
				pos = HexComponent.StructurePosition.northwest;
			break;
		case 12:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southeast;
				break;
			} else
				pos = HexComponent.StructurePosition.northeast;
			break;
		case 15:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.west;
				break;
			} else
				pos = HexComponent.StructurePosition.northeast;
			break;
		case 17:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.east;
				break;
			} else
				pos = HexComponent.StructurePosition.northwest;
			break;
		case 0:
			if (nearHex[0] > nearHex[1] - 2) {
				pos = HexComponent.StructurePosition.west;
				break;
			} else
				pos = HexComponent.StructurePosition.east;
			break;
		case 2:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southeast;
				break;
			} else
				pos = HexComponent.StructurePosition.northwest;
			break;
		case 7:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southwest;
				break;
			} else
				pos = HexComponent.StructurePosition.northeast;
			break;
		case 11:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southwest;
				break;
			} else
				pos = HexComponent.StructurePosition.northeast;
			break;
		case 16:
			if (nearHex[0] > nearHex[1]) {
				pos = HexComponent.StructurePosition.southeast;
				break;
			} else
				pos = HexComponent.StructurePosition.northwest;
			break;
		case 18:
			if (nearHex[0] > nearHex[1] + 2) {
				pos = HexComponent.StructurePosition.west;
				break;
			} else
				pos = HexComponent.StructurePosition.east;
			break;
		}
		return pos;
	}

	private HexComponent.StructurePosition determineNormalSettlementPosition(
			int[] nearHex) {
		if ((nearHex[0] == nearHex[1] - 1 && nearHex[0] > nearHex[2])
				|| (nearHex[0] == nearHex[2] - 1 && nearHex[0] > nearHex[1])) {
			return HexComponent.StructurePosition.west;
		} else if ((nearHex[0] == nearHex[1] - 1 && nearHex[0] < nearHex[2])
				|| (nearHex[0] == nearHex[2] - 1 && nearHex[0] < nearHex[1])) {
			return HexComponent.StructurePosition.northwest;
		} else if ((nearHex[0] == nearHex[1] + 1 && nearHex[0] > nearHex[2])
				|| (nearHex[0] == nearHex[2] + 1 && nearHex[0] > nearHex[1])) {
			return HexComponent.StructurePosition.southeast;
		} else if ((nearHex[0] == nearHex[1] + 1 && nearHex[0] < nearHex[2])
				|| (nearHex[0] == nearHex[2] + 1 && nearHex[0] < nearHex[1])) {
			return HexComponent.StructurePosition.east;
		} else if (nearHex[0] < nearHex[1] - 1 && nearHex[0] < nearHex[2] - 1) {
			return HexComponent.StructurePosition.northeast;
		} else if (nearHex[0] > nearHex[1] + 1 && nearHex[0] > nearHex[2] + 1) {
			return HexComponent.StructurePosition.southwest;
		}

		return null;
	}

	// determines the position for the road to be built on the clicked hex.
	private HexComponent.RoadPosition determineRoadPosition(int[] nearHex) {

		int[] rowArray = { 3, 4, 5, 4, 3 };
		int row = determineRow(nearHex);

		if (nearHex[3] > HexComponent.RADIUS * 2 - 5) {
			// catches the case when the road being built does not start or end
			// at a different hex.
			return handleSingleHexRoadPosition(nearHex);
		} else if (nearHex[4] > HexComponent.RADIUS * 2 - 5) {
			// catches the outlying cases where the road is along the coast.
			return handleCoastalRoadPosition(nearHex);
		}

		if (row > 2) {

			if (nearHex[0] == nearHex[1] + rowArray[row])
				return HexComponent.RoadPosition.southwest;
			if (nearHex[0] == nearHex[1] - rowArray[4])
				return HexComponent.RoadPosition.northeast;
			if (nearHex[0] == nearHex[1] + rowArray[row - 1])
				return HexComponent.RoadPosition.south;
			if (nearHex[0] == nearHex[1] - rowArray[row])
				return HexComponent.RoadPosition.north;
			if (nearHex[3] > HexComponent.RADIUS * HexComponent.Y_SCALAR) {

			}

		} else if (row == 2) {

			if (nearHex[0] == nearHex[1] + rowArray[row])
				return HexComponent.RoadPosition.south;
			if (nearHex[0] == nearHex[1] - rowArray[row])
				return HexComponent.RoadPosition.north;
			if (nearHex[0] == nearHex[1] + rowArray[row - 1])
				return HexComponent.RoadPosition.southwest;
			if (nearHex[0] == nearHex[1] - rowArray[3])
				return HexComponent.RoadPosition.northeast;

		} else if (row < 2) {

			if (nearHex[0] == nearHex[1] - rowArray[row + 1])
				return HexComponent.RoadPosition.north;
			if (nearHex[0] == nearHex[1] + rowArray[row])
				return HexComponent.RoadPosition.south;
			if (nearHex[0] == nearHex[1] - rowArray[row])
				return HexComponent.RoadPosition.northeast;
			if (nearHex[0] == nearHex[1] + rowArray[0])
				return HexComponent.RoadPosition.southwest;
		}

		if (nearHex[1] == nearHex[0] + 1)
			return HexComponent.RoadPosition.northwest;
		if (nearHex[1] == nearHex[0] - 1)
			return HexComponent.RoadPosition.southeast;

		return null;
	}

	private int determineRow(int[] nearHex) {

		if (nearHex[0] < 3)
			return 0;
		else if (nearHex[0] < 7)
			return 1;
		else if (nearHex[0] < 12)
			return 2;
		else if (nearHex[0] < 16)
			return 3;
		else
			return 4;
	}

	private RoadPosition handleCoastalRoadPosition(int[] nearHex) {
		switch (nearHex[0]) {
		case 1:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.south;
			} else
				return HexComponent.RoadPosition.southwest;
		case 3:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.south;
			} else
				return HexComponent.RoadPosition.southeast;
		case 6:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.southwest;
			} else
				return HexComponent.RoadPosition.northwest;
		case 12:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.southeast;
			} else
				return HexComponent.RoadPosition.northeast;
		case 15:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.northwest;
			} else
				return HexComponent.RoadPosition.north;
		case 17:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.northeast;
			} else
				return HexComponent.RoadPosition.north;
		case 0:
			if (nearHex[0] > nearHex[1] - 2) {
				return HexComponent.RoadPosition.southwest;
			} else
				return HexComponent.RoadPosition.southeast;
		case 2:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.south;
			} else
				return HexComponent.RoadPosition.northwest;
		case 7:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.south;
			} else
				return HexComponent.RoadPosition.northeast;
		case 11:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.southwest;
			} else
				return HexComponent.RoadPosition.north;
		case 16:
			if (nearHex[0] > nearHex[1]) {
				return HexComponent.RoadPosition.southeast;
			} else
				return HexComponent.RoadPosition.north;
		case 18:
			if (nearHex[0] > nearHex[1] + 2) {
				return HexComponent.RoadPosition.northwest;
			} else
				return HexComponent.RoadPosition.northeast;
		default:
			return null;
		}
	}

	private RoadPosition handleSingleHexRoadPosition(int[] nearHex) {
		switch (nearHex[0]) {
		case 0:
			return HexComponent.RoadPosition.south;

		case 2:
			return HexComponent.RoadPosition.southwest;
		case 7:
			return HexComponent.RoadPosition.southeast;
		case 11:
			return HexComponent.RoadPosition.northwest;
		case 16:
			return HexComponent.RoadPosition.northeast;
		case 18:
			return HexComponent.RoadPosition.north;
		default:
			return null;

		}
	}

}
