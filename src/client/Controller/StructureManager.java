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
	
	public void addStructure(int playerNumber, int structureId) throws IndexOutOfBoundsException {
		//check to make sure playerNumber and structureId are valid
		if(playerNumber<0) {
			throw new IndexOutOfBoundsException("Player Number "+playerNumber+" not valid.");
		}
		if(playerNumber>structurePieceMaps.size()) {
			throw new IndexOutOfBoundsException("Structure ID "+structureId+" not valid.");
		}
		
		
		//check if structure already exists
		for (HashMap<Integer, RoadPiece> hash : structurePieceMaps) {
			if (hash.containsKey(roadIndex)) {// check if road exists
				// throw new Exception("Road already exists");
				noOverlap = false;
			}
		}
		
		//check to make sure there is no adjacent structure
			//uses structureDependencyMap
		
		//check to make sure there is an adjacent road
		//hasAdjacentRoad(int structureId)
		
		//actually add the structure
	}
	
	public void updateStructure(int playerNumber, int structureId) throws Exception{
		
	}
	
	public int getStructurePointCount(int playerNumber){
		return -1;
	}

}
