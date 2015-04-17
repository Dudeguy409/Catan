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
		structureDependencyMap.put(10, new Structure(10, new int[] {  }));
		structureDependencyMap.put(11, new Structure(11, new int[] { 6,12,17 }));
		structureDependencyMap.put(12, new Structure(12, new int[] {  }));
		structureDependencyMap.put(13, new Structure(13, new int[] {  }));
		structureDependencyMap.put(14, new Structure(14, new int[] {  }));
		structureDependencyMap.put(15, new Structure(15, new int[] {  }));
		structureDependencyMap.put(16, new Structure(16, new int[] {  }));
		structureDependencyMap.put(17, new Structure(17, new int[] {  }));
		structureDependencyMap.put(18, new Structure(18, new int[] {  }));
		
		structureDependencyMap.put(19, new Structure(19, new int[] {  }));
		structureDependencyMap.put(20, new Structure(20, new int[] {  }));
		structureDependencyMap.put(21, new Structure(21, new int[] {  }));
		structureDependencyMap.put(22, new Structure(22, new int[] {  }));
		structureDependencyMap.put(23, new Structure(23, new int[] {  }));
		structureDependencyMap.put(24, new Structure(24, new int[] {  }));
		structureDependencyMap.put(25, new Structure(25, new int[] {  }));
		structureDependencyMap.put(26, new Structure(26, new int[] {  }));
		structureDependencyMap.put(27, new Structure(27, new int[] {  }));
		structureDependencyMap.put(28, new Structure(28, new int[] {  }));
		structureDependencyMap.put(29, new Structure(29, new int[] {  }));
		structureDependencyMap.put(30, new Structure(30, new int[] {  }));
		structureDependencyMap.put(31, new Structure(31, new int[] {  }));
		structureDependencyMap.put(32, new Structure(32, new int[] {  }));
		structureDependencyMap.put(33, new Structure(33, new int[] {  }));
		structureDependencyMap.put(34, new Structure(34, new int[] {  }));
		structureDependencyMap.put(35, new Structure(35, new int[] {  }));
		structureDependencyMap.put(36, new Structure(36, new int[] {  }));
		
		structureDependencyMap.put(37, new Structure(37, new int[] {  }));
		structureDependencyMap.put(38, new Structure(38, new int[] {  }));
		structureDependencyMap.put(39, new Structure(39, new int[] {  }));
		structureDependencyMap.put(40, new Structure(40, new int[] {  }));
		structureDependencyMap.put(41, new Structure(41, new int[] {  }));
		structureDependencyMap.put(42, new Structure(42, new int[] {  }));
		structureDependencyMap.put(43, new Structure(43, new int[] {  }));
		structureDependencyMap.put(44, new Structure(44, new int[] {  }));
		structureDependencyMap.put(45, new Structure(45, new int[] {  }));
		structureDependencyMap.put(46, new Structure(46, new int[] {  }));
		structureDependencyMap.put(47, new Structure(47, new int[] {  }));
		structureDependencyMap.put(48, new Structure(48, new int[] {  }));
		structureDependencyMap.put(49, new Structure(49, new int[] {  }));
		structureDependencyMap.put(50, new Structure(50, new int[] {  }));
		structureDependencyMap.put(51, new Structure(51, new int[] {  }));
		structureDependencyMap.put(52, new Structure(52, new int[] {  }));
		structureDependencyMap.put(53, new Structure(53, new int[] {  }));
		structureDependencyMap.put(54, new Structure(54, new int[] {  }));
		
	}

}
