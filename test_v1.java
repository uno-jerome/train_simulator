/* 2nd term - final project submission by Jerome Ruiz - INF242 in CCPRGG2L - Intermediate Programming 
 * Still in raw testing */

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class test_v1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Card card = new Card();
        String choice;
        boolean validInput;
        boolean continueRiding = true;
        int lrtChoice = 0;
        int currentStation;
        int destinationStation;
        int totalFare = 0;
        float totalDistance = 0;
        int totalStations = 0;
        int rideCount = 0;
        float discount = 0.0f;

        typewriterEffect("\nWelcome to the Train System!\n", "\033[1;38;5;219m");
        while (continueRiding) {
            // Train Line Selection
            validInput = false;
            while (!validInput) {
                typewriterEffect("\nWhich train line are you riding? \n(1 for LRT-1, 2 for LRT-2, 3 for MRT-3):\n",
                        "\033[38;5;219m");
                choice = scanner.nextLine();
                StopAndRestart(choice, args, rideCount, totalFare, totalDistance, totalStations);

                if (isValidNumber(choice)) {
                    lrtChoice = Integer.parseInt(choice);
                    if (lrtChoice >= 1 && lrtChoice <= 3) {
                        validInput = true;
                    } else {
                        System.out.printf("\033[0;31m\nInvalid choice! Please enter 1, 2, or 3.\033[0m\n");
                    }
                } else {
                    System.out.printf("\033[0;31m\nInvalid input! Please enter a number.\n\033[0m");
                }
            }

            // Card Type Selection
            validInput = false;
            String cardType = "";
            while (!validInput) {
                typewriterEffect("\nEnter Card Type (Single or Beep):\n", "\033[38;5;219m");
                cardType = scanner.nextLine();
                StopAndRestart(cardType, args, rideCount, totalFare, totalDistance, totalStations);

                if (cardType.equalsIgnoreCase("Single") || cardType.equalsIgnoreCase("Beep")) {
                    validInput = true;
                } else {
                    System.out.printf("\033[0;31m\nInvalid input! Please enter 'Single' or 'Beep'.\033[0m\n");
                }

                // Prompt for Beep card balance
                if (cardType.equalsIgnoreCase("Beep")) {
                    validInput = false;
                    while (!validInput) {
                        typewriterEffect("\nEnter your Beep card's balance:\n", "\033[38;5;219m");
                        String balanceInput = scanner.nextLine();
                        StopAndRestart(balanceInput, args, rideCount, totalFare, totalDistance, totalStations);

                        if (isValidNumber(balanceInput)) {
                            int balance = Integer.parseInt(balanceInput);
                            if (balance >= 0) {
                                card.setBalance(balance);
                                validInput = true;
                            } else {
                                System.out.printf("Invalid balance! Please enter a non-negative number.\033[0m\n");
                            }
                        } else {
                            System.out.printf("Invalid input! Please enter a number.\033[0m\n");
                        }
                    }
                }
            }
            // Discount Type Selection
            validInput = false;
            while (!validInput) {
                if (cardType.equalsIgnoreCase("Beep")) {
                    typewriterEffect("\nEnter card type for discount\n(R for Regular, or P for PWD/Senior):\n",
                            "\033[38;5;219m");
                } else {
                    typewriterEffect(
                            "\nEnter card type for discount\n(R for Regular, S for Student, P for PWD/Senior):\n",
                            "\033[38;5;219m");
                }
                String discountType = scanner.nextLine();
                StopAndRestart(discountType, args, rideCount, totalFare, totalDistance, totalStations);

                if (cardType.equalsIgnoreCase("Beep")) {
                    if (discountType.equalsIgnoreCase("R") || discountType.equalsIgnoreCase("P")) {
                        discount = getDiscount2(discountType.charAt(0));
                        validInput = true;
                    } else {
                        System.out.printf("\033[0;31mInvalid input! Please enter 'R' or 'P'. \033[0m\n");
                    }
                } else {
                    if (discountType.equalsIgnoreCase("R") || discountType.equalsIgnoreCase("S")
                            || discountType.equalsIgnoreCase("P")) {
                        discount = getDiscount(discountType.charAt(0));
                        validInput = true;
                    } else {
                        System.out.printf("\033[0;31mInvalid input! Please enter 'R', 'S', or 'P'.\033[0m\n");
                    }
                }
            }

            // Beep Card: Get Balance
            if (cardType.equalsIgnoreCase("Beep")) {
                System.out.printf("Current balance: %d pesos\n", card.getBalance());
            }

            // Main Ride Loop
            while (true) {
                // Display stations
                String[] stations = lrtChoice == 1 ? TrainData.lrt1
                        : lrtChoice == 2 ? TrainData.lrt2 : TrainData.mrt3;

                printStations(stations, lrtChoice);

                // Select starting station
                currentStation = selectStation("\nEnter the number of your starting station:", stations.length,
                        scanner,
                        args, rideCount, totalFare, totalDistance, totalStations);

                // Select destination station
                destinationStation = selectStation("\nEnter the number of your destination station:",
                        stations.length,
                        scanner, args, rideCount, totalFare, totalDistance, totalStations);

                // Calculate fare and distance
                int[][][] fareMatrix = lrtChoice == 1 ? TrainData.fares1
                        : lrtChoice == 2 ? TrainData.fares2 : new int[][][] { TrainData.mrt3trainChart };
                float[] distances = lrtChoice == 1 ? TrainData.lrt1_distances
                        : lrtChoice == 2 ? TrainData.lrt2_distances : TrainData.mrt3_distances;
                int fare = fareMatrix[cardType.equalsIgnoreCase("Beep") ? 1 : 0][currentStation
                        - 1][destinationStation
                                - 1];
                float distance = calculateDistance(distances, currentStation - 1, destinationStation - 1);

                // Apply discount
                fare -= fare * discount;
                if (cardType.equalsIgnoreCase("Beep")) {
                    if (card.getBalance() < fare) {
                        topUpBalance(fare, scanner, card);
                    }
                    card.handleTransfers(fare);
                }

                // Update totals
                totalFare += fare;
                totalDistance += distance;
                totalStations += Math.abs(destinationStation - currentStation);
                rideCount++;

                // Output fare and details
                displayCurrent(fare, totalDistance, destinationStation, currentStation, cardType,
                        card);

                // tutuloy k p ba
                System.out.print("\nDo you want to ride again? (y/n): ");
                choice = scanner.nextLine().trim().toLowerCase();
                continueRiding = choice.equals("y");
                if (!continueRiding) {
                    break; // Exit the loop if the user does not want to ride again
                }

                // Handle transfers
                int transferStation = handleTransfers(destinationStation, lrtChoice, scanner);
                if (transferStation != -1) {
                    currentStation = transferStation;
                    lrtChoice = updateTrainLine(lrtChoice, transferStation); // Update line choice
                    continue; // Restart loop with the new line and station
                }
            }
        }
        // Display summary
        displaySummary(rideCount, totalFare, totalDistance, totalStations);
    }

    private static void displayCurrent(int fare, float totalDistance, int destinationStation,
            int currentStation, String cardType, Card card) {
        System.out.printf("\nFare for this ride: %d pesos\n", fare);
        System.out.printf("Total distance traveled: %.2f km\n", totalDistance);
        System.out.printf("Stations passed: %d\n", Math.abs(destinationStation - currentStation));
        if (cardType.equalsIgnoreCase("Beep")) {
            System.out.printf("Remaining balance: %d pesos\n", card.getBalance());
        }

    }

    private static void displaySummary(int rideCount, int totalFare, float totalDistance, int totalStations) {
        System.out.printf("\nTotal rides: %d\nTotal fare: %d pesos\n", rideCount, totalFare);
        System.out.printf("Total distance traveled: %.2f km\n", totalDistance);
        System.out.printf("Total stations passed: %d\n", totalStations);
        System.out.println("Thank you for patronizing public transportation");
    }

    private static void StopAndRestart(String input, String[] args, int rideCount, int totalFare, float totalDistance,
            int totalStations) {
        if (input.equalsIgnoreCase("stop")) {
            System.out.println("\nExiting the program. Goodbye!");
            displaySummary(rideCount, totalFare, totalDistance, totalStations); // Display summary before exiting
            System.exit(0);
        } else if (input.equalsIgnoreCase("restart")) {
            System.out.println("\nRestarting the program...\n");
            main(args);
            System.exit(0);
        }
    }

    // Additional methods
    public static void typewriterEffect(String text, String colorCode) {
        if (colorCode != null) {
            System.out.print(colorCode); // Apply color
        }
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (colorCode != null) {
            System.out.print("\033[0m"); // Reset color
        }
        System.out.println();
    }

    public static String withColor(String text, int colorCode) {
        return "\033[38;5;" + colorCode + "m" + text + "\033[0m";
    }

    public static String textValidation(String prompt, String errorMessage, boolean allowBack) {
        String text = null;
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.printf("%s: ", prompt);
            text = input.nextLine();
            if (allowBack && text.equalsIgnoreCase("back")) {
                if (confirmBack()) {
                    return "back";
                } else {
                    continue;
                }
            }
            if (!text.isBlank()) {
                break;
            }
            System.out.println(withColor(errorMessage + ".\n", 91));
        }
        return text.trim();
    }

    public static boolean confirmBack() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.printf("Are you sure you want to go back? (%s/%s): ", withColor("y", 92), withColor("n", 92));
            String response = input.nextLine();
            if (response.equalsIgnoreCase("y")) {
                return true;
            } else if (response.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println(withColor("Invalid Input.\n", 91));
            }
        }
    }

    public static void printStations(String[] stations, int lrtChoice) {
        String lineName = switch (lrtChoice) {
            case 1 -> "\033[1;32mLRT-1 Stations:";
            case 2 -> "\033[1;34mLRT-2 Stations:";
            case 3 -> "\033[1;33mMRT-3 Stations:";
            default -> throw new IllegalArgumentException("Invalid train line choice: " + lrtChoice);
        };
        System.out.println("\n" + lineName);
        for (int i = 0; i < stations.length; i++) {
            switch (lrtChoice) {
                case 1 -> System.out.printf("\033[1;32m[%d] %s\033[0m%n", i + 1, stations[i]); // LRT-1
                case 2 -> System.out.printf("\033[1;34m[%d] %s\033[0m%n", i + 1, stations[i]); // LRT-2
                case 3 -> System.out.printf("\033[1;33m[%d] %s\033[0m%n", i + 1, stations[i]); // MRT-3
            }
        }
    }

    public static int selectStation(String prompt, int maxStations, Scanner scanner, String[] args, int rideCount,
            int totalFare, float totalDistance, int totalStations) {
        while (true) {
            System.out.printf("%s (1-%d): ", prompt, maxStations);
            String input = scanner.nextLine();

            // Handle special commands (e.g., stop or restart)
            StopAndRestart(input, args, rideCount, totalFare, totalDistance, totalStations);

            if (isValidNumber(input)) { // Using isValidNumber to validate input
                int station = Integer.parseInt(input);
                if (station >= 1 && station <= maxStations) {
                    return station; // Valid station, return immediately
                }
            }
            // Error message for invalid input or out-of-range numbers
            System.out.println(
                    "\033[0;31m\nInvalid input. Please enter a number between 1 and " + maxStations + ".\033[0m");
        }
    }

    public static boolean isValidNumber(String str) {
        return str.matches("\\d+");
    }

    public static float calculateDistance(float[] distances, int initial, int destination) {
        float totalDistance = 0;
        int index = initial;

        while (index != destination) {
            if (initial < destination) { // Moving forward2
                totalDistance += distances[index];
                index++;
            } else { // Moving backward
                totalDistance += distances[index - 1];
                index--;
            }
        }
        return totalDistance;
    }

    public static int updateTrainLine(int currentLine, int transferStation) {
        if (currentLine == 1 && transferStation == 1) {
            return 2; // LRT1 -> LRT2

        }
        if (currentLine == 2 && transferStation == 10) {
            return 1; // LRT2 -> LRT1

        }
        if (currentLine == 2 && transferStation == 4) {
            return 3; // LRT2 -> MRT3

        }
        if (currentLine == 3 && transferStation == 8) {
            return 2; // MRT3 -> LRT2

        }
        return currentLine; // No change
    }

    public static float getDiscount(char cardType) {
        return switch (Character.toLowerCase(cardType)) {
            case 's' -> 0.2f; // Student
            case 'p' -> 0.3f; // PWD/Senior
            case 'r' -> 0.0f; // Regular
            default -> -1;
        };
    }

    public static float getDiscount2(char cardType2) {
        return switch (Character.toLowerCase(cardType2)) {
            case 'p' -> 0.3f; // PWD/Senior
            case 'r' -> 0.0f; // Regular
            default -> -1;
        };
    }

    public static void topUpBalance(int fare, Scanner scanner, Card card) {
        System.out.println("\033[0;31mInsufficient balance.\033[0m");
        boolean validInput = false;

        while (!validInput) {
            System.out.print("\nPlease enter top-up amount (1-1000): ");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                int amount = Integer.parseInt(input);
                if (amount >= 1 && amount <= 1000) {
                    card.topUpBalance(amount);
                    validInput = true;
                } else {
                    System.out.println("\033[0;31mInvalid amount. Please enter between 1-1000.\033[0m");
                }
            } else {
                System.out.println("\033[0;31mInvalid input. Please enter a valid number.\033[0m");
            }
        }
    }

    public static int handleTransfers(int destinationStation, int lrtChoice, Scanner scanner) {
        String choice;
        if (lrtChoice == 1 && destinationStation == 10) { // Doroteo Jose to Recto
            System.out.print(
                    "\nYou are currently at LRT-1 Doroteo Jose Station. Would you like to transfer to LRT-2 Recto Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to LRT-2 Recto Station!");
                return 1; // Reset to Recto (index 1 of LRT2)
            }
        } else if (lrtChoice == 2 && destinationStation == 1) { // Recto to Doroteo Jose
            System.out.print(
                    "\nYou are currently at LRT-2 Recto Station. Would you like to transfer to LRT-1 Doroteo Jose Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to LRT-1 Doroteo Jose Station!");
                return 10; // Since Doroteo is at the 10th position in the array
            }
        } else if (lrtChoice == 2 && destinationStation == 8) { // LRT Cubao to MRT Cubao
            System.out.print(
                    "\nYou are currently at LRT-2 Cubao Station. Would you like to transfer to MRT-3 Araneta Cubao Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to MRT-3 Araneta Cubao Station!");
                return 4; // Reset to Araneta Cubao (index 4 of MRT3)
            }
        } else if (lrtChoice == 3 && destinationStation == 4) { // MRT Cubao to LRT Cubao
            System.out.print(
                    "\nYou are currently at MRT-3 Araneta Cubao Station. Would you like to transfer to LRT-2 Cubao Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to LRT-2 Cubao Station!");
                return 8; // Reset to Cubao (index 8 of LRT2)
            }
        } else if (lrtChoice == 1 && destinationStation == 1) { // Roosevelt to North Ave
            System.out.print(
                    "\nYou are currently at LRT-1 Roosevelt Station. Would you like to transfer to MRT-3 North Avenue Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to MRT-3 North Avenue Station!");
                return 1; // Reset to North Avenue (index 1 of MRT3)
            }
        } else if (lrtChoice == 3 && destinationStation == 1) { // North Ave to Roosevelt
            System.out.print(
                    "\nYou are currently at MRT-3 North Avenue Station. Would you like to transfer to LRT-1 Roosevelt Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to LRT-1 Roosevelt Station!");
                return 1; // Reset to Roosevelt (index 1 of LRT1)
            }
        } else if (lrtChoice == 1 && destinationStation == 19) { // EDSA to Taft
            System.out.print(
                    "\nYou are currently at LRT-1 EDSA Station. Would you like to transfer to MRT-3 Taft Avenue Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to MRT-3 Taft Avenue Station!");
                return 13; // Reset to Taft Avenue (index 13 of MRT3)
            }
        } else if (lrtChoice == 3 && destinationStation == 13) { // Taft to EDSA
            System.out.print(
                    "\nYou are currently at MRT-3 Taft Avenue Station. Would you like to transfer to LRT-1 EDSA Station? (Y/N): ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nWelcome to LRT-1 EDSA Station!");
                return 19; // Reset to EDSA (index 19 of LRT1)
            }
        }
        return -1; // No transfer
    }
}
