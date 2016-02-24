package edu.macalester.comp124.hw3;

import acm.program.ConsoleProgram;

/**
 */
public class Player {

    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }

    public int addToScore(int val){
        this.score += val;
        return score;
    }
}
