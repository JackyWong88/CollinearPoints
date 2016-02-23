
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

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

    private final LineSegment[] segments;
    private Point[] endpoints;
    private int count;

    public BruteCollinearPoints(Point[] inpoints) {   // finds all line segments containing 4 points
        if (inpoints.length == 0) throw new java.lang.NullPointerException();
        if (inpoints == null) throw new java.lang.NullPointerException();
        Point[] points = copy(inpoints);
        Arrays.sort(points);
        count = 0;
        endpoints = new Point[4];
        for (int p = 0; p < points.length - 1; p++) {
            if (points[p] == null) throw new java.lang.NullPointerException();
            for (int q = p + 1; q < points.length; q++) {
                if (points[q] == null) throw new java.lang.NullPointerException();
                if (points[p].compareTo(points[q]) == 0) throw new java.lang.IllegalArgumentException();
//                StdOut.println(points[p].toString().concat(" and ").concat(points[q].toString()));
                double slope1 = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length; r++) {
                    if (points[r] == null) throw new java.lang.NullPointerException();
                    if (points[q].compareTo(points[r]) == 0) throw new java.lang.IllegalArgumentException();
                    double slope2 = points[p].slopeTo(points[r]);
                    if (slope1 != slope2) continue;
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[s] == null) throw new java.lang.NullPointerException();
                        if (points[r].compareTo(points[s]) == 0) throw new java.lang.IllegalArgumentException();
                        double slope3 = points[p].slopeTo(points[s]);
                        if (slope1 == slope3) {
                            Point[] ps = {points[p], points[q], points[r], points[s]};
                            /*StdOut.println(points[p].toString().concat(",").concat(points[q].toString()).
                            concat(",").concat(points[r].toString()).concat(",").concat(points[s].toString()));*/
                            Point[] ends = ends(ps);
                            boolean addPoint = true;
                            for (int i = 0; i < count; i++) {
                                if (endpoints[2 * i].compareTo(ends[0]) == 0 && endpoints[2 * i + 1].compareTo(ends[1]) == 0) {
                                    addPoint = false;
                                    break;
                                }
                            }
                            if (addPoint) {
                                if (count * 2 == endpoints.length) {
                                    resize(4 * endpoints.length);    // quadruple size of array if necessary
                                }
                                endpoints[2 * count] = ends[0];
                                endpoints[2 * count + 1] = ends[1];
                                //                            StdOut.println(ends[0]);
                                //                            StdOut.println(endpoints[2*count]);
                                //                            StdOut.println(ends[1]);
                                //                            StdOut.println(endpoints[2*count+1]);
                                count++;
                                //                            StdOut.println(count);
                            }
                        }
                    }
                }
            }
        }
        segments = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments[i] = new LineSegment(endpoints[2 * i], endpoints[2 * i + 1]);
        }
    }

    public int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
        return segments.clone();
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= count * 2;
        Point[] temp = new Point[capacity];
        for (int i = 0; i < count * 2; i++) {
            temp[i] = endpoints[i];
        }
        endpoints = temp;
    }
    
    private static Point[] copy(Point[] points) {
        int N = points.length;
        Point[] newpoints = new Point[N];
        for (int i = 0; i < N; i++) {
            newpoints[i] = points[i];
        }
        return newpoints;
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
        } else {
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
