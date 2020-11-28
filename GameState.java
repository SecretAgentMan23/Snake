package personal.casey.snale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.audio.Sound;

public class GameState {
    private int boardSize = 30; //How many squares are on the board
    private int yOffset = 400; //How high the board is off the bottom
    private Food mFood = new Food(boardSize); // object from the Food class to handle the pellets
    private Queue<BodyPart> mBody = new Queue<BodyPart>(); // FIFO queue of body parts used to represent the snake
    private Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
    private BitmapFont myFont = new BitmapFont(Gdx.files.internal("sgx/font-export.fnt"));
    private Controls controls;
    private Pixmap pixmap;
    Texture whiteTex;



    private float mTimer = 0;
    private int snakeLength = 3;
    private int score = 0;

    public GameState() {
        mBody.addLast(new BodyPart(15,15,boardSize));
        mBody.addLast(new BodyPart(15,14,boardSize));
        mBody.addLast(new BodyPart(15,13,boardSize));
        pixmap = new Pixmap(2,2, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(0,0,32,32);
        whiteTex = new Texture(pixmap);
        pixmap.dispose();
    }

    public void update(float delta, Viewport viewport, Controls controls){
        mTimer += delta;
        this.controls = controls;
        controls.update(viewport);
        if (mTimer > 0.13f) {
            mTimer = 0;
            advance();
        }
    }
    // This function does not need to be edited to changed to the Scene2d functions
    private void advance(){
        //mBody.addFirst(new BodyPart(mBody.first().getX(), mBody.first().getY()+1, boardSize));
        //mBody.removeLast();

        int headX = mBody.first().getX();
        int headY = mBody.first().getY();
        switch(controls.getDirection()){
            case 0: // UP
                mBody.addFirst((new BodyPart(headX, headY+1, boardSize)));
                break;
            case 1: // RIGHT
                mBody.addFirst(new BodyPart(headX+1, headY, boardSize));
                break;
            case 2: // DOWN
                mBody.addFirst(new BodyPart(headX, headY-1, boardSize));
                break;
            case 3: // LEFT
                mBody.addFirst(new BodyPart(headX-1, headY, boardSize));
                break;
        }
        if (mBody.first().getX() == mFood.getX() && mBody.first().getY() == mFood.getY()) {
            snakeLength++;
            score++;
            dropSound.play();
            boolean flag = true;
            do {
                mFood.randomisePos(boardSize); //TODO check if its not in the body
                for(int i = 1; i < mBody.size; i++){
                    if((mBody.get(i).getX() == mFood.getX()) && (mBody.get(i).getY() == mFood.getY())){
                        mFood.randomisePos(boardSize);
                        i = 1;
                    }
                }
                flag = false;
            } while(flag);
        }

        for (int i =1; i < mBody.size; i++){
            if ((mBody.get(i).getX() == mBody.first().getX())
                && (mBody.get(i).getY() == mBody.first().getY())) {
            snakeLength = 3;
            score = 0;
            }
        }

        while (mBody.size -1 >= snakeLength){
            mBody.removeLast();
        }
    }

    public void draw(int width, int height, OrthographicCamera camera, main game){
        //shapeRenderer.setProjectionMatrix(camera.combined);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //White box
        game.batch.begin();


        /*
        pixmap.setColor(0,0,0,1);
        pixmap.fillRectangle(0,0,32,32);
        Texture blackTex = new Texture(pixmap);
        pixmap.setColor(5/255f, 255/255f, 161/255f, 1);
        pixmap.fillRectangle(0,0,32,32);
        Texture controlTex = new Texture(pixmap);
        pixmap.dispose();
        game.batch.draw(whiteTex, 0, yOffset, width*2, height*2);
        game.batch.draw(blackTex,0+5, yOffset+5, width-5*2, height-5*2);
        game.batch.draw(controlTex, 235,265,130,135);//top
        game.batch.draw(controlTex, 235,0,130,135);//bottom
        game.batch.draw(controlTex, 105,135,130,130);//left
        game.batch.draw(controlTex, 365,135,130,130);//right
        */
        myFont.draw(game.batch, "Score: " + score, 0, yOffset);
        float scaleSnake = width/boardSize;
        for(BodyPart bp : mBody){
            game.batch.draw(whiteTex, bp.getX()*scaleSnake, bp.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        //Food
        game.batch.draw(whiteTex, mFood.getX()*scaleSnake, mFood.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);

        game.batch.end();
    }
}
