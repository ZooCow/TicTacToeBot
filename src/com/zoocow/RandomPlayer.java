package com.zoocow;

import java.util.ArrayList;
import java.util.Random;

class RandomPlayer implements Player{
    String name;
    char symbol;
    Board board;

    public RandomPlayer(String name, char symbol) {
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
        ArrayList<int[]> possibleMoves = board.getOpenPositions();

        if (possibleMoves.size() > 1){
            Random rand = new Random();
            return possibleMoves.get(rand.nextInt(possibleMoves.size() - 1));
        } else {
            return possibleMoves.get(0);
        }
    }

    @Override
    public void wrapUp(Board board, boolean winner){
        return;
    }

}
