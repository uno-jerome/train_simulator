
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class test_v2 {
    // @SuppressWarnings("resource") try-with-resources seems like a better choice
    public static void main(String[] args) throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            Card card = new Card();
            String choice;
            boolean validInput;
            boolean continueRiding = true;
            int lrtChoice = 0;
            int currentStation = 0;
            int destinationStation = 0;
            int totalFare = 0;
            float totalDistance = 0;
            int totalStations = 0;
            int rideCount = 0;
            float discount = 0.0f;
            float currentDistance = 0.0f;
            int rideStations = 0;
            typewriterEffect("\nWelcome to the Train System!\n", "\033[1;38;5;219m");
            while (continueRiding) {
                validInput = false;
                while (!validInput) {
                    typewriterEffect("\nWhich train line are you riding? \n(1 for LRT-1, 2 for LRT-2, 3 for MRT-3):\n",
                            "\033[38;5;219m");
                    choice = scanner.nextLine().strip();
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
                    cardType = scanner.nextLine().strip();
                    StopAndRestart(cardType, args, rideCount, totalFare, totalDistance, totalStations);

                    if (cardType.equalsIgnoreCase("Single") || cardType.equalsIgnoreCase("Beep")) {
                        validInput = true;
                    } else {
                        System.out.printf("\033[0;31m\nInvalid input! Please enter 'Single' or 'Beep'.\033[0m\n");
                    }
                }

                // Check for Beep card balance
                if (cardType.equalsIgnoreCase("Beep")) {
                    validInput = false;
                    while (!validInput) {
                        typewriterEffect("\nEnter your Beep card's balance:\n", "\033[38;5;219m");
                        String balanceInput = scanner.nextLine().strip();
                        StopAndRestart(balanceInput, args, rideCount, totalFare, totalDistance, totalStations);

                        if (isValidNumber(balanceInput)) {
                            int balance = Integer.parseInt(balanceInput);
                            if (balance >= 13) {
                                card.setBalance(balance);
                                validInput = true;
                            } else {
                                System.out.printf("Invalid balance! Please enter a non-negative number.\033[0m\n");
                            }
                        } else {
                            System.out.printf("\033[0;31m\nInvalid input! Please enter a number.\033[0m\n");
                        }
                    }
                }

                // Beep card discount type
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
                    System.out.printf("\nCurrent balance: %d pesos\n", card.getBalance());
                }

                // Main Ride Loop
                while (true) {
                    // Display stations
                    String[] stations = lrtChoice == 1 ? TrainData.lrt1
                            : lrtChoice == 2 ? TrainData.lrt2 : TrainData.mrt3;

                    printStations(stations, lrtChoice);

                    // Select starting station
                    if (rideCount == 0) {
                        currentStation = selectStation("\nEnter the number of your starting station:",
                                stations.length,
                                scanner, args, rideCount, totalFare, totalDistance, totalStations);
                    } else {
                        String lineName = lrtChoice == 1 ? "LRT-1" : lrtChoice == 2 ? "LRT-2" : "MRT-3";
                        System.out.printf("\nYou are currently at %s %s Station.\n", lineName,
                                stations[destinationStation - 1]);
                        // Ask if user wants to continue using Beep card or switch to Single Journey
                        validInput = false;
                        while (!validInput) {
                            if (cardType.equalsIgnoreCase("Beep")) {
                                typewriterEffect(
                                        "\nDo you want to continue using your Beep card or buy a single journey ticket? (Beep/Single):\n",
                                        "\033[38;5;219m");
                            } else {
                                typewriterEffect(
                                        "\nDo you want to buy a single journey ticket or use your Beep card? (Single/Beep):\n",
                                        "\033[38;5;219m");
                            }
                            String cardChoice = scanner.nextLine().strip().toLowerCase();

                            switch (cardChoice) {
                                case "beep" -> {
                                    // Continue using or switch to Beep card
                                    if (cardType.equalsIgnoreCase("Single")) {
                                        // Ask for Beep card balance if switching from sjt to beep
                                        validInput = false;
                                        while (!validInput) {
                                            typewriterEffect("\nEnter your Beep card's balance:\n", "\033[38;5;219m");
                                            String balanceInput = scanner.nextLine().strip();
                                            StopAndRestart(balanceInput, args, rideCount, totalFare, totalDistance,
                                                    totalStations);

                                            if (isValidNumber(balanceInput)) {
                                                int balance = Integer.parseInt(balanceInput);
                                                if (balance >= 13) {
                                                    card.setBalance(balance);
                                                    validInput = true;
                                                } else {
                                                    System.out.printf(
                                                            "Invalid balance! Please enter a non-negative number.\033[0m\n");
                                                }
                                            } else {
                                                System.out.printf(
                                                        "\033[0;31m\nInvalid input! Please enter a number.\033[0m\n");
                                            }
                                        }
                                    }
                                    cardType = "Beep";
                                    validInput = true;
                                }
                                case "single" -> {
                                    // Switch to Single Journey
                                    cardType = "Single";
                                    // Ask for discount type for Single Journey
                                    validInput = false;
                                    while (!validInput) {
                                        typewriterEffect(
                                                "\nEnter card type for discount\n(R for Regular, S for Student, P for PWD/Senior):\n",
                                                "\033[38;5;219m");
                                        String discountType = scanner.nextLine();
                                        StopAndRestart(discountType, args, rideCount, totalFare, totalDistance,
                                                totalStations);

                                        if (discountType.equalsIgnoreCase("R") ||
                                                discountType.equalsIgnoreCase("S") ||
                                                discountType.equalsIgnoreCase("P")) {
                                            discount = getDiscount(discountType.charAt(0));
                                            validInput = true;
                                        } else {
                                            System.out.printf(
                                                    "\033[0;31mInvalid input! Please enter 'R', 'S', or 'P'.\033[0m\n");
                                        }
                                    }
                                    validInput = true;
                                }
                                default -> System.out
                                        .printf("\033[0;31mInvalid input! Please enter 'beep' or 'single'.\033[0m\n");
                            }
                        }
                    }

                    // Select destination station
                    while (true) {
                        destinationStation = selectStation("\nEnter the number of your destination station:",
                                stations.length, scanner, args, rideCount, totalFare, totalDistance, totalStations);
                        if (currentStation == destinationStation) {
                            String lineName = lrtChoice == 1 ? "LRT-1" : lrtChoice == 2 ? "LRT-2" : "MRT-3";
                            System.out.printf(
                                    "\033[0;31m\nYou are already at %s %s Station. Please choose a different station.\n\033[0m",
                                    lineName, stations[currentStation - 1]);
                            continue;
                        }
                        break;
                    }
                    if (destinationStation > currentStation) {
                        moving_train.moveTrainRight();
                    } else {
                        moving_train.moveTrainLeft();
                    }
                    // Calculate fare and distance (displayCurrent)
                    int[][][] fareMatrix = lrtChoice == 1 ? TrainData.fares1
                            : lrtChoice == 2 ? TrainData.fares2 : new int[][][] { TrainData.mrt3trainChart };
                    float[] distances = lrtChoice == 1 ? TrainData.lrt1_distances
                            : lrtChoice == 2 ? TrainData.lrt2_distances : TrainData.mrt3_distances;
                    int fare = fareMatrix[0][currentStation - 1][destinationStation - 1];

                    currentDistance = calculateDistance(distances, currentStation - 1, destinationStation - 1);
                    rideStations = Math.abs(destinationStation - currentStation);

                    // Apply discount
                    fare -= fare * discount;
                    if (cardType.equalsIgnoreCase("Beep")) {
                        if (card.getBalance() < fare) {
                            topUpBalance(fare, scanner, card);
                        }
                        card.handleTransfers(fare);
                    }

                    // Update totals (displaySummary)
                    totalFare += fare;
                    totalDistance += currentDistance;
                    totalStations += rideStations;
                    rideCount++;

                    // Output fare and details
                    displayCurrent(fare, cardType, card, currentDistance, rideStations);

                    // tutuloy k p ba
                    System.out.print("\nDo you want to ride again? (y/n): ");
                    choice = scanner.nextLine().strip().toLowerCase();
                    continueRiding = choice.equals("y");
                    if (continueRiding) {
                        currentStation = destinationStation;
                    } else {
                        break;
                    }

                    // Handle transfers
                    int transferStation = TransferHandler.handleTransfers(destinationStation, lrtChoice, scanner);
                    if (transferStation != -1) {
                        currentStation = transferStation;
                        lrtChoice = TransferHandler.updateTrainLine(lrtChoice, transferStation); // Update line
                    }
                }
            }
            // Display summary
            displaySummary(rideCount, totalFare, totalDistance, totalStations);
        }
    }

    // private int calculateFareAndDistance(int lrtChoice, String cardType, int
    // currentStation, int destinationStation,
    // float discount) {
    // int[][][] fareMatrix = lrtChoice == 1 ? TrainData.fares1
    // : lrtChoice == 2 ? TrainData.fares2
    // : new int[][][] {
    // TrainData.mrt3trainChart };
    // float[] distances = lrtChoice == 1 ? TrainData.lrt1_distances
    // : lrtChoice == 2 ? TrainData.lrt2_distances : TrainData.mrt3_distances;
    // int fare = fareMatrix[cardType.equalsIgnoreCase("Beep") ? 1 :
    // 0][currentStation + 1][destinationStation - 1];
    // currentDistance = calculateDistance(distances, currentStation - 1,
    // destinationStation - 1);
    // rideStations = Math.abs(destinationStation - currentStation);

    // // Apply discount
    // fare -= fare * discount;
    // return fare;
    // }

    private static void displayCurrent(int fare, String cardType, Card card, float currentDistance,
            int rideStations) {
        System.out.printf("\nFare for this ride: %d pesos\n", fare);
        System.out.printf("Total distance traveled: %.2f km\n", currentDistance);
        System.out.printf("Stations passed: %d\n", rideStations);
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
            int totalStations) throws InterruptedException {
        if (input.equalsIgnoreCase("stop")) {
            System.out.println("\nExiting the program. Goodbye!");
            displaySummary(rideCount, totalFare, totalDistance, totalStations); 
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

    public static void printStations(String[] stations, int lrtChoice) {
        String lineName = switch (lrtChoice) {
            case 1 -> "\033[1;32mLRT-1 Stations:";
            case 2 -> "\033[1;34mLRT-2 Stations:";
            case 3 -> "\033[1;33mMRT-3 Stations:";
            default -> throw new IllegalStateException("Invalid Train Line Choice");
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
            int totalFare, float totalDistance, int totalStations) throws InterruptedException {
        while (true) {
            System.out.printf("%s (1-%d): ", prompt, maxStations);
            String input = scanner.nextLine();

            StopAndRestart(input, args, rideCount, totalFare, totalDistance, totalStations);

            if (isValidNumber(input)) {
                int station = Integer.parseInt(input);
                if (station >= 1 && station <= maxStations) {
                    return station;
                }
            }
            System.out.println(
                    "\033[0;31m\nInvalid input. Please enter a number between 1 and " + maxStations + ".\033[0m");
        }
    }

    public static boolean isValidNumber(String str) {
        return str.matches("\\d+");
    }

    public static float calculateDistance(float[] distances, int initial, int destination) {
        float distance = 0;
        if (initial < destination) {
            for (int i = initial; i < destination; i++) {
                distance += distances[i];
            }
        } else {
            for (int i = initial - 1; i >= destination; i--) {
                distance += distances[i];
            }
        }
        return distance;
    }

    public static float getDiscount(char cardType) {
        return switch (Character.toLowerCase(cardType)) {
            case 's' -> 0.5f; // Student
            case 'p' -> 0.3f; // PWD/Senior
            case 'r' -> 0.0f; // Regular
            default -> -1;
        };
    }

    public static float getDiscount2(char cardType2) {
        return switch (Character.toLowerCase(cardType2)) {
            case 'p' -> 0.3f; // PWD/Senior
            case 'r' -> 0.0f; // Regular
            case 's' -> 0.5f;
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

    public static void invalidAttempts (int invalidAttempts){
        if (invalidAttempts == 5) {
            System.out.println("\nToo many invalid attempts. Exiting the program.");
            System.exit(0);
        }
    }

}
