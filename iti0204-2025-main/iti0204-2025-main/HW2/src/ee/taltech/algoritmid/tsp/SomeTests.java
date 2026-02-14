package ee.taltech.algoritmid.tsp;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SomeTests {

    public static int[][] subMatrix(int[][] matrix, int k) {
        if (k < 0 || k > matrix.length) return null;
        int[][] out = new int[k][k];
        for (int i = 0; i < k; i++) {
            System.arraycopy(matrix[i], 0, out[i], 0, k);
        }
        return out;
    }


    private static int ds(int[] r, int[][] a) {
        if (r == null) return -1;
        int d = 0;
        int i = 0;
        while (i < r.length - 1) {
            d += a[r[i]][r[++i]];
        }
        if (r[0] != r[r.length - 1])
            d += a[r[r.length-1]][r[0]];

        return d;
    }

    private static boolean boundsOk(int[] route, int[][] adjacencyMatrix) {
        if (route == null) return false;
        int rows = adjacencyMatrix.length;
        int cols = adjacencyMatrix[0].length;
        for (int i : route) {
            if (i < 0 || i >= rows || i >= cols)
                return false;
        }
        return true;
    }

    private static boolean isValidRoute(int[] route, int length, int[][] adjacencyMatrix) {
        if (route == null) return false;
        Set<Integer> visited = Arrays.stream(route).boxed().collect(Collectors.toSet());

        if (route[0] != 0) {
            System.err.println("Did not start from the post office");
            return false;
        }

        if ((route.length != adjacencyMatrix.length && route.length - 1 != adjacencyMatrix.length)
                || visited.size() != adjacencyMatrix.length) {
            System.err.println("Did not visit each town exactly once");
            return false;
        }

        if (ds(route, adjacencyMatrix) != length) {
            System.err.printf("Path length calculated incorrectly - expected %d, got %d\n", ds(route, adjacencyMatrix), length);
            return false;
        }

        return true;
    }

    private static int[][] symmetric10Cities = {
            {0, 441, 219, 367, 167, 490, 455, 179, 269, 289},
            {441, 0, 111, 359, 172, 397, 232, 140, 122, 9},
            {219, 111, 0, 29, 280, 340, 372, 206, 225, 116},
            {367, 359, 29, 0, 454, 392, 365, 393, 320, 395},
            {167, 172, 280, 454, 0, 444, 287, 494, 414, 321},
            {490, 397, 340, 392, 444, 0, 488, 18, 470, 25},
            {455, 232, 372, 365, 287, 488, 0, 265, 75, 491},
            {179, 140, 206, 393, 494, 18, 265, 0, 321, 165},
            {269, 122, 225, 320, 414, 470, 75, 321, 0, 200},
            {289, 9, 116, 395, 321, 25, 491, 165, 200, 0}
    };

    private static int[][] asymmetric10Cities = {
            {0, 58, 444, 260, 304, 458, 52, 43, 383, 155},
            {434, 0, 186, 303, 74, 4, 464, 442, 385, 309},
            {446, 138, 0, 148, 442, 292, 480, 448, 110, 138},
            {333, 443, 323, 0, 30, 272, 150, 283, 294, 403},
            {384, 136, 421, 103, 0, 472, 370, 344, 204, 229},
            {248, 209, 159, 156, 429, 0, 276, 464, 234, 161},
            {431, 177, 223, 4, 400, 52, 0, 182, 116, 6},
            {59, 126, 206, 246, 487, 114, 366, 0, 229, 417},
            {84, 270, 470, 126, 96, 226, 358, 179, 0, 246},
            {125, 476, 278, 161, 305, 382, 398, 255, 183, 0}
    };

    private static int[][] symmetric3Cities = subMatrix(symmetric10Cities, 3);
    private static int[][] asymmetric3Cities = subMatrix(asymmetric10Cities, 3);


    public void testSymmetric3Cities() {
        var tsp = new HW2();
        var result = tsp.solve(symmetric3Cities);
        int[] route = result.getPath();
        int length = result.getCost();

        if (!boundsOk(route, symmetric3Cities)) {
            throw new RuntimeException("Route indices out of bounds");
        }
        if (!isValidRoute(route, length, symmetric3Cities)) {
            throw new RuntimeException("Result is not a valid route");
        }

        if (length > 771) {
            throw new RuntimeException("Suboptimal route. Optimal route distance is 771 or less, got " + length);
        }
    }

    public void testAsymmetric3Cities() {
        var tsp = new HW2();
        var result = tsp.solve(asymmetric3Cities);
        int[] route = result.getPath();
        int length = result.getCost();

        if (!boundsOk(route, asymmetric3Cities)) {
            throw new RuntimeException("Route indices out of bounds");
        }
        if (!isValidRoute(route, length, asymmetric3Cities)) {
            throw new RuntimeException("Result is not a valid route");
        }

        if (length > 690) {
            throw new RuntimeException("Suboptimal route. Optimal route distance is 690 or less, got " + length);
        }
    }

    public void testSymmetric10Cities() {
        var tsp = new HW2();
        var result = tsp.solve(symmetric10Cities);
        int[] route = result.getPath();
        int length = result.getCost();

        if (!boundsOk(route, symmetric10Cities)) {
            throw new RuntimeException("Route indices out of bounds");
        }
        if (!isValidRoute(route, length, symmetric10Cities)) {
            throw new RuntimeException("Result is not a valid route");
        }

        if (length > 1220) {
            throw new RuntimeException("Suboptimal route. Optimal route distance is 1220 or less, got " + length);
        }
    }

    public void testAsymmetric10Cities() {
        var tsp = new HW2();
        var result = tsp.solve(asymmetric10Cities);
        int[] route = result.getPath();
        int length = result.getCost();

        if (!boundsOk(route, asymmetric10Cities)) {
            throw new RuntimeException("Route indices out of bounds");
        }
        if (!isValidRoute(route, length, asymmetric10Cities)) {
            throw new RuntimeException("Result is not a valid route");
        }

        if (length > 857) {
            throw new RuntimeException("Suboptimal route. Optimal route distance is 857 or less, got " + length);
        }
    }

    public static void main(String[] args) {
        var tests = new SomeTests();
        tests.testSymmetric3Cities();
        tests.testAsymmetric3Cities();
        tests.testSymmetric10Cities();
        tests.testAsymmetric10Cities();
        System.out.println("Your solution works correctly for small inputs. However, make sure it is fast enough and still works correctly with larger inputs.");
    }


}
