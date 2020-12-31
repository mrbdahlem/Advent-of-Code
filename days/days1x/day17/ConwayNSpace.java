package days.days1x.day17;

import java.util.*;

public class ConwayNSpace {
    private Map<PointND, Boolean> cells;
    private int cycles;
    private PointND[] delta;

    public ConwayNSpace(int dimensions, String[][] initial) {
        cells = new HashMap<>();
        cycles = 0;

        delta = deltaCoords(dimensions);

        for (int y = 0; y < initial.length; y++) {
            for (int x = 0; x < initial[y].length; x++) {
                if (initial[y][x].equals("#")) {
                    int[] coord = new int[dimensions];
                    coord[0] = x;
                    coord[1] = y;
                    PointND p = new PointND(coord);

                    cells.put(p, true);
                }
            }
        }
    }

    private static PointND[] deltaCoords(int dimensions) {
        PointND[] d = new PointND[(int)Math.pow(3, dimensions)];

        for (int i = 0; i < d.length; i++) {
            int[] coord = new int[dimensions];
            int c = i;
            for (int j = 0; j < dimensions; j++) {
                coord[j] = (c % 3) - 1;
                c = c / 3;
            }
            d[i] = new PointND(coord);
            //System.out.println(Arrays.toString(coord));
        }

        return d;
    }

    public void step() {
        Map<PointND, Integer> neighbors = new HashMap<>();

        for (PointND p : cells.keySet()) {
            for (PointND d : delta) {
                if (!d.isOrigin()) {
                    PointND q = p.add(d);
                    neighbors.put(q, neighbors.getOrDefault(q, 0) + 1);
                }
            }
        }

        Map<PointND, Boolean> newcells = new HashMap<>();
        for (Map.Entry<PointND, Integer> e : neighbors.entrySet()) {
            int v = e.getValue();
            PointND p = e.getKey();
            if (v == 3 || v == 2 && cells.containsKey(p)) {
                newcells.put(e.getKey(), true);
            }
        }
        cells = newcells;

        cycles++;
    }

    public int numActive() {
        return cells.size();
    }
}