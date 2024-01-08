/**
 *	ScoreCard: First, this class prints out the header to the score
 * 	table seen by the user before or after they are done rolling their dice.
 * 	There are methods here used to calculate score based on the integer
 * 	the user selected in the table (e.g. if they selected 11 we call the
 * 	large straight method to calculate their potential score). 
 *
 *	@author	Prathik Kumar
 *	@since	Oct 6, 2023
 */
public class YahtzeeScoreCard {

	private int[] scoreCard = new int[14]; // Array used to score all 13 scoring options, 0th index not used
	/**
	 *	Get a category score on the score card.
	 *	@param i		the category number (1 to 13)
	 *	@return			the score of that category
	 */
	public int getScore(int i) {

		return scoreCard[i];
	}

	public YahtzeeScoreCard() {
		for(int i = 0; i < scoreCard.length; i++) {
			scoreCard[i] = -1;
		}

	}
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println();
		System.out.printf("\t\t\t                       3of  4of  Fll Smll  Lrg\n");
		System.out.printf("  NAME\t\t 1    2    3    4    5    6    Knd  Knd  Hse Strt  Strt Chnc Ytz!\n");
	}

	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {

		if (scoreCard[choice] != -1) {
			return false;
		} else if (choice >= 1 && choice <= 6) {
			numberScore(choice, dg);
		} else if (choice == 7) {
			threeOfAKind(dg);
		} else if (choice == 8) {
			fourOfAKind(dg);
		} else if (choice == 9) {
			fullHouse(dg);
		} else if (choice == 10) {
			smallStraight(dg);
		} else if (choice == 11) {
			largeStraight(dg);
		} else if (choice == 12) {
			chance(dg);
		} else if (choice == 13) {
			yahtzeeScore(dg);
		}
		else {
			return false;
		}
		return true;
	}

	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int chgScore = 0;

		for(int i = 0; i < 5; i++) {
			int rollVal = dg.getDie(i).getLastRollValue(); //check the roll values against the choice, and calculate the score accordingly.
			if (choice == rollVal) {
				chgScore = chgScore + rollVal;
			}
		}

		scoreCard[choice] = chgScore;

	}
	/**
	 * Calculates and updates the score for the Three of a Kind category.
	 *
	 * Three of a Kind consists of three dice with the same value, regardless of the other two dice.
	 *
	 * @param dg The DiceGroup object representing the current dice configuration.
	 */
	public void threeOfAKind(DiceGroup dg) {
		int[] count = new int[7]; // Initialize for values 0 to 6

		for (int i = 0; i < 7; i++) {
			count[i] = 0;
		}

		for (int i = 0; i < 5; i++) {
			int currentDiceVal = dg.getDie(i).getLastRollValue();
			count[currentDiceVal] = count[currentDiceVal] + 1;
		}

		boolean isItThreeOfAKind = false;

		for (int i = 1; i < 7; i++) {
			if (count[i] > 2) {
				isItThreeOfAKind = true;
			}
		}

		if (isItThreeOfAKind) {
			scoreCard[7] = dg.getTotal();
		} else {
			scoreCard[7] = 0;
		}
	}

	/**
	 * Calculates and updates the score for the Four of a Kind category.
	 *
	 * Four of a Kind consists of four dice with the same value, regardless of the fifth die.
	 *
	 * @param dg The DiceGroup object representing the current dice configuration.
	 */
	public void fourOfAKind(DiceGroup dg) {
		int[] count = new int[7]; // Initialize for values 0 to 6

		for (int i = 0; i < 7; i++) {
			count[i] = 0;
		}

		for (int i = 0; i < 5; i++) { // Adjust loop indices for dice indexing
			int currentDiceVal = dg.getDie(i).getLastRollValue();
			count[currentDiceVal] = count[currentDiceVal] + 1;
		}

		boolean isItFourOfAKind = false;

		for (int i = 1; i < 7; i++) {
			if (count[i] > 3) { // Check if any value appears four or more times
				isItFourOfAKind = true;
			}
		}

		if (isItFourOfAKind) {
			scoreCard[8] = dg.getTotal(); // If four-of-a-kind, update the score accordingly
		} else {
			scoreCard[8] = 0; // If not, set the score to 0
		}
	}


	/**
	 * Calculates and updates the score for the Full House category.
	 *
	 * A Full House consists of three dice with the same value (three of a kind)
	 * and two dice with the same value (a pair).
	 *
	 * @param dg The DiceGroup object representing the current dice configuration.
	 */
	public void fullHouse(DiceGroup dg) {
		int[] count = new int[7]; // Initialize for values 0 to 6

		for (int i = 0; i < 7; i++) {
			count[i] = 0;
		}

		for (int i = 0; i < 5; i++) { // Adjust loop indices for dice indexing
			int rollValue = dg.getDie(i).getLastRollValue();
			count[rollValue]++;
		}

		boolean isFullHouse = false;
		boolean hasThreeOfAKind = false;
		boolean hasPair = false;

		for (int i = 1; i < 7; i++) {
			if (count[i] == 3) {
				hasThreeOfAKind = true;
			} else if (count[i] == 2) {
				hasPair = true;
			}
		}

		if (hasThreeOfAKind && hasPair) {
			isFullHouse = true;
		}

		if (isFullHouse) {
			scoreCard[9] = 25; // If it's a full house, set the score to 25 points
		} else {
			scoreCard[9] = 0; // If it isn't, set the score to 0
		}
	}


	/**
	 * Calculates and sets the score for the Small Straight category in Yahtzee.
	 * A Small Straight consists of 4 consecutive die faces (e.g., 1-2-3-4 or 2-3-4-5).
	 * If a Small Straight is achieved, the score is set to 30 points; otherwise, it's set to 0 points.
	 *
	 * @param dg The DiceGroup representing the rolled dice.
	 */
	public void smallStraight(DiceGroup dg) {
		int[] count = new int[7]; // Initialize for values 0 to 6
		for (int i = 0; i < 7; i++) {
			count[i] = 0;
		}

		for (int i = 0; i < 5; i++) {
			int rollValue = dg.getDie(i).getLastRollValue();
			count[rollValue]++;
		}

		// Check if Small Straight
		if ((count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] >= 1) ||
				(count[2] >= 1 && count[3] >= 1 && count[4] >= 1 && count[5] >= 1)) {
			scoreCard[10] = 30; // Small Straight is 30 points
		} else {
			scoreCard[10] = 0; // No Small Straight
		}
	}


	/**
	 * Calculates and sets the score for the Large Straight category in Yahtzee.
	 * A Large Straight consists of 5 consecutive die faces (e.g., 1-2-3-4-5 or 2-3-4-5-6).
	 * If a Large Straight is achieved, the score is set to 40 points; otherwise, it's set to 0 points.
	 *
	 * @param dg The DiceGroup representing the rolled dice.
	 */
	public void largeStraight(DiceGroup dg) {
		int[] count = new int[7]; // Initialize for values 0 to 6
		for (int i = 0; i < 7; i++) {
			count[i] = 0;
		}
		for (int i = 0; i < 5; i++) {
			int rollValue = dg.getDie(i).getLastRollValue();
			count[rollValue]++;
		}

		// Check for the presence of a Large Straight
		if ((count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1) ||
				(count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1 && count[6] == 1)) {
			scoreCard[11] = 40; // Large Straight is worth 40 points
		} else {
			scoreCard[11] = 0; // No Large Straight.
		}
	}

	/**
	 * Calculates the score for the "Chance" category in Yahtzee.
	 *
	 * The "Chance" category allows the player to score the sum of all the dice
	 * without any specific combination requirements.
	 *
	 * @param dg The DiceGroup containing the dice to be scored.
	 */
	public void chance(DiceGroup dg) {
		int total = 0; // Initialize a variable to store the total score.

		// Loop through all the dice and sum up their values.
		for (int i = 0; i < 5; i++) {
			int rollValue = dg.getDie(i).getLastRollValue();
			total += rollValue;
		}

		scoreCard[12] = total; // Assign the total score to the "Chance" category (index 12).
	}


	/**
	 * Calculates the score for the Yahtzee category based on the given dice roll.
	 * Yahtzee is achieved when all dice have the same value.
	 *
	 * @param dg The DiceGroup containing the rolled dice.
	 */
	public void yahtzeeScore(DiceGroup dg) {
		boolean isYahtzee = true; // Assume all dice have the same value

		// Check if all dice have the same value
		int firstDieValue = dg.getDie(0).getLastRollValue();
		for (int i = 1; i < 5; i++) {
			int currentDieValue = dg.getDie(i).getLastRollValue();
			if (currentDieValue != firstDieValue) {
				isYahtzee = false;
				break; 
			}
		}

		// Assign the score based on whether it's a Yahtzee or not
		if (isYahtzee) {
			scoreCard[13] = 50;
		}
		else {
			scoreCard[13] = 0;
		}
	}
	/**
	 * Calculates and returns the total score from the score card.
	 *
	 * @return The total score obtained by summing up all the scores in the score card.
	 */
	public int getTotal() {
		int total = 0;

		for(int i = 1; i < 14; ++i) {
			total += this.scoreCard[i];
		}
		return total;
	}
}
