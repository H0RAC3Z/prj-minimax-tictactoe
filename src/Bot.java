public class Bot {
    // instance variables
    private char marker;
    private int potentialGameTreeDepth = 0;
    
    // constructor for Bot player
    public Bot(char marker) {
        this.marker = marker;
    }

    /*
     * This function returns the marker that this bot holds.
     */
    public char getMarker() {
        return this.marker;
    }

    public int getPotentialGameTreeDepth() {
        return this.potentialGameTreeDepth;
    }

    /*
     * This function makes the bot select an optimal move on the board. Plays according to min max algorithm and as min when facing a human.
     * Utilizes recursion to find best move for current GameBoard.
     */
    public int[] minimax(GameBoard playingBoard) {
        int[] bestMove = {-1, -1}; // init best move

        this.potentialGameTreeDepth = 0; // re-evaluating the deepest part of the tree we can go
        if (this.marker == 'o') { // maximizer
            int bestScore = Integer.MIN_VALUE; // smallest init value

            // for every cell, test minimax scores on the empty ones
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (playingBoard.cellIsEmpty(row, col)) { // if empty, place marker and evaluate score
                        playingBoard.placeMarker(row, col, this.marker);
                        int score = minimaxScore(playingBoard, false, 1); // depth is always 1 because we already made a move
                        playingBoard.clearCell(row, col); // revert changes
                        if (score > bestScore) { // get best score and best move
                            bestScore = score;
                            bestMove = new int[]{row, col};
                        }
                    }
                }
            }
        } else { // minimizer, best score is lowest score that the maximizer gets
            int bestScore = Integer.MAX_VALUE; // biggest init value
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (playingBoard.cellIsEmpty(row, col)) {
                        playingBoard.placeMarker(row, col, this.marker);
                        int score = minimaxScore(playingBoard, true, 1);
                        playingBoard.clearCell(row, col);
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove = new int[]{row, col};
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimaxScore(GameBoard playingBoard, boolean isMaximizing, int depth) {
        int result = playingBoard.checkWin();
        // set potential game tree depth when we hit a leaf node / terminal state
        if (result == 1) {
            this.potentialGameTreeDepth = Math.max(this.potentialGameTreeDepth, depth);
            return 10; 
        }
        if (result == 2) {
            this.potentialGameTreeDepth = Math.max(this.potentialGameTreeDepth, depth);
            return -10;
        }
        if (result == 0) {
            this.potentialGameTreeDepth = Math.max(this.potentialGameTreeDepth, depth);
            return 0;
        }

        if (isMaximizing) { // recursively traverse the game tree, acting as max
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (playingBoard.cellIsEmpty(row, col)) {
                        playingBoard.placeMarker(row, col, 'o');
                        best = Math.max(best, minimaxScore(playingBoard, false, depth + 1));
                        playingBoard.clearCell(row, col);
                    }
                }
            }
            return best;
        } else { // recursively traverse the game tree, acting as min
            int best = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (playingBoard.cellIsEmpty(row, col)) {
                        playingBoard.placeMarker(row, col, 'x');
                        best = Math.min(best, minimaxScore(playingBoard, true, depth + 1));
                        playingBoard.clearCell(row, col);
                    }
                }
            }
            return best;
        }
    }
}
