public class Bot {
    private char marker;
    
    public Bot(char marker) {
        this.marker = marker;
    }

    public char getMarker() {
        return this.marker;
    }

    /*
     * This function makes the bot select an optimal move on the board. Plays according to min max algorithm and as min when facing a human.
     */
    public int[] minimax() {
        return new int[]{};
    }

    /*
     * This function makes the bot select the optimal move on the board according to the min max algorithm with alpha beta pruning.
     * Utilize for comparison with the amount of branches in consideration.
     */
    public void minimaxWithPruning() {

    }
}
