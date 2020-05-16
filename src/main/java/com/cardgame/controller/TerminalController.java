package com.cardgame.controller;

import com.cardgame.model.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class TerminalController implements GameController {

    @Override
    public int getCardIndexChoice() {
        Scanner in = new Scanner(System.in);
        int cardIndex;
        try {
            cardIndex = in.nextInt()-1;
            in.nextLine();
        } catch(InputMismatchException e) {
            return -1;
        }
        return cardIndex;
    }

    @Override
    public boolean isHitMove() {
        Scanner in = new Scanner(System.in);
        return in.nextLine().contains("h");
    }

    @Override
    public void showStart(Player player) {
        out.println("The game started! " + player.getName() + " is first.");
    }

    @Override
    public void showPlayerInfo(Player player) {
        out.println(player);
    }

    @Override
    public void showUnableToPlay(Player player) {
        out.println(player.getName() + " is unable to play.");
    }

    @Override
    public void askForMove(Player player) {
        out.println(player.getName() + ", hit or pass? (h/p)");
    }

    @Override
    public void askForCardIndexChoice(int handSize) {
        out.println("Choose card (1 to " + handSize + ")");
    }

    @Override
    public void showDamage(Player player, int damage) {
        out.println(player.getName() + " took " + damage + " damage. " + player.getHealth() + " health left.");
    }

    @Override
    public void showCantPlay(Player player) {
        out.println(player.getName() + " can't play this card.");
    }

    @Override
    public void showTurnInfo(Player player) {
        out.println(player.getName() + "'s turn!");
    }

    @Override
    public void showBledOut(Player player) {
        out.println(player.getName() + " has bled out.");
    }

    @Override
    public void showWinner(Player player) {
        out.println("The winner is: " + player.getName());
    }
}
