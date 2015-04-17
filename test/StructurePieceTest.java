import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.Controller.StructureManager;
import client.Model.Structure;

public class StructurePieceTest {
	@Test
	public void testStructureManagerInitializes() {
		StructureManager rm = new StructureManager(3);
		assertNotNull(rm);
	}
	
	@Test
	public void testStructureMapInitializes() {
		StructureManager rm = new StructureManager(3);
		assertNotNull(rm.StructureDependencyMap);
	}
	
	@Test
	public void testStructureMapCorrectSize() {
		StructureManager rm = new StructureManager(3);
		assertEquals(3, rm.StructureDependencyMap.size());
	}
	
	@Test
	public void testStructureMapAddsStructureToCorrectPlayer() {
		StructureManager rm = new StructureManager(3);
		rm.addStructurePieceAtBeginning(1, 5);
		assertEquals(1, rm.getStructureCountForPlayer(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testStructureMapThrowsExceptionWhenNonPlayerReferenced() {
		StructureManager rm = new StructureManager(3);
		rm.addStructurePiece(3, 5);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testStructureMapThrowsExceptionWhenNonLocationReferenced() {
		StructureManager rm = new StructureManager(3);
		rm.addStructurePiece(2, -1);
	}

	@Test
	public void testCorrectMapSizes() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(0, rslt);

		rm.addStructurePiece(0, 19);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(1, rslt);

		rm.addStructurePiece(0, 20);
		rm.addStructurePiece(0, 24);
		rm.addStructurePiece(0, 30);
		rm.addStructurePiece(0, 1);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(5, rslt);
	}
	
	@Test
	public void testAddOneStructureAtBeginning() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(0, rslt);

		rm.addStructurePieceAtBeginning(0, 19);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddOneStructure() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(0, rslt);

		rm.addStructurePieceAtBeginning(0, 14);
		rm.addStructurePiece(0, 19);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(2, rslt);
	}

	@Test
	public void testAddTwoSeparate() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rm.addStructurePieceAtBeginning(0, 19);
		rm.addStructurePieceAtBeginning(0, 51);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(1, rslt);
	}

	@Test
	public void testAddTwoAdjacent() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rm.addStructurePieceAtBeginning(0, 19);
		rm.addStructurePiece(0, 22);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(2, rslt);
	}

	@Test
	public void testLoop() {
		int rslt;
		StructureManager rm = new StructureManager(3);
		rm.addStructurePieceAtBeginning(0, 19);
		rm.addStructurePiece(0, 22);
		rm.addStructurePiece(0, 31);
		rm.addStructurePiece(0, 36);
		rm.addStructurePiece(0, 32);
		rm.addStructurePiece(0, 23);

		rslt = rm.getStructureCountForPlayer(0);
		assertEquals(6, rslt);
	}

}
