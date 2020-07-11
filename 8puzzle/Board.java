/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class Board {

    private int[][] tiles;

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
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        return Arrays.deepEquals(goal, this.tiles);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
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
        int[][] tiles = { { 1, 2, 3 }, { 5, 4, 6 }, { 7, 8, 0 } };
        Board board = new Board(tiles);
        System.out.println(board.toString());
        System.out.println(String.format("dimensions: %d x %d", board.dimension(), board.dimension()));
        System.out.println(String.format("Is goal: %b", board.isGoal()));
    }

}
