//TODO: player set name in order to progress to picking a room
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch;
    private Repository repository;
    private TextField usernameTextField;

    public MainMenu(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void show() {
        final MainRooms mainRooms = new MainRooms(repository);

        batch = new SpriteBatch();
        background = new Sprite(new Texture(("background.jpg")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        usernameTextField = new TextField("YEEAA BOIIIIIIIIIIIII", skin);
        usernameTextField.setPosition(300,600);
        usernameTextField.setSize(1000, 30);

        heading =  new Label(CrazyBomber.TITLE, skin);
        heading.setFontScale(2); //doubles the font size

        buttonExit = new TextButton("EXIT",skin);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonPlay = new TextButton("PLAY",skin); //clickable play button
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                repository.push(mainRooms);
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
        table.debug();
        stage.addActor(table);
        stage.addActor(usernameTextField);
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
            stage.act();
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
