package ttt;

import java.util.Random;

public class TicTacToe
{
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public  static final int EMPTY        = 2;

    public  static final int HUMAN_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int COMPUTER_WIN = 3;

    private int[][] board = new int[3][3];

    private Random random = new Random();
    private int side = random.nextInt(2);
    private int position = UNCLEAR;
    private char computerChar,humanChar;

    // Constructor
    public TicTacToe( ) {
        clearBoard( );
        initSide();
    }

    private void initSide() {
        if (this.side == COMPUTER) {
            computerChar = 'X';
            humanChar = 'O';
        }
        else {
            computerChar = 'O';
            humanChar = 'X';
        }
    }

    public void setComputerPlays() {
        this.side = COMPUTER;
        initSide();
    }

    public void setHumanPlays() {
        this.side = HUMAN;
        initSide();
    }

    public boolean computerPlays() {
        return side == COMPUTER;
    }

    public int chooseMove() {
        Best best = chooseMove(COMPUTER);
        return best.row * 3 + best.column;
    }

    // Find optimal move
    private Best chooseMove(int side) {
        int opp = side == COMPUTER ? HUMAN : COMPUTER; // The other side
        Best reply;           // Opponent's best reply
        int simpleEval;       // Result of an immediate evaluation
        int checkBoard[][] = board.clone();
        int bestRow = 0;
        int bestColumn = 0;

        int value = side  == COMPUTER ? HUMAN_WIN: COMPUTER_WIN;

        if((simpleEval = positionValue()) != UNCLEAR) {
            return new Best(simpleEval);
        }

        for (int y = 0; y < checkBoard[0].length; y++) {
            for (int x = 0; x < checkBoard[1].length; x++) {
                if(!squareIsEmpty(x, y) || !moveOk(x,y)){
                    // Continue if square is not not empty or move not Ok
                    // moveOk currently does the same as square not empty**
                    continue;
                }
                place(x,y, side);
                reply = chooseMove(opp);
                if(side == COMPUTER ? reply.val > value : reply.val < value){
                    value = reply.val;
                    bestRow = x;
                    bestColumn = y;
                }
                place(x, y, EMPTY);
            }
        }

        return new Best(value, bestRow, bestColumn);
    }


    //check if move ok
    public boolean moveOk(int move) {
        return (move >= 0 && move <=8 && board[move / 3][move % 3] == EMPTY);
    }

    public boolean moveOk(int x, int y){
        if(x < 0 || x > 2 || y < 0 || y > 2){
            return false;
        }
        return squareIsEmpty(x,y);
    }

    // play move
    public void playMove(int move) {
        board[move / 3][ move % 3] = this.side;
        if (side==COMPUTER) {
            this.side = HUMAN;
        }
        else {
            this.side = COMPUTER;
        }
    }


    // Simple supporting routines
    private void clearBoard( ) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                board[y][x] = EMPTY;
            }
        }
    }


    private boolean boardIsFull() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                int cell = board[x][y];
                if(cell == EMPTY){
                    return false;
                }
            }
        }

        return true;
    }

    // Returns whether 'side' has won in this position
    public boolean isAWin(int side) {
        // all winning positions
        int [][] winningPositions = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 },

                { 0, 3, 6 },
                { 1, 4, 7 },
                { 2, 5, 8 },

                { 0, 4, 8 },
                { 2, 4, 6 },
        };

        for (int[] winningPosition : winningPositions) {
            // if every position in this winning position is from the same Side, then Side has won
            if (getPlace(winningPosition[0]) == side &&
                    getPlace(winningPosition[1]) == side &&
                    getPlace(winningPosition[2]) == side) {
                return true;
            }
        }

        return false;
    }

    // Play a move, possibly clearing a square
    private void place(int row, int column, int piece)
    {
        board[row][column] = piece;
    }

    private int getPlace(int move){
        return board[move / 3][move % 3];
    }

    // TODO: 3/8/2016 out of bound array exception? - Roelof
    private boolean squareIsEmpty( int row, int column )
    {
        return board[row][column] == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    public int positionValue( )
    {

        if(isAWin(HUMAN)) {
            return HUMAN_WIN;
        }
        else if(isAWin(COMPUTER)) {
            return COMPUTER_WIN;
        }
        else if(boardIsFull()) {
            return DRAW;
        }

        return UNCLEAR;
    }


    public String toString()
    {
        StringBuilder output = new StringBuilder();

        for (int x = 0; x < board[0].length; x++) {
            for (int y = 0; y < board[1].length; y++) {
                int cell = board[x][y];
                if(cell == HUMAN){
                    output.append(humanChar);
                } else if (cell == COMPUTER){
                    output.append(computerChar);
                } else {
                    output.append('.');
                }
            }
            output.append('\n');
        }
        return output.toString();
    }

    public boolean gameOver() {
        this.position = positionValue();
        return this.position != UNCLEAR;
    }

    public String winner() {
        if(this.position==COMPUTER_WIN){
            return "computer";
        }
        else if(this.position==HUMAN_WIN){
            return "human";
        }
        else{
            return "nobody";
        }
    }


    private class Best {
        int row;
        int column;
        int val;

        public Best( int v ){
            this(v, 0, 0);
        }

        public Best(int v, int r, int c){
            val = v; row = r; column = c;
        }
    }

}
