import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {

        Board board;
        int moves;
        SearchNode previous;
        int manhattanP;
        int hammingP;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattanP = board.manhattan() + moves;
            this.hammingP = board.hamming() + moves;
        }

        public int compareTo(SearchNode node) {
            if (manhattanP < node.manhattanP) return -1;
            if (manhattanP > node.manhattanP) return 1;
            return 0;
        }
    }

    private int moves = 0;
    private SearchNode finalNode;
    private SearchNode finalTwinNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode initialNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(initialNode);

        SearchNode twinNode = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        twinPq.insert(twinNode);

        solve(pq, twinPq);
    }

    private void solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> twinPq) {
        SearchNode node = pq.delMin();
        SearchNode twinNode = twinPq.delMin();

        while (!node.board.isGoal() && !twinNode.board.isGoal()) {
            node = step(pq, node);
            twinNode = step(twinPq, twinNode);
        }

        finalNode = node;
        finalTwinNode = twinNode;
    }

    private SearchNode step(MinPQ<SearchNode> pq, SearchNode node) {
        for (Board neighbor : node.board.neighbors()) {
            SearchNode neighborNode = new SearchNode(neighbor, node.moves + 1, node);
            Board previousBoard = node.previous != null ? node.previous.board : null;
            if (!neighborNode.board.equals(previousBoard)) {
                pq.insert(neighborNode);
            }
        }
        return pq.delMin();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return finalNode.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!finalNode.board.isGoal()) return -1;
        return countMoves(0, finalNode);
    }

    private int countMoves(int acc, SearchNode node) {
        if (node == null) return acc - 1;
        return countMoves(acc + 1, node.previous);
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!finalNode.board.isGoal()) {
            return null;
        }
        ArrayList<Board> boards = new ArrayList<Board>();
        SearchNode pointer = finalNode;
        while (pointer.previous != null) {
            boards.add(pointer.board);
            pointer = pointer.previous;
        }

        ArrayList<Board> reversed = new ArrayList<Board>();
        for (int i = boards.size() - 1; i >= 0; i--) {
            reversed.add(boards.get(i));
        }

        return reversed;
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}
