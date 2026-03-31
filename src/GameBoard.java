public class GameBoard {
    // instance variables
    private char[][] playingBoard = {
        {'_', '_', '_'},
        {'_', '_', '_'},
        {'_', '_', '_'}
    };

    // default constructor for GameBoard
    public GameBoard() {

    }

    /*
     * This function places a marker at the specified coordinates after normalization.
     * Normalization refers to how cells are represented on the human side (1, 2, and 3)
     * and how we can change them to be decremented by 1 to represent the actual cell.
     */
    public void placeMarker(int row, int col, char marker) {
        this.playingBoard[row][col] = marker;
    }

    /*
     * This function checks a given cell to determine whether or not it is empty.
     * Returns true if empty.
     * Returns false if not empty.
     */ 
    public boolean cellIsEmpty(int row, int col) {
        if(this.playingBoard[row][col] == '_') return true; // empty
        else return false; // not empty
    }

    /*
     * This function changes a given cell to be blank.
     */ 
    public void clearCell(int row, int col) {
        this.playingBoard[row][col] = '_';
    }

    /*
     * This function displays the current state of the game board.
     */
    public void displayGameBoard() {
        System.out.println(); // separate from existing output

        // print proper and even column labels
        System.out.print("  ");
        for (int col = 0; col < this.playingBoard[0].length; col++) System.out.print(" " + (col + 1) + "  ");
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

    /*
     *  This function checks to see if a player has won or not.
     *  Returns -1 for nothing detected.
     *  Returns 0 for ties.
     *  Returns 1 for o marker wins.
     *  Returns 2 for x marker wins.
     */
    public int checkWin() {
        // check for col win
        for(int col = 0; col < 3; col++) {
            int oMarkers = 0;
            int xMarkers = 0;
            for(int row = 0; row < 3; row++) {
                if(this.playingBoard[row][col] == '_') break; // marker cannot be 3 in a col
                else if(this.playingBoard[row][col] == 'o') oMarkers++;
                else if(this.playingBoard[row][col] == 'x') xMarkers++;
                if(oMarkers > 0 && xMarkers > 0) break; // if two kinds of markers are recorded, cannot be one's win
            }
            if(xMarkers == 3) return 2;
            else if(oMarkers == 3) return 1;
        }

        // check for row win
        for(int row = 0; row < 3; row++) {
            int oMarkers = 0;
            int xMarkers = 0;
            for(int col = 0; col < 3; col++) {
                if(this.playingBoard[row][col] == '_') break;
                else if(this.playingBoard[row][col] == 'o') oMarkers++;
                else if(this.playingBoard[row][col] == 'x') xMarkers++;
                if(oMarkers > 0 && xMarkers > 0) break; // if two kinds of markers are recorded, cannot be one's win
            }
            if(xMarkers == 3) return 2;
            else if(oMarkers == 3) return 1;
        }

        // check for diagonal win [0][0] [1][1] [2][2], [2][0] [1][1] [0][2]
        int oMarkers = 0;
        int xMarkers = 0;
        for(int i = 0; i < 3; i++) {
            if(this.playingBoard[i][i] == '_') break;
            else if(this.playingBoard[i][i] == 'o') oMarkers++;
            else if(this.playingBoard[i][i] == 'x') xMarkers++;
            if(oMarkers > 0 && xMarkers > 0) break; // if two kinds of markers are recorded, cannot be one's win
        }
        if(xMarkers == 3) return 2;
        else if(oMarkers == 3) return 1;

        // reset our marker counters
        oMarkers = 0;
        xMarkers = 0;

        int index = 2;
        for(int i = 0; i < 3; i++) {
            if(this.playingBoard[index][i] == '_') break;
            else if(this.playingBoard[index][i] == 'o') oMarkers++;
            else if(this.playingBoard[index][i] == 'x') xMarkers++;
            if(oMarkers > 0 && xMarkers > 0) break; // if two kinds of markers are recorded, cannot be one's win
            index--;
        }
        if(xMarkers == 3) return 2;
        else if(oMarkers == 3) return 1;

        // check for tie
        boolean isTie = true;
        outer: // label to break out of both loops when the board is not full
        for(int col = 0; col < 3; col++) {
            for(int row = 0; row < 3; row++) {
                if(this.playingBoard[row][col] == '_') { // blank space detected, not a tie
                    isTie = false;
                    break outer;
                }
            }
        }
        if(isTie) return 0; // no more spaces to place markers, therefore, tie

        return -1; // nothing eventful detected
    }
}
