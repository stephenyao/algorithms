/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if ((this.x - that.x == 0) && (this.y - that.y == 0)) return Double.NEGATIVE_INFINITY;
        if (this.x - that.x == 0) return Double.POSITIVE_INFINITY;
        if (this.y - that.y == 0) return 0;
        double gradient = (double) (that.y - this.y) / (double) (that.x - this.x);
        return gradient;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int yResult = compareTo(this.y, that.y);
        if (yResult == 0) return compareTo(this.x, that.x);
        else return yResult;
    }

    private int compareTo(int first, int second) {
        if (first > second) return 1;
        else if (first < second) return -1;
        else return 0;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeComparator(this);
    }

    private static class SlopeComparator implements Comparator<Point> {

        Point reference;

        public SlopeComparator(Point reference) {
            this.reference = reference;
        }

        public int compare(Point x, Point y) {
            double diff = (reference.slopeTo(x) - reference.slopeTo(y));
            if (diff < 0) return -1;
            else if (diff > 0) return 1;
            else return 0;
        }
    }

    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        pointCompareTest();
        pointSlopeTest();
        slopeOrderTest();
    }

    private static void slopeOrderTest() {
        Point inf1 = new Point(2, 5);
        Point inf2 = new Point(2, 2);
        Point inf3 = new Point(2, 10);

        Comparator<Point> c = inf1.slopeOrder();
        System.out.println(
                String.format("inf1 slope order: %d should equal 0", c.compare(inf2, inf3)));
    }

    private static void pointSlopeTest() {
        // System.out.println();
        Point pos1 = new Point(3, 5);
        Point pos2 = new Point(2, 2);
        System.out.println(String.format("pos1.slope(pos2) = %.2f", pos1.slopeTo(pos2)));

        Point neg1 = new Point(3, 5);
        Point neg2 = new Point(4, 2);
        System.out.println(String.format("neg1.slope(neg2) = %.2f", neg1.slopeTo(neg2)));

        Point inf1 = new Point(2, 5);
        Point inf2 = new Point(2, 2);
        System.out.println(String.format("inf1.slope(inf2) = %.2f", inf1.slopeTo(inf2)));

        Point zero1 = new Point(3, 5);
        Point zero2 = new Point(2, 5);
        System.out.println(String.format("zero1.slope(zero2) = %.2f", zero1.slopeTo(zero2)));

        Point negInf1 = new Point(2, 2);
        Point negInf2 = new Point(2, 2);
        System.out
                .println(String.format("negInf1.slope(negInf2) = %.2f", negInf1.slopeTo(negInf2)));
    }

    private static void pointCompareTest() {
        Point lower = new Point(1, 2);
        Point higher = new Point(2, 2);
        System.out.println(String.format("lower.compare(higher) = %d", lower.compareTo(higher)));

        Point eq1 = new Point(2, 2);
        Point eq2 = new Point(2, 2);
        System.out.println(String.format("eq1.compare(eq2) = %d", eq1.compareTo(eq2)));

        Point eqY1 = new Point(3, 1);
        Point eqY2 = new Point(2, 1);
        System.out.println(String.format("eqY1.compare(eqY2) = %d", eqY1.compareTo(eqY2)));
    }
}
