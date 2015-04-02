import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.Controller.RoadManager;

public class LongestRoadTest {

//	@Before
//	public void setUp() throws Exception {
//		RoadManager rm = new RoadManager(3);
//	}
	
	@Test
	public void testRoadManagerInitializes() {
		RoadManager rm = new RoadManager(3);
		assertNotNull(rm);
	}
	
	@Test
	public void testRoadMapInitializes() {
		RoadManager rm = new RoadManager(3);
		assertNotNull(rm.roadDependencyMap);
	}
	
	@Test
	public void testRoadMapCorrectSize() {
		RoadManager rm = new RoadManager(3);
		assertEquals(3, rm.roadDependencyMap.size());
	}
	
	@Test
	public void testRoadMapAddsRoadToCorrectPlayer() {
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(1, 5);
		assertEquals(1, rm.getRoadCountForPlayer(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRoadMapThrowsExceptionWhenNonPlayerReferenced() {
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(3, 5);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRoadMapThrowsExceptionWhenNonLocationReferenced() {
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(2, -1);
	}

	@Test
	public void testCorrectMapSizes() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rslt = rm.getRoadCountForPlayer(0);
		assertEquals(0, rslt);

		rm.addRoadPiece(0, 19);

		rslt = rm.getRoadCountForPlayer(0);
		assertEquals(1, rslt);

		rm.addRoadPiece(0, 20);
		rm.addRoadPiece(0, 24);
		rm.addRoadPiece(0, 30);
		rm.addRoadPiece(0, 1);

		rslt = rm.getRoadCountForPlayer(0);
		assertEquals(5, rslt);
	}

	@Test
	public void testAddOneRoad() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(0, rslt);

		rm.addRoadPiece(0, 19);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddTwoSeparate() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 19);
		rm.addRoadPiece(0, 51);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddTwoAdjacent() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 19);
		rm.addRoadPiece(0, 22);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(2, rslt);
	}

	@Test
	public void testLoop() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 19);
		rm.addRoadPiece(0, 22);
		rm.addRoadPiece(0, 31);
		rm.addRoadPiece(0, 36);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 23);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);
	}

	@Test
	public void testSnakeChain() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 15);
		rm.addRoadPiece(0, 23);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 40);
		rm.addRoadPiece(0, 49);
		rm.addRoadPiece(0, 57);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);
	}

	@Test
	public void testChainWithLoopAtEnd() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		// add chain
		rm.addRoadPiece(0, 15);
		rm.addRoadPiece(0, 23);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 40);
		rm.addRoadPiece(0, 49);
		rm.addRoadPiece(0, 57);

		// connect loop
		rm.addRoadPiece(0, 62);
		rm.addRoadPiece(0, 58);
		rm.addRoadPiece(0, 50);
		rm.addRoadPiece(0, 45);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(10, rslt);

	}

	@Test
	public void testChainWithLoopAtEndAndInMiddle() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		// add chain
		rm.addRoadPiece(0, 1);
		rm.addRoadPiece(0, 2);
		rm.addRoadPiece(0, 7);
		rm.addRoadPiece(0, 15);
		rm.addRoadPiece(0, 23);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 40);
		rm.addRoadPiece(0, 49);
		rm.addRoadPiece(0, 57);

		// connect end loop
		rm.addRoadPiece(0, 62);
		rm.addRoadPiece(0, 58);
		rm.addRoadPiece(0, 50);
		rm.addRoadPiece(0, 45);

		// add second loop in middle
		rm.addRoadPiece(0, 11);
		rm.addRoadPiece(0, 16);
		rm.addRoadPiece(0, 28);
		rm.addRoadPiece(0, 24);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(10, rslt);

	}

	// @Test
	// public void testChainBrokenByOpponent() {
	//
	//
	// }
	//
	// @Test
	// public void testChainLoopBrokenByOpponent() {
	//
	// }

	// This test checks that only your road chain is used to calculate your
	// longest road.
	@Test
	public void testOpponentAddingSeparateRoads() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 15);
		rm.addRoadPiece(0, 23);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 40);
		rm.addRoadPiece(0, 49);
		rm.addRoadPiece(0, 57);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);

		rm.addRoadPiece(1, 25);
		rm.addRoadPiece(1, 34);
		rm.addRoadPiece(1, 42);
		rm.addRoadPiece(1, 51);
		rm.addRoadPiece(1, 17);
		rm.addRoadPiece(1, 59);

		rslt = rm.findLongestRoadForPlayer(1);
		assertEquals(6, rslt);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);
	}

	// An opponent adding adjacent roads to yours should never effect the length
	// of your road. Only if they build a settlement. This test checks that your
	// opponents roads don't accidentally get added to your road for
	// calculating longest road when they touch your roads.
	@Test
	public void testOpponentAddingAdjacentRoads() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 15);
		rm.addRoadPiece(0, 23);
		rm.addRoadPiece(0, 32);
		rm.addRoadPiece(0, 40);
		rm.addRoadPiece(0, 49);
		rm.addRoadPiece(0, 57);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);

		rm.addRoadPiece(1, 25);
		rm.addRoadPiece(1, 34);
		rm.addRoadPiece(1, 42);
		rm.addRoadPiece(1, 51);
		rm.addRoadPiece(1, 54);
		rm.addRoadPiece(1, 50);
		rm.addRoadPiece(1, 45);

		rslt = rm.findLongestRoadForPlayer(1);
		assertEquals(7, rslt);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(6, rslt);
	}

	@Test
	public void testOrderAddingConnectedRoads() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		// first add two unconnected roads
		rm.addRoadPiece(0, 19);
		rm.addRoadPiece(0, 31);

		// then build a road connecting the two
		rm.addRoadPiece(0, 22);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(3, rslt);
	}

	@Test
	public void testConnectedRoadsOnOcean() {
		int rslt;
		RoadManager rm = new RoadManager(3);
		rm.addRoadPiece(0, 1);
		rm.addRoadPiece(0, 2);
		rm.addRoadPiece(0, 3);

		rslt = rm.findLongestRoadForPlayer(0);
		assertEquals(3, rslt);
	}

}
