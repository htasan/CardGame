package com.cardgame.controller;

import com.cardgame.model.Player;

public interface GameController {
    int getCardIndexChoice();
    boolean isHitMove();
    void showStart(Player player);
    void showPlayerInfo(Player player);
    void showUnableToPlay(Player player);
    void askForMove(Player player);
    void askForCardIndexChoice(int handSize);
    void showDamage(Player player, int damage);
    void showCantPlay(Player player);
    void showTurnInfo(Player player);
    void showBledOut(Player player);
    void showWinner(Player player);
}
