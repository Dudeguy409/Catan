package client.Controller;

import java.util.ArrayList;
import java.util.Random;

import client.Controller.Game.Resource;

public class HexColorGenerator implements IHexColorGenerator {

	public ArrayList<Resource> getHexColors(){
		
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

				// randomly chooses one element each from the fixed arrays above, copies
				// it to a new array, and resets the element copied to zero. If the
				// element has already been drawn, it will repeat.

				Random generator = new Random();
				int desertColorIndex = -1;
				for (int i = 0; i < boardSize; i++) {
					int readValue = generator.nextInt(colorNumberArray.size());
					randomColorArray[i] = colorNumberArray.get(readValue);
					colorNumberArray.remove(readValue);

					if (randomColorArray[i] == Resource.desert) {
						desertColorIndex = i;
						randomNumberArray[i] = -1;
					} else {
						if (desertColorIndex < 0) {
							randomNumberArray[i] = rollNumberArray[i];
						} else {
							randomNumberArray[i] = rollNumberArray[i - 1];
						}
					}
				}
		
		return null;
	}
	
public ArrayList<Resource> getHexColors(int seed){
	
	return null;
	}
	
}
