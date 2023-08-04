package Task2;

import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player 1 name:");
        String player1 = scanner.nextLine();

        System.out.println("Enter player 2 name:");
        String player2 = scanner.nextLine();

        System.out.println("Select the mode of game");
        System.out.println("1. You vs computer");
        System.out.println("2. You vs your friend");
        int choice = scanner.nextInt();

        String mode = (choice == 1) ? "mode1" : "mode2";

        // Create an instance of the Game class and start the game
        Game game = new Game(mode, choice);
        game.start(player1, player2);
    }
}

class Game {
    private String mode;
    private int noOfPlayers;
    private String player1;
    private String player2;

    // Represents the tic tac toe board
    private int[][] matrix = new int[3][3];

    Game(String m, int n) {
        this.mode = m;
        this.noOfPlayers = n;
    }


    //for computer
    public Position generateMove() {
        if (mode.equals("mode1")) {
            Position p = null;
            ArrayList<Position> availablePositions = new ArrayList<>();

            // Find all available positions on the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (matrix[i][j] == 0) {
                        availablePositions.add(new Position(i, j));
                    }
                }
            }
            // If there are available positions, randomly choose one
            if (!availablePositions.isEmpty()) {
                int randomIndex = (int) (Math.random() * availablePositions.size());
                p = availablePositions.get(randomIndex);
            }
            return p;
        } else {
            // For mode2 (You vs your friend), return null as we won't use this method
            return null;
        }
    }
    //declare winner
    private void printResult() {
        int winner = checkWinner();
        if (winner == 1) {
            System.out.println("Congratulations! " + player1 + " (X) wins!");
        } else if (winner == 2) {
            System.out.println("Congratulations! " + player2 + " (O) wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
    //return any value in matched sequence(row or column or diagonal) value represents player (0,1,2)
    private int checkWinner() {
        // Check rows for a win
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2] && matrix[i][0] != 0) {
                return matrix[i][0];
            }
        }
        // Check columns for a win
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i] && matrix[0][i] != 0) {
                return matrix[0][i];
            }
        }
        // Check diagonals for a win
        if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] && matrix[0][0] != 0) {
            return matrix[0][0];
        }
        if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0] && matrix[0][2] != 0) {
            return matrix[0][2];
        }
        // No winner; check for a draw
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    // There are still empty cells, so the game is not over
                    return 0;
                }
            }
        }
        // It's a draw
        return -1;
    }
    //for making moves 
    private void makeMove(String playerName, int player) {
        Scanner sc = new Scanner(System.in);

        int row, col;
        if (playerName.equals(player2) && mode.equals("mode1")) {
            // For computer's turn in mode1 (You vs computer)
            Position computerMove = generateMove();
            System.out.println("Computer's turn (" + player2 + ")");
            System.out.println("Computer's move: Row " + computerMove.c1 + ", Column " + computerMove.c2);
            row = computerMove.c1;
            col = computerMove.c2;
        } else {
            // For user's and friend's turns
            System.out.println(playerName + ", enter row (0-2) and column (0-2) to make your move:");
            row = sc.nextInt();
            col = sc.nextInt();

            while (!isValidMove(row, col)) {
                System.out.println("Invalid move. Try again.");
                System.out.println(playerName + ", enter row (0-2) and column (0-2) to make your move:");
                row = sc.nextInt();
                col = sc.nextInt();
            }
        }

        matrix[row][col] = player;
    }
    // find whether a move is valid or not 
    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && matrix[row][col] == 0);
    }
    //method for printing board
    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    System.out.print("-");
                } else if (matrix[i][j] == 1) {
                    System.out.print("X");
                } else if (matrix[i][j] == 2) {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }
    //method to find whther game is over or not
    public boolean gameOver() {
        if (mode.equals("mode1")) {
            // For mode1 (You vs computer), the game is over when the board is full or there is a winner
            int winner = checkWinner();
            if (winner != 0 || isBoardFull()) {
                return true;
            }
        } else {
            // For mode2 (You vs your friend), check for a win or draw condition after at least 5 moves
            int moveCount = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (matrix[i][j] != 0) {
                        moveCount++;
                    }
                }
            }
            //game will be over only when 3 values get equal in any row or column or diagonal and for this happen atleast 5 moves must be made
            if (moveCount >= 5) {
                // Check for a win condition
                for (int i = 0; i < 3; i++) {
                    // Check rows
                    if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2] && matrix[i][0] != 0) {
                        return true;
                    }
                    // Check columns
                    if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i] && matrix[0][i] != 0) {
                        return true;
                    }
                }
                // Check diagonals
                if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] && matrix[0][0] != 0) {
                    return true;
                }
                if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0] && matrix[0][2] != 0) {
                    return true;
                }
            }

            // If no win and board is full, it's a draw
            if (moveCount == 9) {
                return true;
            }
        }

        return false;
    }
    //find whether 
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void start(String p1, String p2) {
        this.player1 = p1;
        this.player2 = p2;
        boolean continueGame = true;
        while (continueGame) {
            // main game logic and ui
            if (this.mode.equals("mode1")) {
                // you vs computer
                // alternate moves of computer and user
                // show the board to user
                while (!gameOver()) {
                    System.out.println("Your turn (" + player1 + ")");
                    makeMove(player1, 1);

                    printBoard();

                    if (gameOver()) {
                        printResult();
                        break;
                    }

                    System.out.println("Computer's turn (" + player2 + ")");
                    makeMove(player2, 2);

                    printBoard();

                    if (gameOver()) {
                        printResult();
                        break;
                    }
                }
            } else {
                // Mode 2: you vs your friend
                printBoard();
                while (!gameOver()) {
                    System.out.println("Your turn (" + player1 + ")");
                    makeMove(player1, 1);

                    printBoard();

                    if (gameOver()) {
                        printResult();
                        break;
                    }

                    System.out.println("Your friend's turn (" + player2 + ")");
                    makeMove(player2, 2);

                    printBoard();

                    if (gameOver()) {
                        printResult();
                        break;
                    }
                }
            }

            System.out.println("Do you wish to continue? (Y/N)");
            Scanner p = new Scanner(System.in);
            String c = p.next();
            if (!c.equalsIgnoreCase("Y")) {
                p.close();
                continueGame = false;
            } else {
                resetBoard(); // Reset the board if the game continues
            }
        }
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = 0;
            }
        }
    }
}

class Position {
    int c1;

    int c2;

    Position(int i, int j) {
        this.c1 = i;
        this.c2 = j;
    }
}
