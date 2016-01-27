package com.zoocow;

/**
 * Created by Chris on 1/23/16.
 */
class Game {

    Player playerOne;
    Player playerTwo;
    int verbosity;
    int startingTurn = 1;
    boolean flipTurns;


    public Game(Player playerOne, Player playerTwo, boolean flipTurns, int verbosity){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.verbosity = verbosity;
        this.flipTurns = flipTurns;
    }

    public int playGames(int numberOfGames){
        int[] player = new int[4];
        player[1] = 0; // Player 1 wins
        player[2] = 0; // Player 2 wins
        player[3] = 0; // Draw


        for(int i = 0; i < numberOfGames; i++){
            player[playGame()]++;

            if(flipTurns) flipTurn();
        }

        System.out.format("Player 1: %d\nPlayer 2: %d\nDraw: %d", player[1], player[2], player[3]);

        return 1;
    }

    public void flipTurn(){
        if (startingTurn == 1){
            startingTurn = 2;
        } else {
            startingTurn = 1;
        }
    }

    public int playGame(){
        int winner = 0;
        Board board = new Board(playerOne.getSymbol(), playerTwo.getSymbol());

        board.initializeBoard();

        // Game loop
        for(int turn = startingTurn; turn > 0; turn++) {

            if(verbosity > 1) board.printBoard();

            boolean haveValidMove = false;
            int[] move = new int[2];

            while(!haveValidMove){
                if (turn % 2 != 0){
                    move = playerOne.getMove(board);
                    haveValidMove = board.makeMove(move, playerOne.getSymbol());
                }
                else {
                    move = playerTwo.getMove(board);
                    haveValidMove = board.makeMove(move, playerTwo.getSymbol());
                }
            }

            winner = board.gameOver();
            if (winner > 0){
                if(verbosity > 1) board.printBoard();
                if(winner == 1){
                    if(verbosity > 0) System.out.println("\n" + playerOne.getName() + " wins!");
                    playerOne.wrapUp(board, true);
                    playerTwo.wrapUp(board, false);
                } else if (winner == 2){
                    if(verbosity > 0) System.out.println("\n" + playerTwo.getName() + " wins!");
                    playerOne.wrapUp(board, false);
                    playerTwo.wrapUp(board, true);
                } else {
                    if(verbosity > 0) System.out.println("\nDraw!");
                    playerOne.wrapUp(board, false);
                    playerTwo.wrapUp(board, false);
                }

                return winner;
            }
        }
        return -1;
    }
}

