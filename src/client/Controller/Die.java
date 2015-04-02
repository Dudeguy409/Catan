package client.Controller;
import java.util.Random;

public class Die {
	Random random;

	public Die() {
		random = new java.util.Random();
	}

	public Die(long seed) {
		random = new java.util.Random(1337);
	}

	public int rollDie() {
		return ((int) (random.nextDouble() * 6 + 1));
	}
}
