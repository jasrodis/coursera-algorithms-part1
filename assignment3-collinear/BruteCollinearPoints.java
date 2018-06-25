import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;

public class BruteCollinearPoints {

    private final LineSegment[] segments;
    private int segmentsSize = 0;

    /** finds all line segments containing 4 points */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points can't be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("point can't be null");
            }
        }

        Point[] p = new Point[points.length];
        System.arraycopy(points, 0, p, 0, points.length);
        segments = analyzeSegments(p);
    }

    private LineSegment[] analyzeSegments(Point[] points) {
        LineSegment[] tmpSegments = new LineSegment[points.length * 4];
        MergeX.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];
                        if (Double.compare(p.slopeTo(q), p.slopeTo(r)) == 0 && Double.compare(p.slopeTo(q), p.slopeTo(s)) == 0) {
                            tmpSegments[segmentsSize++] = new LineSegment(p, s);
                        }
                    }
                }
            }
        }
        LineSegment[] resultSegments = new LineSegment[segmentsSize];
        for (int i = 0; i < segmentsSize; i++) {
            resultSegments[i] = tmpSegments[i];
        }
        return resultSegments;
    }

    /** the number of line segments */
    public int numberOfSegments() {
        return segmentsSize;
    }

    /** the line segments */
    public LineSegment[] segments() {
        LineSegment[] returnSegments = new LineSegment[segments.length];
        System.arraycopy(segments, 0, returnSegments, 0, segments.length);
        return returnSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            String s = in.readString();
            if ("null".equals(s)) {
                points[i] = null;
            } else {
                int x = Integer.parseInt(s);
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
        }

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
