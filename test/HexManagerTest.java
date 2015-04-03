import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;

import org.junit.Test;

import client.Controller.HexManager;
import client.Controller.LocationKey;

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
			roadOccurences[e.getValue()]++;
		}

		for (int i = 0; i < roadOccurences.length; i++) {
			if (Arrays.asList(roadsOnOnlyOneHex).contains(i)) {
				assertTrue(roadOccurences[i] == 1);
			} else {
				assertTrue(roadOccurences[i] == 2);
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

}
