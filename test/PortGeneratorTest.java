import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import org.junit.Test;
import client.Controller.PortResourceTypeGenerator;

public class PortGeneratorTest {
	private static final int numberOfTestLoops = 900000;
	private static final int expectedNumberOfResults = 720;

	@Test
	public void testRandomPermutation() {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		Random randomGenerator = new Random(0L);

		for (int i = 0; i < numberOfTestLoops; i++) {
			String result = Arrays.toString(PortResourceTypeGenerator
					.getRandomPortResources(randomGenerator.nextLong()));
			if (results.containsKey(result)) {
				results.put(result, results.get(result) + 1);
			} else {
				results.put(result, 1);
			}
		}

		assertEquals(expectedNumberOfResults, results.size());

		// actual value: 1250
		int expectedAvg = numberOfTestLoops / expectedNumberOfResults;

		int roof = (int) (expectedAvg * 1.15);
		int floor = (int) (expectedAvg * 0.85);

		for (Entry<String, Integer> e : results.entrySet()) {
			int rslt = e.getValue();
			assertTrue(rslt < roof);
			assertTrue(rslt > floor);
		}

	}

}
