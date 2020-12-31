package days.days0x.day7;

import java.util.*;

public class BagRule {
    public String bag;
    public List<BagCount> contents;

    public int hashCode() {
        return bag.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof BagRule)) {
            return false;
        }

        BagRule ob = (BagRule)other;

        return this.bag.equals(ob.bag) && this.contents.equals(ob.contents);
    }

    public String toString() {
        return bag + " contains " + contents + "\n";
    }

    public boolean canContain(String type) {
        return contents.contains(new BagCount(1, type));
    }

    public List<BagCount> mustContain() {
        return new ArrayList<>(contents);
    }

    public static class BagCount {
        public int count;
        public String type;

        public BagCount(int count, String type) {
            this.count = count;
            this.type = type;
        }

        public String toString() {
            return count + "@" + type;
        }

        public int hashCode() {
            return type.hashCode();
        }
        
        public boolean equals(Object other) {
            if (other instanceof BagCount) {
                return this.type.equals(((BagCount)other).type);
            }
            return false;
        }
    }
}