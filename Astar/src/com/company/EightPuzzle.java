package com.company;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class EightPuzzle {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_PURPLE = "\u001B[35m";

    // Tiles for successfully completed puzzle.
    static final byte [] goalTiles = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    // A* priority queue.
    final PriorityQueue <State> queue = new PriorityQueue<State>(100, new Comparator<State>() {
        @Override
        public int compare(State a, State b) {
            return a.priority() - b.priority();
        }
    });

    // The closed state set.
    final HashSet <State> closed = new HashSet <State>();

    // State of the puzzle including its priority and chain to start state.
    class State {
        final byte [] tiles;    // Tiles left to right, top to bottom.
        final int spaceIndex;   // Index of space (zero) in tiles
        final int g;            // Number of moves from start.
        final int h;            // Heuristic value (difference from goal)
        final State prev;       // Previous state in solution chain.

        // A* priority function (often called F in books).
        int priority() {
            return g + h;
        }

        // Build a start state.
        State(byte [] initial) {
            tiles = initial;
            spaceIndex = index(tiles, 0);
            g = 0;
            h = heuristic(tiles);
            prev = null;
        }

        // Build a successor to prev by sliding tile from given index.
        State(State prev, int slideFromIndex) {
            tiles = Arrays.copyOf(prev.tiles, prev.tiles.length);
            tiles[prev.spaceIndex] = tiles[slideFromIndex];
            tiles[slideFromIndex] = 0;
            spaceIndex = slideFromIndex;
            g = prev.g + 1;
            h = heuristic(tiles);
            this.prev = prev;
        }

        // Return true iif this is the goal state.
        boolean isGoal() {
            return Arrays.equals(tiles, goalTiles);
        }

        // Successor states due to south, north, west, and east moves.
        State moveS() { return spaceIndex > 2 ? new State(this, spaceIndex - 3) : null; }
        State moveN() { return spaceIndex < 6 ? new State(this, spaceIndex + 3) : null; }
        State moveE() { return spaceIndex % 3 > 0 ? new State(this, spaceIndex - 1) : null; }
        State moveW() { return spaceIndex % 3 < 2 ? new State(this, spaceIndex + 1) : null; }

        // Print this state.
        void print() {
            System.out.println(TEXT_YELLOW+"p = " + priority() + " = g+h = " + g + "+" + h+TEXT_RESET);
            int matrix[][]=new int[100][100];
            int count=0;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    matrix[i][j]=tiles[count];
                    count++;
                }
            }
int row=0;
            while (row<3) {
                for (int i = 0; i < 7 * 3; i++) {
                    System.out.print(TEXT_BLUE + "-" + TEXT_RESET);
                }
                System.out.println(TEXT_BLUE + "-" + TEXT_RESET);

                for (int i = 1; i <= 3; i++) {
                    System.out.printf(TEXT_BLUE + "|" + TEXT_RESET + TEXT_RED + " %4d " + TEXT_RESET, matrix[row][i - 1]);
                }
                System.out.println(TEXT_BLUE+"|"+TEXT_RESET);
                row++;
            }

 row--;

            if(row == 3 -  1){

                // when we reach the last row,
                // print bottom line "---------"

                for(int i = 0;i <  7 * 3 ;i++){
                    System.out.print(TEXT_BLUE+"-"+TEXT_RESET);
                }
                System.out.println(TEXT_BLUE+"-"+TEXT_RESET);

            }

        }

        // Print the solution chain with start state first.
        void printAll() {
            if (prev != null) prev.printAll();
            System.out.println();
            print();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof State) {
                State other = (State)obj;
                return Arrays.equals(tiles, other.tiles);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(tiles);
        }
    }

    // Add a valid (non-null and not closed) successor to the A* queue.
    void addSuccessor(State successor) {
        if (successor != null && !closed.contains(successor))
            queue.add(successor);
    }

    // Run the solver.
    void solve(byte [] initial) {

        queue.clear();
        closed.clear();

        // Click the stopwatch.
        long start = System.currentTimeMillis();

        // Add initial state to queue.
        queue.add(new State(initial));

        while (!queue.isEmpty()) {

            // Get the lowest priority state.
            State state = queue.poll();

            // If it's the goal, we're done.
            if (state.isGoal()) {
                long elapsed = System.currentTimeMillis() - start;
                state.printAll();
                System.out.println(TEXT_PURPLE+"elapsed (ms) = "+TEXT_RESET + elapsed);
                return;
            }

            // Make sure we don't revisit this state.
            closed.add(state);

            // Add successors to the queue.
            addSuccessor(state.moveS());
            addSuccessor(state.moveN());
            addSuccessor(state.moveW());
            addSuccessor(state.moveE());
        }
    }

    // Return the index of val in given byte array or -1 if none found.
    static int index(byte [] a, int val) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == val) return i;
        return -1;
    }

    // Return the Manhatten distance between tiles with indices a and b.
    static int manhattanDistance(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }


    // For our A* heuristic, we just use max of Manhatten distances of all tiles.
    static int heuristic(byte [] tiles) {
        int h = 0;
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] != 0)
                h = Math.max(h, manhattanDistance(i, tiles[i]));
        return h;
    }



    public static void main(String[] args) {

        // This is a harder puzzle than the SO example
        byte [] initial = { 1, 2, 5, 3, 4, 0, 6, 7, 8 };

        // This is taken from the SO example.
        //byte [] initial = { 1, 4, 2, 3, 0, 5, 6, 7, 8 };

        new EightPuzzle().solve(initial);
    }
}