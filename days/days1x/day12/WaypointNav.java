package days.days1x.day12;

import java.util.*;
import java.awt.Point;

public class WaypointNav {
    private Point pos; // waypoint relative to boat pos
    private Point boat;

    public WaypointNav(Point waypointStart) {
        pos = waypointStart;
        boat = new Point(0,0);
    }

    public void move(String command) {
        // System.out.println(command);
        int val = Integer.parseInt(command.substring(1));
        command = command.substring(0, 1);
        Direction move;
        double r;
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

            case "R":
                r = Math.toRadians(val);
                this.pos = new Point (
                    (int)Math.round((pos.x)*Math.cos(r) - (pos.y)*Math.sin(r)),
                    (int)Math.round((pos.x)*Math.sin(r) + (pos.y)*Math.cos(r))
                );
                // System.out.println("Waypoint at Delta: " + this.pos);
                return;
            case "L":
                r = Math.toRadians(-val);
                this.pos = new Point (
                    (int)Math.round((pos.x)*Math.cos(r) - (pos.y)*Math.sin(r)),
                    (int)Math.round((pos.x)*Math.sin(r) + (pos.y)*Math.cos(r))
                );
                // System.out.println("Waypoint at Delta: " + this.pos);
                return;    

            case "F":
                boat = new Point(boat.x + (pos.x * val),
                                 boat.y + (pos.y * val));
                return;
            
            default:
                return;
        }

        this.pos = new Point(this.pos.x + (move.dx * val),
                             this.pos.y + (move.dy * val));
        // System.out.println("Waypoint at Delta: " + this.pos);
    }

    public Point getBoatPos() {
        return boat;
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