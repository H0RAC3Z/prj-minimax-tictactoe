import java.util.Scanner;

public class Human {
    private char marker;
    
    // constructor for Human player
    public Human(char marker) {
        this.marker = marker;
    }

    /*
     * This function returns the marker this human player object has.
     */
    public char getMarker() {
        return this.marker;
    }

    /*
     *  This function makes a human player choose a row and column to make their marker placement.
     */
    public int[] askForMove(Scanner scan, Game game) {
        int row = 0;
        int column = 0;

        while(true) { // get user input until its valid
            try {
                // initialization
                String userInput = "";

                // get user input for row and parse
                System.out.print("Choose a row to place your marker: ");
                userInput = scan.nextLine();
                row = Integer.parseInt(userInput);

                // get user input for column and parse
                System.out.print("Choose a column to place your marker: ");
                userInput = scan.nextLine();
                column = Integer.parseInt(userInput);

                // break out of asking for a valid int input from user if pass both parses for row and column
                break;
            } catch(NumberFormatException e) {
                // catches "cannot be converted to int" errors
                System.out.println(game.getRedOutput() + "Bad input, please enter a valid integer." + game.getResetOutput());
            }
        }
        
        return new int[]{row - 1, column - 1}; // user input maps is +1 of actual indices
    }
}
