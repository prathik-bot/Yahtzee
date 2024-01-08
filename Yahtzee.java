/**
 *	First, this class prints out the introduction to the game Yahtzee, 
 * 	and it takes player name input. Afterwards, we calculate 
 * 	which player had a bigger dice roll and they go first accordingly.
 * 	The score table is printed out after this is done. There is also a method that 
 * 	simulates one player turn 13 times (one turn is when both players make a move).
 * 	In each turn, we collect the number of dice the user wants to keep and 
 * 	we don't reroll them. At the end, the scores of both players are displayed here.
 *
 *	@author Prathik Kumar
 *	@since Oct 6, 2023
 */
import java.util.Arrays;
import java.util.Scanner;

public class Yahtzee {

	YahtzeePlayer player1; // Object for first player entered
	YahtzeePlayer player2; // Object for second player entered

	YahtzeePlayer currentPlayer; // Object for the player who is currently rolling dice
	YahtzeeScoreCard scoreCard; // Object to access the score of either player

	private boolean player1RollsFirst; // boolean used to determine who goes first
	private final String DEFAULT_CHOICE; // constant containing -1, used to terminate a player's turn

	public static void main(String[] var0) {
		Yahtzee yahtzee = new Yahtzee();
		yahtzee.run();
	}

	/**
	 * Default constructor
	 */
	public Yahtzee() {
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		currentPlayer = new YahtzeePlayer();
		player1RollsFirst = true; 
		DEFAULT_CHOICE = "-1";
		scoreCard = new YahtzeeScoreCard();
	}

	/**
	 * Prints Yahtzee game instructions.
	 */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

	/**
	 * Runs the Yahtzee game, including setup, player turns, and displaying results.
	 * and displays the final results of the game.
	 */
	public void run() {
		printHeader();
		// Get player names
		getPlayerNames();
		determineStartingPlayer();
		displayCardDetails();
		for (int round = 1; round <= 13; round++) {
			System.out.printf("\nRound %d of 13 rounds.\n\n", round);
			playTurn(currentPlayer);
			switchPlayers();
			playTurn(currentPlayer);
			switchPlayers();

		}

		displayResults();
		System.out.println();
	}

	/**
	 * Alternate between player1 and player 2 as needed.
	 */
	public void switchPlayers() {
		player1RollsFirst = !player1RollsFirst;
		if (!player1RollsFirst)
			currentPlayer = player2;
		else
			currentPlayer = player1;

	}

	/**
	 * Prompts the user to enter names for Player 1 and Player 2 and
	 * assigns the entered names to the respective players.
	 * This method ensures that both names are non-empty before proceeding.
	 */
	public void getPlayerNames() {
		Scanner scanner = new Scanner(System.in);

		boolean validNamesEntered = false;

		while (!validNamesEntered) {
			System.out.print("Player 1, please enter your first name :  ");
			String player1Name = scanner.nextLine();
			System.out.println();
			System.out.print("Player 2, please enter your first name : ");
			String player2Name = scanner.nextLine();
			System.out.println();
			// Check for error conditions (e.g., empty names)
			if (player1Name.isEmpty() || player2Name.isEmpty()) {
				System.out.println("Player names cannot be empty. Please enter valid names.");
			} else {
				// Valid names are entered, exit the loop
				validNamesEntered = true;
				player1.setName(player1Name);
				player2.setName(player2Name);
			}
		}
	}

	/**
	 * Determines the starting player through a die rolls.
	 * The player with the higher total roll gets to go first.
	 */
	public void determineStartingPlayer() {
		Scanner scanner = new Scanner(System.in);
		DiceGroup diceGroup = new DiceGroup();
		boolean inputReceived = false;
		while (!inputReceived) {
			System.out.printf("\nLet's see who will go first. %s, please hit enter to roll the dice : ", player1.getName());
			scanner.nextLine();
			diceGroup.rollDice();
			diceGroup.printDice();
			int player1Total = diceGroup.getTotal();
			System.out.println();
			System.out.printf("%s, it's your turn. Please hit enter to roll the dice: ", player2.getName());
			scanner.nextLine();
			diceGroup.rollDice();
			diceGroup.printDice();
			int player2Total = diceGroup.getTotal();
			inputReceived = true;
			if (player1Total == player2Total) {
				System.out.printf("Whoops, we have a tie (both rolled %d). Looks like we'll have to try that again...\n", player1Total);
				currentPlayer = player1;//default
			} else {
				if (player1Total > player2Total) {
					System.out.printf("\n%s, you rolled a sum of %d, and %s, you rolled a sum of %d.\n", player1.getName(), player1Total, player2.getName(), player2Total);
					System.out.print(player1.getName());
					setRollsFirst(player1); // Set player1 as the one who rolls first
					currentPlayer = player1;
				} else {
					System.out.printf("\n%s, you rolled a sum of %d, and %s, you rolled a sum of %d.\n", player1.getName(), player1Total, player2.getName(), player2Total);
					System.out.print(player2.getName());
					setRollsFirst(player2); // Set player2 as the one who rolls first
					currentPlayer = player2;
				}

				System.out.println(", since your sum was higher, you'll roll first.\n");
				break;
			}
		}
	}

	/**
	 * Sets the player who rolls the dice first.
	 *
	 * @param player The player to set as the first roller.
	 */
	public void setRollsFirst(YahtzeePlayer player) {
		player1RollsFirst = (player == player1);
	}

	/**
	 * Allows a Yahtzee player to take their turn in the game.
	 *
	 * @param player The Yahtzee player who is taking the turn.
	 */
	public void playTurn(YahtzeePlayer player) {
		Scanner scanner = new Scanner(System.in);
		DiceGroup diceGroup = new DiceGroup();
		System.out.printf("\n%s, it's your turn to play. Please hit enter to roll the dice : ", player.getName());
		scanner.nextLine();
		diceGroup.rollDice();
		diceGroup.printDice();
		int rollCount = 0;
		while (rollCount < 2) {
			System.out.println("\nWhich di(c)e would you like to keep?  Enter the values you'd like to 'hold' without");
			System.out.println("spaces. For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125");
			System.out.print("(enter -1 if you'd like to end the turn) : ");
			String userC = scanner.nextLine();
			System.out.println("user choice " + userC);
			if (userC.equals(DEFAULT_CHOICE)) {
				break; 
			} else {
				diceGroup.rollDice(userC);
				diceGroup.printDice();
			}
			rollCount++;
		}
		displayCardDetails();
		System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
		System.out.printf("%s, now you need to make a choice. Pick a valid integer from the list above : ", player.getName());
		boolean validChoice = false;
		int categoryChoice;

		do {
			categoryChoice = scanner.nextInt();

			if (categoryChoice >= 1 && categoryChoice <= 13) {
				if (player.getScoreCard().getScore(categoryChoice) > Integer.parseInt(DEFAULT_CHOICE)) {
					System.out.print("Pick a valid integer from the list above : ");
				} else {
					validChoice = true;
				}
			} else {
				System.out.print("Pick a valid integer from the list above : ");
			}
		} while (!validChoice);

		player.getScoreCard().changeScore(categoryChoice, diceGroup);
		displayCardDetails();
		System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
		
	}

	/**
	 * Displays score for player 1 and player 2
	 */
	public void displayCardDetails() {
		scoreCard.printCardHeader();
		// Display Player 1's scores
		displayPlayerScores(player1);
		// Display Player 2's scores
		displayPlayerScores(player2);
		System.out.printf("+-------------------------------------------------------------------------------+\n");

	}

	/**
	 * Displays the scores of a Yahtzee player's score card in a formatted table.
	 *
	 * @param player The Yahtzee player whose scores will be displayed.
	 */
	private void displayPlayerScores(YahtzeePlayer player) {
		System.out.printf("+-------------------------------------------------------------------------------+\n");
		YahtzeeScoreCard scoreCard = player.getScoreCard();

		System.out.printf("| %-12s |", player.getName());

		for (int category = 1; category <= 13; category++) {
			int score = scoreCard.getScore(category);
			if (score > Integer.parseInt(DEFAULT_CHOICE)) {
				System.out.printf(" %2d |", score);
			} else {
				System.out.printf("    |");
			}
		}
		System.out.println();
	}

	/**
	 * Displays final results when game is over.
	 */
	public void displayResults() {
		int player1Total = player1.getScoreCard().getTotal();
		int player2Total = player2.getScoreCard().getTotal();
		System.out.printf("%-10s had a score of %d\n", this.player1.getName(), player1Total);
		System.out.printf("%-10s had a score of %d\n", this.player2.getName(), player2Total);
	}
}
