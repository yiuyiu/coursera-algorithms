/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public final class Solver {
    private int moves;
    private SearchNode solution;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        // long startTime = System.currentTimeMillis();
        Board twin = initial.twin();
        MinPQ<SearchNode> pqInit = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        pqInit.insert(new SearchNode(initial, null, 0));
        pqTwin.insert(new SearchNode(twin, null, 0));
        SearchNode snInit = pqInit.delMin();
        SearchNode snTwin = pqTwin.delMin();
        while (true) {
            if (snInit.board.isGoal()) {
                solution = snInit;
                solvable = true;
                break;
            }
            if (snTwin.board.isGoal()) {
                solution = snTwin;
                solvable = false;
                break;
            }
            int moveInit = snInit.priority - snInit.board.manhattan();
            for (Board bd : snInit.board.neighbors()) {
                if (snInit.predecessor != null && bd.equals(snInit.predecessor.board)) {
                    continue;
                }
                pqInit.insert(new SearchNode(bd, snInit, moveInit + 1));
            }
            int moveTwin = snTwin.priority - snTwin.board.manhattan();
            for (Board bd : snTwin.board.neighbors()) {
                if (snTwin.predecessor != null && bd.equals(snTwin.predecessor.board)) {
                    continue;
                }
                pqTwin.insert(new SearchNode(bd, snTwin, moveTwin + 1));
            }
            moves++;
            snInit = pqInit.delMin();
            snTwin = pqTwin.delMin();
        }
        // long endTime = System.currentTimeMillis();
        // float excTime = (float) (endTime - startTime) / 1000;
        // System.out.println("执行时间：" + excTime + "s");
    }
    // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return solvable;
    }
    // is the initial board solvable?

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode predecessor;
        private int priority;

        public SearchNode(Board board, SearchNode predecessor, int moves) {
            this.board = board;
            this.predecessor = predecessor;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode sn) {
            return this.priority - sn.priority;
        }
    }

    public int moves() {
        if (!solvable) {
            return -1;
        }
        int move = 0;
        SearchNode temp = solution;
        while (temp.predecessor != null) {
            temp = temp.predecessor;
            move++;
        }
        return move;
    }
    // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }
        Stack<Board> boards = new Stack<Board>();
        SearchNode temp = solution;
        while (temp != null) {
            boards.push(temp.board);
            temp = temp.predecessor;
        }
        return boards;
    }
    // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {

    }
    // solve a slider puzzle (given below)
}
