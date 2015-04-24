package client.Model;

import client.Controller.Game;
import client.Controller.Game.Resource;

public class Hex {

	private Resource resource;
	private int rollNumber;

	public Hex(int rollNumber, Game.Resource resource) {
		this.rollNumber = rollNumber;
		this.resource = resource;
	}

	public int getRollNumber() {
		return this.rollNumber;
	}

	public Resource getResource() {
		return this.resource;
	}

}
