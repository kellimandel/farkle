package edu.macalester.comp124.hw3;

import acm.program.ConsoleProgram;

/**
 * Created by bjackson on 9/24/2015.
 */
public class FarkleProgram extends ConsoleProgram {

    public static final int WINNING_SCORE = 10000;
    public static final int NUM_DICE = 6;
    private Player p0;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Die d0;
    private Die d1;
    private Die d2;
    private Die d3;
    private Die d4;
    private Die d5;


    public void init(){

//        setFont("SanSarif-24");
//        setSize(1200, 800);

        String p0name = readLine("Enter player 1 name: ");
        String p1name = readLine("Enter player 2 name: ");
        String p2name = readLine("Enter player 3 name: ");
        p0 = new Player(p0name);
        p1 = new Player(p1name);
        p2 = new Player(p2name);
        currentPlayer = p0;
        d0 = new Die();
        d1 = new Die();
        d2 = new Die();
        d3 = new Die();
        d4 = new Die();
        d5 = new Die();
    }

    /**
     * The main function for the entire program.
     *
     * You should create other helper methods inside FarkleProgram.
     * Some of them should create an instances of the Player and Die classes.
     * Make sure to test your new methods in the TestFarkleProgram junit class.
     *
     */
    public void run() {

        Player winner = null;

        println(currentPlayer.getName()+"'s turn:");
        takeTurn();

        while(true){
            if (winner == null && testForWin(currentPlayer)) {
                winner = currentPlayer;
                println("\t"+currentPlayer.toString()+" "+winner.getName()+" could be the winner. You have one last chance turn.");
            }
            advanceTurn();
            println();

            if (winner != null && currentPlayer.equals(winner)) {
                // We have a winner and have now given everyone else a turn
                winner = findHighestScorer();
                println("\t"+winner.toString()+ " " +winner.getName() +" wins the game!");
                break;
            }
        }
    }


    private Player findHighestScorer(){
        int score0 = p0.getScore();
        int score1 = p1.getScore();
        int score2 = p2.getScore();
        if(score0 >= score1 && score0 >= score2){
            return p0;
        }
        else if(score1 >= score0 && score1 >= score2){
            return p1;
        }
        else if(score2 >= score0 && score2 >= score1){
            return p2;
        }
        else{
            return null;
        }

    }
    private void advanceTurn(){
        if(currentPlayer.getName().equals(p0.getName())){
            currentPlayer = p1;
            println("\n\n" + currentPlayer.getName()+"'s turn:");
            resetDice();
            takeTurn();
        }
        else if(currentPlayer.getName().equals(p1.getName())){
            currentPlayer = p2;
            println("\n\n" + currentPlayer.getName()+"'s turn:");
            resetDice();
            takeTurn();
        }
        else if(currentPlayer.getName().equals(p2.getName())){
            currentPlayer = p0;
            println("\n\n" + currentPlayer.getName()+"'s turn:");
            resetDice();
            takeTurn();
        }
    }
    private void takeTurn(){
        rollDice();
        printDice();

        if(testBust()){
            println("Sorry, you busted!");
            println(currentPlayer.getName() + " has " + currentPlayer.getScore() + " points. Next player's turn!");
            return;
        }


        String originalRolls = toString(d0) + toString(d1)
                + toString(d2) + toString(d3)
                + toString(d4) + toString(d5);

        boolean hotDice = checkAllDiceScore(originalRolls);

        if(hotDice){
            println();
            println("HOT DICE!");
            currentPlayer.addToScore(calculateScore(originalRolls));
            println("You earned " + calculateScore(originalRolls) + " points and now have "
                    + currentPlayer.getScore() + " total points.");
            resetDice();
            takeTurn();
            return;
        }

        if (!d0.getIsSetAside()) {
            boolean setAside0 = readBoolean("Would you like to set D0 aside? (true or false)");
            d0.setIsSetAside(setAside0);
        }
        if (!d1.getIsSetAside()) {
            boolean setAside1 = readBoolean("Would you like to set D1 aside? (true or false)");
            d1.setIsSetAside(setAside1);
        }
        if (!d2.getIsSetAside()) {
            boolean setAside2 = readBoolean("Would you like to set D2 aside? (true or false)");
            d2.setIsSetAside(setAside2);
        }
        if (!d3.getIsSetAside()) {
            boolean setAside3 = readBoolean("Would you like to set D3 aside? (true or false)");
            d3.setIsSetAside(setAside3);
        }
        if (!d4.getIsSetAside()) {
            boolean setAside4 = readBoolean("Would you like to set D4 aside? (true or false)");
            d4.setIsSetAside(setAside4);
        }
        if (!d5.getIsSetAside()) {
            boolean setAside5 = readBoolean("Would you like to set D5 aside? (true or false)");
            d5.setIsSetAside(setAside5);
        }


        String diceValues = "";
        if(d0.getIsSetAside()){
            diceValues += toString(d0);
        }
        if(d1.getIsSetAside()){
            diceValues += toString(d1);
        }
        if(d2.getIsSetAside()){
            diceValues += toString(d2);
        }
        if(d3.getIsSetAside()){
            diceValues += toString(d3);
        }
        if(d4.getIsSetAside()){
            diceValues += toString(d4);
        }
        if(d5.getIsSetAside()){
            diceValues += toString(d5);
        }

        int currentScore = calculateScore(diceValues);


        Boolean continueR = readBoolean("Current score is " + currentScore + ". Continue rolling (true or false)? ");
        if(continueR){
            takeTurn();
        }
        else{
            currentPlayer.addToScore(currentScore);
            println(currentPlayer.getName() + " has " + currentPlayer.getScore() + " points. Next player's turn!");
        }



    }
    private boolean checkAllDiceScore(String diceValues){
        if((countOccurrences(diceValues,2) - 3*checkThreeOfAKind(diceValues, 2)) > 0){
            return false;
        }
        else if((countOccurrences(diceValues,3) - 3*checkThreeOfAKind(diceValues, 3)) > 0){
            return false;
        }
        else if((countOccurrences(diceValues,4) - 3*checkThreeOfAKind(diceValues, 4)) > 0){
            return false;
        }
        else if((countOccurrences(diceValues,6) - 3*checkThreeOfAKind(diceValues, 6)) > 0){
            return false;
        }
        else{
            return true;
        }
    }

    private void resetDice(){
        d0 = new Die();
        d1 = new Die();
        d2 = new Die();
        d3 = new Die();
        d4 = new Die();
        d5 = new Die();
    }

    private void rollDice(){
        if(!d0.getIsSetAside()){
            d0.roll();
        }
        if(!d1.getIsSetAside()){
            d1.roll();
        }
        if(!d2.getIsSetAside()){
            d2.roll();
        }
        if(!d3.getIsSetAside()){
            d3.roll();
        }
        if(!d4.getIsSetAside()){
            d4.roll();
        }
        if(!d5.getIsSetAside()){
            d5.roll();
        }
    }

    private boolean testBust(){
        String rolledDice = "";
        String otherDice = "";
        if(!d0.getIsSetAside()){
            rolledDice += toString(d0);
        }
        else{ otherDice += toString(d0);}

        if(!d1.getIsSetAside()){
            rolledDice += toString(d1);
        }
        else{ otherDice += toString(d1);}

        if(!d2.getIsSetAside()){
            rolledDice += toString(d2);
        }
        else{ otherDice += toString(d2);}

        if(!d3.getIsSetAside()){
            rolledDice += toString(d3);
        }
        else{ otherDice += toString(d3);}

        if(!d4.getIsSetAside()){
            rolledDice += toString(d4);
        }
        else{ otherDice += toString(d4);}

        if(!d5.getIsSetAside()){
            rolledDice += toString(d5);
        }
        else{ otherDice += toString(d5);}


        if(calculateScore(rolledDice + otherDice) > calculateScore(otherDice)){
            return false;
        }

        return true;
    }
    private int calculateScore(String diceValues) {
        int pendingScore = 0;

        int countOnes = countOccurrences(diceValues, 1);
        int countFives = countOccurrences(diceValues, 5);

        pendingScore += 1000*checkThreeOfAKind(diceValues, 1);
        pendingScore += 200*checkThreeOfAKind(diceValues, 2);
        pendingScore += 300*checkThreeOfAKind(diceValues, 3);
        pendingScore += 400*checkThreeOfAKind(diceValues, 4);
        pendingScore += 500*checkThreeOfAKind(diceValues, 5);
        pendingScore += 600*checkThreeOfAKind(diceValues, 6);

        //Accounts for stray ones and fives that do not fit into groups of 3:

        while(countOnes - (3*checkThreeOfAKind(diceValues, 1)) > 0){
            pendingScore += 100;
            countOnes -= 1;
        }

        while(countFives - (3*checkThreeOfAKind(diceValues, 5)) > 0){
            pendingScore += 50;
            countFives -= 1;
        }


        return pendingScore;
    }

    private int checkThreeOfAKind(String diceValues, int num){
        int occurences = countOccurrences(diceValues, num);
        if(occurences == 6){
            return 2;
        }
        else if(occurences >= 3){
            return 1;
        }
        else{
            return 0;
        }
    }
    private int countOccurrences(String combinedRolls,int testVal){
        return combinedRolls.length() - combinedRolls.replace(Integer.toString(testVal), "").length();
    }
    private void printDice(){

        println("D0: " + d0.getFaceValue() + setDiceAside(d0) + ",\tD1: " + d1.getFaceValue()
                + setDiceAside(d1) + ",\tD2: " + d2.getFaceValue() + setDiceAside(d2) + ",\tD3: "
                + d3.getFaceValue() + setDiceAside(d3) + ",\tD4: " + d4.getFaceValue() + setDiceAside(d4)
                +",\tD5: " + d5.getFaceValue() + setDiceAside(d5) );

    }

    private String setDiceAside(Die d){
        if(d.getIsSetAside()){
            return " Set Aside";
        }
        return "";

    }
    private boolean testForWin(Player p){
        if(p.getScore() >= 10000){
            return true;
        }
        return false;
    }

    private String toString(Die d){
        return Integer.toString(d.getFaceValue());
    }

    public Player getP0() {
        return p0;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Die getD0() {
        return d0;
    }

    public Die getD1() {
        return d1;
    }

    public Die getD2() {
        return d2;
    }

    public Die getD3() {
        return d3;
    }

    public Die getD4() {
        return d4;
    }

    public Die getD5() {
        return d5;
    }
}
