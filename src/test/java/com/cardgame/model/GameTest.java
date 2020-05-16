package com.cardgame.model;

import com.cardgame.controller.GameController;
import com.cardgame.testutil.TestConstants;
import com.cardgame.util.GameRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Mock
    private GameController gameController;

    private Game createGameWithRule(GameRule gameRule) {
        return new Game("TestPlayer1", "TestPlayer2", gameRule, gameController);
    }

    @Test
    public void die() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(1).cardDamages(TestConstants.EMPTY_DAMAGE_LIST)
                .bleedOutDamage(1).build());
        game.play();
        verify(gameController).showStart(game.getActivePlayer());
        verify(gameController).showBledOut(game.getActivePlayer());
        verify(gameController).showWinner(game.getOtherPlayer());
    }

    @Test
    public void beUnableToPlayAsHandIsEmptyAndDie() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(2).cardDamages(TestConstants.EMPTY_DAMAGE_LIST)
                .bleedOutDamage(1).maxManaSlot(5).initialManaSlot(3).build());
        game.play();
        verify(gameController).showStart(game.getActivePlayer());
        verify(gameController).showPlayerInfo(game.getActivePlayer());
        verify(gameController).showPlayerInfo(game.getOtherPlayer());
        verify(gameController).showUnableToPlay(game.getActivePlayer());
        verify(gameController).showUnableToPlay(game.getOtherPlayer());
        verify(gameController).showTurnInfo(game.getActivePlayer());
        verify(gameController).showTurnInfo(game.getOtherPlayer());
        verify(gameController).showBledOut(game.getActivePlayer());
        verify(gameController).showWinner(game.getOtherPlayer());
    }

    @Test
    public void beUnableToPlayAsManaIsNotEnoughAndDie() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(1).cardDamages(TestConstants.DAMAGE_LIST_WITH_FOUR_ELEMENTS)
                .maxHandSize(5).initialNumberOfCards(3).bleedOutDamage(1).build());
        game.play();
        verify(gameController).showStart(game.getActivePlayer());
        verify(gameController).showPlayerInfo(game.getActivePlayer());
        verify(gameController).showPlayerInfo(game.getOtherPlayer());
        verify(gameController).showUnableToPlay(game.getActivePlayer());
        verify(gameController).showUnableToPlay(game.getOtherPlayer());
        verify(gameController).showTurnInfo(game.getActivePlayer());
        verify(gameController).showTurnInfo(game.getOtherPlayer());
        verify(gameController).showBledOut(game.getActivePlayer());
        verify(gameController).showWinner(game.getOtherPlayer());
    }

    @Test
    public void hitAndKill() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(2).cardDamages(TestConstants.DAMAGE_LIST_WITH_ONE_ELEMENT)
                .maxHandSize(5).initialManaSlot(2).maxManaSlot(10).bleedOutDamage(1).build());
        when(gameController.isHitMove()).thenReturn(true);
        when(gameController.getCardIndexChoice()).thenReturn(0);
        game.play();
        verify(gameController).showStart(game.getActivePlayer());
        verify(gameController).showPlayerInfo(game.getActivePlayer());
        verify(gameController, times(0)).showUnableToPlay(game.getActivePlayer());
        verify(gameController).askForMove(game.getActivePlayer());
        verify(gameController).askForCardIndexChoice(1);
        verify(gameController).showDamage(game.getOtherPlayer(), 3);
        verify(gameController).showWinner(game.getActivePlayer());
    }

    @Test
    public void passThenGetHitAndDie() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(2).cardDamages(TestConstants.DAMAGE_LIST_WITH_ONE_ELEMENT)
                .maxHandSize(5).initialManaSlot(2).maxManaSlot(10).build());
        Player player1 = game.getActivePlayer();
        Player player2 = game.getOtherPlayer();
        when(gameController.isHitMove()).thenReturn(false).thenReturn(true);
        when(gameController.getCardIndexChoice()).thenReturn(0);
        game.play();
        verify(gameController).showStart(player1);
        verify(gameController).showPlayerInfo(player1);
        verify(gameController).showPlayerInfo(player2);
        verify(gameController, times(0)).showUnableToPlay(any(Player.class));
        verify(gameController).askForMove(player1);
        verify(gameController).askForMove(player2);
        verify(gameController).askForCardIndexChoice(1);
        verify(gameController).showDamage(player1, 3);
        verify(gameController).showWinner(player2);
    }

    @Test
    public void cantPlayThenHitAndKill() {
        Game game = createGameWithRule(GameRule.builder()
                .maxHealth(2).cardDamages(TestConstants.DAMAGE_LIST_WITH_ONE_ELEMENT)
                .maxHandSize(5).initialManaSlot(2).maxManaSlot(10).build());
        when(gameController.isHitMove()).thenReturn(true).thenReturn(true);
        when(gameController.getCardIndexChoice()).thenReturn(1).thenReturn(0);
        game.play();
        verify(gameController).showStart(game.getActivePlayer());
        verify(gameController, times(2)).showPlayerInfo(game.getActivePlayer());
        verify(gameController, times(0)).showUnableToPlay(game.getActivePlayer());
        verify(gameController, times(2)).askForMove(game.getActivePlayer());
        verify(gameController, times(2)).askForCardIndexChoice(1);
        verify(gameController).showCantPlay(game.getActivePlayer());
        verify(gameController).showDamage(game.getOtherPlayer(), 3);
        verify(gameController).showWinner(game.getActivePlayer());
    }


}
