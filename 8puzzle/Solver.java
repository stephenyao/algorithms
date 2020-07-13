import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {

        Board board;
        int moves;
        SearchNode previous;
        int manhattanP;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattanP = board.manhattan() + moves;
        }

        public int compareTo(SearchNode node) {
            int p = board.manhattan() + moves;
            int nodeP = node.board.manhattan() + moves;
            if (manhattanP < node.manhattanP) return -1;
            if (manhattanP > node.manhattanP) return 1;
            return 0;
        }
    }

    private int moves = 0;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode initialNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(initialNode);
        solve(pq);
    }

    private void solve(MinPQ<SearchNode> pq) {
        SearchNode node = pq.delMin();

        while (!node.board.isGoal()) {
            for (Board neighbor : node.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, node.moves + 1, node);
                Board previousBoard = node.previous != null ? node.previous.board : null;
                if (!neighborNode.board.equals(previousBoard)) {
                    pq.insert(neighborNode);
                }
            }
            node = pq.delMin();

            // System.out.println(node.board.toString());
        }

        while (node.previous != null) {
            System.out.println(node.board.toString());
            node = node.previous;
            moves++;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}
