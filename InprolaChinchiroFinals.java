import java.util.Random;
import java.util.Scanner;

class Roll {
    String type;
    int rank;
    int value;
    int[] dice = new int[3];

}

public class InprolaChinchiroFinals {
	
	public static Random random = new Random();
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		int playerPerica = 90000;
		int gameRound = 0;
		int playerBet = 0;

		System.out.println("Please enter the following numbers");
			System.out.println("[0] Play ahaead and skip instructions");
			System.out.println("[1] Read instructions and play ahead");
			int playerChoice = input.nextInt();

			while (playerChoice > 1 || playerChoice < 0) {
				System.out.println("Please pick from the choices above!");
				playerChoice = input.nextInt();
			}

			if (playerChoice == 1) {
				System.out.println("Welcome to ChinChinRoRinRoRinRinRoRin");
				System.out.println("ChinChinRoRinRoRinRinRoRin is played by rolling three dice. The goal is to get 2 dice to land on the same number. The remaining dice will be the score of the roll.");
				System.out.println("The remaining dice determines who wins, the higher the roll, the stronger the value");
				System.out.println("If the player rolls 3 different numbers, then it’s a bust.");
				System.out.println("If the player rolls a bust 3 consecutive times or he rolls a pisser, the player automatically loses.");
				System.out.println("A pisser happens when at least one die rolls out of the bowl.");
				System.out.println("The goal is to reach 500,000 Perica and survive the game without going bunkrupt");
				System.out.println("Goodluck");
				pressEnter();
				input.nextLine();
			} else {
				System.out.println("Welcome to ChinChinRoRinRoRinRinRoRin");
			}

		while (gameRound < 10 && playerPerica >= 100) {
			gameRound++;

			//Bet Phase
			playerBet = wager(playerPerica);
			input.nextLine();
			System.out.println("--------------------------------");
			System.out.println("You've bet " + playerBet);
			pressEnter();

			//Roll Phase			
			int rollPhase = 0;
			while (rollPhase == 0) {

				//Round Display
				System.out.println("--------------------------------");
				System.out.println("Current Round: " + gameRound);
				System.out.println("Current earnings: " + playerPerica + " Perica");
				System.out.println("Waged Perica: " + playerBet);
				pressEnter();

				//Dealer Roll				
				System.out.println("--------------------------------");
				System.out.println("Initiating Dealer Roll");
				System.out.println();
				Roll dealerRoll = rollDice();
				pressEnter();

				//Dealer Roll Conditions
				if (dealerRoll.type == "Pisser") {
					System.out.println("--------------------------------");
					System.out.println("The Dealer rolled a pisser");
					System.out.println("You received " + playerBet);
					playerPerica = playerPerica + playerBet;
					rollPhase = 1;
				} else {
					System.out.println("-----Dealer Roll Result-----");
					printRoll(dealerRoll);
					diceRank(dealerRoll);
					System.out.println(dealerRoll.type + " detected");
					pressEnter();

					int dRollAttempt = 1;
					if (dealerRoll.rank == 0) {
						while (dRollAttempt < 3 && dealerRoll.type == "Bust") {
							dRollAttempt++;
							dealerRoll = rollDice();
							if (dealerRoll.type == "Pisser") {
								System.out.println("--------------------------------");
								System.out.println("The Dealer rolled a pisser");
								System.out.println("You received " + playerBet);
								playerPerica = playerPerica + playerBet;
								rollPhase = 1;
							} else {
								System.out.println("-----Dealer Roll Result-----");
								printRoll(dealerRoll);
								diceRank(dealerRoll);
								System.out.println(dealerRoll.type + " detected");
								pressEnter();
							}
						}
					}

					//Player Roll Condition
					Roll playerRoll = rollDice();
					if (rollPhase == 1) {
						System.out.println("--------------------------------");
						System.out.println("You've earned " + playerBet);
						playerPerica = playerPerica + playerBet;
					} else {
						//Player Roll
						System.out.println("--------------------------------");
						System.out.println("It's your turn!");
						pressEnter();

						System.out.println("-----Your Roll Result-----");
						printRoll(playerRoll);
						diceRank(playerRoll);
						System.out.println(playerRoll.type + " detected");
						pressEnter();

						int pRollAttempt = 1;
						if (playerRoll.rank == 0) {
							while (pRollAttempt < 3 && playerRoll.type == "Bust") {
								pRollAttempt++;
								System.out.println("-----Your Roll Result-----");
								playerRoll = rollDice();
								printRoll(playerRoll);
								diceRank(playerRoll);
								System.out.println(playerRoll.type + " detected");
								pressEnter();
							}
						}
					}

					//Compare Values
					evaluateValue(playerRoll);
					evaluateValue(dealerRoll); 
					System.out.println("--------------------------------");
					System.out.println("RESULT EVALUATION");
                    System.out.println("DEALER ROLL:");
                    System.out.println("Rank: " + dealerRoll.rank);
                    System.out.println("Type: " + dealerRoll.type);
                    System.out.println("Value: " + dealerRoll.value);
                    System.out.println("PLAYER ROLL");
                    System.out.println("Rank: " + playerRoll.rank);
                    System.out.println("Type: " + playerRoll.type);
                    System.out.println("Value: " + playerRoll.value);

					int earnings = payoutPerica(playerRoll,dealerRoll, playerBet);
                    if (earnings == 0){
                        System.out.println("It's a draw, returning previous bet");
                        rollPhase = 1;
                    } else {
                        if (earnings > 0) {
                            System.out.println("you've earned " + earnings);
                        } else {
                            System.out.println("you've lost " + earnings);
                        }
                        playerPerica = playerPerica + earnings;
                        rollPhase = 1;
                    }
				}
			}

		}
		evaluateEnding(playerPerica, gameRound);
	}

	public static void pressEnter() {
        System.out.println("Press enter to continue");
        input.nextLine();
    }

    public static int wager(int playerPerica) {
        int playerBet = 0;
		System.out.println("--------------------------------");
		System.out.println("Current Earnings: " + playerPerica);
		System.out.println("How much do you want to bet?");
		playerBet = input.nextInt();
		while (playerBet < 100 || playerBet > playerPerica) {
			if (playerBet < 100) {
				System.out.println("The minimum bet is 100! Please enter another bet");
				playerBet = input.nextInt();
			} else {
				System.out.println("You cannot bet more than your current earnings! Please enter another bet");
				playerBet = input.nextInt();
			}
		}
        return playerBet;
    }
    public static Roll rollDice() {
        Roll roll = new Roll();
        int pisserChance = random.nextInt(100);
        if (pisserChance < 5) {
            roll.type = "Pisser";
        } else {
            for (int i = 0; i < roll.dice.length; i++) {
                roll.dice[i] = random.nextInt(6) + 1;
            }
        }
        return roll;
    }
    public static void printRoll(Roll roll) {
        if (roll.type == "Pisser") {
            System.out.println("One of the dice got out of the bowl");
        } else {
            for (int i = 0; i < roll.dice.length; i++) {
                System.out.println(roll.dice[i]);
            }
        }
    }
	public static void diceRank(Roll roll) {

        if (roll.dice[0] == 0 && roll.dice[1] == 0 && roll.dice[2] == 0){
            roll.type = "Pisser";
            roll.rank = 1;
        } else if (roll.dice[0] == 1 && roll.dice[1] == 1 && roll.dice[2] == 1) {
            roll.type = "Snake Eyes";
            roll.rank = 6;
        } else if(roll.dice[0] == roll.dice[1] && roll.dice[1] == roll.dice[2]) {
            roll.type = "Triple Roll";
            roll.rank = 5;
        } else if((roll.dice[0] != roll.dice[1] && roll.dice[0] != roll.dice[2] && roll.dice[1] != roll.dice[2])) {
            if ((roll.dice[0] == 4 || roll.dice[0] == 5 || roll.dice[0] == 6) && (roll.dice[1] == 4 || roll.dice[1] == 5 || roll.dice[1] == 6) && (roll.dice[2] == 4 || roll.dice[2] == 5 || roll.dice[2] == 6)) {
                roll.type = "4-5-6";
                roll.rank = 4;
            } else if ((roll.dice[0] == 1 || roll.dice[0] == 2 || roll.dice[0] == 3) && (roll.dice[1] == 1 || roll.dice[1] == 2 || roll.dice[1] == 3) && (roll.dice[2] == 1 || roll.dice[2] == 2 || roll.dice[2] == 3)){
                roll.type = "1-2-3";
                roll.rank = 2;
            } else {
                roll.type = "Bust";
                roll.rank = 0;
            }
        } else if (roll.dice[0] == roll.dice[1] || roll.dice[0] == roll.dice[2] || roll.dice[1] == roll.dice[2]) {
            roll.type = "Double Roll";
            roll.rank = 3;
        }
    }
	public static void evaluateValue(Roll roll) {
        if (roll.type == "Triple Roll"){
            roll.value = roll.dice[1];
        } else if (roll.type == "Double Roll"){
            if (roll.dice[0] == roll.dice[1]) {
                roll.value = roll.dice[2];
            } else if (roll.dice[0] == roll.dice[2]) {
                roll.value = roll.dice[1];
            } else if (roll.dice[1] == roll.dice[2]){
                roll.value = roll.dice[0];
            }
        }
    }
	/* Mechanic Guide
		ROLL HIERARCHY
			Snake eyes = 6
			Triple = 5
			4-5-6 = 4
			Doubles = 3
			1-2-3 = 2
			Pisser = 1
			Bust = 0 	
		MULTIPLIERS
			Snake Eyes - x5 of wager
			Triple - x3 of wager
			4-5-6 - x2 of wager
			Doubles - x1 of wager
			1-2-3 - x2 of wager
			Pisser - x1 of wager
			Bust - x1 of wager	*/
	public static int payoutPerica(Roll playerRoll, Roll dealerRoll, int playerBet) {
        int earnings = 0;

        if (dealerRoll.rank == 1) { // If dealer rolled pisser
			//player wins bet
			earnings = earnings + playerBet;
		} else if (playerRoll.rank == 2 || dealerRoll.rank == 2) { //If there's a 1-2-3
			if (playerRoll.rank == dealerRoll.rank) { //if it's a tie
				//Player wins bet
				earnings = earnings + playerBet;
			} else if (playerRoll.rank == 2) { //if player rolled 1-2-3
				//Player loses bet multiplied by 2
				earnings = earnings - (playerBet*2);
			} else if (dealerRoll.rank == 2) { //if dealer rolled 1-2-3
				if (playerRoll.rank == 1) {
					//It's a draw
					earnings = 0;
				} else if (playerRoll.rank == 6) {
					//player wins bet multiplied by 5
					earnings = earnings + (playerBet*5);
				} else if (playerRoll.rank == 5) {
					//player wins bet mulitplied by 3
					earnings = earnings + (playerBet*3);
				} else if (playerRoll.rank == 4) {
					//player wins bet multiplied by 2
					earnings = earnings + (playerBet*2);
				} else {
					//player wins bet
					earnings = earnings + playerBet;
				}
			}
		} else if (playerRoll.rank == 6 || dealerRoll.rank == 6) {  //if there's a snake eye
			if (playerRoll.rank == dealerRoll.rank) {
				//It's a tie
				earnings = 0;
			} else if (playerRoll.rank == 6) {
				//player wins bet multiplied by 5
				earnings = earnings + (playerBet*5);
			} else if (dealerRoll.rank == 6) {
				if (playerRoll.rank == 2) {
					//dealer wins bet multiplied by 2
					earnings = earnings - (playerBet*2);
				} else {
					//dealer wins bet
					earnings = earnings - playerBet;
				}
			}
		} else if (playerRoll.rank == 5 || dealerRoll.rank == 5) { //if there's a triple
			if (playerRoll.rank == dealerRoll.rank) {
				if (playerRoll.value == dealerRoll.value) { //check value
					//it's a tie
					earnings = 0;
				} else if (playerRoll.value > dealerRoll.value) {
					//player wins bet multiplied by 3
					earnings = earnings + (playerBet*3);
				} else {
					//dealer wins bet
					earnings = earnings - playerBet;
				}
			} else if (playerRoll.rank == 5) {
				//player wins bet multiplied by 3
				earnings = earnings + (playerBet*3);
			} else {
				//dealer wins bet
				earnings = earnings - playerBet;
			}
		} else if (playerRoll.rank == 4 || dealerRoll.rank ==4) { //if there's 4-5-6
			if (playerRoll.rank == dealerRoll.rank) {
				//It's a tie
				earnings = 0;
			} else if (playerRoll.rank == 4 ) {
				//player wins bet multiplied by 2
				earnings = earnings + (playerBet*2);
			} else if (dealerRoll.rank == 4) {
				//dealer wins bet
				earnings = earnings - playerBet;
			}
		} else if (playerRoll.rank == 3 || dealerRoll.rank == 3) { //if there's doubles
			if (playerRoll.rank == dealerRoll.rank) {
				if (playerRoll.value == dealerRoll.value) { //check value
					//it's a tie
					earnings = 0;
				} else if (playerRoll.value > dealerRoll.value) {
					//player wins bet
					earnings = earnings + playerBet;
				} else {
					//dealer wins bet
					earnings = earnings - playerBet;
				}
			} else if (playerRoll.rank == 3) {
				//player wins bet
				earnings = earnings + playerBet;
			} else if (dealerRoll.rank == 3) {
				//dealer wins bet
				earnings = earnings - playerBet;
			}
		} else if (playerRoll.rank == 1) { //if player pulled a pisser
			//player loses automatically
			earnings = earnings - playerBet;
		} else { // if both got bust
			//player wins bet
			earnings = earnings + playerBet;
		}
		return earnings;
    }
	public static void evaluateEnding(int playerPerica, int gameRound) {
		System.out.println("--------------------------------");
		System.out.println("Session Ended");
		System.out.println("Rounds Left: " + (10-gameRound));
		System.out.println("Your final Perica is: " + playerPerica);

		if (gameRound == 10) {
			if (playerPerica >= 500000) {
				System.out.println("You finished the game with a total amount of " + playerPerica + " Pericas");
				System.out.println("You've used your earnings to pay for your expenses, bought a house, a car, and started to live a comfortable life");
				System.out.println("You decided to quit gambling because you're rich now, you've started to invest the money you've earned for more sustainable income");
				System.out.println("ACHIEVEMENT UNLOCKED");
				System.out.println("Best Ending");
			} else if (playerPerica >= 90000 && playerPerica < 500000) {
				System.out.println("You've started the game with 90000 in your name and ended with " + playerPerica + " Pericas");
				System.out.println("You were not satisfied because the perica you've earned is only enough for your expenses. But nevertheless, you've finished the game with a positive earnings");
				System.out.println("ACHIEVEMENT UNLOCKED");
				System.out.println("Good Ending");
			} else if (playerPerica < 90000 && playerPerica > 100) {
				System.out.println("You've started the game with 90000 in your name but only ended with " + playerPerica + " at the end of the game");
				System.out.println("You were disappointed as you didn't benefit and loss an amount of Perica at your hand");
				System.out.println("To bring back the Perica you've lost, you've looked for more gambling sites to gain it back, even though you don't know what will be the outcome");
				System.out.println("ACHIEVEMENT UNLOCKED");
				System.out.println("Boring Ending");
			} else {
				System.out.println("You've gone negative");
				System.out.println("Lmao u suck");
				System.out.println("ACHIEVEMENT UNLOCKED");
				System.out.println("Worst Ending");
			}
		} else if (playerPerica < 0) {
			System.out.println("You've gone negative");
			System.out.println("Lmao u suck");
			System.out.println("ACHIEVEMENT UNLOCKED");
			System.out.println("Worst Ending");
		} else if (playerPerica == 0) {
			System.out.println("Lmao Itlog");
			System.out.println("ACHIEVEMENT UNLOCKED");
			System.out.println("Bad Ending");
		} else {
			System.out.println("You've finished the game with only less than the waging price");
			System.out.println("But hey, you might still have a handful amount to buy candies");
			System.out.println("ACHIEVEMENT UNLOCKED");
			System.out.println("Bad Ending");
		}
		System.out.println("GAME END");
	}
}