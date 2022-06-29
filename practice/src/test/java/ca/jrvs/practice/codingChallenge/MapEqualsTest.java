package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MapEqualsTest {

    @Test
    public void compareMaps() {
        Map<String, String[]> a1 = new HashMap<>();
        a1.put("address", new String[]{"canada", "ontario", "toronto"});
        a1.put("city", new String[]{"toronto"});

        Map<String, String[]> a2 = new HashMap<>();
        a2.put("address", new String[]{"canada", "ontario", "toronto"});
        a2.put("city", new String[]{"toronto"});

        Map<String, String[]> a3 = new HashMap<>();
        a3.put("address", new String[]{"canada", "ontario", "Toronto"});
        a3.put("city", new String[]{"Toronto"});

        assertTrue(MapEquals.compareMaps(a1, a2));
        assertNotEquals(a1, a3);
    }
}