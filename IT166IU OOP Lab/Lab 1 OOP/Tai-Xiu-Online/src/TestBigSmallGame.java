import java.util.Scanner;
public class TestBigSmallGame {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Please give me your name: " );
            String name = scanner.next();
            System.out.printf("Your money will start at 10000, Mr.%s . Wish you a lucky day!%n", name );
            Player player1 = new Player(name,10000);
            House house1 = new House(10000);
            Dice dice1 = new Dice(0);
            System.out.println("The Player has 10000$ ");
            System.out.println("The House has 10000$ ");
            System.out.println("Try your luck to win all money of the house!");
            int Round = 0;
            while (true) {
                // Play a round

                //Print the round play
                Round = Round + 1;
                System.out.printf("Round %d:%n", Round);

                //Take the bet and choose the result:
                System.out.println("How much do you want to bet ?" );
                int bet = scanner.nextInt();
                
                // Make sure the bet can not higher or lower than the money he has 
                while(bet > player1.getMoney() || bet < 0){
                    System.out.println("Value Invalid! Try again!");
                    bet=scanner.nextInt();
                }
                System.out.printf("You have bet %d$! %n",bet );
                int money_Player_after_bet = player1.money - bet;
                
                // Pick the choice : Big or Small
                System.out.println("Please choose the result: Big or Small" );
                String Choice = scanner.next();
                int sumDice = 0;
                
                // Take the dice value
                for (int i = 0; i < 3; i++){
                    dice1.roll();
                    int dice_value = dice1.getpoint();
                    System.out.printf("The dice %d is: %d%n",i +1, dice_value );
                    sumDice = sumDice + dice_value;
                }
                System.out.printf("The sum of 3 dices is:%d%n",sumDice);
                
                // Scenario: Win or Lose
                if (4 <= sumDice && sumDice <= 10) {
                    if (Choice.equals("Big") || Choice.equals("big")){
                        // win the game Big
                        System.out.printf("You just won %d$. Congratulation %n",bet);
                        player1.Winbet(bet * 2);
                        house1.subtractMoney(bet);
                        System.out.printf("The House has %d$ money.%n",house1.getMoney());
                        System.out.printf("The Player has %d$ money.%n",player1.money);
                    }else{
                        // lose the game Big
                        System.out.printf("You just lose %d$. Too bad%n",bet);
                        player1.money = money_Player_after_bet;
                        house1.addMoney(bet);
                        System.out.printf("The House has %d$ money.%n",house1.getMoney());
                        System.out.printf("The Player has %d$ money.%n",player1.money);
                    }
                } else if(11<= sumDice && sumDice <= 17){
                    if (Choice.equals("Small") || Choice.equals("small")){
                        // win the game Small
                        System.out.printf("You just won %d$. Congratulation%n",bet);
                        player1.Winbet(bet * 2);
                        house1.subtractMoney(bet);
                        System.out.printf("The House has %d$ money.%n",house1.getMoney());
                        System.out.printf("The Player has %d$ money.%n",player1.money);
                    }else{
                        // lose the game Big
                        System.out.printf("You just lose %d$. Too bad%n",bet);
                        player1.money = money_Player_after_bet;
                        house1.addMoney(bet);
                        System.out.printf("The House has %d$ money.%n",house1.getMoney());
                        System.out.printf("The Player has %d$ money.%n",player1.money);
                    }
                } else {
                    // lose the game Big
                    System.out.printf("You just lose %d$. Too bad%n",bet);
                    player1.money = money_Player_after_bet;
                    house1.addMoney(bet);
                    System.out.printf("The House has %d$ money.%n",house1.getMoney());
                    System.out.printf("The Player has %d$ money.%n",player1.money);
                }

                // Check if the money of player or the house run off
                if (player1.money == 0){
                    System.out.println("You are out of money! Bye!");
                    break;
                }
                else if(house1.money == 0){
                    System.out.println("House are out of money! You are the best Big Small game!");
                    break;
                }

            // Check if the palyer want to continue
            System.out.println("Do you still want to continue to play? (True/False)");
            String choice_play=scanner.next();
            if (choice_play.equals("True") || choice_play.equals("true")  ) {
                continue;
            }
            else {
                break;
            }

            }
        }
    }
} 