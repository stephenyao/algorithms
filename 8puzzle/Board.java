/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class Board {

    private int[][] tiles;
    private final int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d\n", this.tiles.length));
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                // System.out.print(String.format("%d", tiles[row][column]));
                builder.append(String.format("%2d", tiles[row][column]));
            }
            if (row != tiles.length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                int goal = this.goal[row][column];
                int tile = this.tiles[row][column];
                if (goal != 0 && goal != tile) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                int tile = tiles[row][column];
                if (tile != 0) {
                    int goalRow = (tile - 1) / 3;
                    int goalColumn = (tile - 1) % 3;
                    int rowDist = goalRow - row;
                    int columnDist = goalColumn - column;
                    int manhattanDist = Math.abs(rowDist) + Math.abs(columnDist);
                    manhattan += manhattanDist;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(goal, this.tiles);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board board = (Board) y;
        if (board == null) {
            return false;
        }

        return Arrays.deepEquals(board.tiles, this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
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
        System.out.println(
                String.format("Is equal to different board: %b",
                              board.equals(new Board(otherTiles))));
        
    }

}
