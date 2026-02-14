package ee.taltech.algoritmid.socialmedia;

import java.util.*;

public class SocialMedia {

    Map<String, Integer> visits = new HashMap<>();

    private static class Entry {
        String key;
        int count;
        Entry(String key, int count) {
            this.key = key;
            this.count = count;
        }
    }
    PriorityQueue<Entry> maxHeap = new PriorityQueue<>(
            (a, b) -> b.count - a.count);

    public void addVisit(String url, String http) throws IllegalArgumentException {
        if (!http.equals("GET") && !http.equals("POST") &&
                !http.equals("PUT") && !http.equals("DELETE")) {
            throw new IllegalArgumentException("Invalid HTTP method: " + http);
        }

        String key = url + "|" + http;
        int count = visits.getOrDefault(key, 0) + 1;
        visits.put(key, count);
        maxHeap.offer(new Entry(key, count));
    }

    public Integer getNumberOfVisits(String url, String http) throws IllegalArgumentException {
        if (!http.equals("GET") && !http.equals("POST") &&
                !http.equals("PUT") && !http.equals("DELETE")) {
            throw new IllegalArgumentException("Invalid HTTP method: " + http);
        }
        String key = url + "|" + http;
        Integer count = visits.get(key);
        if (count == null) {
            return 0;
        };

        return count;
    }

    private Entry getTopEntry() {
        while (!maxHeap.isEmpty()) {
            Entry top = maxHeap.peek();
            int actualCount = visits.getOrDefault(top.key, 0);
            if (top.count == actualCount) return top;
            maxHeap.poll();
        }
        return null;
    }

    public Integer getTop1() {
        Entry top = getTopEntry();
        return top != null ? top.count : 0;
    }

    public List<Integer> getTop2() {
        List<Integer> result = new ArrayList<>();

        Entry first = getTopEntry();
        if (first == null) {
            result.add(0);
            result.add(0);
            return result;
        }
        result.add(first.count);

        maxHeap.poll();
        Entry second = getTopEntry();
        result.add(second != null ? second.count : 0);

        maxHeap.offer(first);
        return result;
    }
}