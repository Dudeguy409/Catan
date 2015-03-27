import static org.junit.Assert.*;

import org.junit.Test;


public class test {

	@Test
	public void testRollDieMoreThanZero() {
		Die die = new Die();
		assertTrue(die.rollDie()>0);
	}
	
	@Test
	public void testRollDieLessThanSeven() {
		Die die = new Die();
		assertTrue(die.rollDie()<7);
	}
	
	@Test
	public void testRoadExists() {
		Road road = new Road(0, null, null);
		assertNotNull(road);
	}

	@Test
	public void testStructureGetPlayer() {
		Structure struct = new Structure(1, 0, 0, 0, 0, 0, null);
		assertEquals(1, struct.getPlayer());
	}
}
