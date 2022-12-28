package com.example.champions.DAL;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

public class MyComparator implements Comparator<String>, Serializable {
    private Map<String, Integer> sortedMap;

    public MyComparator() {
    }

    public void setSortedMap(Map<String, Integer> sortedMap) {
        this.sortedMap = sortedMap;
    }

    @Override
    public int compare(String s1, String s2) {
        if (s1 == null || !sortedMap.containsKey(s1)) {
            return -1;
        }
        if (s2 == null || !sortedMap.containsKey(s2)) {
            return 1;
        }
        return Objects.requireNonNull(sortedMap.get(s1)).compareTo(Objects.requireNonNull(sortedMap.get(s2)));
    }
}

