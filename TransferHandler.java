import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TransferHandler {

    private static final Map<Integer, Map<Integer, TransferInfo>> transferLine = new HashMap<>();
    private static final Map<String, Integer> transferRules = new HashMap<>();

    static {
        // transfer information // would be called in the main method (test_v2)
        transferInfo(1, 10, "LRT-1 Doroteo Jose Station", "LRT-2 Recto Station", 1);
        transferInfo(2, 1, "LRT-2 Recto Station", "LRT-1 Doroteo Jose Station", 10);
        transferInfo(2, 8, "LRT-2 Cubao Station", "MRT-3 Araneta Cubao Station", 4);
        transferInfo(3, 4, "MRT-3 Araneta Cubao Station", "LRT-2 Cubao Station", 8);
        transferInfo(1, 1, "LRT-1 Roosevelt Station", "MRT-3 North Avenue Station", 1);
        transferInfo(3, 1, "MRT-3 North Avenue Station", "LRT-1 Roosevelt Station", 1);
        transferInfo(1, 19, "LRT-1 EDSA Station", "MRT-3 Taft Avenue Station", 13);
        transferInfo(3, 13, "MRT-3 Taft Avenue Station", "LRT-1 EDSA Station", 19);

        // transfer rules
        transferRules.put("1-1", 2); // LRT1 -> LRT2
        transferRules.put("2-10", 1); // LRT2 -> LRT1
        transferRules.put("2-4", 3); // LRT2 -> MRT3
        transferRules.put("3-8", 2); // MRT3 -> LRT2
    }

    private static void transferInfo(int lrtChoice, int destinationStation, String currentStation,
            String transferStation, int resetIndex) {
        transferLine.computeIfAbsent(lrtChoice, k -> new HashMap<>())
                .put(destinationStation, new TransferInfo(currentStation, transferStation, resetIndex));
    }

    public static int handleTransfers(int destinationStation, int lrtChoice, Scanner scanner) {
        Map<Integer, TransferInfo> destinationMap = transferLine.get(lrtChoice);
        if (destinationMap != null) {
            TransferInfo transferInfo = destinationMap.get(destinationStation);
            if (transferInfo != null) {
                System.out.print(
                        "\nYou are currently at " + transferInfo.currentStation + ". Would you like to transfer to "
                                + transferInfo.transferStation + "? (y/n): ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("\nWelcome to " + transferInfo.transferStation + "!");
                    return transferInfo.resetIndex;
                }
            }
        }
        return -1;
    }

    public static int updateTrainLine(int currentLine, int transferStation) {
        String key = currentLine + "-" + transferStation;
        return transferRules.getOrDefault(key, currentLine);
    }

    private static class TransferInfo {
        String currentStation;
        String transferStation;
        int resetIndex;

        TransferInfo(String currentStation, String transferStation, int resetIndex) {
            this.currentStation = currentStation;
            this.transferStation = transferStation;
            this.resetIndex = resetIndex;
        }
    }
}