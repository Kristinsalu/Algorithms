package ee.taltech.algoritmid.tsp;

public class HW2 implements TSP {

    @Override
    public Result solve(int[][] matrix) {
        int n = matrix.length;

        if (n <= 20) {
            return heldKarp(matrix);
        }

        int[] bestPath = null;
        int bestDistance = Integer.MAX_VALUE;

        for (int start = 0; start < n; start++) {
            int[] path = new int[n];
            boolean[] visited = new boolean[n];

            int current = start;
            visited[current] = true;
            path[0] = current;
            int pathIndex = 1;

            int distance = 0;

            for (int step = 1; step < n; step++) {
                int next = -1;
                int best = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    if (!visited[i] && matrix[current][i] < best) {
                        best = matrix[current][i];
                        next = i;
                    }
                }

                visited[next] = true;
                distance += best;
                current = next;
                path[pathIndex++] = current;
            }

            distance += matrix[current][start];

            if (distance < bestDistance) {
                bestDistance = distance;
                bestPath = path.clone();
            }
        }

        if (isSymmetric(matrix)) {
            bestPath = twoOpt(bestPath, matrix);
            bestDistance = computeDistance(bestPath, matrix);
        }

        int startIndex = -1;
        for (int i = 0; i < n; i++) {
            if (bestPath[i] == 0) {
                startIndex = i;
                break;
            }
        }

        int[] finalPath = new int[n];
        for (int i = 0; i < n; i++) {
            finalPath[i] = bestPath[(startIndex + i) % n];
        }

        final int finalDistance = bestDistance;

        return new Result() {
            @Override
            public int[] getPath() {
                return finalPath;
            }

            @Override
            public int getCost() {
                return finalDistance;
            }
        };
    }

    private Result heldKarp(int[][] distances) {
        int n = distances.length;

        if (n == 1) {
            return new Result() {
                @Override public int[] getPath() { return new int[]{0}; }
                @Override public int getCost() { return 0; }
            };
        }

        int subsetCount = 1 << n;
        final int infinity = Integer.MAX_VALUE / 4;

        int[][] dp = new int[subsetCount][n];
        int[][] parents = new int[subsetCount][n];

        for (int mask = 0; mask < subsetCount; mask++) {
            for (int j = 0; j < n; j++) {
                dp[mask][j] = infinity;
                parents[mask][j] = -1;
            }
        }

        dp[1][0] = 0;

        for (int mask = 1; mask < subsetCount; mask++) {
            if ((mask & 1) == 0) continue;

            for (int j = 1; j < n; j++) {
                if ((mask & (1 << j)) == 0) continue;

                int prevMask = mask ^ (1 << j);

                for (int k = 0; k < n; k++) {
                    if ((prevMask & (1 << k)) == 0) continue;

                    int cost = dp[prevMask][k] + distances[k][j];
                    if (cost < dp[mask][j]) {
                        dp[mask][j] = cost;
                        parents[mask][j] = k;
                    }
                }
            }
        }

        int fullMask = subsetCount - 1;
        int minimumCost = infinity;
        int lastCity = 0;

        for (int j = 1; j < n; j++) {
            int cost = dp[fullMask][j] + distances[j][0];
            if (cost < minimumCost) {
                minimumCost = cost;
                lastCity = j;
            }
        }

        int[] path = new int[n];
        int mask = fullMask;
        int city = lastCity;
        int index = n - 1;

        while (city != 0) {
            path[index--] = city;
            int parent = parents[mask][city];
            mask ^= (1 << city);
            city = parent;
        }

        path[index] = 0;
        int finalCost = minimumCost;

        return new Result() {
            @Override public int[] getPath() { return path; }
            @Override public int getCost() { return finalCost; }
        };
    }

    private int[] twoOpt(int[] path, int[][] matrix) {
        int n = path.length;
        boolean improved = true;
        int maxIterations = n * 20;
        int iterations = 0;

        while (improved && iterations < maxIterations) {
            improved = false;
            iterations++;

            for (int i = 0; i < n - 1; i++) {
                for (int k = i + 2; k < n; k++) {

                    int a = path[i];
                    int b = path[(i + 1) % n];
                    int c = path[k];
                    int d = path[(k + 1) % n];

                    int oldCost = matrix[a][b] + matrix[c][d];
                    int newCost = matrix[a][c] + matrix[b][d];

                    if (newCost < oldCost) {
                        reverse(path, i + 1, k);
                        improved = true;
                    }
                }
            }
        }

        return path;
    }

    private void reverse(int[] path, int i, int k) {
        while (i < k) {
            int temp = path[i];
            path[i] = path[k];
            path[k] = temp;
            i++;
            k--;
        }
    }

    private int computeDistance(int[] path, int[][] matrix) {
        int distance = 0;
        int n = path.length;

        for (int i = 0; i < n - 1; i++) {
            distance += matrix[path[i]][path[i + 1]];
        }
        distance += matrix[path[n - 1]][path[0]];
        return distance;
    }

    private boolean isSymmetric(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != matrix[j][i]) return false;
            }
        }
        return true;
    }
}

