package days.days2x.day24;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexPoint {
    public final int x;
    public final int y;

    private final static int NW = 0;
    private final static int NE = 1;
    private final static int E = 2;
    private final static int SE = 3;
    private final static int SW = 4;
    private final static int W = 5;
    private final static int[] DX = { 0,  1,  1,  0, -1, -1};
    private final static int[] DY = {-1, -1,  0,  1,  1,  0};

    public HexPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HexPoint() {
        this(0, 0);
    }

    public HexPoint(String trail) {
        int xx = 0;
        int yy = 0;

        Pattern pat = Pattern.compile("nw|ne|sw|se|w|e");
        Matcher m = pat.matcher(trail);

        while (m.find()) {
            String move = m.group();
            switch (move) {
                case "nw":
                    xx += DX[NW];
                    yy += DY[NW];
                    break;
                case "ne":
                    xx += DX[NE];
                    yy += DY[NE];
                    break;
                case "e":
                    xx += DX[E];
                    yy += DY[E];
                    break;
                case "se":
                    xx += DX[SE];
                    yy += DY[SE];
                    break;
                case "sw":
                    xx += DX[SW];
                    yy += DY[SW];
                    break;
                case "w":
                    xx += DX[W];
                    yy += DY[W];
                    break;
            }
        }
        x = xx;
        y = yy;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof HexPoint)) {
            return false;
        }

        HexPoint o = (HexPoint) other;

        return this.x == o.x && this.y == o.y;
    }

    public int hashCode() {
        return  y + (31 * x); 
    }

    public HexPoint e() {
        return new HexPoint(x + DX[E], y + DY[E]);
    }

    public HexPoint w() {
        return new HexPoint(x + DX[W], y + DY[W]);
    }

    public HexPoint nw() {
        return new HexPoint(x + DX[NW], y + DY[NW]);
    }

    public HexPoint ne() {
        return new HexPoint(x + DX[NE], y + DY[NE]);
    }

    public HexPoint sw() {
        return new HexPoint(x + DX[SW], y + DY[SW]);
    }

    public HexPoint se() {
        return new HexPoint(x + DX[SE], y + DY[SE]);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public HexPoint[] neighbors() {
        return new HexPoint[] { nw(), ne(), e(), se(), sw(), w() };
    }
}