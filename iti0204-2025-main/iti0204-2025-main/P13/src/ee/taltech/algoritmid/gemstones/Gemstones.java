package ee.taltech.algoritmid.gemstones;

import java.util.*;
import java.util.HashMap;

public class Gemstones {

    public static List<Integer> findGemstones(int sum, List<Integer> gemstoneOptions) throws IllegalArgumentException {
        if (gemstoneOptions == null || gemstoneOptions.isEmpty()) {
            throw new IllegalArgumentException("Gemstone options list cannot be empty.");
        }

        Map<Integer, State> bestForSum = new HashMap<>();
        bestForSum.put(0, new State(0, 0, 0, 0));

        for (int s = 1; s <= sum; s++) {
            State best = null;

            for (int value : gemstoneOptions) {
                int prevSum = s - value;
                if (prevSum < 0) continue;

                State previousBest = bestForSum.get(prevSum);
                if (previousBest == null) continue;

                int newCount = previousBest.count + 1;
                int newPerfectCount = previousBest.perfectCount + (isPerfect(value) ? 1 : 0);
                State newState = new State(newCount, newPerfectCount, prevSum, value);

                if (best == null || isBetter(newState, best)) {
                    best = newState;
                }
            }

            if (best != null) {
                bestForSum.put(s, best);
            }
        }

        List<Integer> resultList = new ArrayList<>();
        int currentSum = sum;

        while (currentSum > 0) {
            State currentBest = bestForSum.get(currentSum);

            if (currentBest == null) {
                return List.of();
            }

            resultList.add(currentBest.gemstoneValue);
            currentSum = currentBest.previousSum;
        }

        Collections.reverse(resultList);
        return resultList;
    }


    private static class State {
        final int count;
        final int perfectCount;
        final int previousSum;
        final int gemstoneValue;

        State(int count, int perfectCount, int previousSum, int gemstoneValue) {
            this.count = count;
            this.perfectCount = perfectCount;
            this.previousSum = previousSum;
            this.gemstoneValue = gemstoneValue;
        }
    }

    private static boolean isBetter(State a, State b) {
        if (a.count != b.count) {
            return a.count < b.count;
        }
        return a.perfectCount > b.perfectCount;
    }

    private static boolean isPerfect(int v) {
        return v != 1 && v % 10 != 0;
    }

}