public class moving_train_right {
    public static void main(String[] args) throws InterruptedException {
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

        String road = " ##################################################################################################################################################";
        int width = 80;
        int trainLength = train[0].length();

        // Clear console once at start
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Print road at bottom
        System.out.println(road);

        for (int i = -trainLength; i <= width; i++) {
            // Move cursor up
            System.out.print("\033[" + (train.length + 1) + "A");

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
}