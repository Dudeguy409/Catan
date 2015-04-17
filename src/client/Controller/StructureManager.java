package client.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import client.Model.Road;
import client.Model.RoadPiece;
import client.Model.Structure;

public class StructureManager {
	
	public ArrayList<HashMap<Integer, StructurePiece>> structurePieceMaps;
	public HashMap<Integer, Structure> structureDependencyMap;

	public StructureManager(int playerCount) {
		structurePieceMaps = new ArrayList<HashMap<Integer, StructurePiece>>();
		for (int i = 0; i < playerCount; i++) {
			structurePieceMaps.add(new HashMap<Integer, StructurePiece>());
		}
	}
	
	public void addStructure(int structureId, int playerNumber) throws Exception {
		
	}
	
	public void updateStructure(int structureId, int playerNumber) throws Exception{
		
	}
	
	public int getStructurePointCount(int playerNumber){
		return -1;
	}

}
