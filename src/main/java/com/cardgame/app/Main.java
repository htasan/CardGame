package com.cardgame.app;

import com.cardgame.controller.TerminalController;
import com.cardgame.model.Game;
import com.cardgame.util.GameRule;

public class Main {

    public static void main(String[]  args) {
        new Game("Player 1", "Player 2", GameRule.defaultGameRule(), new TerminalController())
                .play();
    }

}
