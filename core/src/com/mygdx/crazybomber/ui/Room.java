//TODO: when player enters, avatar is red for not ready, if blank, leave blank
package com.mygdx.crazybomber.ui;
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
    private Sprite background,avatar, avatarReady, avatarNotReady;
    private SpriteBatch batch = new SpriteBatch();
    private Stage stage;
    private Table table;
    private TextButton buttonBack, buttonReady, buttonStart;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Texture splashTexture;
    private Repository repository;
    private boolean ready;
    private Screen currentScreen;

    public Room (Texture texture, Repository repository) {
        this.repository = repository;
        this.splashTexture = texture;
    }

    @Override
    public void show() {
        currentScreen = this;
        ready = false;

        background = new Sprite(splashTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        avatarReady = new Sprite(new Texture("ready.png"));
        avatarReady.setPosition(100,300);
        avatarReady.setSize(200,200);
        avatarNotReady = new Sprite(new Texture("notready.png"));
        avatarNotReady.setPosition(100,300);
        avatarNotReady.setSize(200,200);
        avatar = avatarNotReady;

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);

        buttonBack = new BackTextButton(skin, repository);

        buttonReady = new TextButton("READY", skin);
        buttonReady.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ready = !ready;
                avatar = ready ? avatarReady : avatarNotReady;
            }
        });

        buttonStart = new TextButton("START", skin);
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ready) {
                    repository.getListOfStates().push(currentScreen);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(repository.getListOfMaps().get(0)); // default map for now
                    //TODO: if we're doing multiple maps, the flow has to change
                }
            }
        });

        heading =  new Label("LOBBY", skin);
        heading.setFontScale(2);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        background.draw(batch);
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
