import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node2d root;
    private int size;

    /** construct an empty set of points */
    public KdTree() {
        root = null;
        size = 0;
    }

    /** is the set empty? */
    public boolean isEmpty() {
        return root == null;
    }

    /** number of points in the set  */
    public int size() {
        return size;
    }

    /** add the point to the set (if it is not already in the set) */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        if (root == null || !contains(p)) {
            size++;
            root = insert(root, p, true);
        }
    }


    private Node2d insert(Node2d node, Point2D p, boolean horizontalSplit) {
        if (node == null) {
            return new Node2d(p, null, null, horizontalSplit);
        }

        if (node.horizontalSplit) {
            if (p.x() < node.point.x()) {
                node.left = insert(node.left, p, !horizontalSplit);
            } else {
                node.right = insert(node.right, p, !horizontalSplit);
            }
        } else {
            if (p.y() < node.point.y()) {
                node.left = insert(node.left, p, !horizontalSplit);
            } else {
                node.right = insert(node.right, p, !horizontalSplit);
            }
        }

        return node;
    }

    /** does the set contain point p? */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        return contains(root, p);
    }

    private boolean contains(Node2d node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (p.equals(node.point)) {
            return true;
        }
        if (node.horizontalSplit && p.x() < node.point.x() ||
            !node.horizontalSplit && p.y() < node.point.y()) {
            return contains(node.left, p);
        } else {
            return contains(node.right, p);
        }
    }

    /** draw all points to standard draw */
    public void draw() {
        if (!isEmpty()) {
            root.draw();
        }
    }

    /** all points that are inside the rectangle (or on the boundary) */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect can't be null");
        }
        List<Point2D> inRangePoints = new ArrayList<>();
        if (!isEmpty()) {
            range(root, rect, inRangePoints);
        }
        return inRangePoints;
    }

    private void range(Node2d currentNode, RectHV rect, List<Point2D> inRangePoints) {
        if (currentNode == null) {
            return;
        }

        final boolean both;

        Point2D p = currentNode.point;
        if (rect.contains(p)) {
            // rect contains point
            inRangePoints.add(p);
            both = true;
        } else if (currentNode.horizontalSplit && rect.intersects(new RectHV(currentNode.point.x(), 0.0, currentNode.point.x(), 1.0)) ||
            !currentNode.horizontalSplit && rect.intersects(new RectHV(0.0, currentNode.point.y(), 1.0, currentNode.point.y()))) {
            // rect intersects with current node
            both = true;
        } else {
            both = false;
        }


        if (both) {
            range(currentNode.left, rect, inRangePoints);
            range(currentNode.right, rect, inRangePoints);
        } else {
            if ((currentNode.horizontalSplit && p.x() > rect.xmin()) || (!currentNode.horizontalSplit && p.y() > rect.ymin())) {
                range(currentNode.left, rect, inRangePoints);
            } else {
                range(currentNode.right, rect, inRangePoints);
            }
        }
    }

    /** a nearest neighbor in the set to point p; null if the set is empty */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point can't be null");
        }
        if (root == null) {
            return null;
        }

        return nearest(root, p, root).point;
    }

    private Node2d nearest(Node2d current, Point2D p, Node2d nearest) {
        if (current == null || nearest.point.distanceSquaredTo(p) < current.point.distanceSquaredTo(p)) {
            return nearest;
        }

        if (current.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = current;
        }

        Node2d left = nearest(current.left, p, nearest);
        if (left.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = left;
        }

        Node2d right = nearest(current.right, p, nearest);
        if (right.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = right;
        }

        return nearest;
    }

    private void print() {
        root.print(0);
    }

    private static class Node2d {
        private final Point2D point;
        private final boolean horizontalSplit;
        private Node2d left;
        private Node2d right;

        private Node2d(Point2D point, Node2d left, Node2d right, boolean horizontalSplit) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.horizontalSplit = horizontalSplit;
        }

        private void draw() {
            if (left != null) {
                left.draw();
            }
            point.draw();
            if (right != null) {
                right.draw();
            }
        }

        private void print(int level) {
            System.out.println(point + " level: " + level + " horizontalSplit: " + horizontalSplit);
            if (left != null) {
                System.out.print("  left of" + point + ": ");
                left.print(level + 1);
            }
            if (right != null) {
                System.out.print("  right of" + point + ": ");
                right.print(level + 1);
            }
        }
    }

    /** unit testing of the methods (optional) */
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.print();
    }
}
