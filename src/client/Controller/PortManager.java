package client.Controller;

import java.util.HashMap;

import client.Controller.Game.Resource;

public class PortManager {
	private HashMap<Integer, Resource> portMap;

	public PortManager(Game.Resource[] portTypes) {
		this.portMap = new HashMap<Integer, Game.Resource>();
		this.portMap.put(51, portTypes[0]);
		this.portMap.put(52, portTypes[0]);
		this.portMap.put(24, portTypes[1]);
		this.portMap.put(30, portTypes[1]);
		this.portMap.put(6, portTypes[2]);
		this.portMap.put(11, portTypes[2]);
		this.portMap.put(8, portTypes[3]);
		this.portMap.put(3, portTypes[3]);
		this.portMap.put(25, portTypes[4]);
		this.portMap.put(19, portTypes[4]);
		this.portMap.put(49, portTypes[5]);
		this.portMap.put(50, portTypes[5]);

		this.portMap.put(1, Game.Resource.desert);
		this.portMap.put(2, Game.Resource.desert);
		this.portMap.put(37, Game.Resource.desert);
		this.portMap.put(43, Game.Resource.desert);
		this.portMap.put(42, Game.Resource.desert);
		this.portMap.put(48, Game.Resource.desert);
	}

	public Resource getPortTypeForStructurePosition(int structId) {
		return this.portMap.get(structId);
	}

}
