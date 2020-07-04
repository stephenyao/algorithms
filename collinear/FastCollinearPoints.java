/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class FastCollinearPoints {

    private final Point[] points;
    private int numberOfSegments;
    private LineSegment[] segments = null;

    public FastCollinearPoints(Point[] points) {
        this.points = sanitisePoints(points);
        this.segments();
    }

    private static Point[] sanitisePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            copy[i] = points[i];
        }

        MergeX.sort(copy);

        Point previous = null;
        for (Point p : copy) {
            if (previous == null) {
                previous = p;
                continue;
            }

            if (previous.compareTo(p) == 0) throw new IllegalArgumentException();
            previous = p;
        }

        return copy;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        if (this.segments != null) {
            return this.segments.clone();
        }

        ArrayList<LineSegment> list = new ArrayList<LineSegment>();
        Point[] copy = new Point[points.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = points[i];
        }

        for (int k = 0; k < points.length; k++) {
            Point origin = points[k];
            MergeX.sort(copy);
            MergeX.sort(copy, origin.slopeOrder());

            double slope = Double.NEGATIVE_INFINITY;
            int slopeCount = 1;
            boolean slopeChanged;
            for (int i = 1; i < copy.length; i++) {
                double newSlope = origin.slopeTo(copy[i]);
                slopeChanged = slope != newSlope;
                if (newSlope == slope) {
                    slopeCount++;
                }

                if (slopeChanged || i == copy.length - 1) {
                    int lastIndex = (slopeChanged) ? i - 1 : i;
                    int firstIndex = (slopeChanged) ? i - slopeCount : i - slopeCount + 1;
                    if (slopeCount >= 3) {
                        Point first = copy[firstIndex];
                        Point last = copy[lastIndex];

                        if (!(origin.compareTo(first) < 0)) {
                            slopeCount = 1;
                            slope = newSlope;
                            continue;
                        }
                        else {
                            list.add(new LineSegment(origin, last));
                        }
                    }
                    slopeCount = 1;
                }

                slope = newSlope;
            }
        }

        LineSegment[] segments = list.toArray(new LineSegment[list.size()]);
        this.numberOfSegments = segments.length;
        this.segments = segments;
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        sanitisePoints(points);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        LineSegment[] segments = collinear.segments();
        segments[0] = null;

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

}
