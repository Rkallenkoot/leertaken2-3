package ttt;

import org.junit.Test;

import static org.junit.Assert.*;


public class TicTacToeTest {

    @Test
    public void testSimpleChooseMove() throws Exception {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setHumanPlays();

        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(3); // Human

        assertEquals(ticTacToe.positionValue(), ticTacToe.UNCLEAR);
    }

    @Test
    public void testPositionValueHuman() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setHumanPlays();

        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(3); // Human
        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(6); // Human

        assertEquals(ticTacToe.positionValue(), ticTacToe.HUMAN_WIN);
    }

    @Test
    public void testPositionValueComputer() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setComputerPlays();

        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(3); // Human
        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(6); // Human

        assertEquals(ticTacToe.positionValue(), ticTacToe.COMPUTER_WIN);
    }

    @Test
    public void testPositionValueDraw() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setComputerPlays();

        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(6); // Human
        ticTacToe.playMove(4); // Computer
        ticTacToe.playMove(5); // Human
        ticTacToe.playMove(3); // Computer
        ticTacToe.playMove(7); // Human
        ticTacToe.playMove(8); // Computer

        assertEquals(ticTacToe.positionValue(), ticTacToe.DRAW);
    }

    @Test
    public void testIsAWinHuman() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setHumanPlays();

        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(3); // Human
        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(6); // Human

        assertEquals(ticTacToe.isAWin(ticTacToe.HUMAN), true);
        assertEquals(ticTacToe.isAWin(ticTacToe.COMPUTER), false);
    }

    @Test
    public void testIsAWinComputer() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setComputerPlays();

        ticTacToe.playMove(4); // Computer
        ticTacToe.playMove(1); // Human
        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(8); // Human
        ticTacToe.playMove(6); // Computer

        assertEquals(ticTacToe.isAWin(ticTacToe.HUMAN), false);
        assertEquals(ticTacToe.isAWin(ticTacToe.COMPUTER), true);
    }

    @Test
    public void testIsAWinDraw() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setComputerPlays();

        ticTacToe.playMove(2); // Computer
        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(6); // Human
        ticTacToe.playMove(4); // Computer
        ticTacToe.playMove(5); // Human
        ticTacToe.playMove(3); // Computer
        ticTacToe.playMove(7); // Human
        ticTacToe.playMove(8); // Computer

        assertEquals(ticTacToe.isAWin(ticTacToe.HUMAN), false);
        assertEquals(ticTacToe.isAWin(ticTacToe.COMPUTER), false);
    }

    @Test
    public void testChooseMove() {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setHumanPlays();

        ticTacToe.playMove(0); // Human
        ticTacToe.playMove(1); // Computer
        ticTacToe.playMove(4); // Human
        ticTacToe.playMove(3); // Computer
        ticTacToe.playMove(6); // Human
        ticTacToe.playMove(8); // Computer
        ticTacToe.playMove(7); // Human

        assertEquals(ticTacToe.chooseMove(), 2);
    }

}