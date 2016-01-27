package com.zoocow;

public class Main {

    public static void main(String[] args) {

        Game newGame = new Game(new MindfulPlayer("MemoryAI", 'O', "memory.txt", false), new RandomPlayer("RandomAI", 'X'), false, 0);

        newGame.playGames(10);
    }
}












