package personal.casey.snale;

import com.badlogic.gdx.math.MathUtils;

public class Food {
    // X and Y represent the coordinates of a piece of food
    private int x,y;

    // These are the methods the get the postion of the food and will be used
    // to draw the food onto the game screen
    public int getX() { return x; }
    public int getY() { return y; }

    // This fucntion sets random values for the x and y to determine the position
    // of the food based on the boardsize
    public void randomisePos(int boardSize){
        x = MathUtils.random(boardSize-1);
        y = MathUtils.random(boardSize-1);
    }

    public Food(int boardSize){
        randomisePos(boardSize);
    }
}
