package client.Controller;


public class Dice {

	private Die firstDie;
	private Die secondDie;

	public Dice() {
		this.firstDie = new Die();
		this.secondDie = new Die();
	}

	/**
	 * 
	 * @return an array with { the total, first roll, second roll}
	 */
	public int[] rollDice() {
		int[] result = new int[3];
		int first = this.firstDie.rollDie();
		int second = this.secondDie.rollDie();

		result[0] = first + second;
		result[1] = first;
		result[2] = second;

		return result;
	}

}
