import static org.junit.Assert.*;

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
		for(Entry<LocationKey, Integer> e : hm.roadMap.entrySet()) {
			set.add(e.getValue());
		}
		
		assertEquals(72, set.size());
	}

}
