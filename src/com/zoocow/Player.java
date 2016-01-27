package com.zoocow;

interface Player {
    int[] getMove(Board board);
    String getName();
    char getSymbol();
    void wrapUp(Board board, boolean winner);
}
