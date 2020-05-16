package com.cardgame.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GameRule {

    private final int maxHealth;
    private final List<Integer> cardDamages;
    private final int initialNumberOfCards;
    private final int initialManaSlot;
    private final int maxHandSize;
    private final int maxManaSlot;
    private final int bleedOutDamage;

    public static GameRule defaultGameRule() {
        return new GameRule(30,Collections.unmodifiableList(Arrays.asList(0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8)),3,0,5,10,1);
    }
}
