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

}
