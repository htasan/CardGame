package com.cardgame.model;

import com.cardgame.testutil.TestConstants;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void shouldDrawRandomCard() {
        Deck deck = new Deck(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS);
        assertEquals(4, deck.getSize());
        assertNotNull(deck.drawRandomCard());
        assertEquals(3, deck.getSize());
    }

    @Test
    public void shouldNotDrawCardWhenEmpty() {
        Deck deck = new Deck(TestConstants.EMPTY_DAMAGE_LIST);
        assertNull(deck.drawRandomCard());
        assertEquals(0, deck.getSize());
    }

    @Test
    public void shouldNotDrawCardWhenNull() {
        Deck deck = new Deck(TestConstants.EMPTY_DAMAGE_LIST);
        assertNull(deck.drawRandomCard());
        assertEquals(0, deck.getSize());
    }

    @Test
    public void shouldCreateEmptyDeckWhenDamageListIsNull() {
        Deck deck = new Deck(null);
        assertEquals(0, deck.getSize());
    }

    @Test
    public void shouldReturnDeckSize() {
        Deck deck = new Deck(TestConstants.DAMAGE_LIST_WITH_FIVE_ELEMENTS);
        assertEquals(5, deck.getSize());
    }
}
