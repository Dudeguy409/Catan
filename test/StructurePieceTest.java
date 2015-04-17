import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import client.Controller.RoadManager;
import client.Controller.StructureLocationKey;
import client.Controller.StructureManager;
import client.GUI.HexComponent;
import client.Model.Structure;

public class StructurePieceTest {
	@Test
	public void testStructureManagerInitializes() {
		StructureManager sm = new StructureManager(3);
		assertNotNull(sm);
	}

	@Test
	public void testStructureMapInitializes() {
		StructureManager sm = new StructureManager(3);
		assertNotNull(sm.structureDependencyMap);
	}

	@Test
	public void testStructureMapsCorrectSize() {
		StructureManager sm = new StructureManager(3);
		assertEquals(54, sm.structureDependencyMap.size());
		assertEquals(3, sm.structurePieceMaps.size());
	}

	@Test
	public void testStructureMapAddsStructureToCorrectPlayer() throws Exception {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(1, 5);
		assertEquals(1, sm.getSettlementCountForPlayer(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testStructureMapThrowsExceptionWhenNonPlayerReferenced() throws Exception {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(3, 5);
	}

	@Test(expected = Exception.class)
	public void testStructureMapThrowsExceptionWhenNonLocationReferenced() throws Exception {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(2, -1);
	}

	@Test
	public void testAddOneStructureAtBeginning() throws Exception {
		int rslt;
		StructureManager sm = new StructureManager(3);
		rslt = sm.getSettlementCountForPlayer(0);
		assertEquals(0, rslt);

		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);

		rslt = sm.getSettlementCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddTwoSeparate() throws Exception {
		int rslt;
		StructureManager sm = new StructureManager(3);

		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);

		rm.addRoadPieceAtBeginning(0, 66);
		sm.addStructure(0, 51);

		rslt = sm.getSettlementCountForPlayer(0);
		assertEquals(2, rslt);
	}

	@Test(expected = Exception.class)
	public void testAddTwoAdjacent() throws Exception {
		int rslt;
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);
		sm.addStructure(0, 20);

		rslt = sm.getSettlementCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test(expected = Exception.class)
	public void testOpponentAddAdjacent() throws Exception {
		int rslt;
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);

		rm.addRoadPieceAtBeginning(1, 31);
		sm.addStructure(1, 20);

		rslt = sm.getSettlementCountForPlayer(1);
		assertEquals(0, rslt);
	}

	@Test
	public void testStructureIteration() {
		StructureManager sm = new StructureManager(3);

		Structure s = sm.structureDependencyMap.get(50);
		System.out.println(s);
		Integer[] lst = s.getAdjacentSettlements();
		List<Integer> ls = Arrays.asList(lst);
		assertEquals(s.getId(), 50);
		assertTrue(ls.contains(45));
		assertTrue(ls.contains(53));
		assertEquals(ls.size(), 2);

		s = sm.structureDependencyMap.get(53);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 53);
		assertTrue(ls.contains(54));
		assertTrue(ls.contains(50));
		assertEquals(ls.size(), 2);

		s = sm.structureDependencyMap.get(51);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 51);
		assertTrue(ls.contains(54));
		assertTrue(ls.contains(52));
		assertTrue(ls.contains(46));
		assertEquals(ls.size(), 3);

		s = sm.structureDependencyMap.get(40);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 40);
		assertTrue(ls.contains(34));
		assertTrue(ls.contains(46));
		assertTrue(ls.contains(41));
		assertEquals(ls.size(), 3);

		s = sm.structureDependencyMap.get(37);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 37);
		assertTrue(ls.contains(43));
		assertTrue(ls.contains(31));
		assertEquals(ls.size(), 2);

		s = sm.structureDependencyMap.get(27);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 27);
		assertTrue(ls.contains(33));
		assertTrue(ls.contains(26));
		assertTrue(ls.contains(21));
		assertEquals(ls.size(), 3);

		s = sm.structureDependencyMap.get(28);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 28);
		assertTrue(ls.contains(34));
		assertTrue(ls.contains(22));
		assertTrue(ls.contains(29));
		assertEquals(ls.size(), 3);

		s = sm.structureDependencyMap.get(29);
		ls = Arrays.asList(s.getAdjacentSettlements());
		assertEquals(s.getId(), 29);
		assertTrue(ls.contains(23));
		assertTrue(ls.contains(35));
		assertTrue(ls.contains(28));
		assertEquals(ls.size(), 3);

	}

}
