package days.days2x.day20;

import java.util.*;
import java.awt.Point;

public class Tile {
    private Tile north;
    private Tile south;
    private Tile east;
    private Tile west;

    private String[][] contents;
    private Map<Dir, String> edges;
    private Map<Dir, String> rEdges;

    public final int id;

    private Point pos;

    public Tile(String data) {
        String[] lines = data.split("\n");

        lines[0] = lines[0].split(" ")[1];
        id = Integer.parseInt(lines[0].substring(0, lines[0].length() - 1));

        contents = new String[lines.length - 1][];

        for (int r = 0; r < lines.length - 1; r++) {
            contents[r] = lines[r + 1].split("");
        }

        edges = new HashMap<>();
        rEdges = new HashMap<>();
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getPos() {
        return pos;
    }

    public String getEdge(Dir edge) {
        String edgeStr = edges.get(edge);
        if (edgeStr != null) {
            return edgeStr;
        }

        switch (edge) {
            case NORTH:
                edgeStr = combine(contents[0]);
                break;
            case SOUTH:
                edgeStr = combine(contents[contents.length - 1]);
                break;
            case EAST:
                edgeStr = column(contents[0].length - 1);
                break;
            case WEST:
                edgeStr = column(0);
                break;
        }

        edges.put(edge, edgeStr);
        return edgeStr;
    }
    private String combine(String[] in) {
        return Arrays.stream(in)
            .reduce("", (acc, s) -> acc + s);
    }
    private String column(int col) {
        String s = "";

        for (int r = 0; r < contents.length; r++) {
            s += contents[r][col];
        }

        return s;
    }

    public String getREdge(Dir edge) {
        String edgeStr = rEdges.get(edge);
        if (edgeStr != null) {
            return edgeStr;
        }

        edgeStr = getEdge(edge);
           
        char[] c = edgeStr.toCharArray();
        for (int i = 0; i < c.length / 2; i++) {
            char tmp = c[i];
            c[i] = c[c.length - 1 - i];
            c[c.length - 1 - i] = tmp;
        }
        edgeStr = new String(c);
        rEdges.put(edge, edgeStr);
        return edgeStr;
    }

    public boolean isConnected() {
        return (north != null || south != null ||
            east != null || west != null);
    }

    public Tile connected(Dir edge) {
        switch (edge) {
            case NORTH:
                return north;
            case SOUTH:
                return south;
            case EAST:
                return east;
            case WEST:
                return west;
        }
        return null;
    }

    public boolean checkRotations(String edge, Dir connectEdge) {
        if (isConnected()) {
            return getEdge(connectEdge).equals(edge);
        }

        for (Dir d : Dir.values()) {
            if (getEdge(d).equals(edge)) return true;
            if (getREdge(d).equals(edge)) return true;
        }
        return false;
    }

    public void connect(Tile other, Dir edge) {
        other.rotateToMatch(this, edge);

        // System.out.println(this.id + " " + edge + ": " +
        //                     other.id + " " + edge.opposite() );

        switch (edge) {
            case NORTH:
                this.north = other;
                other.south = this;
                break;
            case SOUTH:
                this.south = other;
                other.north = this;
                break;
            case EAST:
                this.east = other;
                other.west = this;
                break;
            case WEST:
                this.west = other;
                other.east = this;
                break;
        }

    }

    private void rotateToMatch(Tile other, Dir edge) {
        String match = other.getEdge(edge);
        edge = edge.opposite();

        for (int f = 0; f <= 1; f++) {
            for (int r = 0; r < 4; r++) {
                if (match.equals(getEdge(edge))) {
                    return;
                }
                if (!rotate()) break;
            }
            if (!flip()) break;
        }

        String s = "Could not connect " + id + " on " + edge + " to " + other.id;
        throw new RuntimeException(s);
    }

    private boolean rotate() {
        if (north != null || south != null ||
            east != null || west != null) {
            return false;
        }

        edges = new HashMap<>();
        rEdges = new HashMap<>();

        final int N = contents.length - 1;
        for (int r = 0; r < contents.length / 2; r++) {
            for (int c = r; c < N - r; c++) {
                String temp = contents[r][c]; 
                contents[r][c] = contents[c][N - r]; 
                contents[c][N - r] = contents[N - r][N - c]; 
                contents[N - r][N - c] = contents[N - c][r]; 
                contents[N - c][r] = temp; 
            }
        }
        return true;
    }

    private boolean flip() {
        if (north != null || south != null ||
            east != null || west != null) {
            return false;
        }

        edges = new HashMap<>();
        rEdges = new HashMap<>();

        final int N = contents[0].length - 1;
        for (int r = 0; r < contents.length; r++) {
            for (int c = 0; c < contents[0].length / 2; c++) {
                String tmp = contents[r][c];
                contents[r][c] = contents[r][N - c];
                contents[r][N - c] = tmp; 
            }
        }
        return true;
    }

    public String toString() {
        String s = "";

        s += id + "\n";

        for (int r = 0; r < contents.length; r++) {
            for (int c = 0; c < contents[r].length; c++) {
                s += contents[r][c];
            }
            s += "\n";
        }
        return s;
    }

    public String get(int c, int r) {
        if (r < contents.length && c < contents[r].length) {
            return contents[r][c];
        }
        else {
            return " ";
        }
    }

    public int getWidth() {
        return contents[0].length;
    }

    public int getHeight() {
        return contents.length;
    }

    public void shrink() {
        String[][] shrunk = new String[contents.length - 2]
                                      [contents[0].length - 2];
        
        for (int r = 0; r < shrunk.length; r++) {
            for (int c = 0; c < shrunk[r].length; c++) {
                shrunk[r][c] = contents[r + 1][c + 1];
            }
        }

        contents = shrunk;
    }
}