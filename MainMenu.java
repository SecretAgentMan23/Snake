package personal.casey.snale;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MainMenu implements Screen {
    private main game;
    private Skin skin;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Table table;
    private Music mainMusic;
    private Slider volSlider;
    public static float mastervol = .5f;

    private int width = 600;
    private int height = 1000;
    private Pixmap pixmap;

    public MainMenu(final main game){
        this.game = game;
        //Music from Bensound.com https://www.bensound.com/royalty-free-music/track/summer-chill-relaxed-tropical
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("bensound-summer.wav"));
        mainMusic.setLooping(true);
        mainMusic.setVolume(mastervol);
        mainMusic.play();
        skin = new Skin(Gdx.files.internal("sgx/sgx.json"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        viewport = new FitViewport(width, height, camera);
        viewport.apply();
        stage = new Stage(viewport);
        Table tTable = new Table();
        //final Slider volSlider;
        final Table menuTable = new Table();
        menuTable.setFillParent(true);
        final Table settingsTable = new Table();
        settingsTable.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        tTable.setFillParent(true);
        /*
        Label.LabelStyle style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("MyFont.fnt"),
                Gdx.files.internal("MyFont.png"), false);
        style.font = myFont;
        */
        Label title = new Label("SNAKE", skin, "title", "font");


        tTable.add(title).padTop(100);
        stage.addActor(tTable.center().top());



        final TextButton PlayButton = new TextButton("Play Game", skin, "menu-button");
        final TextButton settingsButton = new TextButton("Settings", skin, "menu-button");
        TextButton exitButton = new TextButton("Exit Game", skin, "menu-button");
        volSlider = new Slider(0, 1, .01f, false, skin);
        final TextButton backButton = new TextButton("Main Menu", skin, "menu-button");

        menuTable.add(PlayButton).width(PlayButton.getWidth()).height(PlayButton.getHeight());
        menuTable.row();
        menuTable.add(settingsButton).width(PlayButton.getWidth()).height(PlayButton.getHeight());
        menuTable.row();
        menuTable.add(exitButton).width(PlayButton.getWidth()).height(PlayButton.getHeight());
        stage.addActor(menuTable);
        Label vol = new Label("Vol:", skin, "font", "font");
        settingsTable.add(vol);
        settingsTable.add(volSlider);
        settingsTable.row();
        settingsTable.add(backButton).width(PlayButton.getWidth()+20).height(PlayButton.getHeight()).colspan(2);


        PlayButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen((new GameScreen(game)));
                dispose();
            }
        });

        settingsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
               menuTable.addAction(Actions.removeActor());
               stage.addActor(settingsTable);
               volSlider.setValue(mainMusic.getVolume());
            }
        });

        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor){
                System.exit(0);
            }
        });

        backButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                settingsTable.remove();
                stage.addActor(menuTable);
            }
        });

        volSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                mastervol = volSlider.getValue();
                mainMusic.setVolume(mastervol);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0,0,0,1);
        //Gdx.gl.glClearColor(255/255f,113/255f,206/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw((new Texture(Gdx.files.internal("background.png"))),0,0,width,height);
        stage.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        game.batch.begin();
        stage.draw();
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) { stage.getViewport().update(width,height, true); }

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
    stage.dispose();
    skin.dispose();
    }

}
