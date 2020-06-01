import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private boolean[][] open;
    private boolean[][] connectedToTop;
    private boolean[][] connectedToBottom;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF uf;
    private int n;
    private boolean percolates = false;

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
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.grid = new int[n][n];
        this.open = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n);
        this.connectedToTop = new boolean[n][n];
        this.connectedToBottom = new boolean[n][n];
        this.n = n;

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
        validate(row, col);
        int rowIndex = row - 1;
        int colIndex = col - 1;
        boolean connectedToTop = false;
        boolean connectedToBottom = false;
        int objectIdentifier = this.grid[rowIndex][colIndex];

        if (this.open[rowIndex][colIndex]) {
            return;
        }

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
                int opened = this.grid[rowIndex][colIndex];
                int adjacent = this.grid[index.rowIndex][index.columnIndex];
                int rootOfAdjacent = this.uf.find(adjacent);
                int adjacentRootRowIndex = rootOfAdjacent / n;
                int adjacentRootColumnIndex = rootOfAdjacent % n;
                this.uf.union(opened, adjacent);
                if (this.connectedToTop[adjacentRootRowIndex][adjacentRootColumnIndex]) {
                    connectedToTop = true;
                }

                if (this.connectedToBottom[adjacentRootRowIndex][adjacentRootColumnIndex]) {
                    connectedToBottom = true;
                }

            }
        }

        if (row == 1) {
            connectedToTop = true;
        }

        if (row == this.n) {
            connectedToBottom = true;
        }

        int root = uf.find(objectIdentifier);
        int rootRowIndex = root / n;
        int rootColumnIndex = root % n;

        if (connectedToTop) {
            // this.connectedToTop[rowIndex][colIndex] = true;
            this.connectedToTop[rootRowIndex][rootColumnIndex] = true;
        }

        if (connectedToBottom) {
            // this.connectedToBottom[rowIndex][colIndex] = true;
            this.connectedToBottom[rootRowIndex][rootColumnIndex] = true;
        }

        if (connectedToTop && connectedToBottom) {
            this.percolates = true;
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int rowIndex = row - 1;
        int colIndex = col - 1;
        return this.open[rowIndex][colIndex];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int rowIndex = row - 1;
        int colIndex = col - 1;
        int objectIdentifier = grid[rowIndex][colIndex];
        int root = uf.find(objectIdentifier);
        int rootRowIndex = root / n;
        int rootColumnIndex = root % n;
        return this.connectedToTop[rootRowIndex][rootColumnIndex];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.percolates;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row or col is outside of bounds");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);

    }
}

