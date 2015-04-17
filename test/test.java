import static org.junit.Assert.*;

import org.junit.Test;

import client.Controller.Dice;
import client.Controller.Die;
import client.Model.Road;

public class test {

	@Test
	public void testDieInitialize() {
		Die die = new Die();
		assertTrue(die != null);
	}

	@Test
	public void testDieInitializeSeed() {
		Die die = new Die(1337);
		assertTrue(die != null);
	}

	@Test
	public void testRollDieMoreThanZero() {
		Die die = new Die(1337);
		assertTrue(die.rollDie() > 0);

		die = new Die(0);
		assertTrue(die.rollDie() > 0);

		die = new Die(30000);
		assertTrue(die.rollDie() > 0);
	}

	@Test
	public void testRollDieLessThanSeven() {
		Die die = new Die(1337);
		assertTrue(die.rollDie() < 7);

		die = new Die(0);
		assertTrue(die.rollDie() < 7);

		die = new Die(30000);
		assertTrue(die.rollDie() < 7);
	}

	@Test
	public void testDiceInitialize() {
		Dice dice = new Dice();
		assertTrue(dice != null);
	}

	@Test
	public void testRoadExists() {
		Road road = new Road(0, null, null);
		assertNotNull(road);
	}

	@Test
	public void testStructureGetPlayer() {
		// TODO fix
		// Structure struct = new Structure(1, 0, 0, 0, 0, 0, null);
		// assertEquals(1, struct.getPlayer());
		// fail();
	}
}
