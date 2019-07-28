//TODO: player set name in order to progress to picking a room
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.crazybomber.CrazyBomber;

public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch = new SpriteBatch();
    private Repository repository;
    private Screen currentScreen;

    public MainMenu(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void show() {
        currentScreen = this;

        background = new Sprite(new Texture(("backgroundimg3.png")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        heading =  new Label(CrazyBomber.TITLE, skin);
        heading.setFontScale(2);

        buttonExit = new TextButton("EXIT",skin);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonPlay = new TextButton("PLAY",skin);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                repository.getListOfStates().push(currentScreen);
                MainRooms mainRooms = new MainRooms(repository);
                ((Game)Gdx.app.getApplicationListener()).setScreen(mainRooms);
            }
        });

        table.add(heading);
        table.getCell(heading).spaceBottom(10);
        table.row();
        table.padBottom(1);
        table.add(buttonPlay);
        table.row();
        table.add(buttonExit);

        stage = new Stage();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        background.draw(batch);
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
