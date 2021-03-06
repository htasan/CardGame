package com.cardgame.controller;

import com.cardgame.model.Player;
import com.cardgame.util.GameRule;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TerminalControllerTest {

    private static final PrintStream SYSTEM_OUT = System.out;
    private static final InputStream SYSTEM_IN = System.in;

    private final OutputStream out = new ByteArrayOutputStream();

    @InjectMocks
    private TerminalController controller;

    @Before
    public void setUpOutStream() {
        System.setOut(new PrintStream(out));
    }

    private InputStream in(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    private Player createTestPlayer() {
        return new Player("TestPlayer",
                GameRule.builder().maxHealth(5).cardDamages(Arrays.asList(1,1,1))
                        .maxHandSize(3).initialNumberOfCards(3).build());
    }

    @Test
    public void shouldReturnHitMove() {
        System.setIn(in("h\n"));
        assertTrue(controller.getHitMove(createTestPlayer()));
        assertEquals("TestPlayer, hit or pass? (h/p)\n", out.toString());
    }

    @Test
    public void shouldReturnPassMove() {
        System.setIn(in("p\n"));
        assertFalse(controller.getHitMove(createTestPlayer()));
        assertEquals("TestPlayer, hit or pass? (h/p)\n", out.toString());
    }

    @Test
    public void shouldReturnPassMoveIfInputIsEmpty() {
        System.setIn(in("\n"));
        assertFalse(controller.getHitMove(createTestPlayer()));
        assertEquals("TestPlayer, hit or pass? (h/p)\n", out.toString());
    }

    @Test
    public void shouldGetCardIndexChoice() {
        System.setIn(in("1\n"));
        assertEquals(0, controller.getCardIndexChoice(createTestPlayer()));
        assertEquals("Choose card (1 to 3)\n", out.toString());
    }

    @Test
    public void shouldCatchInputMismatchException() {
        System.setIn(in("some string\n"));
        assertEquals(-1, controller.getCardIndexChoice(createTestPlayer()));
        assertEquals("Choose card (1 to 3)\n", out.toString());
    }

    @Test
    public void showStart() {
        controller.showStart(createTestPlayer());
        assertEquals("The game started! TestPlayer is first.\n", out.toString());
    }

    @Test
    public void showPlayerInfo() {
        controller.showPlayerInfo(createTestPlayer());
        assertEquals("TestPlayer - Health:5 Mana:0 Hand:[1, 1, 1]\n", out.toString());
    }

    @Test
    public void showUnableToPlay() {
        controller.showUnableToPlay(createTestPlayer());
        assertEquals("TestPlayer is unable to play.\n", out.toString());
    }

    @Test
    public void showDamage() {
        controller.showDamage(createTestPlayer(), 3);
        assertEquals("TestPlayer took 3 damage. 5 health left.\n", out.toString());
    }

    @Test
    public void showCantPlay() {
        controller.showCantPlay(createTestPlayer());
        assertEquals("TestPlayer can't play this card.\n", out.toString());
    }

    @Test
    public void showTurnInfo() {
        controller.showTurnInfo(createTestPlayer());
        assertEquals("TestPlayer's turn!\n", out.toString());
    }

    @Test
    public void showBledOut() {
        controller.showBledOut(createTestPlayer());
        assertEquals("TestPlayer has bled out.\n", out.toString());
    }

    @Test
    public void showWinner() {
        controller.showWinner(createTestPlayer());
        assertEquals("The winner is: TestPlayer\n", out.toString());
    }

    @After
    public void restoreStreams() {
        System.setIn(SYSTEM_IN);
        System.setOut(SYSTEM_OUT);
    }
}
