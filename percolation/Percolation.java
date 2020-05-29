/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private boolean[][] open;

    public Percolation(int n) {
        this.grid = new int[n][n];
        this.open = new boolean[n][n];

        for (int i = 1; i <= n * n; i++) {
            int rowIndex = (i - 1) / n;
            int columnIndex = (i - 1) % n;
            this.grid[rowIndex][columnIndex] = i;
            this.open[rowIndex][columnIndex] = false;
        }

        for (int[] a : this.grid) {
            for (int number : a) {
                System.out.println(number);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.open[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // Percolation p = new Percolation(5);
        Percolation percolation = new Percolation(3);
        
    }
}

