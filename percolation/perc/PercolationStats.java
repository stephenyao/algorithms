package perc;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double mean = 0;
    private double stddev = 0;
    private double lo = 0;
    private double hi = 0;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials must be greater than 0");
        }

        double[] ratios = new double[trials];

        for (int i = 0; i < trials; i++) {
            // StdDraw.enableDoubleBuffering();
            Percolation p = new Percolation(n);
            // perc.PercolationVisualizer.draw(p, n);
            // StdDraw.show();

            while (!p.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                p.open(randomRow, randomCol);
                // draw n-by-n percolation system
                // perc.PercolationVisualizer.draw(p, n);
                // StdDraw.show();
            }

            int openSites = p.numberOfOpenSites();
            int totalSites = n * n;
            double ratio = (double) openSites / (double) totalSites;
            ratios[i] = ratio;
        }

        double mean = StdStats.mean(ratios);
        double stdDeviation = StdStats.stddev(ratios);
        double loConfidence = mean - 1.96 * stdDeviation / Math.sqrt(trials);
        double hiConfidence = mean + 1.96 * stdDeviation / Math.sqrt(trials);

        this.mean = mean;
        this.stddev = stdDeviation;
        this.lo = loConfidence;
        this.hi = hiConfidence;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return hi;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "perc.Percolation stats must have 2 inputs, n and T");
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, T);

        System.out.println(String.format("mean = %.20f", stats.mean()));
        System.out.println(String.format("stddev = %.20f", stats.stddev()));
        System.out.println(
                String.format("95%% confidence interval = [%.20f %.20f]", stats.confidenceLo(),
                              stats.confidenceHi()));
    }
}
