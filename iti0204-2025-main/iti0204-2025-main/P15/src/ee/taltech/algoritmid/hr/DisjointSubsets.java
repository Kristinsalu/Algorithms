package ee.taltech.algoritmid.hr;

import java.util.HashMap;
import java.util.Map;

public class DisjointSubsets {


    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    public String find(String element) throws IllegalArgumentException {
        if (!parent.containsKey(element)) {
            throw new IllegalArgumentException("Element not found: " + element);
        }

        if (!parent.get(element).equals(element)) {
            parent.put(element, find(parent.get(element)));
        }
        return parent.get(element);
    }

    public String union(String element1, String element2) throws IllegalArgumentException {
        if (!parent.containsKey(element1) || !parent.containsKey(element2)) {
            throw new IllegalArgumentException("One or both elements not present.");
        }

        String root1 = find(element1);
        String root2 = find(element2);

        if (!root1.equals(root2)) {
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);

            if (rank1 < rank2) {
                parent.put(root1, root2);
                return root2;
            } else if (rank1 > rank2) {
                parent.put(root2, root1);
                return root1;
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
                return root1;
            }
        }
        return root1;
    }

    public void addSubset(String element) throws IllegalArgumentException {
        if (parent.containsKey(element)) {
            throw new IllegalArgumentException("Element already exists: " + element);
        }
        parent.put(element, element);
        rank.put(element, 0);
    }

}