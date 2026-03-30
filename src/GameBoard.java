public class GameBoard {
    private char[][] playingBoard = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    public GameBoard() {

    }

    public char[][] getPlayingBoard() {
        return this.playingBoard;
    }

    /*
     * This function displays the current state of the game board.
     */
    public void displayGameBoard() {
        System.out.println(); // separate from existing output

        // print proper and even column labels
        System.out.print("  ");
        for (int col = 0; col < this.playingBoard[0].length; col++) {
            System.out.print(" " + (col + 1) + "  ");
        }
        System.out.println();

        for (int row = 0; row < this.playingBoard.length; row++) {
            // print row label
            System.out.print((row + 1) + " ");

            // print each cell in the row
            for (int col = 0; col < this.playingBoard[row].length; col++) {
                System.out.print(" " + this.playingBoard[row][col] + " ");
                if (col < this.playingBoard[row].length - 1) System.out.print("|"); // cell divider
            }
            System.out.println();

            // print a divider row, except after the last row
            if (row < this.playingBoard.length - 1) {
                System.out.print("  ");
                for (int col = 0; col < this.playingBoard[row].length; col++) {
                    System.out.print("---");
                    if (col < this.playingBoard[row].length - 1) System.out.print("+"); // vertices
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public void placeMarker(int[] location, char marker) {
        int row = location[0];
        int col = location[1];
        this.playingBoard[row - 1][col - 1] = marker;
    }

    /*
     *  This function checks to see if a player has won or not.
     *  Returns 0 for ties.
     *  Returns 1 for o marker wins.
     *  Returns 2 for x marker wins.
     */
    public int checkWin() {
        return 0; // tie
    }
}
