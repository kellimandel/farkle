package edu.macalester.comp124.hw3;

import acm.util.RandomGenerator;

/**
 *
 */
public class Die {

    private int faceValue;
    private RandomGenerator rgen = new RandomGenerator();
    private boolean isSetAside;

    public Die() {
        faceValue = rgen.nextInt(6) + 1;
        isSetAside = false;
    }

    public void roll(){
        faceValue = rgen.nextInt(6) + 1;
    }

    public boolean getIsSetAside(){
        return isSetAside;
    }

    public int getFaceValue(){
        return faceValue;
    }

    public void setIsSetAside(boolean answer){
        isSetAside = answer;
    }

}
