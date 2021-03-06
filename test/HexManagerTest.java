import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map.Entry;

import org.junit.Test;

import client.Controller.HexManager;
import client.GUI.HexComponent;
import client.Model.LocationKey;
import client.Model.StructureLocationKey;

public class HexManagerTest {

	@Test
	public void testInitializeRoadMapSize() {
		HexManager hm = new HexManager();
		assertEquals(114, hm.roadMap.size());
	}

	@Test
	public void testAllRoadsCovered() {
		HexManager hm = new HexManager();
		HashSet<Integer> set = new HashSet<Integer>();
		for (Entry<LocationKey, Integer> e : hm.roadMap.entrySet()) {
			set.add(e.getValue());
		}

		assertEquals(72, set.size());
	}

	@Test
	public void testCorrectNumberForAllRoads() {
		HexManager hm = new HexManager();
		int[] roadsOnOnlyOneHex = { 1, 2, 3, 4, 5, 6, 9, 10, 12, 13, 18, 21,
				26, 30, 35, 38, 43, 47, 52, 55, 60, 61, 63, 64, 67, 68, 69, 70,
				71, 72 };
		int[] roadOccurences = new int[72];
		for (Entry<LocationKey, Integer> e : hm.roadMap.entrySet()) {
			roadOccurences[e.getValue() - 1]++;
		}

		for (int i = 0; i < roadOccurences.length; i++) {
			boolean isOnOnlyOneHex = false;
			for (int j = 0; j < roadsOnOnlyOneHex.length; j++) {
				if (roadsOnOnlyOneHex[j] == i + 1) {
					isOnOnlyOneHex = true;
					break;
				}
			}

			if (isOnOnlyOneHex) {
				assertEquals(roadOccurences[i], 1);
			} else {
				assertEquals(roadOccurences[i], 2);
			}

		}
	}

	@Test
	public void testCorrectNumberOfRoadsForEachHex() {
		HexManager hm = new HexManager();
		int[] hexRoadCounts = new int[19];
		for (Entry<LocationKey, Integer> e : hm.roadMap.entrySet()) {
			hexRoadCounts[((LocationKey) (e.getKey())).getHexIndex()]++;
		}

		for (int i = 0; i < hexRoadCounts.length; i++) {
			assertEquals(6, hexRoadCounts[i]);
		}
	}

	@Test
	public void testCorrectNumberOfStructuresForEachHex() {
		HexManager hm = new HexManager();
		int[] hexStructureCounts = new int[19];
		for (Entry<StructureLocationKey, Integer> e : hm.structureMap
				.entrySet()) {
			hexStructureCounts[((StructureLocationKey) (e.getKey()))
					.getHexIndex()]++;
		}

		for (int i = 0; i < hexStructureCounts.length; i++) {
			assertEquals(6, hexStructureCounts[i]);
		}
	}

	@Test
	public void testCorrectNumberForAllStructures() {
		HexManager hm = new HexManager();
		int[] structuresOnOnlyOneHex = { 1, 2, 3, 6, 7, 12, 18, 13, 25, 30, 37,
				42, 43, 48, 49, 52, 53, 54 };
		int[] structuresOnTwoHexs = { 4, 5, 8, 11, 19, 24, 31, 36, 44, 47, 50,
				51 };
		int[] structureOccurences = new int[54];
		for (Entry<StructureLocationKey, Integer> e : hm.structureMap
				.entrySet()) {
			structureOccurences[e.getValue() - 1]++;
		}

		for (int i = 0; i < structureOccurences.length; i++) {
			boolean isOnOnlyOneHex = false;

			for (int j = 0; j < structuresOnOnlyOneHex.length; j++) {
				if (structuresOnOnlyOneHex[j] == i + 1) {
					isOnOnlyOneHex = true;
					break;
				}

			}

			if (isOnOnlyOneHex) {
				assertEquals(structureOccurences[i], 1);
			} else {
				boolean isOnTwoHexes = false;
				for (int j = 0; j < structuresOnTwoHexs.length; j++) {
					if (structuresOnTwoHexs[j] == i + 1) {
						isOnTwoHexes = true;
						break;
					}

				}

				if (isOnTwoHexes) {
					assertEquals(structureOccurences[i], 2);
				} else {
					assertEquals(structureOccurences[i], 3);
				}

			}

		}
	}

	@Test
	public void testAllStructuresCovered() {
		HexManager hm = new HexManager();
		HashSet<Integer> set = new HashSet<Integer>();
		for (Entry<StructureLocationKey, Integer> e : hm.structureMap
				.entrySet()) {
			set.add(e.getValue());
		}

		assertEquals(54, set.size());
	}

	@Test
	public void testInitializeStructureMapSize() {
		HexManager hm = new HexManager();
		assertEquals(114, hm.structureMap.size());
	}

	@Test
	public void testStructureMapIteration() {
		HexManager hm = new HexManager();
		int id = hm.structureMap.get(new StructureLocationKey(6,
				HexComponent.StructurePosition.northeast));
		assertEquals(id, 2);

		id = hm.structureMap.get(new StructureLocationKey(6,
				HexComponent.StructurePosition.east));
		assertEquals(id, 5);

		id = hm.structureMap.get(new StructureLocationKey(5,
				HexComponent.StructurePosition.northwest));
		assertEquals(id, 5);

		id = hm.structureMap.get(new StructureLocationKey(6,
				HexComponent.StructurePosition.southeast));
		assertEquals(id, 10);

		id = hm.structureMap.get(new StructureLocationKey(5,
				HexComponent.StructurePosition.west));
		assertEquals(id, 10);

		id = hm.structureMap.get(new StructureLocationKey(5,
				HexComponent.StructurePosition.southwest));
		assertEquals(id, 16);

		id = hm.structureMap.get(new StructureLocationKey(15,
				HexComponent.StructurePosition.east));
		assertEquals(id, 16);

		id = hm.structureMap.get(new StructureLocationKey(15,
				HexComponent.StructurePosition.southeast));
		assertEquals(id, 22);

		id = hm.structureMap.get(new StructureLocationKey(15,
				HexComponent.StructurePosition.southwest));
		assertEquals(id, 21);

		id = hm.structureMap.get(new StructureLocationKey(16,
				HexComponent.StructurePosition.east));
		assertEquals(id, 21);

		id = hm.structureMap.get(new StructureLocationKey(18,
				HexComponent.StructurePosition.northwest));
		assertEquals(id, 21);
	}

}
