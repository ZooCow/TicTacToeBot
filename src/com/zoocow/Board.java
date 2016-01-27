package com.zoocow;

import java.util.ArrayList;

class Board {

    char[][] board = new char[3][3];
    char playerOneSymbol;
    char playerTwoSymbol;

    public Board(char playerOneSymbol, char playerTwoSymbol){
        this.playerOneSymbol = playerOneSymbol;
        this.playerTwoSymbol = playerTwoSymbol;
    }

   public Board(Board toCopyBoard){

       for(int i = 0; i < 3; i++)
           for(int j = 0; j < 3; j++)
                board[i][j] = toCopyBoard.get(i, j);

       this.playerOneSymbol = toCopyBoard.playerOneSymbol;
       this.playerTwoSymbol = toCopyBoard.playerTwoSymbol;
   }


    public void printBoard(){
        System.out.println("YX 0 1 2");
        System.out.format("0  %c %c %c\n", board[0][0], board[1][0], board[2][0]);
        System.out.format("1  %c %c %c\n", board[0][1], board[1][1], board[2][1]);
        System.out.format("2  %c %c %c\n", board[0][2], board[1][2], board[2][2]);
        System.out.println("");
        return;
    }

    public boolean isValidMove(int[] move){
        if(move[0] >= 0 &&
                move[0] <= 2 &&
                move[1] >= 0 &&
                move[1] <= 2 &&
                (board[move[0]][move[1]] == '-'))
            return true;
        else return false;
    }


    boolean makeMove(int[] move, char symbol){
        if(isValidMove(move)) {
            board[move[0]][move[1]] = symbol;
            return true;
        }
        return false;
    }

    public int gameOver(){
        // 0 = not over
        // 1 = player 1 wins
        // 2 = player 2 wins
        // 3 = cat

        char winningSymbol = 0;

        // Check rows
        if(board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != '-') winningSymbol = board[0][0];
        if(board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != '-') winningSymbol = board[0][1];
        if(board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != '-') winningSymbol = board[0][2];

        // Check columns
        if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != '-') winningSymbol = board[0][0];
        if(board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != '-') winningSymbol = board[1][0];
        if(board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != '-') winningSymbol = board[2][0];

        // Check diagonals
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') winningSymbol = board[0][0];
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') winningSymbol = board[0][2];

        if(winningSymbol == playerOneSymbol) return 1; // player 1 wins
        if(winningSymbol == playerTwoSymbol) return 2;

        // at least one open space?
        for(int x = 0; x < 3; x++)
            for( int y = 0; y < 3; y++)
                if(board[x][y] == '-')
                    return 0;

        // all filled, but no winner
        return 3;
    }

    public char get(int x, int y){
        return board[x][y];
    }

    public void initializeBoard() {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board[x][y] = '-';

        return;
    }

    public ArrayList<int[]> getOpenPositions(){

        ArrayList<int[]> openPositions = new ArrayList<int[]>();

        for(int x = 0; x < 3; x++) {
            for( int y = 0; y < 3; y++){
                if (board[x][y] == '-') {
                    int[] move = new int[2];
                    move[0] = x;
                    move[1] = y;
                    openPositions.add(move);
                }
            }
        }
        return openPositions;
    }

    public String toString(){
        String answer = "";

        for(int x = 0; x < 3; x++)
            for(int y = 0; y <3; y++)
                answer += board[x][y];

        return answer;
    }

}
