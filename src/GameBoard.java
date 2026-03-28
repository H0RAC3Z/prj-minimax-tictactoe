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
     *  This function checks to see if a player has won or not.
     *  Returns 0 for ties.
     *  Returns 1 for o marker wins.
     *  Returns 2 for x marker wins.
     */
    public int checkWin(char[][] playingBoard) {
        return 0; // tie
    }
}
