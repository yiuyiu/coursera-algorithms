import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
    private int N;
    private Node root;
    private double minDistance;
    private Point2D nearestPoint;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        private Node(Point2D point) {
            this.p = point;
        }
    }


    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // number of points in the set
    public int size() {
        return N;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1);
            N++;
            return;
        }
        put(root, p, 1);
    }


    private void put(Node node, Point2D p, int level) {
        if (isEven(level)) {
            if (p.y() < node.p.y()) {
                if (node.lb == null) {
                    Node newNode = new Node(p);
                    newNode.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                    node.lb = newNode;
                    N++;
                } else {
                    node = node.lb;
                    put(node, p, level + 1);
                }
            } else {
                if (node.p.equals(p)) {
                    return;
                }
                if (node.rt == null) {
                    Node newNode = new Node(p);
                    newNode.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                    node.rt = newNode;
                    N++;
                } else {
                    node = node.rt;
                    put(node, p, level + 1);
                }
            }
        } else {
            if (p.x() < node.p.x()) {
                if (node.lb == null) {
                    Node newNode = new Node(p);
                    newNode.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                    node.lb = newNode;
                    N++;
                } else {
                    node = node.lb;
                    put(node, p, level + 1);
                }

            } else {
                if (node.p.equals(p)) {
                    return;
                }
                if (node.rt == null) {
                    Node newNode = new Node(p);
                    newNode.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                    node.rt = newNode;
                    N++;
                } else {
                    node = node.rt;
                    put(node, p, level + 1);
                }

            }
        }
    }

    private boolean isEven(int i) {
        return i % 2 == 0;
    }

    private boolean get(Node node, Point2D p, int level) {
        if (isEven(level)) {
            if (p.y() < node.p.y()) {
                if (node.lb == null) {
                    return false;
                } else {
                    node = node.lb;
                    return get(node, p, level + 1);
                }
            } else {
                if (p.equals(node.p)) {
                    return true;
                }
                if (node.rt == null) {
                    return false;
                } else {
                    node = node.rt;
                    return get(node, p, level + 1);
                }
            }
        } else {
            if (p.x() < node.p.x()) {
                if (node.lb == null) {
                    return false;
                } else {
                    node = node.lb;
                    return get(node, p, level + 1);
                }

            } else {
                if (p.equals(node.p)) {
                    return true;
                }
                if (node.rt == null) {
                    return false;
                } else {
                    node = node.rt;
                    return get(node, p, level + 1);
                }

            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        return get(root, p, 1);
    }

    // draw all points to standard draw
    public void draw() {
        drawPoints(root);
    }

    private void drawPoints(Node node) {
        if (node == null) {
            return;
        }
        node.rect.draw();
        drawPoints(node.lb);
        drawPoints(node.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Stack<Point2D> a = new Stack<Point2D>();
        addPoint(root, a, rect);
        return a;
    }

    private void addPoint(Node node, Stack<Point2D> a, RectHV intersect) {
        if (node == null) {
            return;
        }
        if (node.rect.intersects(intersect)) {
            if (intersect.contains(node.p)) {
                a.push(node.p);
            }
            addPoint(node.lb, a, intersect);
            addPoint(node.rt, a, intersect);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        minDistance = root.p.distanceTo(p);
        nearestPoint = root.p;
        findClosest(root, p);
        return nearestPoint;
    }

    private void findClosest(Node node, Point2D target) {
        if (node == null) {
            return;
        }
        if (node.rect.distanceTo(target) <= minDistance) {
            if (node.p.distanceTo(target) <= minDistance) {
                minDistance = node.p.distanceTo(target);
                nearestPoint = node.p;
            }
        } else {
            return;
        }
        findClosest(node.lb, target);
        findClosest(node.rt, target);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);
        KdTree xixi = new KdTree();
        xixi.insert(a);
        xixi.insert(b);
        xixi.insert(c);
        xixi.insert(d);
        xixi.insert(e);
        if (xixi.contains(e)) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
        System.out.println(xixi.size());
    }
}
