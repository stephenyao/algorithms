/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] tiles;
    private int hammingCache = -1;
    private int manhattanCache = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = this.tiles.length;
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingCache != -1) {
            return hammingCache;
        }

        int hammingDistance = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                int goal = goalTile(row, column);
                int tile = this.tiles[row][column];
                if (goal != 0 && goal != tile) {
                    hammingDistance++;
                }
            }
        }

        hammingCache = hammingDistance;
        return hammingCache;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanCache != -1) {
            return manhattanCache;
        }

        int manhattan = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                int tile = tiles[row][column];
                if (tile != 0) {
                    int goalRow = (tile - 1) / dimension();
                    int goalColumn = (tile - 1) % dimension();
                    int rowDist = goalRow - row;
                    int columnDist = goalColumn - column;
                    int manhattanDist = Math.abs(rowDist) + Math.abs(columnDist);
                    manhattan += manhattanDist;
                }
            }
        }
        manhattanCache = manhattan;
        return manhattanCache;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != goalTile(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private int goalTile(int row, int col) {
        if (row == tiles.length - 1 && col == tiles.length - 1) {
            return 0;
        }

        return row * tiles.length + (col + 1);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board board = (Board) y;
        return Arrays.deepEquals(board.tiles, this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<Board>();
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                int tile = tiles[row][column];
                if (tile == 0) {
                    int left = column - 1;
                    int right = column + 1;
                    int up = row - 1;
                    int down = row + 1;

                    Board leftNeighbor = swap(row, left, row, column);
                    Board rightNeighbor = swap(row, right, row, column);
                    Board topNeighbor = swap(up, column, row, column);
                    Board bottomNeighbor = swap(down, column, row, column);

                    if (leftNeighbor != null) list.add(leftNeighbor);
                    if (rightNeighbor != null) list.add(rightNeighbor);
                    if (topNeighbor != null) list.add(topNeighbor);
                    if (bottomNeighbor != null) list.add(bottomNeighbor);
                }
            }
        }

        return list;
    }

    private Board swap(int row, int column, int currentRow, int currentColumn) {
        if (row < 0 || column < 0 || row > dimension() - 1 || column > dimension() - 1) {
            return null;
        }
        int[][] newTiles = deepCopy(tiles);
        int swap = newTiles[row][column];
        int tile = newTiles[currentRow][currentColumn];
        newTiles[row][column] = tile;
        newTiles[currentRow][currentColumn] = swap;
        return new Board(newTiles);
    }

    private int[][] deepCopy(int[][] source) {
        int[][] copy = new int[source.length][source.length];
        for (int row = 0; row < source.length; row++) {
            for (int col = 0; col < source.length; col++) {
                copy[row][col] = source[row][col];
            }
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int dim = dimension();
        int r = -1;
        int c = -1;
        outerloop1:
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (tiles[row][col] != 0) {
                    r = row;
                    c = col;
                    break outerloop1;
                }
            }
        }

        int swapR = -1;
        int swapC = -1;

        outerloop2:
        for (int row = dim - 1; row >= 0; row--) {
            for (int col = dim - 1; col >= 0; col--) {
                if (tiles[row][col] != 0) {
                    swapR = row;
                    swapC = col;
                    break outerloop2;
                }
            }
        }

        return swap(r, c, swapR, swapC);
    }

    private static void test4Neighbors() {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(tiles);
        System.out.println("4 Neighbors:");
        System.out.println(board.toString());
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }
    }

    private static void test3Neighbors() {
        int[][] tiles = { { 8, 1, 3 }, { 4, 6, 2 }, { 7, 0, 5 } };
        Board board = new Board(tiles);
        System.out.println("3 Neighbors:");
        System.out.println(board.toString());
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }
    }

    private static void test2Neighbors() {
        int[][] tiles = { { 0, 1, 3 }, { 4, 6, 2 }, { 7, 8, 5 } };
        Board board = new Board(tiles);
        System.out.println("2 Neighbors:");
        System.out.println(board.toString());
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }
    }

    private static void testTwin() {
        int[][] tiles = { { 5, 1, 3 }, { 4, 6, 2 }, { 7, 0, 8 } };
        Board board = new Board(tiles);
        Board twin = board.twin();
        System.out.println("Twins:");
        System.out.println(board.toString());
        System.out.println(twin.toString());
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] otherTiles = { { 8, 5, 3 }, { 4, 0, 2 }, { 7, 6, 1 } };
        Board board = new Board(tiles);
        System.out.println(board.toString());
        System.out.println(
                String.format("dimensions: %d x %d", board.dimension(), board.dimension()));
        System.out.println(String.format("Is goal: %b", board.isGoal()));
        System.out.println(String.format("Hamming distance: %d", board.hamming()));
        System.out.println(String.format("Manhattan distance: %d", board.manhattan()));
        System.out.println(
                String.format("Is equal to same board: %b", board.equals(new Board(tiles))));
        System.out.println(String.format("Is equal to different board: %b",
                                         board.equals(new Board(otherTiles))));


        test4Neighbors();
        test3Neighbors();
        test2Neighbors();
        testTwin();
    }

}
