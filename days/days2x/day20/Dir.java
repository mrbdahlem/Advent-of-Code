package days.days2x.day20;

public enum Dir {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);

    public final int DX;
    public final int DY;
    
    private Dir(int dx, int dy) {
        this.DX = dx;
        this.DY = dy;
    }

    public Dir opposite() {
        switch (this) {
            case NORTH:
                return SOUTH;

            case SOUTH:
                return NORTH;

            case EAST:
                return WEST;

            case WEST:
                return EAST;
        }

        return null;
    }
}