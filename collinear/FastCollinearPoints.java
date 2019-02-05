import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class FastCollinearPoints {
    private Item[] items = new Item[2];
    private int n;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        if (points.length < 4) {
            return;
        }
        // copy
        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            aux[i] = points[i];
        }
        // iterate
        for (int i = 0; i < points.length; i++) {
            Point basePoint = points[i];
            Arrays.sort(aux, basePoint.slopeOrder());
            // self point slope is negative infinity
            int pointerStart = 1;
            int pointerEnd = 1;
            int highestPoint = 0;
            int lowestPoint = 0;
            double baseSlope = basePoint.slopeTo(aux[1]);
            for (int j = 2; j < aux.length; j++) {
                double slope = basePoint.slopeTo(aux[j]);
                if (slope == baseSlope) {
                    if (aux[j].compareTo(aux[highestPoint]) > 0) {
                        highestPoint = j;
                    }
                    if (aux[j].compareTo(aux[lowestPoint]) < 0) {
                        lowestPoint = j;
                    }
                    // repeat
                    if (aux[j].compareTo(aux[0]) == 0) {
                        throw new IllegalArgumentException();
                    }
                    pointerEnd = j;
                    if (j == aux.length - 1) {
                        if (pointerEnd - pointerStart >= 2) {
                            insertValid(aux[lowestPoint], aux[highestPoint]);
                        }
                    }
                }
                else {
                    if (pointerEnd - pointerStart >= 2) {
                        insertValid(aux[lowestPoint], aux[highestPoint]);
                    }
                    baseSlope = slope;
                    pointerStart = j;
                    pointerEnd = j;
                    highestPoint = j;
                    lowestPoint = j;
                }
            }
        }

    }

    private class Item {
        Point start;
        Point end;
        double slope;

        public Item(Point start, Point end, double slope) {
            this.start = start;
            this.end = end;
            this.slope = slope;
        }
    }

    private void insertValid(Point lowestPoint, Point highestPoint) {
        double slope = lowestPoint.slopeTo(highestPoint);
        Item item = new Item(lowestPoint, highestPoint, slope);
        if (n == items.length) {
            resize(n * 2);
        }
        for (int i = 0; i < n; i++) {
            if (items[i].slope == slope) {
                // 平行线段还是在一条直线上
                Point start = items[i].start;
                Point end = items[i].end;
                if (start.compareTo(lowestPoint) == 0 || end.compareTo(highestPoint) == 0 ||
                        Math.abs(start.slopeTo(lowestPoint)) == Math.abs(slope)) {
                    if (lowestPoint.compareTo(items[i].start) < 0) {
                        items[i].start = lowestPoint;
                    }
                    if (highestPoint.compareTo(items[i].end) > 0) {
                        items[i].end = highestPoint;
                    }
                    return;
                }
            }
        }
        items[n++] = item;
    }

    private void resize(int len) {
        Item[] temp = new Item[len];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return n;
    }
    // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            temp[i] = new LineSegment(items[i].start, items[i].end);
        }
        return temp;
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


        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment[] ls = fcp.segments();
        for (int i = 0; i < ls.length; i++) {
            ls[i].draw();
        }
        StdDraw.show();
    }
}
