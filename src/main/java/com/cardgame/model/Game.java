package com.cardgame.model;

import com.cardgame.controller.GameController;
import com.cardgame.util.GameRule;

public class Game {

    private final GameController gameController;
    private Player activePlayer;
    private Player otherPlayer;

    public Game(String player1Name, String player2Name, GameRule gameRule, GameController gameController) {
        this.gameController = gameController;
        activePlayer = new Player(player1Name, gameRule);
        otherPlayer = new Player(player2Name, gameRule);
    }

    public void play() {
        gameController.showStart(activePlayer);
        activePlayer.prepareForNewTurn();
        do {
            if(activePlayer.hasLost()) {
                gameController.showBledOut(activePlayer);
                gameController.showWinner(otherPlayer);
                return;
            }
            if(otherPlayer.hasLost()) {
                gameController.showWinner(activePlayer);
                return;
            }
            gameController.showPlayerInfo(activePlayer);
            if(activePlayer.isUnableToPlay()) {
                gameController.showUnableToPlay(activePlayer);
                changeActivePlayer();
                continue;
            }
            playTurn();
        } while(true);
    }

    private void playTurn() {
        gameController.askForMove(activePlayer);
        if(gameController.isHitMove()) {
            hit();
        } else {
            changeActivePlayer();
        }
    }

    private void hit() {
        gameController.askForCardIndexChoice(activePlayer.getHandSize());
        Card card = activePlayer.playCard(gameController.getCardIndexChoice());
        if(card != null) {
            int damage = card.getDamage();
            otherPlayer.takeDamage(damage);
            gameController.showDamage(otherPlayer, damage);
        } else {
            gameController.showCantPlay(activePlayer);
            hit();
        }
    }

    private void changeActivePlayer() {
        switchPlayers();
        gameController.showTurnInfo(activePlayer);
        activePlayer.prepareForNewTurn();
    }

    private void switchPlayers() {
        Player temp = activePlayer;
        activePlayer = otherPlayer;
        otherPlayer = temp;
    }

    Player getActivePlayer() {
        return activePlayer;
    }

    Player getOtherPlayer() {
        return otherPlayer;
    }
}
