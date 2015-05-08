package client.Controller;

import java.util.ArrayList;
import java.util.Random;

import client.Controller.Game.Resource;

public class PortResourceTypeGenerator {

	public static Resource[] getRandomPortResources(long seed) {

		// makes an array of the different resources for the changeable ports.
		ArrayList<Resource> portResourceArray = new ArrayList<Game.Resource>();
		portResourceArray.add(Resource.desert);
		portResourceArray.add(Resource.wood);
		portResourceArray.add(Resource.brick);
		portResourceArray.add(Resource.ore);
		portResourceArray.add(Resource.wheat);
		portResourceArray.add(Resource.sheep);

		// randomly chooses one element from the fixed array above, copies
		// it to the new array, and deletes the element copied.
		Resource[] randomPortArray = new Resource[portResourceArray.size()];
		Random generator = new Random(seed);
		for (int i = 0; i < randomPortArray.length; i++) {
			int readValue = generator.nextInt(portResourceArray.size());
			randomPortArray[i] = portResourceArray.get(readValue);
			portResourceArray.remove(readValue);
		}
		return randomPortArray;
	}

}
