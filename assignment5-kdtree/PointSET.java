import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class PointSET {
    private final Set<Point2D> points;

    /** construct an empty set of points */
    public PointSET() {
        this.points = new TreeSet<>();
    }

    /** is the set empty? */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /** number of points in the set  */
    public int size() {
        return points.size();
    }

    /** add the point to the set (if it is not already in the set) */
    public void insert(Point2D p) {
        points.add(p);
    }

    /** does the set contain point p? */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /** draw all points to standard draw */
    public void draw() {
        points.forEach(Point2D::draw);
    }

    /** all points that are inside the rectangle (or on the boundary) */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> insidePoints = new ArrayList<>();
        for (Point2D point : points) {
            if (point.x() >= rect.xmin() && point.x() <= rect.xmax() &&
                point.y() >= rect.ymin() && point.y() <= rect.ymax()) {
                insidePoints.add(point);
            }
        }
        return insidePoints;
    }

    /** a nearest neighbor in the set to point p; null if the set is empty */
    public Point2D nearest(Point2D p) {
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D closestPoint = null;
        for (Point2D point : points) {
            if (p.distanceSquaredTo(point) < minDistance) {
                minDistance = p.distanceSquaredTo(point);
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    /** unit testing of the methods (optional) */
    public static void main(String[] args) {
    }
}
