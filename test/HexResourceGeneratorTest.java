import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.junit.Test;

import client.Controller.HexResourceTypeGenerator;

public class HexResourceGeneratorTest {
	private static final int numberOfTestLoops = 900000;
	private static final int expectedMinimumNumberOfResults = 3000;

	@Test
	public void testRandomPermutation() {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		Random randomGenerator = new Random(0L);

		for (int i = 0; i < numberOfTestLoops; i++) {
			String result = Arrays.toString(HexResourceTypeGenerator
					.getHexColors(randomGenerator.nextLong()));
			if (results.containsKey(result)) {
				results.put(result, results.get(result) + 1);
			} else {
				results.put(result, 1);
			}
		}

		assertTrue(expectedMinimumNumberOfResults < results.size());

		// No single combination of hex resources should make up more than 2
		// percent of the results
		int expectedMaximumNumberOfOccurences = (int) (results.size() * .02);

		int max = 0;

		for (Entry<String, Integer> e : results.entrySet()) {
			int rslt = e.getValue();
			if (rslt > max) {
				max = rslt;
			}
		}

		assertFalse(max > expectedMaximumNumberOfOccurences);
	}
}
