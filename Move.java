package sample;

/**
 * Created by colinstclaire on 9/25/16.
 */
public class Move {
    // 1 = white
    // 2 = black
    int color;
    int row;
    int col;

    public Move(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
}
