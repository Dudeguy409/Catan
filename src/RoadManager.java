import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class RoadManager {

	private HashMap<Integer, RoadPiece> roadDependencyMap;

	public void run() {
		roadDependencyMap = new HashMap<Integer, RoadPiece>();

		roadDependencyMap.put(1,
				new RoadPiece(1, new int[] { 2 }, new int[] {}));
		roadDependencyMap.put(2, new RoadPiece(2, new int[] { 1 },
				new int[] { 7 }));
		roadDependencyMap.put(7, new RoadPiece(7, new int[] { 2 }, new int[] {
				15, 11 }));
		roadDependencyMap.put(11, new RoadPiece(11, new int[] { 16 },
				new int[] { 15, 7 }));
		roadDependencyMap.put(15, new RoadPiece(15, new int[] { 23 },
				new int[] { 7, 11 }));
		roadDependencyMap.put(16, new RoadPiece(16, new int[] { 24 },
				new int[] { 11 }));
		roadDependencyMap.put(23, new RoadPiece(23, new int[] { 28, 32 },
				new int[] { 15 }));
		roadDependencyMap.put(24, new RoadPiece(24, new int[] { 16 },
				new int[] { 28 }));
		roadDependencyMap.put(28, new RoadPiece(28, new int[] { 24 },
				new int[] { 32, 23 }));
		roadDependencyMap.put(32, new RoadPiece(32, new int[] { 40 },
				new int[] { 23, 28 }));
		roadDependencyMap.put(40, new RoadPiece(40, new int[] { 49, 45 },
				new int[] { 32 }));
		roadDependencyMap.put(45, new RoadPiece(45, new int[] { 40, 49 },
				new int[] { 50 }));
		roadDependencyMap.put(49, new RoadPiece(49, new int[] { 57 },
				new int[] { 45 }));
		roadDependencyMap.put(50, new RoadPiece(50, new int[] { 58 },
				new int[] { 45 }));
		roadDependencyMap.put(57, new RoadPiece(57, new int[] { 62 },
				new int[] { 49 }));
		roadDependencyMap.put(58, new RoadPiece(58, new int[] { 62 },
				new int[] { 50 }));
		roadDependencyMap.put(62, new RoadPiece(62, new int[] { 57 },
				new int[] { 58 }));

		// for (Entry<Integer, RoadPiece> e : roadDependencyMap.entrySet()) {
		// RoadPiece road = e.getValue();
		// System.out.println(road.toString());
		// System.out.println(Arrays.toString(road.getAllAdjacentRoads()));
		// }

		System.out.println(findLongestRoad());

	}

	// TODO create a map/ add feature through the road class. Then, when someone
	// builds a settlement in between two rival segments, sever the connection
	// in the dependency map.
	public int findLongestRoad() {
		int max = 0;
		for (Entry<Integer, RoadPiece> e : roadDependencyMap.entrySet()) {
			RoadPiece road = e.getValue();
			int rslt = findLongestRoad(e.getKey());
			System.out.println(road.toString() + ":        " + rslt);
			if (rslt > max) {
				max = rslt;
			}
		}
		return max;
	}

	public int findLongestRoad(int road) {
		int[] adjacentRoads = roadDependencyMap.get(road).getAllAdjacentRoads();
		int[] results = new int[adjacentRoads.length];

		for (int i = 0; i < adjacentRoads.length; i++) {
			results[i] = findLongestRoad(adjacentRoads[i], road,
					new int[] { road });
		}

		int max = 0;

		for (int i = 0; i < adjacentRoads.length; i++) {
			if (results[i] > max)
				max = results[i];
		}

		return max + 1;
	}

	public int findLongestRoad(int road, int previousRoad,
			int[] previouslyVisitedRoads) {

		int[] roadsToCheck = roadDependencyMap.get(road)
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
			return 1 + findLongestRoad(roadsToVisit.get(0), road, visitedRoads);
		}

		if (visitCount == 2) {
			int a = findLongestRoad(roadsToVisit.get(0), road, visitedRoads);
			int b = findLongestRoad(roadsToVisit.get(1), road, visitedRoads);
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

	public void addRoadPiece(int playerIndex, int roadIndex) {
		// TODO Auto-generated method stub

	}

	public int findLongestRoadForPlayer(int playerIndex) {
		return findLongestRoad();
	}

	public int getRoadCountForPlayer(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
