package client.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import client.Model.Road;
import client.Model.Structure;

public class StructureManager {
	
	public ArrayList<HashMap<Integer, StructurePiece>> structurePieceMaps;
	public HashMap<Integer, Structure> structureDependencyMap;
	
	public StructureManager(int playerCount) {
		structurePieceMaps = new ArrayList<HashMap<Integer, StructurePiece>>();
		for (int i = 0; i < playerCount; i++) {
			structurePieceMaps.add(new HashMap<Integer, StructurePiece>());
		}
		
		initializeStructureGraph();
	}
	
	public void addStructure(int playerNumber, int structureId) throws IndexOutOfBoundsException {
		//check to make sure playerNumber and structureId are valid
		if(playerNumber<0) {
			throw new IndexOutOfBoundsException("Player Number "+playerNumber+" not valid.");
		}
		if(playerNumber>structurePieceMaps.size()) {
			throw new IndexOutOfBoundsException("Structure ID "+structureId+" not valid.");
		}
		
		
		//check if structure already exists
//		for (HashMap<Integer, RoadPiece> hash : structurePieceMaps) {
//			if (hash.containsKey(roadIndex)) {// check if road exists
//				// throw new Exception("Road already exists");
//				noOverlap = false;
//			}
//		}
		
		//check to make sure there is no adjacent structure
			//uses structureDependencyMap
		
		//check to make sure there is an adjacent road
		//hasAdjacentRoad(int structureId)
		
		//actually add the structure
	}
	
	public void updateStructure(int playerNumber, int structureId) throws Exception{
		
	}
	
	public int getStructureCountForPlayer(int playerNumber){
		return -1;
	}
	
	private void initializeStructureGraph() {
		structureDependencyMap = new HashMap<Integer, Structure>();

		structureDependencyMap.put(1, new Structure(1, new int[] { 4,2}));
		structureDependencyMap.put(2, new Structure(2, new int[] { 1,5 }));
		structureDependencyMap.put(3, new Structure(3, new int[] { 8,4 }));
		structureDependencyMap.put(4, new Structure(4, new int[] {  1,3,9}));
		structureDependencyMap.put(5, new Structure(5, new int[] { 2,6,10 }));
		structureDependencyMap.put(6, new Structure(6, new int[] { 5,11 }));
		structureDependencyMap.put(7, new Structure(7, new int[] { 13, 8 }));
		structureDependencyMap.put(8, new Structure(8, new int[] { 3, 7, 14 }));
		structureDependencyMap.put(9, new Structure(9, new int[] { 4,10,15 }));
		structureDependencyMap.put(10, new Structure(10, new int[] { 5,9,16 }));
		structureDependencyMap.put(11, new Structure(11, new int[] { 6,12,17 }));
		structureDependencyMap.put(12, new Structure(12, new int[] { 11,18 }));
		structureDependencyMap.put(13, new Structure(13, new int[] { 7,19 }));
		structureDependencyMap.put(14, new Structure(14, new int[] { 8,15,20 }));
		structureDependencyMap.put(15, new Structure(15, new int[] { 9,14,21 }));
		structureDependencyMap.put(16, new Structure(16, new int[] { 10,17,22 }));
		structureDependencyMap.put(17, new Structure(17, new int[] { 11,23,16 }));
		structureDependencyMap.put(18, new Structure(18, new int[] { 12,24 }));
		
		structureDependencyMap.put(19, new Structure(19, new int[] { 13,25,20 }));
		structureDependencyMap.put(20, new Structure(20, new int[] { 14,19,26 }));
		structureDependencyMap.put(21, new Structure(21, new int[] { 15,27,22 }));
		structureDependencyMap.put(22, new Structure(22, new int[] { 16,21,28 }));
		structureDependencyMap.put(23, new Structure(23, new int[] { 17,24,29 }));
		structureDependencyMap.put(24, new Structure(24, new int[] { 18,30,23 }));
		structureDependencyMap.put(25, new Structure(25, new int[] { 19, 31 }));
		structureDependencyMap.put(26, new Structure(26, new int[] { 20,32,27 }));
		structureDependencyMap.put(27, new Structure(27, new int[] { 21,26,33 }));
		structureDependencyMap.put(28, new Structure(28, new int[] { 22,34,29 }));
		structureDependencyMap.put(29, new Structure(29, new int[] { 28,23,35 }));
		structureDependencyMap.put(30, new Structure(30, new int[] { 24,36 }));
		structureDependencyMap.put(31, new Structure(31, new int[] { 25,37,32 }));
		structureDependencyMap.put(32, new Structure(32, new int[] { 31,26,38 }));
		structureDependencyMap.put(33, new Structure(33, new int[] { 27,39,34 }));
		structureDependencyMap.put(34, new Structure(34, new int[] { 33,28,40 }));
		structureDependencyMap.put(35, new Structure(35, new int[] { 29,36,41 }));
		structureDependencyMap.put(36, new Structure(36, new int[] { 35,30,42 }));
		
		structureDependencyMap.put(37, new Structure(37, new int[] { 31,43 }));
		structureDependencyMap.put(38, new Structure(38, new int[] { 32,44,39 }));
		structureDependencyMap.put(39, new Structure(39, new int[] { 38,33,45 }));
		structureDependencyMap.put(40, new Structure(40, new int[] { 34,41,46 }));
		structureDependencyMap.put(41, new Structure(41, new int[] { 35,40,47 }));
		structureDependencyMap.put(42, new Structure(42, new int[] { 36,48 }));
		structureDependencyMap.put(43, new Structure(43, new int[] { 37,44 }));
		structureDependencyMap.put(44, new Structure(44, new int[] { 38,43,49 }));
		structureDependencyMap.put(45, new Structure(45, new int[] { 39,50,46 }));
		structureDependencyMap.put(46, new Structure(46, new int[] { 40,45,51 }));
		structureDependencyMap.put(47, new Structure(47, new int[] { 41,48,52 }));
		structureDependencyMap.put(48, new Structure(48, new int[] { 47,42 }));
		structureDependencyMap.put(49, new Structure(49, new int[] { 44,50 }));
		structureDependencyMap.put(50, new Structure(50, new int[] { 49,45,53 }));
		structureDependencyMap.put(51, new Structure(51, new int[] { 46,52,54 }));
		structureDependencyMap.put(52, new Structure(52, new int[] { 51,47 }));
		structureDependencyMap.put(53, new Structure(53, new int[] { 50,54 }));
		structureDependencyMap.put(54, new Structure(54, new int[] { 53, 51 }));
		
	}

}
