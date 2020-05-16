package com.cardgame.model;

import com.cardgame.util.GameRule;
import com.cardgame.testutil.TestConstants;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player createPlayerWithRule(GameRule gameRule) {
        return new Player("TestPlayer", gameRule);
    }

    @Test
    public void shouldDrawInitialCardsAndNotBleedOut() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(3).maxHandSize(5).bleedOutDamage(1).build());
        assertEquals(3, player.getHandSize());
        assertEquals(30, player.getHealth());
    }

    @Test
    public void shouldBleedOutInitiallyIfDeckHasNotEnoughNumberOfCards() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(6).maxHandSize(5).bleedOutDamage(1).build());
        assertEquals(4, player.getHandSize());
        assertEquals(28, player.getHealth());
    }

    @Test
    public void shouldPrepareForNewTurnAndNotBleedOut() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(1).initialManaSlot(4).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        assertEquals(4, player.getManaSlot());
        assertEquals(0, player.getMana());
        assertEquals(1, player.getHandSize());
        player.prepareForNewTurn();
        assertEquals(5, player.getManaSlot());
        assertEquals(5, player.getMana());
        assertEquals(2, player.getHandSize());
        assertEquals(30, player.getHealth());
    }

    @Test
    public void shouldNotIncreaseManaSlotIfAtMax() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialManaSlot(10).maxHandSize(5).maxManaSlot(10).build());
        assertEquals(10, player.getManaSlot());
        assertEquals(0, player.getMana());
        player.prepareForNewTurn();
        assertEquals(10, player.getManaSlot());
        assertEquals(10, player.getMana());
    }

    @Test
    public void shouldBleedOutIfDeckIsEmptyWhenPreparingForNewTurn() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.EMPTY_DAMAGE_LIST)
                .maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertEquals(29, player.getHealth());
    }

    @Test
    public void shouldNotDrawCardDespiteDeckIsNotEmptyIfHandIsFullWhenPreparingForNewTurn() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_SIX_ELEMENTS)
                .initialNumberOfCards(5).maxHandSize(5).bleedOutDamage(1).build());
        assertEquals(5, player.getHandSize());
        player.prepareForNewTurn();
        assertEquals(5, player.getHandSize());
        assertEquals(30, player.getHealth());
    }

    @Test
    public void shouldBleedOutDespiteHandIsFullIfDeckIsEmptyWhenPreparingForNewTurn() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FIVE_ELEMENTS)
                .initialNumberOfCards(5).maxHandSize(5).bleedOutDamage(1).build());
        assertEquals(5, player.getHandSize());
        player.prepareForNewTurn();
        assertEquals(5, player.getHandSize());
        assertEquals(29, player.getHealth());
    }

    @Test
    public void shouldPlayCard() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(3).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertEquals(4, player.getHandSize());
        assertEquals(1, player.getMana());
        player.sortHand();
        assertNotNull(player.playCard(0));
        assertEquals(3, player.getHandSize());
        assertEquals(0, player.getMana());
    }

    @Test
    public void shouldNotPlayCardIfNotEnoughMana() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(3).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertEquals(1, player.getMana());
        player.sortHand();
        assertNull(player.playCard(2));
        assertEquals(1, player.getMana());
    }

    @Test
    public void shouldNotPlayCardIfIndexOutOfBounds() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(3).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertEquals(1, player.getMana());
        assertNull(player.playCard(9));
        assertEquals(1, player.getMana());
    }

    @Test
    public void shouldBeUnableToPlayIfHandIsEmpty() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.EMPTY_DAMAGE_LIST)
                .initialNumberOfCards(0).initialManaSlot(1).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertTrue(player.isUnableToPlay());
    }

    @Test
    public void shouldBeUnableToPlayIfManaIsNotEnough() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(1).initialManaSlot(-1).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertTrue(player.isUnableToPlay());
    }

    @Test
    public void shouldBeAbleToPlay() {
        Player player = createPlayerWithRule(GameRule.builder()
                .maxHealth(30).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(1).maxHandSize(5).maxManaSlot(10).initialManaSlot(3).bleedOutDamage(1).build());
        player.prepareForNewTurn();
        assertFalse(player.isUnableToPlay());
    }

    @Test
    public void shouldReturnPlayerInfo() {
        Player player = createPlayerWithRule(GameRule.builder().maxHealth(30)
                .cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .initialNumberOfCards(4).maxHandSize(5).maxManaSlot(10).bleedOutDamage(1).build());
        player.sortHand();
        assertEquals("TestPlayer - Health:30 Mana:0 Hand:[1, 1, 2, 3]", player.toString());
    }

    @Test
    public void shouldReturnHasNotLost() {
        Player player = createPlayerWithRule(GameRule.builder().maxHealth(30).build());
        assertFalse(player.hasLost());
    }

    @Test
    public void shouldReturnHasLost() {
        Player player = createPlayerWithRule(GameRule.builder().maxHealth(-1).build());
        assertTrue(player.hasLost());
    }

    @Test
    public void shouldGetNameAndHealth() {
        Player player = createPlayerWithRule(GameRule.builder().maxHealth(30).build());
        assertEquals("TestPlayer", player.getName());
        assertEquals(30, player.getHealth());
    }
}
