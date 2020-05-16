package com.cardgame.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void shouldReturnStringValueOfDamage() {
        assertEquals("3", new Card(3).toString());
    }
}
