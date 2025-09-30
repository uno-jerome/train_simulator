import java.util.Scanner;

public class Card {
    private int balance;

    public Card() {
        this.balance = 0;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            System.out.println("Invalid balance.");
        } else {
            this.balance = balance;
        }
    }

    public void topUpBalance(int amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Balance successfully topped up. New balance: " + this.balance);
        } else {
            System.out.println("Insufficient balance, Beep card has minimum 13 pesso requirement when reloading");
        }
    }

    public void handleTransfers(int fare) {
        if (this.balance >= fare) {
            this.balance -= fare;
            System.out.println("\nRemaining balance: " + this.balance);
        } else {
            System.out.println("\033[0;31mInsufficient balance.\033[0m");
            Scanner scanner = new Scanner(System.in);
            boolean validInput = false;
            
            while (!validInput) {
                System.out.print("\nEnter the amount you would like to top up: ");
                String input = scanner.nextLine();
                
                if (input.matches("\\d+")) {
                    int amount = Integer.parseInt(input);
                    if (amount >= 13 && amount <= 10000) {
                        topUpBalance(amount);
                        validInput = true;
                        handleTransfers(fare); 
                    } else {
                        System.out.println("\033[0;31mInvalid amount. Enter amount you would like to top up:\033[0m");
                    }
                } else {
                    System.out.println("\033[0;31mInvalid input. Please enter a valid number.\033[0m");
                }
            }
        }
    }
}