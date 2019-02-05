/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private LineSegment[] lss = new LineSegment[2];
    private int n;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int len = points.length;
        for (int a = 0; a < len; a++) {
            // 测试输入会有points小于4的情况，并且里面会有某个部分为Null
            Point P1 = points[a];
            if (P1 == null) {
                throw new IllegalArgumentException();
            }
            for (int b = a + 1; b < len; b++) {
                Point P2 = points[b];
                if (P2 == null) {
                    throw new IllegalArgumentException();
                }
                if (P1.compareTo(P2) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int c = b + 1; c < len; c++) {
                    Point P3 = points[c];
                    if (P3 == null) {
                        throw new IllegalArgumentException();
                    }
                    if (P1.compareTo(P3) == 0 || P2.compareTo(P3) == 0) {
                        throw new IllegalArgumentException();
                    }
                    for (int d = c + 1; d < len; d++) {
                        Point P4 = points[d];
                        if (P4 == null) {
                            throw new IllegalArgumentException();
                        }
                        // check repeated
                        if (P1.compareTo(P4) == 0 || P2.compareTo(P4) == 0
                                || P3.compareTo(P4) == 0) {
                            throw new IllegalArgumentException();
                        }
                        Point[] temp = new Point[] {
                                P1, P2, P3, P4
                        };

                        sort(temp);
                        Point p1 = temp[0];
                        Point p2 = temp[1];
                        Point p3 = temp[2];
                        Point p4 = temp[3];
                        double p1p2 = p1.slopeTo(p2);
                        double p1p3 = p1.slopeTo(p3);
                        if (p1p2 != p1p3) {
                            continue;
                        }
                        double p1p4 = p1.slopeTo(p4);
                        if (p1p3 == p1p4) {
                            LineSegment ls = new LineSegment(p1, p4);
                            if (n == lss.length) {
                                resize(n * 2);
                            }
                            lss[n++] = ls;
                        }
                    }
                }
            }
        }

    }


    private void resize(int len) {
        LineSegment[] ls = new LineSegment[len];
        for (int i = 0; i < n; i++) {
            ls[i] = lss[i];
        }
        lss = ls;
    }

    private void swap(Point[] points, int i, int j) {
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    // selection sort
    private void sort(Point[] points) {
        int len = points.length;
        int pointer = 0;
        while (pointer < len) {
            Point minP = points[pointer];
            for (int i = pointer + 1; i < len; i++) {
                if (points[i].compareTo(minP) < 0) {
                    swap(points, pointer, i);
                    minP = points[pointer];
                    continue;
                }
            }
            pointer++;
        }
    }
    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return n;
    }
    // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            ls[i] = lss[i];
        }
        return ls;
    }
    // the line segments

    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // draw the points
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        LineSegment[] ls = bcp.segments();
        System.out.println(ls.length);
        for (int i = 0; i < ls.length; i++) {
            ls[i].draw();
            System.out.println(ls[i]);
        }
        StdDraw.show();
    }
}
