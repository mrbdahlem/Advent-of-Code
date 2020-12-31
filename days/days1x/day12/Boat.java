package days.days1x.day12;

import java.util.*;
import java.awt.Point;

public class Boat {
    private Point pos;
    private Direction dir;

    public Boat() {
        pos = new Point(0,0);
        dir = Direction.EAST;
    }

    public void move(String command) {
        int val = Integer.parseInt(command.substring(1));
        command = command.substring(0, 1);
        Direction move;
        switch (command) {
            case "N":
                move = Direction.NORTH;
                break;
            case "S":
                move = Direction.SOUTH;
                break;
            case "E":
                move = Direction.EAST;
                break;
            case "W":
                move = Direction.WEST;
                break;
            case "F":
                move = this.dir;
                break;
            case "R":
                this.dir = this.dir.turn(val);
                move = this.dir;
                val = 0;
                break;
            case "L":
                this.dir = this.dir.turn(-val);
                move = this.dir;
                val = 0;
                break;
            default:
                return;
        }

        this.pos = new Point(this.pos.x + (move.dx * val),
                             this.pos.y + (move.dy * val));
    }

    public Point getPos() {
        return pos;
    }

    public static enum Direction {
        NORTH(0, -1, 270),
        SOUTH(0, 1, 90),
        EAST(1, 0, 0),
        WEST(-1, 0, 180);

        public final int dx;
        public final int dy;
        private final int deg;

        private Direction(int dx, int dy, int deg) {
            this.dx = dx;
            this.dy = dy;
            this.deg = deg;
        }

        public Direction turn(int degrees) {
            degrees = this.deg + degrees;

            while (degrees < 0) {
                degrees += 360;
            }
            degrees = degrees % 360;

            for (Direction d : Direction.values()) {
                if (degrees == d.deg) {
                    return d;
                }
            }
            return null;
        }
    }
}