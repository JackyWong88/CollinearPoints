
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacky
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int count;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        count = 0;
        segments = new LineSegment[1];
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                if (points[p].compareTo(points[q]) == 0) throw new java.lang.IllegalArgumentException();
                double slope1 = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length; r++) {
                    if (points[q].compareTo(points[r]) == 0) throw new java.lang.IllegalArgumentException();
                    double slope2 = points[p].slopeTo(points[r]);
                    if (slope1 != slope2) continue;
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[r].compareTo(points[s]) == 0) throw new java.lang.IllegalArgumentException();
                        double slope3 = points[p].slopeTo(points[s]);
                        if(slope1 != slope3) continue;
                        else {
                            Point[] ps = {points[p],points[q],points[r],points[s]};
                            Point[] endpoints = ends(ps);
                            if (count == segments.length) {
                                resize(2 * segments.length);    // double size of array if necessary
                            }
                            segments[count++] = new LineSegment(endpoints[0],endpoints[1]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
        return segments;
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= count;
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < count; i++) {
            temp[i] = segments[i];
        }
        segments = temp;
    }
    
    private Point[] ends(Point[] ps) {
        Point high1, high2, low1, low2, low, high;
        if (ps.length != 4) throw new java.lang.IllegalArgumentException();
        if (ps[0].compareTo(ps[1]) == 1) {
            high1 = ps[0];
            low1 = ps[1];
        } else {
            high1 = ps[1];
            low1 = ps[0];
        }
        if (ps[2].compareTo(ps[3]) == 1) {
            high2 = ps[2];
            low2 = ps[3];
        }  else {
            high2 = ps[3];
            low2 = ps[2];
        }
        if (low1.compareTo(low2) == 1) {
            low = low2;
        } else {
            low = low1;
        }
        if (high1.compareTo(high2) == 1) {
            high = high1;
        } else {
            high = high2;
        }
        Point[] ends = {low, high};
        return ends;
    }
    
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
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
    }
}
