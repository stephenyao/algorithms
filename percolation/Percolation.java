import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private boolean[][] open;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF uf;
    private int n;

    private class ArrayIndices {
        int rowIndex;
        int columnIndex;
        int gridSize;

        private ArrayIndices(int rowIndex, int column, int gridSize) {
            this.rowIndex = rowIndex;
            this.columnIndex = column;
            this.gridSize = gridSize;
        }

        private boolean isValid() {
            return this.rowIndex >= 0
                    && this.columnIndex >= 0
                    && this.rowIndex < gridSize
                    && this.columnIndex < gridSize;
        }
    }

    public Percolation(int n) {
        this.grid = new int[n][n];
        this.open = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n);
        this.n = n;

        for (int i = 1; i <= n * n; i++) {
            int value = i - 1;
            int rowIndex = (i - 1) / n;
            int columnIndex = (i - 1) % n;
            this.grid[rowIndex][columnIndex] = value;
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
        int rowIndex = row - 1;
        int colIndex = col - 1;
        this.open[rowIndex][colIndex] = true;
        this.open[rowIndex][colIndex] = true;
        this.numberOfOpenSites++;

        ArrayIndices[] indices = new ArrayIndices[] {
                new ArrayIndices(rowIndex - 1, colIndex, this.n),
                new ArrayIndices(rowIndex, colIndex - 1, this.n),
                new ArrayIndices(rowIndex, colIndex + 1, this.n),
                new ArrayIndices(rowIndex + 1, colIndex, this.n)
        };

        for (ArrayIndices index : indices) {
            if (index.isValid() && this.open[index.rowIndex][index.columnIndex]) {
                int first = this.grid[rowIndex][colIndex];
                int second = this.grid[index.rowIndex][index.columnIndex];

                System.out.println(String.format("union first: %d, second %d", first, second));

                this.uf.union(this.grid[rowIndex][colIndex],
                              this.grid[index.rowIndex][index.columnIndex]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        int objectIdentifier = this.grid[rowIndex][colIndex];
        for (int i = 0; i < n; i++) {
            if (this.open[0][i]) {
                int topIdentifier = this.grid[0][i];
                if (this.uf.connected(objectIdentifier, topIdentifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);

    }
}

