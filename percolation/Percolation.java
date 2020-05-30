import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private boolean[][] open;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF uf;
    private int n;
    private int virtualTopSite;
    private int virtualBotSite;

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
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        this.virtualTopSite = n * n;
        this.virtualBotSite = n * n + 1;

        for (int i = 0; i < n * n; i++) {
            int value = i;
            int rowIndex = i / n;
            int columnIndex = i % n;
            this.grid[rowIndex][columnIndex] = value;
            this.open[rowIndex][columnIndex] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;

        if (this.open[rowIndex][colIndex]) {
            return;
        }

        this.open[rowIndex][colIndex] = true;
        this.numberOfOpenSites++;

        if (rowIndex == 0) {
            this.uf.union(this.grid[rowIndex][colIndex], virtualTopSite);
        }

        if (rowIndex == n - 1) {
            this.uf.union(this.grid[rowIndex][colIndex], virtualBotSite);
        }

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
        return this.uf.connected(objectIdentifier, virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(virtualTopSite, virtualBotSite);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);

    }
}

