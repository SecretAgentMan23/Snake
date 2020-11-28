package personal.casey.snale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Controls {
    private int currentDirection = 0; //0,1,2,3 U,R,D,L
    private int nextDirection = 0;
    private Vector2 touch = new Vector2();

    //static variables for the Directions to make them easier to follow
    private static int UP = 0;
    private static int RIGHT = 1;
    private static int DOWN = 2;
    private static int LEFT = 3;

    private Rectangle upBox = new Rectangle(235, 265, 130, 135);
    private Rectangle downBox = new Rectangle(235, 0, 130, 135);
    private Rectangle leftBox = new Rectangle(65, 135, 130, 135);
    private Rectangle rightBox = new Rectangle(365, 135, 130, 135);

    public int getDirection(){
        currentDirection = nextDirection;
        return nextDirection;
    }

    public void update(Viewport viewport) {
        if(Gdx.input.isTouched()){
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            viewport.unproject(touch);
        }
        if(upBox.contains(touch) && (currentDirection != DOWN)) nextDirection = UP;
        else if(rightBox.contains(touch) && (currentDirection != LEFT)) nextDirection = RIGHT;
        else if(downBox.contains(touch) && (currentDirection != UP)) nextDirection = DOWN;
        else if(leftBox.contains(touch) && (currentDirection != RIGHT)) nextDirection = LEFT;
    }
}
