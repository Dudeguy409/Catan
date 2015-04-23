package client.Controller;

import java.util.ArrayList;
import java.util.Random;

import client.Controller.Game.Resource;

public class HexResourceTypeGenerator {
	
public static Resource[] getHexColors(long seed) {
	
	// makes an array of the different resources for the hexes.
			ArrayList<Resource> colorNumberArray = new ArrayList<Game.Resource>();
			colorNumberArray.add(Resource.desert);
			colorNumberArray.add(Resource.wood);
			colorNumberArray.add(Resource.wood);
			colorNumberArray.add(Resource.wood);
			colorNumberArray.add(Resource.wood);
			colorNumberArray.add(Resource.brick);
			colorNumberArray.add(Resource.brick);
			colorNumberArray.add(Resource.brick);
			colorNumberArray.add(Resource.ore);
			colorNumberArray.add(Resource.ore);
			colorNumberArray.add(Resource.ore);
			colorNumberArray.add(Resource.wheat);
			colorNumberArray.add(Resource.wheat);
			colorNumberArray.add(Resource.wheat);
			colorNumberArray.add(Resource.wheat);
			colorNumberArray.add(Resource.sheep);
			colorNumberArray.add(Resource.sheep);
			colorNumberArray.add(Resource.sheep);
			colorNumberArray.add(Resource.sheep);

			// randomly chooses one element from the fixed array above, copies
			// it to the new array, and deletes the element copied.
			Resource[] randomColorArray = new Resource[colorNumberArray.size()];
			Random generator = new Random(seed);
			for (int i = 0; i < randomColorArray.length; i++) {
				int readValue = generator.nextInt(colorNumberArray.size());
				randomColorArray[i] = colorNumberArray.get(readValue);
				colorNumberArray.remove(readValue);
			}
			return randomColorArray;
	}
	
}
