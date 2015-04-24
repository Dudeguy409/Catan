package client.Model;

import client.Controller.Game;
import client.Controller.Game.Resource;

public class Hex {

	private Resource resource;
	private int rollNumber;
	private int hexID;

	public Hex(int rollNumber, Game.Resource resource, int hexID) {
		this.rollNumber = rollNumber;
		this.resource = resource;
		this.hexID = hexID;
	}

	public int getRollNumber() {
		return this.rollNumber;
	}

	public Resource getResource() {
		return this.resource;
	}
	
	public int getHexID() {
		return this.hexID;
	}

}
