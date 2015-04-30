

import client.Controller.IDice;

public class FakeDice implements IDice {

	private int[] riggedDiceRollArrayA;
	private int[] riggedDiceRollArrayB;
	private int numberOfRolls;

	public FakeDice(int[] rollArrayA, int[] RollArrayB) {
		this.riggedDiceRollArrayA = rollArrayA;
		this.riggedDiceRollArrayB = rollArrayA;
		this.numberOfRolls = 0;
	}

	/**
	 * 
	 * @return an array with { the total, first roll, second roll}
	 */
	@Override
	public int[] rollDice() {
		int[] rslt = new int[3];

		rslt[1] = riggedDiceRollArrayA[numberOfRolls
				% riggedDiceRollArrayA.length];
		
		rslt[2] = riggedDiceRollArrayB[numberOfRolls
				% riggedDiceRollArrayB.length];
		
		rslt[0] = rslt[1] + rslt[2];
		
		numberOfRolls++;
		
		return rslt;
	}

}
