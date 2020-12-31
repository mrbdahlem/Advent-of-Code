package days.days1x.day16;

import java.util.*;

public class Field {
    private String title;
    private int[] range1;
    private int[] range2;

    public Field(String rule) {
        String[] parts = rule.split(": ");
        title = parts[0];

        parts = parts[1].split(" or ");
        range1 = Arrays.stream(parts[0].split("-")).mapToInt(Integer::parseInt).toArray();
        range2 = Arrays.stream(parts[1].split("-")).mapToInt(Integer::parseInt).toArray();
    }

    public String getName() {
        return title;
    }

    public boolean matches(int val) {
        return (range1[0] <= val && val <= range1[1]) ||
               (range2[0] <= val && val <= range2[1]);
    }
}