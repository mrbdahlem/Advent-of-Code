package days.days1x.day17;

import java.util.Arrays;

public class PointND {
    public final int[] coords;
    private final int hash;
    private boolean origin;

    public PointND(int ...coords) {
        this.coords = Arrays.copyOf(coords, coords.length);

        origin = Arrays.stream(coords)
                    .allMatch(c -> c == 0);

        this.hash = Arrays.stream(coords)
                        .reduce(0, (acc, c) -> (31 * acc) + c);
    }

    public boolean isOrigin() {
        return origin;
    }

    public PointND add(PointND other) {
        int[] ncoords = new int[coords.length];
        for (int i = 0; i < coords.length; i++) {
            ncoords[i] = coords[i] + other.coords[i];
        }
        return new PointND(ncoords);
    }
    
    public int hashCode() {
        return hash;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PointND)) {
            return false;
        }

        PointND o = (PointND)other;

        return Arrays.equals(coords, o.coords);
    }
}