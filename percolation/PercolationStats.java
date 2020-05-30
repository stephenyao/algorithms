/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials must be greater than 0");
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return 0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Percolation stats must have 2 inputs, n and T");
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        StdDraw.enableDoubleBuffering();
        Percolation p = new Percolation(n);
        PercolationVisualizer.draw(p, n);
        StdDraw.show();

        while (!p.percolates()) {
            // try {
            //     Thread.sleep(20);
            // }
            // catch (InterruptedException ex) {
            //     Thread.currentThread().interrupt();
            // }
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            p.open(randomRow, randomCol);
            // draw n-by-n percolation system
            PercolationVisualizer.draw(p, n);
            StdDraw.show();
            System.out.println(String.format("number of open sites: %d", p.numberOfOpenSites()));
        }

        int openSites = p.numberOfOpenSites();
        int totalSites = n * n;
        double ratio = (double) openSites / (double) totalSites;
        System.out.println(ratio);
        System.out.println(
                String.format("open sites: %d total sites: %d ratio %f", openSites, totalSites,
                              ratio));

    }
}
