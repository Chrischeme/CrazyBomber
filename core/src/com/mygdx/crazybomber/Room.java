//TODO: when player enters, avatar is red for not ready, if blank, leave blank
package com.mygdx.crazybomber;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Room implements Screen {
    private Sprite splash,avatar, avatarReady, avatarNotReady;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private TextButton buttonBack, buttonReady, buttonStart;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Texture splashTexture;
    private MapRepository mapRepository;
    private StateRepository stateRepository;
    private boolean ready = false;

    public Room (Texture texture, final StateRepository stateRepository, MapRepository mapRepository) {
        this.mapRepository = mapRepository;
        this.splashTexture = texture;
        this.stateRepository = stateRepository;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        avatarReady = new Sprite(new Texture("ready.png"));
        avatarReady.setPosition(100,300);
        avatarReady.setSize(200,200);
        avatarNotReady = new Sprite(new Texture("notready.png"));
        avatarNotReady.setPosition(100,300);
        avatarNotReady.setSize(200,200);
        avatar = avatarNotReady;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        buttonBack = new TextButton("BACK", skin);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(stateRepository.peek());
            }
        });

        buttonReady = new TextButton("READY", skin);
        buttonReady.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ready == false){
                    ready = true;
                    avatar = avatarReady;
                }
                else {
                    ready = false;
                    avatar = avatarNotReady;
                }
            }
        });

        buttonStart = new TextButton("START", skin);
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: if logic to check if everyone's ready
                ((Game) Gdx.app.getApplicationListener()).setScreen(mapRepository.getMap(1)); // default map for now
                //TODO: if we're doing multiple maps, the flow has to change
            }
        });

        heading =  new Label("LOBBY", skin);
        heading.setFontScale(2);
        table.add(heading);
        table.row();
        table.add(buttonReady);
        table.getCell(buttonReady).size(1000,100);
        table.getCell(buttonReady).padBottom(300);
        table.row();
        table.add(buttonStart);
        table.row();
        table.add(buttonBack);
        table.getCell(buttonBack).bottom().left();
        table.debug();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        splash.draw(batch);
        avatar.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
