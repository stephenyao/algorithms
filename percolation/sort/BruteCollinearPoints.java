import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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

    Point[] points;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;
    }

    public int numberOfSegments() {
        return 0;
    }  // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[2];
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Comparator c = points[i].slopeOrder();
                        if ((c.compare(points[j], points[k]) == 0) && (
                                c.compare(points[j], points[l]) == 0)) {
                            Point[] pointsOnSegment = {
                                    points[i], points[j], points[k], points[l]
                            };
                            Insertion.sort(pointsOnSegment);
                            LineSegment segment = new LineSegment(pointsOnSegment[0],
                                                                  pointsOnSegment[3]);
                            segments[i] = segment;
                            c.compare(points[j], points[l]);
                        }
                    }
                }
            }
        }

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
