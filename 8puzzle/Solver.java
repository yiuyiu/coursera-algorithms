/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Board initial;
    private Board twin;
    private SearchNode solution;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        this.twin = initial.twin();
        MinPQ<SearchNode> pq;
        pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, null, 0));
        pq.insert(new SearchNode(this.twin, null, 0));
        SearchNode sn = pq.delMin();
        // int insertNum = 0;
        // int delMinNum = 0;
        while (!sn.board.isGoal()) {
            int move = sn.priority - sn.board.manhattan();
            for (Board bd : sn.board.neighbors()) {
                if (sn.predecessor != null && bd.equals(sn.predecessor.board)) {
                    continue;
                }
                pq.insert(new SearchNode(bd, sn, move + 1));
                // insertNum++;
            }
            sn = pq.delMin();
            // delMinNum++;
        }
        solution = sn;
        // System.out.println(insertNum);
        // System.out.println(delMinNum);
    }
    // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        SearchNode temp = solution;
        while (temp.predecessor != null) {
            temp = temp.predecessor;
        }
        if (temp.board.equals(initial)) {
            return true;
        }
        if (temp.board.equals(twin)) {
            return false;
        }
        return false;
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
        if (!isSolvable()) {
            return -1;
        }
        return solution.priority - solution.board.manhattan();
    }
    // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (!isSolvable()) {
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
