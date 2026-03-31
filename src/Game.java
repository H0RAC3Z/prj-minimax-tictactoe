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
    private int gameTreeDepth = 0;

    // default class constructor
    public Game() {

    }

    public String getRedOutput() {
        return ANSI_RED;
    }

    public String getResetOutput() {
        return ANSI_RESET;
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
            System.out.println(ANSI_RED + "Bad input, please enter a valid integer." + ANSI_RESET);
            return 0;
        }
    }

    /*
     * This function validates whether the spot the player chooses is already taken or not.
     */
    public boolean validMove(int[] move, GameBoard playingBoard) {
        int row = move[0];
        int col = move[1];
        try {
            if(!playingBoard.cellIsEmpty(row, col)) {
                System.out.println(ANSI_RED + "There is a marker already placed at this index." + ANSI_RESET);
                return false;
            } else return true;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(ANSI_RED + "Out of bounds." + ANSI_RESET);
            return false;
        }
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
                System.out.println(ANSI_RED + "Bad input, please enter a valid integer." + ANSI_RESET);
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
        System.out.println("Welcome to the human vs. ai game loop.");

        // variables needed for the human vs. ai game loop
        GameBoard gameBoard = new GameBoard();
        Human humanPlayer = new Human(oMarker); // preseting human player as min's max for simplicity
        Bot botPlayer = new Bot(xMarker);
        int gameStatus = -1;

        // human vs. ai game loop
        do {
            // isolation of human player moves so that we can retry over and over again
            gameBoard.displayGameBoard(); // displays current state of game board
            do {
                int[] move = humanPlayer.askForMove(scan, this); // makes the human player make a move

                // if the returned move location is out of bounds or is already occupied, reask for a move
                if(!this.validMove(move, gameBoard)) continue;

                // unpackage move and place marker
                int row = move[0]; // unpackage move
                int col = move[1];
                gameBoard.placeMarker(row, col, humanPlayer.getMarker()); // place marker
                this.gameTreeDepth++;

                // if we can make it to the end of the loop, break out
                break;
            } while(true);

            // display current game tree depth
            System.out.println("The current depth we are at in the game tree is " + this.gameTreeDepth + ".");

            // check for eventful move from human
            if((gameStatus = gameBoard.checkWin()) != -1) break;

            /*
             * Ai player shouldn't need to be isolated nor move validated because 
             * it should have full knowledge of the playing board.
             */
            int[] move = botPlayer.minimax(gameBoard); // run minimax algorithm to find optimal move

            // display calculated game tree depth
            System.out.println("The potential game tree depth is " + (this.gameTreeDepth + botPlayer.getPotentialGameTreeDepth()) + ".");

            gameBoard.displayGameBoard(); // displays current state of game board

            // unpackage move
            int row = move[0];
            int col = move[1];

            // place marker at specified location
            gameBoard.placeMarker(row, col, botPlayer.getMarker());
            this.gameTreeDepth++;

            // display where min has placed their marker
            System.out.println(ANSI_RED + "Min" + ANSI_RESET + " has placed their marker at row " + (move[0] + 1) + " col " + (move[1] + 1) + ".");

            // display current game tree depth
            System.out.println("The current depth we are at in the game tree is " + this.gameTreeDepth + ".");
        } while((gameStatus = gameBoard.checkWin()) == -1);

        // determine the result of the game
        if(gameStatus == 0) System.out.println(ANSI_CYAN + "Tie detected." + ANSI_RESET);
        else if(gameStatus == 1) System.out.println(ANSI_GREEN + "You win!" + ANSI_RESET);
        else if(gameStatus == 2) System.out.println(ANSI_RED + "AI wins!" + ANSI_RESET);
        gameBoard.displayGameBoard();
    }

    public void playAIAI() {
        System.out.println("Welcome to the ai vs. ai game loop.");
        GameBoard gameBoard = new GameBoard();
        Bot maxBot = new Bot(oMarker);
        Bot minBot = new Bot(xMarker);
        int gameStatus = -1;
        do {
            /*
             * Ai player shouldn't need to be isolated nor move validated because 
             * it should have full knowledge of the playing board.
             */
            int[] move1 = maxBot.minimax(gameBoard); // run minimax algorithm to find optimal move

            // unpackage move
            int row1 = move1[0];
            int col1 = move1[1];

            // display calculated game tree depth
            System.out.println("The potential game tree depth is " + (this.gameTreeDepth + maxBot.getPotentialGameTreeDepth()) + ".");

            // place marker at specified location
            gameBoard.placeMarker(row1, col1, maxBot.getMarker());
            this.gameTreeDepth++;

            // display what row and col max have placed their marker
            System.out.println(ANSI_GREEN + "Max" + ANSI_RESET + " has placed their marker at row " + (move1[0] + 1) + " col " + (move1[1] + 1) + ".");
            gameBoard.displayGameBoard(); // displays current state of game board

            // check eventful move from bot1
            if((gameStatus = gameBoard.checkWin()) != -1) break;

            // get current depth of game tree
            System.out.println("The current depth we are at in the game tree is " + this.gameTreeDepth + ".");

            

            // allows for break to analyze bot vs bot
            System.out.println("Press enter to continue.");
            this.scan.nextLine();

            /*
             * Ai player shouldn't need to be isolated nor move validated because 
             * it should have full knowledge of the playing board.
             */
            int[] move2 = minBot.minimax(gameBoard); // run minimax algorithm to find optimal move

            // unpackage move
            int row2 = move2[0];
            int col2 = move2[1];

            // place marker at specified location
            gameBoard.placeMarker(row2, col2, minBot.getMarker());
            this.gameTreeDepth++;

            // display where min bot has placed their specified marker
            System.out.println(ANSI_RED + "Min" + ANSI_RESET + " has placed their marker at row " + (move2[0] + 1) + " col " + (move2[1] + 1) + ".");

            // display game board
            gameBoard.displayGameBoard(); // displays current state of game board

            // get current depth of game tree
            System.out.println("The current depth we are at in the game tree is " + this.gameTreeDepth + ".");

            // allows for break to analyze bot vs bot
            System.out.println("Press enter to continue.");
            this.scan.nextLine();
        } while((gameStatus = gameBoard.checkWin()) == -1); // exit condition is detection of terminal state

        // determine the result of the game
        if(gameStatus == 0) System.out.println(ANSI_CYAN + "Tie detected." + ANSI_RESET);
        else if(gameStatus == 1) System.out.println(ANSI_CYAN + "Max wins!" + ANSI_RESET);
        else if(gameStatus == 2) System.out.println(ANSI_CYAN + "Min wins!" + ANSI_RESET);
        gameBoard.displayGameBoard();
    }
}
