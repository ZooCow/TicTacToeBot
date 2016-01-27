package com.zoocow;

import java.util.Scanner;

/**
 * Created by Chris on 1/23/16.
 */
class HumanPlayer implements Player{
    String name;
    char symbol;
    Board board;

    public HumanPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public char getSymbol(){
        return symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getMove(Board board) {
        this.board = board;
        boolean valid = false;

        int[] move = new int[2];

        while (!valid) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter X Y moves for " + name + ":");
            move[0] = scanner.nextInt();
            move[1] = scanner.nextInt();

            valid = board.isValidMove(move);
            if (!valid) System.out.println("invalid move");
        }
        return move;
    }

    @Override
    public void wrapUp(Board board, boolean winner){
        return;
    }
}
