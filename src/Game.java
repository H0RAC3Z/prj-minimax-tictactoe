import java.util.Scanner;

public class Game {
    // colors used like System.out.println(ANSI_CYAN + "I am cyan" + ANSI_RESET + "\nI am normal");
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final char oMarker = 'o';
    private static final char xMarker = 'x';
    private Scanner scan = new Scanner(System.in); // one scanner for reading all user inputs

    public Game() {

    }

    /*
     * This function validates integer only inputs.
     * Returns 0 if not able to turn into int.
     * Returns the userInput if able to turn into int.
     */
    public int validUserInput(String userInput) {
        userInput.trim();
        try {
            return Integer.parseInt(userInput);
        } catch(NumberFormatException e) {
            System.out.println("Bad input, please enter a valid integer.");
            return 0;
        }
    }

    /*
     * This function validates whether the spot the player chooses is already taken or not.
     */
    public boolean validMove(int[] move, char[][] playingBoard) {
        int row = move[0];
        int col = move[1];
        try {
            if(playingBoard[row - 1][col - 1] != '_') {
                System.out.println("There is a marker already placed at this index.");
                return false;
            } else return true;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Out of bounds.");
            return false;
        }
    }

    /*
     * This function displays the game tree depth so far.
     */
    public void displayCurrentGameTreeDepth() {

    }

    /*
     * This function manages the game loop.
     */
    public void start() {
        int selection = 0;

        // menu loop
        while(true) {
            System.out.println(ANSI_CYAN + "--------- Menu ---------\n" +
                ANSI_RESET +
                "Choose an option:\n" +
                ANSI_GREEN +
                "1. Play\n" +
                ANSI_RED +
                "2. Exit\n" +
                ANSI_RESET
            );
            String userInput = scan.nextLine();
            if((selection = validUserInput(userInput)) == 0) continue; // re-prompt in the case of not int
            else if(selection == 1) break;
            else if(selection == 2) break;
            else { // re-prompt in the case of not 1 or 2
                System.out.println("Bad input, please enter a valid integer.");
                continue;
            }
        }

        if(selection == 2) { // exit chosen
            scan.close();
            return;
        }

        // game options loop
        while(true) {
            System.out.println(ANSI_CYAN + "--------- Options ---------\n" +
                ANSI_RESET +
                "Choose an option:\n" +
                ANSI_GREEN +
                "1. Human Vs. AI\n" +
                ANSI_GREEN +
                "2. AI Vs. AI\n" +
                ANSI_RED +
                "3. Exit\n" +
                ANSI_RESET
            );
            String userInput = scan.nextLine();
            
            if((selection = validUserInput(userInput)) == 0) continue; // re-prompt in the case of not int
            else if(selection == 1) break;
            else if(selection == 2) break;
            else if(selection == 3) break;
            else { // re-prompt in the case of not 1, 2, or 3
                System.out.println("Bad input, please enter a valid integer.");
                continue;
            }
        }

        if(selection == 3) { // exit chosen
            scan.close();
            return;
        } else if(selection == 1) this.playHumanAI(); // start human vs. AI game loop
        else if(selection == 2) this.playAIAI(); // start AI vs. AI game loop

        scan.close();
    }

    public void playHumanAI() {
        System.out.println("Welcome to human vs. ai game loop.");
        GameBoard gameBoard = new GameBoard();
        Human humanPlayer = new Human(oMarker);
        Bot botPlayer = new Bot(xMarker);
        while(true) {
            do { // isolation of human player moves so that we can retry over and over again
                gameBoard.displayGameBoard(); // displays current state of game board
                int[] move = humanPlayer.askForMove(scan); // makes the human player make a move
                if(!this.validMove(move, gameBoard.getPlayingBoard())) continue;
                gameBoard.placeMarker(move, humanPlayer.getMarker());
                break;
            } while(true);
            
            // ai player shouldn't need to be isolated nor move validated because it should have full knowledge of the playing board
            gameBoard.displayGameBoard(); // displays current state of game board
            // TODO: Implement botPlayer.minimax();
            // int[] move = botPlayer.minimax(); // run minimax algorithm to find optimal move
            // gameBoard.placeMarker(move, botPlayer.getMarker());
            break;
        }
    }

    public void playAIAI() {
        System.out.println("Welcome to ai vs. ai game loop.");
    }
}
