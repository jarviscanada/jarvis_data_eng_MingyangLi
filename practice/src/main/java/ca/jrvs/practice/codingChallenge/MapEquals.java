package ca.jrvs.practice.codingChallenge;

import java.util.Map;
import java.util.Set;

public class MapEquals {
    public static <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2) {
        Set<K> kSet = m1.keySet();
        if (!kSet.equals(m2.keySet())) {
            return false;
        }
        for (K key : kSet) {
            if (!m1.get(key).equals(m2.get(key))) {
                return false;
            }
        }
        return true;
    }
}
