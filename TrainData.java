/*sources: https://www.lrta.gov.ph/tickets-and-fares/,
 *  https://www.dotrmrt3.gov.ph/fare-matrix.pdf
 * https://www.lrta.gov.ph/distance-between-stations/
 */
public class TrainData {
        // LRT2 Stations

        static final String[] lrt2 = {
                        "Recto", "Legarda", "Pureza", "V.Mapa", "J.Ruiz",
                        "Gilmore", "Betty Go-Belmonte", "Cubao", "Anonas",
                        "Katipunan", "Santolan", "Marikina-Pasig", "Antipolo"
        };

        // LRT1 Stations
        static final String[] lrt1 = {
                        "Roosevelt", "Balintawak", "Monumento", "5th Avenue",
                        "R.Papa", "Abad Santos", "Blumentritt", "Tayuman",
                        "Bambang", "Doroteo Jose", "Carriedo", "Central Terminal",
                        "United Nations", "Pedro Gil", "Quirino", "Vito Cruz",
                        "Gil Puyat", "Libertad", "EDSA", "Baclaran"
        };

        // MRT3 Stations
        static final String[] mrt3 = {
                        "North Avenue", "Quezon Avenue", "GMA Kamuning",
                        "Araneta Cubao", "Santolan Annapolis", "Ortigas",
                        "Shaw Blvd", "Boni Avenue", "Guadalupe", "Buendia",
                        "Ayala", "Magallanes", "Taft Avenue"
        };

        // Fare Matrices for LRT2, LRT1, and MRT3
        static final int[][][] fares1 = { {
                        { 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30, 30, 30, 30, 30, 35, 35 }, // baclaran
                        { 15, 0, 15, 15, 20, 20, 20, 20, 25, 25, 25, 25, 25, 25, 30, 30, 30, 30, 35, 35 }, // edsa
                        { 15, 15, 0, 15, 15, 20, 20, 20, 20, 25, 25, 25, 25, 25, 25, 30, 30, 30, 35, 35 }, // libertad
                        { 20, 15, 15, 0, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 25, 30, 30, 30, 35 }, // gil puyat
                        { 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30, 30, 35 }, // vito cruz
                        { 20, 20, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 30, 30 }, // quirino
                        { 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 30, 30 }, // pedro gil
                        { 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 20, 20, 20, 20, 25, 25, 25, 30, 30 }, // un avenue
                        { 25, 25, 20, 20, 20, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20, 20, 25, 25, 30 }, // central
                        { 25, 25, 25, 20, 20, 20, 20, 20, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25, 30 }, // carriedo
                        { 25, 25, 25, 25, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 20, 25, 25 }, // d.jose
                        { 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 20, 25, 25 }, // bambang
                        { 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 25, 25 }, // tayuman
                        { 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 20, 15 }, // blumentritt
                        { 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 15, 20, 20, 25 }, // abad santos
                        { 30, 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20, 25 }, // r.papa
                        { 30, 30, 30, 30, 25, 25, 25, 25, 20, 20, 20, 20, 20, 20, 15, 15, 0, 15, 20, 20 }, // 5th avenue
                        { 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 20, 15, 0, 20, 20 }, // monumento
                        { 35, 35, 35, 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 0, 20 }, // balintawak
                        { 35, 35, 35, 35, 35, 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 25, 20, 20, 20, 0 } // roosevelt
        }
        };
        static final int[][][] fares2 = { {
                        { 0, 15, 20, 20, 20, 25, 25, 25, 25, 30, 30, 35, 35 }, // Recto
                        { 15, 0, 15, 20, 20, 20, 25, 25, 25, 25, 30, 30, 35 }, // Legarda
                        { 20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30, 30, 30 }, // Pureza
                        { 20, 20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30, 30 }, // V.mapa
                        { 20, 20, 20, 15, 0, 15, 20, 20, 20, 20, 25, 25, 30 }, // J.Ruiz
                        { 25, 20, 20, 20, 15, 0, 15, 20, 20, 20, 25, 25, 30 }, // Gilmore
                        { 25, 25, 20, 20, 20, 15, 0, 15, 20, 20, 20, 25, 25 }, // Betty Go
                        { 25, 25, 20, 20, 20, 20, 15, 0, 15, 20, 20, 25, 25 }, // Cubao
                        { 25, 25, 25, 20, 20, 20, 20, 15, 0, 15, 20, 20, 25 }, // Anonas
                        { 30, 25, 25, 25, 20, 20, 20, 20, 15, 0, 20, 20, 25 }, // Katipunan
                        { 30, 30, 30, 25, 25, 25, 20, 20, 20, 20, 0, 15, 20 }, // Santolan
                        { 35, 30, 30, 30, 25, 25, 25, 25, 20, 20, 15, 0, 20 }, // Marikina
                        { 35, 35, 30, 30, 30, 30, 25, 25, 25, 25, 20, 20, 0 } // Antipolo
        }
        };
        static final int[][] mrt3trainChart = {
                        { 0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24, 28, 28 }, // North Ave
                        { 13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24, 28 }, // Quezon Ave
                        { 13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24, 24 }, // GMA Kamuning
                        { 16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24, 24 }, // Araneta-Cubao
                        { 16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20, 24 }, // Santolan-Annapolis
                        { 20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20, 20 }, // Ortigas
                        { 20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20, 20 }, // Shaw Blvd.
                        { 20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16, 20 }, // Boni
                        { 24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16, 16 }, // Guadalupe
                        { 24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13, 16 }, // Buendia
                        { 24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13, 13 }, // Ayala
                        { 28, 24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0, 13 }, // Magallanes
                        { 28, 28, 24, 24, 24, 20, 20, 20, 16, 16, 13, 13, 0 } // Taft Ave}
        };

        // Distances for LRT1, LRT2, and MRT3
        static final float[] lrt1_distances = { 1.87f, 2.25f, 1.08f, 0.95f, 0.66f, 0.92f, 0.67f, 0.61f, 0.64f, 0.68f,
                        0.72f,
                        1.21f, 0.75f, 0.79f,
                        0.82f, 1.06f, 0.73f, 1.01f, 0.58f };

        static final float[] lrt2_distances = { 1.05f, 1.38f, 1.35f, 1.23f, 0.92f, 1.07f,
                        1.16f, 1.43f, 0.95f, 1.97f, 1.79f, 2.23f };

        static final float[] mrt3_distances = { 1.22f, 0.94f, 1.85f, 1.45f, 2.31f, 0.77f, 0.98f, 0.77f, 1.83f, 0.88f,
                        1.19f,
                        1.89f };
}
