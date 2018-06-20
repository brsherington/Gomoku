package sample;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by colinstclaire on 9/25/16.
 */
public class Board {
    // 0 = no stone
    // 1 = white stone
    // 2 = black stone
    private final int EMPTY = 0;
    private final int WHITE = 1;
    private final int BLACK = 2;
    private final char HORIZONTAL = 'h'; // horizontal
    private final char VERTICAL = 'v'; // vertical
    private final char NWSE = 'n'; // north-west; south-east;
    private final char SWNE = 's'; // south-west; north-east;

    public int[][] board;
    public int row;
    public int col;
    private Stack<Move> moves;
    public Move winningMove;
    public char winningMoveDirection;

    public Board(int rows, int cols) {
        row = rows;
        col = cols;
        board = new int[row][col];
        moves = new Stack<>();
    }

    public Move move(int row, int col, int color) {
        Move m;
        if (board[row][col] == 0) {
            board[row][col] = color;
            m = new Move(row, col, color);
            moves.push(m);
            return m;
        }
        return m = new Move(0, 0, 0);
    }

    public Move undoMove() {
        Move lastMove = moves.pop();
        board[lastMove.row][lastMove.col] = 0;
        return lastMove;
    }

    public boolean checkForWins() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] != EMPTY) {
                    if (verticalWin(i, j)) {
                        winningMove = new Move(i, j, board[i][j]);
                        winningMoveDirection = VERTICAL;
                        return true;
                    }
                    if (horizontalWin(i, j)) {
                        winningMove = new Move(i, j, board[i][j]);
                        winningMoveDirection = HORIZONTAL;
                        return true;
                    }
                    if (nwseWin(i, j)) {
                        winningMove = new Move(i, j, board[i][j]);
                        winningMoveDirection = NWSE;
                        return true;
                    }
                    if (swneWin(i, j)) {
                        winningMove = new Move(i, j, board[i][j]);
                        winningMoveDirection = SWNE;
                        return true;
                    }
                }
            }
        }
        winningMove = new Move(0, 0, 0);
        return false;
    }

    private boolean verticalWin(int row, int col) {
        if (isValidMoveForWin(row, col, this.VERTICAL)) {
            return ((board[row][col] == WHITE && board[row+1][col] == WHITE && board[row+2][col] == WHITE
                    && board[row+3][col] == WHITE && board[row+4][col] == WHITE)
                    || (board[row][col] == BLACK && board[row+1][col] == BLACK && board[row+2][col] == BLACK
                    && board[row+3][col] == BLACK && board[row+4][col] == BLACK));
        }
        return false;
    }

    private boolean horizontalWin(int row, int col) {
        if (isValidMoveForWin(row, col, this.HORIZONTAL)) {
            return ((board[row][col] == WHITE && board[row][col+1] == WHITE && board[row][col+2] == WHITE
                    && board[row][col+3] == WHITE && board[row][col+4] == WHITE)
                    || (board[row][col] == BLACK && board[row][col+1] == BLACK && board[row][col+2] == BLACK
                    && board[row][col+3] == BLACK && board[row][col+4] == BLACK));
        }
        return false;
    }

    private boolean nwseWin(int row, int col) {
        if (isValidMoveForWin(row, col, this.NWSE)) {
            return ((board[row][col] == WHITE && board[row+1][col+1] == WHITE && board[row+2][col+2] == WHITE
                    && board[row+3][col+3] == WHITE && board[row+4][col+4] == WHITE)
                    || (board[row][col] == BLACK && board[row+1][col+1] == BLACK && board[row+2][col+2] == BLACK
                    && board[row+3][col+3] == BLACK && board[row+4][col+4] == BLACK));
        }
        return false;
    }

    private boolean swneWin(int row, int col) {
        if (isValidMoveForWin(row, col, this.SWNE)) {
            return ((board[row][col] == WHITE && board[row+1][col-1] == WHITE && board[row+2][col-2] == WHITE
                    && board[row+3][col-3] == WHITE && board[row+4][col-4] == WHITE)
                    || (board[row][col] == BLACK && board[row+1][col-1] == BLACK && board[row+2][col-2] == BLACK
                    && board[row+3][col-3] == BLACK && board[row+4][col-4] == BLACK));
        }
        return false;
    }

    private boolean isValidMoveForWin(int row, int col, char direction) {
        switch (direction) {
            case VERTICAL:
                return (row >= 0) && (row <= this.row - 5);
            case HORIZONTAL:
                return (col >= 0) && (col <= this.col - 5);
            case NWSE:
                return (row >= 0) && (row <= this.row - 5) && (col >= 0) && (col <= this.col - 5);
            case SWNE:
                return (row >= 0) && (row <= this.row - 5) && (col >= 4) && (col < this.col);
            default:
                return false;
        }
    }

    public static void printBoard(int[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Board b = new Board(15, 15);
        Board.printBoard(b.board);
        for (int i = 0, j = 4; i < 5; i++, j--) {
            b.move(0, i, 1);
        }
        //b.move(0,0,1);
        Board.printBoard(b.board);
        System.out.println(b.checkForWins());
        if (b.checkForWins()) {
            System.out.println(b.winningMove.row + " " + b.winningMove.col + " " + b.winningMove.color + " " + b.winningMoveDirection);
        }
    }
}