import java.util.Scanner;

public class Human {
    private char marker;
    
    public Human(char marker) {
        this.marker = marker;
    }

    /*
     *  This function makes a human player choose a row and column to make their marker placement.
     */
    public int[] askForMove() {
        int row = 0;
        int column = 0;

        while(true) { // get user input until its valid
            try {
                Scanner scan = new Scanner(System.in); // scanner object to scan input from user
                String userInput = "";
                System.out.print("Choose a row to place your marker: ");
                userInput = scan.nextLine();
                row = Integer.parseInt(userInput);
                System.out.print("Choose a column to place your marker: ");
                userInput = scan.nextLine();
                column = Integer.parseInt(userInput);
                scan.close();
                break;
            } catch(NumberFormatException e) {
                System.out.println("Bad input, please enter a valid integer.");
            }
        }
        
        return new int[]{row, column};
    }
}
