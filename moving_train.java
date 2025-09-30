public class moving_train {
    public static void moveTrainRight() throws InterruptedException {
        String[] train = {
                "__-============-_____-========-__-======-_-====-_         ",
                "(_                                                 _(     ",
                "_)                                                   (OO  ",
                "(_                                                   _)  0",
                "(_                                                _)     0o",
                "'=-dwb-===-___________-========-_____-===-___-='         o",
                "         _________                                o.",
                "_____      |         |  ______________          ______ . ",
                "__||___||__   |_________|  |            ________ ||__||_()",
                "|_         _| |_00______Y__ |            | |      | 5991 FSNB)",
                "P=\"OO-------OO\"=\"OO-------OO\"=\"OO--------OO\"=\"OO--OO\"=\"OO----OO-\\"
        };

        String road = " ################################################################################################################################################";
        int width = 79;
        int trainLength = train[0].length();

        // Clear console once at start
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Print road at bottom
        System.out.println(road);

        for (int i = -trainLength; i <= width; i++) {
            // Move cursor up
            System.out.print("\33[" + (train.length + 1) + "A");

            // Print train at current position
            for (String line : train) {
                // Clear the line first
                System.out.print("\033[K");

                if (i < 0) {
                    // Train is moving in from left
                    if (-i < line.length()) {
                        System.out.println(line.substring(-i));
                    } else {
                        System.out.println();
                    }
                } else {
                    // Train is moving to right
                    System.out.println(" ".repeat(i) + line);
                }
            }

            // Print road at bottom
            System.out.println(road);

            Thread.sleep(5);
        }
    }

    public static void moveTrainLeft() throws InterruptedException {
        String[] train = {
                "         _-====-__-======-__-========-_____-============-__",
                "         _(                                                 _)",
                "      OO(                                                   )_",
                "     0  (_                                                   _)",
                "   o0     (_                                                _)",
                "  o         '=-___-===-_____-========-___________-===-dwb-='",
                " .o                                _________",
                " . ______          ______________  |         |      _____",
                " _()_||__|| ________ |            |  |_________|   __||___||__",
                " (BNSF 1995| |      | |            | __Y______00_| |_         _|",
                " /-OO----OO\"=\"OO--OO\"=\"OO--------OO\"=\"OO-------OO\"=\"OO-------OO\"=P"
        };

        String road = " ##################################################################################################################################################";
        int width = 100;
        int trainLength = train[0].length();

        // Clear console once at start
        System.out.flush();

        // Print road at bottom
        System.out.println(road);

        for (int i = width; i >= -trainLength; i--) {
            // Move cursor up
            System.out.print("\033[" + (train.length + 1) + "A");

            // Print train at current position
            for (String line : train) {
                // Clear the line first
                System.out.print("\033[K");

                if (i >= 0) {
                    // Train is moving in from right
                    System.out.println(" ".repeat(i) + line);
                } else {
                    // Train is moving out to left
                    if (-i < line.length()) {
                        System.out.println(line.substring(-i));
                    } else {
                        System.out.println();
                    }
                }
            }

            // Print road at bottom
            System.out.println(road);

            Thread.sleep(5);
        }
    }
}