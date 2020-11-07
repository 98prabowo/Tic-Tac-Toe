package tictactoe;

import java.util.Scanner;

/**
 * Tic-Tac-Toe: Two-player console, non-graphics
 * All variables/methods are declared as static (belong to the class)
 * Presented by: Pad89
 */

public class Main {
    // Name-constant to represent various states of the game
    public static boolean crossWon = false;
    public static boolean noughtWon = false;

    // The game board and the game status
    public static final int ROWS = 3, COLS = 3; // number of rows and columns
    public static final String inputBoard = "_________";
    public static char[][] board = new char[ROWS][COLS]; // game board in 2D array
    //  containing (EMPTY, CROSS, NOUGHT)

    // Initializing player movement
    public static String playerMovement;
    public static int playerRow = 0;
    public static int playerCol = 0;
    public static char playerTurn = 'X';

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initGame();

        // Run Tic-tac-toe
        do {
            System.out.print("> ");
            playerMovement = scanner.nextLine();

            if (alphabetError(playerMovement))
                System.out.println("You should enter numbers!");
            else if (numericError(playerMovement))
                System.out.println("Coordinates should be from 1 to 3!");
            else {
                playerRow = Integer.parseInt(String.valueOf(playerMovement.charAt(0))) - 1;
                playerCol = Integer.parseInt(String.valueOf(playerMovement.charAt(2))) - 1;

                changeCoordinate();

                if (occupied(board, playerRow, playerCol))
                    System.out.println("This cell is occupied! Choose another one!");
                else {
                    playerMove(board);
                    hasWon(board);
                    gameResult();
                }

            }

        } while (!stopGame());

    }


    // GAME BUILDER
    public static void initGame() {
        // Initialize board
        stringToArray(inputBoard, board);
        printBoard(board);
    }

    public static void playerMove(char[][] content) {
        switch (playerTurn) {
            case 'X' :
                content[playerRow][playerCol] = playerTurn;
                playerTurn = 'O';
                break;
            case 'O' :
                content[playerRow][playerCol] = playerTurn;
                playerTurn = 'X';
                break;
        }

        printBoard(content);
    }


    // GET GAME STATUS
    public  static  boolean stopGame () {
        return noughtWon || crossWon || isDraw(board);
    }

    public static void gameResult() {
        if (noughtWon) {
            System.out.println("O wins");
        } else if (crossWon) {
            System.out.println("X wins");
        } else if (isDraw(board)){
            System.out.println("Draw");
        }
    }

    public static boolean isDraw(char[][] content) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (content[row][col] == '_') {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true; // no empty cell, it's a draw
    }

    public static void hasWon(char[][] content) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col <COLS; ++col) {
                if (content[row][0] == 'O'
                        && content[row][1] == 'O'
                        && content[row][2] == 'O'
                        || content[0][col] == 'O'      // 3-in-the-column
                        && content[1][col] == 'O'
                        && content[2][col] == 'O'
                        || row == col                  // 3-in-the-diagonal
                        && content[0][0] == 'O'
                        && content[1][1] == 'O'
                        && content[2][2] == 'O'
                        || row + col == 2              // 3-in-the-opposite-diagonal
                        && content[0][2] == 'O'
                        && content[1][1] == 'O'
                        && content[2][0] == 'O' ) {
                    noughtWon = true;
                }
                if (content[row][0] == 'X'
                        && content[row][1] == 'X'
                        && content[row][2] == 'X'
                        || content[0][col] == 'X'      // 3-in-the-column
                        && content[1][col] == 'X'
                        && content[2][col] == 'X'
                        || row == col                 // 3-in-the-diagonal
                        && content[0][0] == 'X'
                        && content[1][1] == 'X'
                        && content[2][2] == 'X'
                        || row + col == 2             // 3-in-the-opposite-diagonal
                        && content[0][2] == 'X'
                        && content[1][1] == 'X'
                        && content[2][0] == 'X' ) {
                    crossWon = true;
                }
            }
        }

    }

    public static boolean occupied(char[][] content, int playerRow, int playerCol) {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (content[playerRow][playerCol] == 'X' || content[playerRow][playerCol] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }


    // PRINT TIC-TAC-TOE BOARD
    public static void printBoard(char[][] content) {
        System.out.println("---------");
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (col == 0) {
                    System.out.print("| ");
                }
                printCell(content[row][col]); // print each of the cells
                if (col == COLS - 1)
                    System.out.println("|");
            }
        }
        System.out.println("---------");
    }

    public static void printCell(char content) {
        switch (content) {
            case '_': System.out.print("_ "); break;
            case 'O': System.out.print("O "); break;
            case 'X': System.out.print("X "); break;
            default: System.out.println("  "); break;
        }
    }


    // MATH METHOD
    public static void stringToArray(String input, char[][] content) {
        int i = 0;
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                content[row][col] = input.charAt(i);
                ++i;
            }
        }
    }

    public static void changeCoordinate() {
        // Reverse array coordinate as desired coordinate
        if (playerRow == 0 && playerCol == 0) {
            playerRow = ROWS - 1;
        } else if (playerRow == 0 && playerCol == 1) {
            playerRow = ROWS - 2;
            playerCol = 0;
        } else if (playerRow == 0 && playerCol == 2) {
            playerCol = 0;
        } else if (playerRow == 1 && playerCol == 0) {
            playerRow = ROWS - 1;
            playerCol = COLS - 2;
        } else if (playerRow == 1 && playerCol == 2) {
            playerRow = 0;
            playerCol = COLS - 2;
        } else if (playerRow == 2 && playerCol == 0) {
            playerCol = COLS - 1;
        } else if (playerRow == 2 && playerCol == 1) {
            playerRow = ROWS - 2;
            playerCol = COLS - 1;
        } else if (playerRow == 2 && playerCol == 2) {
            playerRow = 0;
        }
    }


    // ERROR HANDLE
    public static boolean alphabetError(String input) {
        for (int i = 0; i < input.length(); i+=2) {
            int checkInput = input.charAt(i);
            if (checkInput < 48 || checkInput > 57) {
                return true; // check ASCII code of number
            }
        }
        return false;
    }

    public static boolean numericError (String input) {
        for (int i = 0; i < input.length(); i+=2) {
            int checkInput = input.charAt(i);
            if (checkInput < 49 || checkInput > 51) {
                return true;
            }
        }
        return false;
    }
}
