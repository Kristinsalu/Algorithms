import ee.taltech.algoritmid.tsp.HW2;
import ee.taltech.algoritmid.tsp.TSP;
import ee.taltech.algoritmid.tsp.Result;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        TSP solver = new HW2();

        System.out.println("--- Väikesed testid ---");
        int[][][] manualTests = {
                // 1. väike, sümmeetriline
                {
                        {0, 10, 15, 20},
                        {10, 0, 35, 25},
                        {15, 35, 0, 30},
                        {20, 25, 30, 0}
                },
                // 2. väike, sümmeetriline
                {
                        {0, 2, 9, 10, 7},
                        {2, 0, 6, 4, 3},
                        {9, 6, 0, 8, 5},
                        {10, 4, 8, 0, 6},
                        {7, 3, 5, 6, 0}
                },
                // 3. keskmine, sümmeetriline
                {
                        {0, 3, 7, 4, 8, 6},
                        {3, 0, 2, 5, 9, 7},
                        {7, 2, 0, 6, 3, 4},
                        {4, 5, 6, 0, 5, 2},
                        {8, 9, 3, 5, 0, 1},
                        {6, 7, 4, 2, 1, 0}
                },
                // 4. väike, asümmeetriline
                {
                        {0, 10, 15, 20},
                        {12, 0, 35, 25},
                        {15, 30, 0, 28},
                        {18, 25, 30, 0}
                },
                // 5. väike, asümmeetriline
                {
                        {0, 3, 8, 5, 7},
                        {2, 0, 4, 6, 3},
                        {7, 5, 0, 3, 8},
                        {6, 4, 2, 0, 9},
                        {5, 7, 3, 8, 0}
                },
        };

        for (int t = 0; t < manualTests.length; t++) {
            runTest(solver, manualTests[t], "Manuaalne " + (t + 1));
        }

        System.out.println("\n--- Suured testid ---");
        int[] sizes = {20, 50, 100, 500, 1000};
        int maxCost = 100;

        for (int n : sizes) {
            System.out.println("\nSuurusega N=" + n + "...");

            int[][] symMatrix = generateRandomSymmetricMatrix(n, maxCost);
            runTest(solver, symMatrix, "Sümmeetriline N=" + n);

            int[][] asymMatrix = generateRandomAsymmetricMatrix(n, maxCost);
            runTest(solver, asymMatrix, "Asümmeetriline N=" + n);
        }
    }

    private static void runTest(TSP solver, int[][] matrix, String testName) {
        System.gc();

        long start = System.nanoTime();
        Result result = solver.solve(matrix);
        long end = System.nanoTime();

        double timeMs = (end - start) / 1e6;

        System.out.println(String.format("%-25s | Kulu: %-6d | Aeg: %.4f ms", testName, result.getCost(), timeMs));

        if (matrix.length <= 20) {
            System.out.print("   Tee: ");
            for (int city : result.getPath()) {
                System.out.print(city + " ");
            }
            System.out.println();
        }
    }

    public static int[][] generateRandomSymmetricMatrix(int n, int maxCost) {
        int[][] matrix = new int[n][n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int cost = rand.nextInt(maxCost) + 1;
                matrix[i][j] = cost;
                matrix[j][i] = cost;
            }
        }
        return matrix;
    }

    public static int[][] generateRandomAsymmetricMatrix(int n, int maxCost) {
        int[][] matrix = new int[n][n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = rand.nextInt(maxCost) + 1;
                }
            }
        }
        return matrix;
    }
}