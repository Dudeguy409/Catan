import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.Controller.RoadManager;
import client.Controller.StructureManager;
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
		assertNotNull(sm.StructureDependencyMap);
	}
	
	@Test
	public void testStructureMapCorrectSize() {
		StructureManager sm = new StructureManager(3);
		assertEquals(3, sm.StructureDependencyMap.size());
	}
	
	@Test
	public void testStructureMapDoesntAddsStructure() {
		StructureManager sm = new StructureManager(3);
		sm.addStructure(1, 5);
		assertEquals(0, sm.getStructureCountForPlayer(1));
	}
	
	@Test
	public void testStructureMapAddsStructureToCorrectPlayer() {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(1, 5);
		assertEquals(1, sm.getStructureCountForPlayer(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testStructureMapThrowsExceptionWhenNonPlayerReferenced() {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(3, 5);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testStructureMapThrowsExceptionWhenNonLocationReferenced() {
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(1, 5);
		sm.addStructure(2, -1);
	}
	
	@Test
	public void testAddOneStructureAtBeginning() {
		int rslt;
		StructureManager sm = new StructureManager(3);
		rslt = sm.getStructureCountForPlayer(0);
		assertEquals(0, rslt);
		
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);

		rslt = sm.getStructureCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddTwoSeparate() {
		int rslt;
		StructureManager sm = new StructureManager(3);
		
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);
		
		rm.addRoadPieceAtBeginning(0, 66);
		sm.addStructure(0, 51);

		rslt = sm.getStructureCountForPlayer(0);
		assertEquals(2, rslt);
	}

	@Test
	public void testAddTwoAdjacent() {
		int rslt;
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);
		sm.addStructure(0, 20);

		rslt = sm.getStructureCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testOpponentAddAdjacent() {
		int rslt;
		StructureManager sm = new StructureManager(3);
		RoadManager rm = new RoadManager(3);
		rm.addRoadPieceAtBeginning(0, 27);
		sm.addStructure(0, 19);
		
		rm.addRoadPieceAtBeginning(1, 31);
		sm.addStructure(1, 20);

		rslt = sm.getStructureCountForPlayer(1);
		assertEquals(0, rslt);
	}

}
