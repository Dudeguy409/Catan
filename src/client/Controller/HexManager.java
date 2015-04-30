package client.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import client.GUI.HexComponent;
import client.Model.LocationKey;
import client.Model.StructureLocationKey;

public class HexManager {

	public HashMap<LocationKey, Integer> roadMap;
	public HashMap<StructureLocationKey, Integer> structureMap;

	public HexManager() {

		initializeRoadMap();
		initializeStructureMap();

	}

	public int getRoadId(int hexIndex, HexComponent.RoadPosition pos) {
		return this.roadMap.get(new LocationKey(hexIndex, pos));
	}

	public int getStructureId(int hexIndex, HexComponent.StructurePosition pos) {
		return this.structureMap.get(new StructureLocationKey(hexIndex, pos));
	}

	public ArrayList<Integer> getAdjacentHexesForSettlement(int structureId) {
		ArrayList<Integer> rslts = new ArrayList<>();

		for (Entry<StructureLocationKey, Integer> e : this.structureMap
				.entrySet()) {
			if (e.getValue().intValue() == structureId) {
				rslts.add(e.getKey().getHexIndex());
			}
		}

		return rslts;
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

	private void initializeStructureMap() {

		this.structureMap = new HashMap<StructureLocationKey, Integer>();

		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.west), 50);
		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.northwest), 45);
		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.northeast), 46);
		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.east), 51);
		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.southeast), 54);
		this.structureMap.put(new StructureLocationKey(0,
				HexComponent.StructurePosition.southwest), 53);

		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.west), 46);
		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.northwest), 40);
		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.northeast), 41);
		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.east), 47);
		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.southeast), 52);
		this.structureMap.put(new StructureLocationKey(1,
				HexComponent.StructurePosition.southwest), 51);

		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.west), 41);
		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.northwest), 35);
		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.northeast), 36);
		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.east), 42);
		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.southeast), 48);
		this.structureMap.put(new StructureLocationKey(2,
				HexComponent.StructurePosition.southwest), 47);

		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.west), 29);
		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.northwest), 23);
		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.northeast), 24);
		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.east), 30);
		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.southeast), 36);
		this.structureMap.put(new StructureLocationKey(3,
				HexComponent.StructurePosition.southwest), 35);

		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.west), 17);
		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.northwest), 11);
		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.northeast), 12);
		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.east), 18);
		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.southeast), 24);
		this.structureMap.put(new StructureLocationKey(4,
				HexComponent.StructurePosition.southwest), 23);

		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.west), 10);
		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.northwest), 5);
		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.northeast), 6);
		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.east), 11);
		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.southeast), 17);
		this.structureMap.put(new StructureLocationKey(5,
				HexComponent.StructurePosition.southwest), 16);

		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.west), 4);
		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.northwest), 1);
		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.northeast), 2);
		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.east), 5);
		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.southeast), 10);
		this.structureMap.put(new StructureLocationKey(6,
				HexComponent.StructurePosition.southwest), 9);

		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.west), 8);
		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.northwest), 3);
		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.northeast), 4);
		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.east), 9);
		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.southeast), 15);
		this.structureMap.put(new StructureLocationKey(7,
				HexComponent.StructurePosition.southwest), 14);

		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.west), 13);
		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.northwest), 7);
		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.northeast), 8);
		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.east), 14);
		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.southeast), 20);
		this.structureMap.put(new StructureLocationKey(8,
				HexComponent.StructurePosition.southwest), 19);

		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.west), 25);
		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.northwest), 19);
		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.northeast), 20);
		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.east), 26);
		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.southeast), 32);
		this.structureMap.put(new StructureLocationKey(9,
				HexComponent.StructurePosition.southwest), 31);

		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.west), 37);
		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.northwest), 31);
		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.northeast), 32);
		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.east), 38);
		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.southeast), 44);
		this.structureMap.put(new StructureLocationKey(10,
				HexComponent.StructurePosition.southwest), 43);

		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.west), 44);
		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.northwest), 38);
		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.northeast), 39);
		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.east), 45);
		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.southeast), 50);
		this.structureMap.put(new StructureLocationKey(11,
				HexComponent.StructurePosition.southwest), 49);

		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.west), 39);
		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.northwest), 33);
		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.northeast), 34);
		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.east), 40);
		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.southeast), 46);
		this.structureMap.put(new StructureLocationKey(12,
				HexComponent.StructurePosition.southwest), 45);

		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.west), 34);
		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.northwest), 28);
		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.northeast), 29);
		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.east), 35);
		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.southeast), 41);
		this.structureMap.put(new StructureLocationKey(13,
				HexComponent.StructurePosition.southwest), 40);

		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.west), 22);
		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.northwest), 16);
		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.northeast), 17);
		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.east), 23);
		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.southeast), 29);
		this.structureMap.put(new StructureLocationKey(14,
				HexComponent.StructurePosition.southwest), 28);

		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.west), 15);
		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.northwest), 9);
		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.northeast), 10);
		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.east), 16);
		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.southeast), 22);
		this.structureMap.put(new StructureLocationKey(15,
				HexComponent.StructurePosition.southwest), 21);

		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.west), 20);
		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.northwest), 14);
		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.northeast), 15);
		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.east), 21);
		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.southeast), 27);
		this.structureMap.put(new StructureLocationKey(16,
				HexComponent.StructurePosition.southwest), 26);

		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.west), 32);
		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.northwest), 26);
		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.northeast), 27);
		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.east), 33);
		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.southeast), 39);
		this.structureMap.put(new StructureLocationKey(17,
				HexComponent.StructurePosition.southwest), 38);

		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.west), 27);
		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.northwest), 21);
		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.northeast), 22);
		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.east), 28);
		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.southeast), 34);
		this.structureMap.put(new StructureLocationKey(18,
				HexComponent.StructurePosition.southwest), 33);

	}

}
