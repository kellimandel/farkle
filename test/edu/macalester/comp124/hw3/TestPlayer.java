package edu.macalester.comp124.hw3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class TestPlayer {

    @Test
    public void myTest() {
        Player p = new Player("Johnny");
        assertNotNull(p);
        assertEquals(0,p.getScore());
        p.addToScore(700);
        assertEquals(700,p.getScore());

//         test that the player behaves properly
    }
}
