import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({ test.class, GameTest.class, HexManagerTest.class,
		StructurePieceTest.class, LongestRoadTest.class,
		DevelopmentCardTest.class, GameTradeTest.class })
public class AllTests {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("AllTests");
	}
}
