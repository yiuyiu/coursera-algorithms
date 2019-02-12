/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public final class Board {
    private final int[][] nodes;
    private final int dimension;
    private int hammingNum;
    private int manhattanNum;

    public Board(int[][] blocks) {
        this.dimension = blocks.length;
        this.nodes = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int item = blocks[i][j];
                this.nodes[i][j] = item;
                if (item != dimension * i + j + 1 && item != 0) {
                    // hamming
                    hammingNum++;
                    // manhattan
                    int gloalI = (item - 1) / dimension;
                    int globalJ = (item - 1) % dimension;
                    manhattanNum = manhattanNum + Math.abs(gloalI - i) + Math.abs(globalJ - j);
                }
            }
        }
    }
    // construct a board from an n-by-n array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return dimension;
    }
    // board dimension n

    public int hamming() {
        return hammingNum;
    }
    // number of blocks out of place

    public int manhattan() {
        return manhattanNum;
    }
    // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        if (manhattan() == 0) {
            return true;
        }
        return false;
    }
    // is this board the goal board?

    public Board twin() {
        int[][] twins = new int[dimension][dimension];
        int i1 = -1;
        int i2 = -1;
        int j1 = -1;
        int j2 = -1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twins[i][j] = nodes[i][j];
                if (nodes[i][j] != 0) {
                    if (i1 < 0) {
                        i1 = i;
                        j1 = j;
                        continue;
                    }
                    if (i2 < 0) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
        }
        int temp = twins[i1][j1];
        twins[i1][j1] = twins[i2][j2];
        twins[i2][j2] = temp;
        Board twin = new Board(twins);
        return twin;
    }
    // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.nodes[i][j] != that.nodes[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    // does this board equal y?

    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (nodes[i][j] == 0) {
                    if (i > 0) {
                        boards.enqueue(getNeighbourBoard(i, j, i - 1, j));
                    }
                    if (i < dimension - 1) {
                        boards.enqueue(getNeighbourBoard(i, j, i + 1, j));
                    }
                    if (j > 0) {
                        boards.enqueue(getNeighbourBoard(i, j, i, j - 1));
                    }
                    if (j < dimension - 1) {
                        boards.enqueue(getNeighbourBoard(i, j, i, j + 1));
                    }
                    break;
                }
            }
        }
        return boards;
    }

    private Board getNeighbourBoard(int orginI, int originJ, int newI, int newJ) {
        int[][] items = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                items[i][j] = nodes[i][j];
            }
        }
        int temp = items[orginI][originJ];
        items[orginI][originJ] = items[newI][newJ];
        items[newI][newJ] = temp;
        return new Board(items);
    }
    // all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", nodes[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    }
    // unit tests (not graded)

}
