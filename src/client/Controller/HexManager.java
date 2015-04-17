package client.Controller;

import java.util.HashMap;
import client.GUI.HexComponent;

public class HexManager {

	public HashMap<LocationKey, Integer> roadMap;
	public HashMap<StructureLocationKey, Integer> structureMap;

	public HexManager() {

		initializeRoadMap();

	}

	public int getRoadId(int hexIndex, HexComponent.RoadPosition pos) {
		return this.roadMap.get(new LocationKey(hexIndex, pos));
	}

	public int getStructureId(int hexIndex, HexComponent.StructurePosition pos) {
		return -1;
	}

	private void initializeRoadMap() {
		this.roadMap = new HashMap<LocationKey, Integer>();

		this.roadMap.put(
				new LocationKey(0, HexComponent.RoadPosition.northwest), 65);
		this.roadMap.put(new LocationKey(0, HexComponent.RoadPosition.north),
				62);
		this.roadMap.put(
				new LocationKey(0, HexComponent.RoadPosition.northeast), 66);
		this.roadMap.put(
				new LocationKey(0, HexComponent.RoadPosition.southeast), 71);
		this.roadMap.put(new LocationKey(0, HexComponent.RoadPosition.south),
				72);
		this.roadMap.put(
				new LocationKey(0, HexComponent.RoadPosition.southwest), 70);

		this.roadMap.put(
				new LocationKey(1, HexComponent.RoadPosition.northwest), 58);
		this.roadMap.put(new LocationKey(1, HexComponent.RoadPosition.north),
				54);
		this.roadMap.put(
				new LocationKey(1, HexComponent.RoadPosition.northeast), 59);
		this.roadMap.put(
				new LocationKey(1, HexComponent.RoadPosition.southeast), 67);
		this.roadMap.put(new LocationKey(1, HexComponent.RoadPosition.south),
				69);
		this.roadMap.put(
				new LocationKey(1, HexComponent.RoadPosition.southwest), 66);

		this.roadMap.put(
				new LocationKey(2, HexComponent.RoadPosition.northwest), 51);
		this.roadMap.put(new LocationKey(2, HexComponent.RoadPosition.north),
				46);
		this.roadMap.put(
				new LocationKey(2, HexComponent.RoadPosition.northeast), 52);
		this.roadMap.put(
				new LocationKey(2, HexComponent.RoadPosition.southeast), 60);
		this.roadMap.put(new LocationKey(2, HexComponent.RoadPosition.south),
				63);
		this.roadMap.put(
				new LocationKey(2, HexComponent.RoadPosition.southwest), 59);

		this.roadMap.put(
				new LocationKey(3, HexComponent.RoadPosition.northwest), 34);
		this.roadMap.put(new LocationKey(3, HexComponent.RoadPosition.north),
				29);
		this.roadMap.put(
				new LocationKey(3, HexComponent.RoadPosition.northeast), 35);
		this.roadMap.put(
				new LocationKey(3, HexComponent.RoadPosition.southeast), 43);
		this.roadMap.put(new LocationKey(3, HexComponent.RoadPosition.south),
				46);
		this.roadMap.put(
				new LocationKey(3, HexComponent.RoadPosition.southwest), 42);

		this.roadMap.put(
				new LocationKey(4, HexComponent.RoadPosition.northwest), 17);
		this.roadMap.put(new LocationKey(4, HexComponent.RoadPosition.north),
				12);
		this.roadMap.put(
				new LocationKey(4, HexComponent.RoadPosition.northeast), 18);
		this.roadMap.put(
				new LocationKey(4, HexComponent.RoadPosition.southeast), 26);
		this.roadMap.put(new LocationKey(4, HexComponent.RoadPosition.south),
				29);
		this.roadMap.put(
				new LocationKey(4, HexComponent.RoadPosition.southwest), 25);

		this.roadMap.put(
				new LocationKey(5, HexComponent.RoadPosition.northwest), 8);
		this.roadMap
				.put(new LocationKey(5, HexComponent.RoadPosition.north), 5);
		this.roadMap.put(
				new LocationKey(5, HexComponent.RoadPosition.northeast), 9);
		this.roadMap.put(
				new LocationKey(5, HexComponent.RoadPosition.southeast), 17);
		this.roadMap.put(new LocationKey(5, HexComponent.RoadPosition.south),
				20);
		this.roadMap.put(
				new LocationKey(5, HexComponent.RoadPosition.southwest), 16);

		this.roadMap.put(
				new LocationKey(6, HexComponent.RoadPosition.northwest), 2);
		this.roadMap
				.put(new LocationKey(6, HexComponent.RoadPosition.north), 1);
		this.roadMap.put(
				new LocationKey(6, HexComponent.RoadPosition.northeast), 3);
		this.roadMap.put(
				new LocationKey(6, HexComponent.RoadPosition.southeast), 8);
		this.roadMap.put(new LocationKey(6, HexComponent.RoadPosition.south),
				11);
		this.roadMap.put(
				new LocationKey(6, HexComponent.RoadPosition.southwest), 7);

		this.roadMap.put(
				new LocationKey(7, HexComponent.RoadPosition.northwest), 6);
		this.roadMap
				.put(new LocationKey(7, HexComponent.RoadPosition.north), 4);
		this.roadMap.put(
				new LocationKey(7, HexComponent.RoadPosition.northeast), 7);
		this.roadMap.put(
				new LocationKey(7, HexComponent.RoadPosition.southeast), 15);
		this.roadMap.put(new LocationKey(7, HexComponent.RoadPosition.south),
				19);
		this.roadMap.put(
				new LocationKey(7, HexComponent.RoadPosition.southwest), 14);

		this.roadMap.put(
				new LocationKey(8, HexComponent.RoadPosition.northwest), 13);
		this.roadMap.put(new LocationKey(8, HexComponent.RoadPosition.north),
				10);
		this.roadMap.put(
				new LocationKey(8, HexComponent.RoadPosition.northeast), 14);
		this.roadMap.put(
				new LocationKey(8, HexComponent.RoadPosition.southeast), 22);
		this.roadMap.put(new LocationKey(8, HexComponent.RoadPosition.south),
				27);
		this.roadMap.put(
				new LocationKey(8, HexComponent.RoadPosition.southwest), 21);

		this.roadMap.put(
				new LocationKey(9, HexComponent.RoadPosition.northwest), 30);
		this.roadMap.put(new LocationKey(9, HexComponent.RoadPosition.north),
				27);
		this.roadMap.put(
				new LocationKey(9, HexComponent.RoadPosition.northeast), 31);
		this.roadMap.put(
				new LocationKey(9, HexComponent.RoadPosition.southeast), 39);
		this.roadMap.put(new LocationKey(9, HexComponent.RoadPosition.south),
				44);
		this.roadMap.put(
				new LocationKey(9, HexComponent.RoadPosition.southwest), 38);

		this.roadMap.put(new LocationKey(10,
				HexComponent.RoadPosition.northwest), 47);
		this.roadMap.put(new LocationKey(10, HexComponent.RoadPosition.north),
				44);
		this.roadMap.put(new LocationKey(10,
				HexComponent.RoadPosition.northeast), 48);
		this.roadMap.put(new LocationKey(10,
				HexComponent.RoadPosition.southeast), 56);
		this.roadMap.put(new LocationKey(10, HexComponent.RoadPosition.south),
				61);
		this.roadMap.put(new LocationKey(10,
				HexComponent.RoadPosition.southwest), 55);

		this.roadMap.put(new LocationKey(11,
				HexComponent.RoadPosition.northwest), 56);
		this.roadMap.put(new LocationKey(11, HexComponent.RoadPosition.north),
				53);
		this.roadMap.put(new LocationKey(11,
				HexComponent.RoadPosition.northeast), 57);
		this.roadMap.put(new LocationKey(11,
				HexComponent.RoadPosition.southeast), 65);
		this.roadMap.put(new LocationKey(11, HexComponent.RoadPosition.south),
				68);
		this.roadMap.put(new LocationKey(11,
				HexComponent.RoadPosition.southwest), 64);

		this.roadMap.put(new LocationKey(12,
				HexComponent.RoadPosition.northwest), 49);
		this.roadMap.put(new LocationKey(12, HexComponent.RoadPosition.north),
				45);
		this.roadMap.put(new LocationKey(12,
				HexComponent.RoadPosition.northeast), 50);
		this.roadMap.put(new LocationKey(12,
				HexComponent.RoadPosition.southeast), 58);
		this.roadMap.put(new LocationKey(12, HexComponent.RoadPosition.south),
				62);
		this.roadMap.put(new LocationKey(12,
				HexComponent.RoadPosition.southwest), 57);

		this.roadMap.put(new LocationKey(13,
				HexComponent.RoadPosition.northwest), 41);
		this.roadMap.put(new LocationKey(13, HexComponent.RoadPosition.north),
				37);
		this.roadMap.put(new LocationKey(13,
				HexComponent.RoadPosition.northeast), 42);
		this.roadMap.put(new LocationKey(13,
				HexComponent.RoadPosition.southeast), 51);
		this.roadMap.put(new LocationKey(13, HexComponent.RoadPosition.south),
				54);
		this.roadMap.put(new LocationKey(13,
				HexComponent.RoadPosition.southwest), 50);

		this.roadMap.put(new LocationKey(14,
				HexComponent.RoadPosition.northwest), 24);
		this.roadMap.put(new LocationKey(14, HexComponent.RoadPosition.north),
				20);
		this.roadMap.put(new LocationKey(14,
				HexComponent.RoadPosition.northeast), 25);
		this.roadMap.put(new LocationKey(14,
				HexComponent.RoadPosition.southeast), 34);
		this.roadMap.put(new LocationKey(14, HexComponent.RoadPosition.south),
				37);
		this.roadMap.put(new LocationKey(14,
				HexComponent.RoadPosition.southwest), 33);

		this.roadMap.put(new LocationKey(15,
				HexComponent.RoadPosition.northwest), 15);
		this.roadMap.put(new LocationKey(15, HexComponent.RoadPosition.north),
				11);
		this.roadMap.put(new LocationKey(15,
				HexComponent.RoadPosition.northeast), 16);
		this.roadMap.put(new LocationKey(15,
				HexComponent.RoadPosition.southeast), 24);
		this.roadMap.put(new LocationKey(15, HexComponent.RoadPosition.south),
				28);
		this.roadMap.put(new LocationKey(15,
				HexComponent.RoadPosition.southwest), 23);

		this.roadMap.put(new LocationKey(16,
				HexComponent.RoadPosition.northwest), 22);
		this.roadMap.put(new LocationKey(16, HexComponent.RoadPosition.north),
				19);
		this.roadMap.put(new LocationKey(16,
				HexComponent.RoadPosition.northeast), 23);
		this.roadMap.put(new LocationKey(16,
				HexComponent.RoadPosition.southeast), 32);
		this.roadMap.put(new LocationKey(16, HexComponent.RoadPosition.south),
				36);
		this.roadMap.put(new LocationKey(16,
				HexComponent.RoadPosition.southwest), 31);

		this.roadMap.put(new LocationKey(17,
				HexComponent.RoadPosition.northwest), 39);
		this.roadMap.put(new LocationKey(17, HexComponent.RoadPosition.north),
				36);
		this.roadMap.put(new LocationKey(17,
				HexComponent.RoadPosition.northeast), 40);
		this.roadMap.put(new LocationKey(17,
				HexComponent.RoadPosition.southeast), 49);
		this.roadMap.put(new LocationKey(17, HexComponent.RoadPosition.south),
				53);
		this.roadMap.put(new LocationKey(17,
				HexComponent.RoadPosition.southwest), 48);

		this.roadMap.put(new LocationKey(18,
				HexComponent.RoadPosition.northwest), 32);
		this.roadMap.put(new LocationKey(18, HexComponent.RoadPosition.north),
				28);
		this.roadMap.put(new LocationKey(18,
				HexComponent.RoadPosition.northeast), 33);
		this.roadMap.put(new LocationKey(18,
				HexComponent.RoadPosition.southeast), 41);
		this.roadMap.put(new LocationKey(18, HexComponent.RoadPosition.south),
				45);
		this.roadMap.put(new LocationKey(18,
				HexComponent.RoadPosition.southwest), 40);

	}

}
