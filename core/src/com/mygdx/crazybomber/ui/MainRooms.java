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

public class MainRooms implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonBack;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch = new SpriteBatch();
    private Repository repository;
    private Screen currentScreen;

    public MainRooms(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void show() {
        currentScreen = this;

        background = new Sprite(new Texture("background1.png"));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);

        heading =  new Label("Rooms", skin);
        heading.setFontScale(2);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.add(heading);
        table.row();

        for (int i = 0; i < repository.getListOfRooms().size(); i++) {
            TextButton button = new TextButton("Room " + (i + 1), skin);
            Room room = repository.getListOfRooms().get(i);
            button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    repository.getListOfStates().push(currentScreen);
                    ((Game)Gdx.app.getApplicationListener()).setScreen(room);
                }
            });
            table.add(button);
            table.getCell(button).size(1000, 150);
            table.row();
        }

        buttonBack = new BackTextButton(skin, repository);
        table.padBottom(50);
        table.add(buttonBack).bottom().left();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
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