package days.days2x.day20;

import days.AocDay;
import java.io.*;
import java.util.*;
import java.awt.Point;
import java.awt.Rectangle;

public class Day20 extends AocDay {
    private static final String[] MONSTER = {"                  # ",
                                             "#    ##    ##    ###",
                                             " #  #  #  #  #  #   "};

    private final Tile[] tiles;

    private Map<Point, Tile> tileMap;
    
    /**
     * Prepare/parse the input in preparation for running the parts.
     * @param input the entire problem input as downloaded
     * @param output any display/debug output will be sent to output
     */
    public Day20(String input, PrintStream output) {
        super(input, output);

        tiles = Arrays.stream(input.split("\n\n"))
                    .map(data -> new Tile(data))
                    .toArray(size -> new Tile[size]);
    }

    /**
     * Solve part 1 of the day's challenge using the prepared input (prepare
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    @Override
    public String part1() {
        List<Tile> connected = new ArrayList<>();
        List<Tile> unconnected = new ArrayList<>();
        tileMap = new HashMap<>();
        
        Point pos = new Point(0,0);
        tiles[0].setPos(pos);
        tileMap.put(pos, tiles[0]);
        connected.add(tiles[0]);
        
        for (int i = 1; i < tiles.length; i++) {
            unconnected.add(tiles[i]);
        }
        
        for (int i = 0; i < connected.size(); i++) {
            Tile t = connected.get(i);
            Point p = t.getPos();

            for (Dir dir : Dir.values()) {
                if (t.connected(dir) == null) {
                    String edge = t.getEdge(dir);

                    for (int j = 0; j < unconnected.size(); j++) {
                        Tile t2 = unconnected.get(j);

                        if (t2 == t) continue;

                        if (t2.checkRotations(edge, dir.opposite())) {
                            t.connect(t2, dir);
                            connected.add(t2);
                            unconnected.remove(t2);

                            Point p2 = new Point(p.x + dir.DX,
                                                 p.y + dir.DY);
                            t2.setPos(p2);
                            tileMap.put(p2, t2);

                            for (Dir d2 : Dir.values()) {
                                if (d2 == dir.opposite()) continue;
                                Point p3 = new Point(p2.x + d2.DX,
                                                     p2.y + d2.DY);
                                Tile t3 = tileMap.get(p3);
                                if (t3 != null) {
                                    t2.connect(t3, d2);
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }

        Rectangle ext = getExtents(tileMap);
        Point min = new Point((int)ext.getMinX(), (int)ext.getMinY());
        Point max = new Point((int)ext.getMaxX(), (int)ext.getMaxY());

        long product = tileMap.get(min).id;
        product *= tileMap.get(new Point(min.x, max.y)).id;
        product *= tileMap.get(new Point(max.x, min.y)).id;
        product *= tileMap.get(max).id;

        // out.println("product of corner ids: " + product);

        return "" + product;
    }

    /**
     * Solve part 2 of the day's challenge using the prepared input (prepare 
     * must be called first).
     * @param output any display/debug output will be sent to output
     *
     * @return the solution for the day's challenge.
     */
    @Override
    public String part2() {
        int tWidth = 0;
        int tHeight = 0;
        for (Tile t : tileMap.values()) {
            t.shrink();
            tWidth = t.getWidth();
            tHeight = t.getHeight();
        }

        Rectangle rect = getExtents(tileMap);
        int minX = (int)rect.getMinX();
        int minY = (int)rect.getMinY();

        String[][] pic = new String[tWidth * ((int)rect.getWidth() + 1)]
                                   [tHeight * ((int)rect.getHeight() + 1)];

        // out.println(pic[0].length + "x" + pic.length);

        for (int r = 0; r < pic.length; r++) {
            int tmr = (r / tHeight) + minY;
            int tr = r % tHeight;
            for (int c = 0; c < pic[r].length; c++) {
                int tmc = (c / tWidth) + minX;
                int tc = c % tWidth;
                pic[r][c] = tileMap.get(new Point(tmc, tmr)).get(tc, tr);
            }
        }

        String[][] tile = new String[MONSTER.length][];
        for (int r = 0; r < MONSTER.length; r++) {
            tile[r] = MONSTER[r].split("");
        }

        int count = 0;
        for (int f = 0; f <= 1; f++) {
            for (int r = 0; r < 4; r++) {
                count += replace(pic, tile);
                tile = rotate(tile);
            }
            tile = flip(tile);
        }

        // print(pic);
        // out.println(count + " monsters found!");

        count = 0;
        for (int r = 0; r < pic.length; r++) {
            for (int c = 0; c < pic.length; c++) {
                if (pic[r][c].equals("#")) {
                    count++;
                }
            }
        }

        // out.println("Non-monster #s: " + count);
        return "" + count;
    }

    private static int replace(String[][] pic, String[][] tile) {
        int count = 0;
        for (int r = 0; r < pic.length - tile.length; r++) {
            for (int c = 0; c < pic[r].length - tile[0].length; c++) {
                boolean found = true;

                for (int dr = 0; dr < tile.length && found; dr++) {
                    for (int dc = 0; dc < tile[dr].length && found; dc++) {
                        if (tile[dr][dc].equals("#")) {
                            if (!pic[r + dr][c + dc].equals("#")) {
                                found = false;
                            }
                        }
                    }
                }

                if (found) {
                    count++;
                    for (int dr = 0; dr < tile.length; dr++) {
                        for (int dc = 0; dc < tile[dr].length; dc++) {
                            if (tile[dr][dc].equals("#")) {
                                pic[r + dr][c + dc] = "@";
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private static void print(String[][] pic) {
        for (int r = pic.length - 1; r >= 0; r--) {
            for (int c = 0; c < pic[r].length; c++) {
                if (pic[r][c].equals("#")) {
                    System.out.print("\u001b[37;1m\u001b[40m-");
                }
                else if (pic[r][c].equals("@")) {
                    System.out.print("\u001b[42m\u001b[32m ");
                }
                else {
                    System.out.print("\u001b[37;1m\u001b[40m" + pic[r][c]);
                }
            }
            System.out.println();
        }
    }

    private static String[][] rotate(String[][] tile) {
        String[][] rotated = new String[tile[0].length][tile.length];

        for (int r = 0; r < tile.length; r++) {
            for (int c = 0; c < tile[r].length; c++) {
                rotated[rotated.length - 1 - c][r] = tile[r][c];
            }
        }
        return rotated;
    }

    private static String[][] flip(String[][] tile) {
        String[][] flipped = new String[tile.length][tile[0].length];

        for (int r = 0; r < tile.length; r++) {
            for (int c = 0; c < tile[r].length; c++) {
                flipped[r][tile[r].length - 1 - c] = tile[r][c];
            }
        }
        return flipped;
    }

    private static Rectangle getExtents(Map<Point, Tile> tileMap) {
        Point min = new Point(0, 0);
        Point max = new Point(0, 0);

        for (Point p : tileMap.keySet()) {
            if (p.x < min.x) min = new Point(p.x, min.y);
            if (p.y < min.y) min = new Point(min.x, p.y);
            if (p.x > max.x) max = new Point(p.x, max.y);
            if (p.y > max.y) max = new Point(max.x, p.y);
        }

        return new Rectangle(min.x, min.y, max.x - min.x, max.y - min.y);
    }
}