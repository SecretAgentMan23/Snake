package personal.casey.snale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
    //reference to the main class so we can access the Batch
    private main game;
    private GameState gameState = new GameState();

    //Size of the game screen
    private int width = 600;
    private int height = 1000;

    //Creating a new camera and viewport for the camera. This is so LibGDX knows we are making a 2D game.
    private OrthographicCamera camera = new OrthographicCamera(width, height);
    private FitViewport viewport;
    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("sgx/sgx.json"));
    private Controls controls = new Controls(); // Controls object it to use the controls class

    //Class gets passed the references to main and uses the camera with the view port to create the screen
    //The FitViewport class makes it so that the screen will scale for any device that it is on
    public GameScreen(main game){
        this.game = game;
        camera.setToOrtho(false, width, height);
        viewport = new FitViewport(width, height, camera);
        viewport.apply();
        stage = new Stage(viewport);

        // This will be the screen and all the buttons.
        TextButton UP, DOWN, RIGHT, LEFT;
        UP = new TextButton("", skin, "emphasis-colored");
        DOWN = new TextButton(" ",skin, "emphasis-colored");
        LEFT = new TextButton("", skin, "emphasis-colored" );
        RIGHT = new TextButton("", skin, "emphasis-colored");
        UP.setPosition(235,265);
        UP.setSize(130f, 135f);
        RIGHT.setPosition(365, 135);
        RIGHT.setSize(130f, 135f);
        DOWN.setPosition(235, 0);
        DOWN.setSize(130f, 135f);
        LEFT.setPosition(105, 135);
        LEFT.setSize(130f, 135f);
        stage.addActor(UP);
        stage.addActor(DOWN);
        stage.addActor(LEFT);
        stage.addActor(RIGHT);
        Gdx.input.setInputProcessor(stage);
        //Now lets add the frame around the playable screen

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw((new Texture(Gdx.files.internal("background.png"))),0,0, width,height);
        stage.getBatch().end();
        stage.draw();

        //This makes sure that the viewport gets updated whenever the screen size is changed.
        camera.update();
        viewport.apply();
        gameState.update(delta, viewport, controls);

        //Gdx.gl.glClearColor(0,0,0,1);
        //Gdx.gl.glClearColor(255/255f,113/255f,206/255f, 1);
        gameState.draw(width, height, camera, game);

    }

    @Override
    public void resize(int width, int height) {
       stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
