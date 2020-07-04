import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

public class BruteCollinearPoints {

    private final Point[] points;
    private int numberOfSegments;
    private LineSegment[] segments = null;

    public BruteCollinearPoints(Point[] points) {
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
    }  // the number of line segments

    public LineSegment[] segments() {
        if (this.segments != null) {
            return this.segments.clone();
        }

        ArrayList<LineSegment> list = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Comparator<Point> c = points[i].slopeOrder();
                        if ((c.compare(points[j], points[k]) == 0) && (
                                c.compare(points[j], points[l]) == 0)) {
                            Point[] pointsOnSegment = {
                                    points[i], points[j], points[k], points[l]
                            };
                            Insertion.sort(pointsOnSegment);
                            LineSegment segment = new LineSegment(pointsOnSegment[0],
                                                                  pointsOnSegment[3]);
                            list.add(segment);
                        }
                    }
                }
            }
        }

        this.numberOfSegments = list.size();
        this.segments = list.toArray(new LineSegment[list.size()]);
        return this.segments.clone();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
