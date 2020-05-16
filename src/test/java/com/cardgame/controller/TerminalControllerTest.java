package com.cardgame.controller;

import com.cardgame.model.Player;
import com.cardgame.util.GameRule;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
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

    @Test
    public void shouldCardIndexChoice() {
        System.setIn(in("1\n"));
        assertEquals(0, controller.getCardIndexChoice());
    }

    @Test
    public void shouldCatchInputMismatchException() {
        System.setIn(in("some string\n"));
        assertEquals(-1, controller.getCardIndexChoice());
    }

    @Test
    public void shouldReturnHitMove() {
        System.setIn(in("h\n"));
        assertTrue(controller.isHitMove());
    }

    @Test
    public void shouldReturnPassMove() {
        System.setIn(in("p\n"));
        assertFalse(controller.isHitMove());
    }

    @Test
    public void shouldReturnPassMoveIfInputIsEmpty() {
        System.setIn(in("\n"));
        assertFalse(controller.isHitMove());
    }

    private Player createTestPlayer() {
        return new Player("TestPlayer", GameRule.builder().build());
    }

    @Test
    public void showStart() {
        controller.showStart(createTestPlayer());
        assertEquals("The game started! TestPlayer is first.\n", out.toString());
    }

    @Test
    public void showPlayerInfo() {
        controller.showPlayerInfo(createTestPlayer());
        assertEquals("TestPlayer - Health:0 Mana:0 Hand:[]\n", out.toString());
    }

    @Test
    public void showUnableToPlay() {
        controller.showUnableToPlay(createTestPlayer());
        assertEquals("TestPlayer is unable to play.\n", out.toString());
    }

    @Test
    public void askForMove() {
        controller.askForMove(createTestPlayer());
        assertEquals("TestPlayer, hit or pass? (h/p)\n", out.toString());
    }

    @Test
    public void askForCardIndexChoice() {
        controller.askForCardIndexChoice(5);
        assertEquals("Choose card (1 to 5)\n", out.toString());
    }

    @Test
    public void showDamage() {
        controller.showDamage(createTestPlayer(), 3);
        assertEquals("TestPlayer took 3 damage. 0 health left.\n", out.toString());
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
