package com.cardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private static final Random random = new Random();

    private final List<Card> cards;

    public Deck(List<Integer> cardDamages) {
        cards = new ArrayList<>();
        setCards(cardDamages);
    }

    public Card drawRandomCard() {
        if(cards.isEmpty()) {
            return null;
        }
        return cards.remove(random.nextInt(cards.size()));
    }

    private void setCards(List<Integer> initialCardDamages) {
        if(initialCardDamages == null) {
            return;
        }
        for(int damage : initialCardDamages) {
            cards.add(new Card(damage));
        }
    }

    int getSize() {
        return cards.size();
    }
}
