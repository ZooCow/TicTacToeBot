package com.zoocow;

import java.io.*;
import java.util.*;

class MindfulPlayer implements Player{
    String name;
    char symbol;
    Boolean trainingMode;
    String filePath;
    ArrayList<String> rememberedLocations;
    Hashtable<String, Integer> stateFitnessMap;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * String name -- Player's name
     * Char symbol -- The player's board symbol
     * String filePath -- Where the player's "state memory is located
     * Boolean trainingMode -- True = just play randomly; false = play the best move
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public MindfulPlayer(String name, char symbol, String filePath, Boolean trainingMode) {
        this.name = name;
        this.symbol = symbol;
        this.filePath = filePath;
        this.trainingMode = trainingMode;
        rememberedLocations = new ArrayList<String>();
        stateFitnessMap = new Hashtable<String, Integer>();
        loadFitness();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * loadFitness()
     * Loads board state-score pairs from the file path
     * and puts them in the hashTable.
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void loadFitness(){
        FileReader fileReader;

        try {
            fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);

            for (String line = br.readLine(); line != null; line = br.readLine()) {

                String[] splitLine = line.split(" ");

                if(splitLine.length != 2)
                    return;
                stateFitnessMap.put(splitLine[0], new Integer(splitLine[1]));
            }

            fileReader.close();
            br.close();
        } catch(java.io.IOException e) {
            return;
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * saveMemoryFromDisk()
     * Save the board state-score pairs from the hash table
     * to a file at the file path
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void saveFitness(){

        FileOutputStream outputStream;
        OutputStreamWriter outputStreamWriter;

        try{
            outputStream = new FileOutputStream(filePath);
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            Writer writer = new BufferedWriter(outputStreamWriter);
            Set set = stateFitnessMap.entrySet();
            Iterator it = set.iterator();

            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
                writer.write("" + entry.getKey() + " " + entry.getValue() + "\n");
            }

            writer.flush();
            writer.close();
            outputStreamWriter.close();
            outputStream.close();

        } catch(java.io.IOException e){
            System.out.println("Error " + e);
            return;
        }

        return;
    }


     /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * getSymbol()
     * Return the player's symbol
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Override
    public char getSymbol(){
        return symbol;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * getName()
     * Return the player's name
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Override
    public String getName() {
        return name;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * getMove()
     * Return the player's move
     * If in training mode, then it's random
     * If in regular mode, then it's the best remembered move
     * Board board - the current board
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Override
    public int[] getMove(Board board) {
        Board workingBoard = new Board(board);
        int[] move = new int[2];
        ArrayList<int[]> possibleMoves = workingBoard.getOpenPositions();

        if(possibleMoves.size() == 1){
            move = possibleMoves.get(0);
        } else {
            if (trainingMode) {
                    Random rand = new Random();
                    move = possibleMoves.get(rand.nextInt(possibleMoves.size() - 1));
            } else {
                move = getBestMove(workingBoard);
            }
        }

        logMove(workingBoard, move);

        return move;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * getBestMove()
     * Returns the move with the highest fitness
     * Board board - The current board.
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int[] getBestMove(Board board){
        ArrayList<int[]> possibleMoves = board.getOpenPositions();

        HashMap<int[], Integer> moves = new HashMap<int[], Integer>();

        for(int[] m : possibleMoves){
            // Emulate board with that move having been made
            Board workingBoard = new Board(board);
            workingBoard.makeMove(m, symbol);

            // If the board has been seen before use it's fitness,
            // otherwise give it a fitness of 0.
            if(stateFitnessMap.containsKey(workingBoard.toString())){
                moves.put(m, stateFitnessMap.get(workingBoard.toString()));
            } else {
                moves.put(m, 0);
            }
        }

        return getHighestMove(moves);
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * getHighestMove()
     * Returns the move with the highest fitness
     * HashMap moves - Matches a move to its fitness score
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int[] getHighestMove(HashMap<int[], Integer> moves){
        int max = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

            Set set = moves.entrySet();
            Iterator it = set.iterator();

            while (it.hasNext()) {
                Map.Entry<int[], Integer> entry = (Map.Entry<int[], Integer>) it.next();
                if (entry.getValue() > max){
                    max = entry.getValue();
                    bestMove = entry.getKey();
                }
            }

        return bestMove;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * modifyStateFitness()
     * For all of the moves seen this game.
     * int modifyBy - Add this integer to the fitness of all seen states
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    void modifyStateFitness(int modifyBy){

        // for all of the states seen this game
        for(String s : rememberedLocations){

            //if the state is already remembered, modify the state by the modifyBy value
            if(stateFitnessMap.containsKey(s)){
                int value = stateFitnessMap.remove(s);
                stateFitnessMap.put(s, value + modifyBy);

            // Otherwise, add the state to memory
            } else {
                stateFitnessMap.put(s, modifyBy);
            }
        }
    }


    void logMove(Board board, int[] move){
        Board working = new Board(board);
        working.makeMove(move, symbol);
        rememberedLocations.add(working.toString());
    }

    @Override
    public void wrapUp(Board board, boolean winner){
        if(winner){
            modifyStateFitness(1);
        } else {
            modifyStateFitness(-1);
        }
        rememberedLocations.clear();
        saveFitness();
    }

}
