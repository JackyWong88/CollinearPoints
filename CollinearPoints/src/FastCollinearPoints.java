import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
/**
 *
 * @author Jacky
 */
public class FastCollinearPoints {
    private LineSegment[] segments;
    private Point[] endpoints;
    private int count;
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points.length == 0) throw new java.lang.NullPointerException();
        if (points == null) throw new java.lang.NullPointerException();
        int N = points.length;
        for (int i = 0; i < N; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            Comparator<Point> p = points[i].slopeOrder();
            
        }
    }
    public int numberOfSegments(){        // the number of line segments
       return count;
    }
    public LineSegment[] segments() {               // the line segments
       return segments;
    }
    
    private static boolean equal(Comparator p, Point v, Point w){
        return p.compare(v,w) == 0;
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
