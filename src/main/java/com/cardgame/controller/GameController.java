package com.cardgame.controller;

import com.cardgame.model.Player;

public interface GameController {
    boolean getHitMove(Player player);
    int getCardIndexChoice(Player player);
    void showStart(Player player);
    void showPlayerInfo(Player player);
    void showUnableToPlay(Player player);
    void showDamage(Player player, int damage);
    void showCantPlay(Player player);
    void showTurnInfo(Player player);
    void showBledOut(Player player);
    void showWinner(Player player);
}
