package com.cardgame.model;

import com.cardgame.util.GameRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {

    private final String name;
    private final GameRule gameRule;
    private final Deck deck;
    private final List<Card> hand;
    private int health;
    private int manaSlot;
    private int mana;

    public Player(String name, GameRule gameRule) {
        this.name = name;
        this.gameRule = gameRule;
        health = gameRule.getMaxHealth();
        deck = new Deck(gameRule.getCardDamages());
        manaSlot = gameRule.getInitialManaSlot();
        hand = new ArrayList<>();
        initHand(gameRule.getInitialNumberOfCards());
    }

    public void prepareForNewTurn() {
        increaseManaSlot();
        refreshMana();
        drawCard();
    }

    public Card playCard(int cardIndex) {
        int requiredMana;
        try {
            requiredMana = hand.get(cardIndex).getDamage();
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
        if(!hasEnoughMana(requiredMana)) {
            return null;
        }
        spendMana(requiredMana);
        return hand.remove(cardIndex);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean hasLost() {
        return health <= 0;
    }

    public int getHandSize() {
        return hand.size();
    }

    public boolean isUnableToPlay() {
        return hand.isEmpty() || hasNotEnoughManaToPlayAnyCard();
    }

    public boolean hasNotEnoughManaToPlayAnyCard() {
        for(Card card : hand) {
            if(hasEnoughMana(card.getDamage())) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return name + " - Health:" + health + " Mana:" + mana + " Hand:" + hand;
    }

    private void initHand(int initialNumberOfCards) {
        for(int i = 0; i < initialNumberOfCards; i++) {
            drawCard();
        }
    }

    private void increaseManaSlot() {
        if(isManaSlotIncreasable()) {
            manaSlot++;
        }
    }

    private boolean isManaSlotIncreasable()  {
        return manaSlot < gameRule.getMaxManaSlot();
    }

    private void refreshMana() {
        mana = manaSlot;
    }

    private void drawCard() {
        Card card = deck.drawRandomCard();
        if(card == null) {
            takeDamage(gameRule.getBleedOutDamage());
        } else {
            takeCard(card);
        }
    }

    private void takeCard(Card card) {
        if(canTakeCard()) {
            hand.add(card);
        }
    }

    private boolean canTakeCard() {
        return hand.size() < gameRule.getMaxHandSize();
    }

    private boolean hasEnoughMana(int requiredMana) {
        return mana >= requiredMana;
    }

    private void spendMana(int manaToSpend) {
        mana -= manaToSpend;
    }

    int getManaSlot() {
        return manaSlot;
    }

    int getMana() {
        return mana;
    }

    void sortHand() {
        hand.sort(Comparator.comparing(Card::getDamage));
    }
}
