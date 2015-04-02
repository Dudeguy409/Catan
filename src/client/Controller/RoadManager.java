package client.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import client.Model.Road;
import client.Model.RoadPiece;

public class RoadManager {

	public ArrayList<HashMap<Integer, RoadPiece>> roadPieceDependencyMaps;
	public HashMap<Integer, Road> roadDependencyMap;

	public RoadManager(int playerCount) {

		roadPieceDependencyMaps = new ArrayList<HashMap<Integer, RoadPiece>>();
		for (int i = 0; i < playerCount; i++) {
			roadPieceDependencyMaps.add(new HashMap<Integer, RoadPiece>());
		}

		initializeRoadGraph();

		// roadDependencyMap.put(1,
		// new RoadPiece(1, new int[] { 2 }, new int[] {}));
		// roadDependencyMap.put(2, new RoadPiece(2, new int[] { 1 },
		// new int[] { 7 }));
		// roadDependencyMap.put(7, new RoadPiece(7, new int[] { 2 }, new int[]
		// {
		// 15, 11 }));
		// roadDependencyMap.put(11, new RoadPiece(11, new int[] { 16 },
		// new int[] { 15, 7 }));
		// roadDependencyMap.put(15, new RoadPiece(15, new int[] { 23 },
		// new int[] { 7, 11 }));
		// roadDependencyMap.put(16, new RoadPiece(16, new int[] { 24 },
		// new int[] { 11 }));
		// roadDependencyMap.put(23, new RoadPiece(23, new int[] { 28, 32 },
		// new int[] { 15 }));
		// roadDependencyMap.put(24, new RoadPiece(24, new int[] { 16 },
		// new int[] { 28 }));
		// roadDependencyMap.put(28, new RoadPiece(28, new int[] { 24 },
		// new int[] { 32, 23 }));
		// roadDependencyMap.put(32, new RoadPiece(32, new int[] { 40 },
		// new int[] { 23, 28 }));
		// roadDependencyMap.put(40, new RoadPiece(40, new int[] { 49, 45 },
		// new int[] { 32 }));
		// roadDependencyMap.put(45, new RoadPiece(45, new int[] { 40, 49 },
		// new int[] { 50 }));
		// roadDependencyMap.put(49, new RoadPiece(49, new int[] { 57 },
		// new int[] { 45 }));
		// roadDependencyMap.put(50, new RoadPiece(50, new int[] { 58 },
		// new int[] { 45 }));
		// roadDependencyMap.put(57, new RoadPiece(57, new int[] { 62 },
		// new int[] { 49 }));
		// roadDependencyMap.put(58, new RoadPiece(58, new int[] { 62 },
		// new int[] { 50 }));
		// roadDependencyMap.put(62, new RoadPiece(62, new int[] { 57 },
		// new int[] { 58 }));

		// for (Entry<Integer, RoadPiece> e : roadDependencyMap.entrySet()) {
		// RoadPiece road = e.getValue();
		// System.out.println(road.toString());
		// System.out.println(Arrays.toString(road.getAllAdjacentRoads()));
		// }

		// System.out.println(findLongestRoad());

	}

	public int findLongestRoadForPlayer(int playerIndex) {
		int max = 0;
		for (Entry<Integer, RoadPiece> e : roadPieceDependencyMaps.get(
				playerIndex).entrySet()) {
			RoadPiece road = e.getValue();
			int rslt = findLongestRoadForRoadPiece(e.getKey(), playerIndex);
			System.out.println(road.toString() + ":        " + rslt);
			if (rslt > max) {
				max = rslt;
			}
		}
		return max;
	}

	public int findLongestRoadForRoadPiece(int road, int playerIndex) {
		int[] adjacentRoads = roadPieceDependencyMaps.get(playerIndex)
				.get(road).getAllAdjacentRoads();
		int[] results = new int[adjacentRoads.length];

		for (int i = 0; i < adjacentRoads.length; i++) {
			results[i] = findLongestRoad(adjacentRoads[i], road,
					new int[] { road }, playerIndex);
		}

		int max = 0;

		for (int i = 0; i < adjacentRoads.length; i++) {
			if (results[i] > max)
				max = results[i];
		}

		return max + 1;
	}

	public int findLongestRoad(int road, int previousRoad,
			int[] previouslyVisitedRoads, int playerIndex) {

		int[] roadsToCheck = roadPieceDependencyMaps.get(playerIndex).get(road)
				.getOppositeDirectionRoads(previousRoad);
		ArrayList<Integer> roadsToVisit = new ArrayList<Integer>();
		for (int i = 0; i < roadsToCheck.length; i++) {
			if (!(containsRoad(previouslyVisitedRoads, roadsToCheck[i]))) {
				roadsToVisit.add(roadsToCheck[i]);
			}
		}

		int visitCount = roadsToVisit.size();

		if (visitCount == 0) {
			return 1;
		}

		int oldLength = previouslyVisitedRoads.length;
		int[] visitedRoads = Arrays.copyOf(previouslyVisitedRoads,
				oldLength + 1);
		visitedRoads[oldLength] = road;

		if (visitCount == 1) {
			return 1 + findLongestRoad(roadsToVisit.get(0), road, visitedRoads,
					playerIndex);
		}

		if (visitCount == 2) {
			int a = findLongestRoad(roadsToVisit.get(0), road, visitedRoads,
					playerIndex);
			int b = findLongestRoad(roadsToVisit.get(1), road, visitedRoads,
					playerIndex);
			return 1 + Math.max(a, b);
		}

		// CRITICAL ERROR!!!
		return -4000;
	}

	private boolean containsRoad(int[] previouslyVisitedRoads, int roadToCheck) {
		for (int i = 0; i < previouslyVisitedRoads.length; i++) {
			if (previouslyVisitedRoads[i] == roadToCheck) {
				return true;
			}
		}
		return false;
	}

	// TODO sever the connection in the dependency map if a player builds a
	// settlement in between two of their opponents roads.
	public void addRoadPiece(int playerIndex, int roadIndex) {
		// for each player, make sure that the piece doesn't already exist.
		// Then, check to make sure one of its adjoining roads exists. Then link
		// it to any roadpiece it may be touching and vice versa
		
	}

	public void addRoadPieceAtBeginning(int playerIndex, int roadIndex) {
		// for each player, make sure that the piece doesn't already exist.
		// Then, don't check to make sure one of its adjoining roads exists.
		// Then, link it to any road that it may be touching and vice versa.

	}

	public int getRoadCountForPlayer(int playerIndex) {
		return roadPieceDependencyMaps.get(playerIndex).size();
	}

	private void initializeRoadGraph() {
		roadDependencyMap = new HashMap<Integer, Road>();

		roadDependencyMap.put(1, new Road(1, new int[] { 2 }, new int[] { 3 }));
		roadDependencyMap.put(2, new Road(2, new int[] { 1 },
				new int[] { 4, 7 }));
		roadDependencyMap.put(3, new Road(3, new int[] { 1 },
				new int[] { 8, 5 }));
		roadDependencyMap.put(4, new Road(4, new int[] { 6 },
				new int[] { 2, 7 }));
		roadDependencyMap.put(5, new Road(5, new int[] { 9 },
				new int[] { 3, 8 }));
		roadDependencyMap.put(6, new Road(6, new int[] { 4 }, new int[] { 10,
				14 }));
		roadDependencyMap.put(7, new Road(7, new int[] { 2, 4 }, new int[] {
				11, 15 }));
		roadDependencyMap.put(8, new Road(8, new int[] { 3, 5 }, new int[] {
				11, 16 }));
		roadDependencyMap.put(9, new Road(9, new int[] { 5 }, new int[] { 17,
				12 }));
		roadDependencyMap.put(10, new Road(10, new int[] { 6, 14 },
				new int[] { 13 }));
		roadDependencyMap.put(11, new Road(11, new int[] { 7, 15 }, new int[] {
				8, 16 }));
		roadDependencyMap.put(12, new Road(12, new int[] { 9, 17 },
				new int[] { 18 }));
		roadDependencyMap.put(13, new Road(13, new int[] { 10 },
				new int[] { 21 }));
		roadDependencyMap.put(14, new Road(14, new int[] { 10, 6 }, new int[] {
				22, 19 }));
		roadDependencyMap.put(15, new Road(15, new int[] { 19, 23 }, new int[] {
				7, 11 }));
		roadDependencyMap.put(16, new Road(16, new int[] { 11, 8 }, new int[] {
				24, 20 }));
		roadDependencyMap.put(17, new Road(17, new int[] { 20, 25 }, new int[] {
				9, 12 }));
		roadDependencyMap.put(18, new Road(18, new int[] { 12 },
				new int[] { 26 }));
		roadDependencyMap.put(19, new Road(19, new int[] { 14, 22 }, new int[] {
				15, 23 }));
		roadDependencyMap.put(20, new Road(20, new int[] { 16, 24 }, new int[] {
				17, 25 }));
		roadDependencyMap.put(21, new Road(21, new int[] { 30, 27 },
				new int[] { 13 }));
		roadDependencyMap.put(22, new Road(22, new int[] { 27, 31 }, new int[] {
				14, 19 }));
		roadDependencyMap.put(23, new Road(23, new int[] { 19, 15 }, new int[] {
				32, 28 }));
		roadDependencyMap.put(24, new Road(24, new int[] { 28, 33 }, new int[] {
				16, 20 }));
		roadDependencyMap.put(25, new Road(25, new int[] { 20, 17 }, new int[] {
				34, 29 }));
		roadDependencyMap.put(26, new Road(26, new int[] { 18 }, new int[] {
				29, 35 }));
		roadDependencyMap.put(27, new Road(27, new int[] { 21, 30 }, new int[] {
				22, 31 }));
		roadDependencyMap.put(28, new Road(28, new int[] { 23, 32 }, new int[] {
				24, 33 }));
		roadDependencyMap.put(29, new Road(29, new int[] { 25, 34 }, new int[] {
				26, 35 }));
		roadDependencyMap.put(30, new Road(30, new int[] { 21, 27 },
				new int[] { 38 }));
		roadDependencyMap.put(31, new Road(31, new int[] { 27, 22 }, new int[] {
				39, 36 }));
		roadDependencyMap.put(32, new Road(32, new int[] { 23, 28 }, new int[] {
				36, 40 }));
		roadDependencyMap.put(33, new Road(33, new int[] { 28, 24 }, new int[] {
				41, 37 }));
		roadDependencyMap.put(34, new Road(34, new int[] { 25, 29 }, new int[] {
				37, 42 }));
		roadDependencyMap.put(35, new Road(35, new int[] { 29, 26 },
				new int[] { 43 }));
		roadDependencyMap.put(36, new Road(36, new int[] { 31, 39 }, new int[] {
				32, 40 }));
		roadDependencyMap.put(37, new Road(37, new int[] { 33, 41 }, new int[] {
				34, 42 }));
		roadDependencyMap.put(38, new Road(38, new int[] { 30 }, new int[] {
				47, 44 }));
		roadDependencyMap.put(39, new Road(39, new int[] { 44, 48 }, new int[] {
				31, 36 }));
		roadDependencyMap.put(40, new Road(40, new int[] { 36, 32 }, new int[] {
				49, 45 }));
		roadDependencyMap.put(41, new Road(41, new int[] { 45, 50 }, new int[] {
				33, 37 }));
		roadDependencyMap.put(42, new Road(42, new int[] { 37, 34 }, new int[] {
				51, 46 }));
		roadDependencyMap.put(43, new Road(43, new int[] { 35 }, new int[] {
				46, 52 }));
		roadDependencyMap.put(44, new Road(44, new int[] { 38, 47 }, new int[] {
				39, 48 }));
		roadDependencyMap.put(45, new Road(45, new int[] { 40, 49 }, new int[] {
				41, 50 }));
		roadDependencyMap.put(46, new Road(46, new int[] { 42, 51 }, new int[] {
				43, 52 }));
		roadDependencyMap.put(47, new Road(47, new int[] { 38, 44 },
				new int[] { 55 }));
		roadDependencyMap.put(48, new Road(48, new int[] { 56, 53 }, new int[] {
				44, 39 }));
		roadDependencyMap.put(49, new Road(49, new int[] { 53, 57 }, new int[] {
				40, 45 }));
		roadDependencyMap.put(50, new Road(50, new int[] { 45, 41 }, new int[] {
				58, 54 }));
		roadDependencyMap.put(51, new Road(51, new int[] { 54, 59 }, new int[] {
				42, 46 }));
		roadDependencyMap.put(52, new Road(52, new int[] { 46, 43 },
				new int[] { 60 }));
		roadDependencyMap.put(53, new Road(53, new int[] { 48, 56 }, new int[] {
				49, 57 }));
		roadDependencyMap.put(54, new Road(54, new int[] { 50, 58 }, new int[] {
				51, 59 }));
		roadDependencyMap.put(55, new Road(55, new int[] { 47 },
				new int[] { 61 }));
		roadDependencyMap.put(56, new Road(56, new int[] { 61, 64 }, new int[] {
				48, 53 }));
		roadDependencyMap.put(57, new Road(57, new int[] { 53, 49 }, new int[] {
				65, 62 }));
		roadDependencyMap.put(58, new Road(58, new int[] { 62, 66 }, new int[] {
				50, 54 }));
		roadDependencyMap.put(59, new Road(59, new int[] { 54, 51 }, new int[] {
				67, 63 }));
		roadDependencyMap.put(60, new Road(60, new int[] { 52 },
				new int[] { 63 }));
		roadDependencyMap.put(61, new Road(61, new int[] { 55 }, new int[] {
				56, 64 }));
		roadDependencyMap.put(62, new Road(62, new int[] { 57, 65 }, new int[] {
				58, 66 }));
		roadDependencyMap.put(63, new Road(63, new int[] { 59, 67 },
				new int[] { 60 }));
		roadDependencyMap.put(64, new Road(64, new int[] { 61, 56 },
				new int[] { 68 }));
		roadDependencyMap.put(65, new Road(65, new int[] { 68, 70 }, new int[] {
				57, 62 }));
		roadDependencyMap.put(66, new Road(66, new int[] { 62, 58 }, new int[] {
				71, 69 }));
		roadDependencyMap.put(67, new Road(67, new int[] { 59, 63 },
				new int[] { 69 }));
		roadDependencyMap.put(68, new Road(68, new int[] { 64 }, new int[] {
				65, 70 }));
		roadDependencyMap.put(69, new Road(69, new int[] { 67 }, new int[] {
				66, 71 }));
		roadDependencyMap.put(70, new Road(70, new int[] { 68, 65 },
				new int[] { 72 }));
		roadDependencyMap.put(71, new Road(71, new int[] { 66, 69 },
				new int[] { 72 }));
		roadDependencyMap.put(72, new Road(72, new int[] { 70 },
				new int[] { 71 }));
	}

}
