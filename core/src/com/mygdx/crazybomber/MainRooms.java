//TODO: right now this is basically just MainMaps and goes to the map class,
// Need to make it go to a room instead, which is the room class
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

public class MainRooms implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonRoom1,buttonRoom2,buttonRoom3,buttonBack;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite background;
    private SpriteBatch batch;
    private Repository repository;

    public MainRooms(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Sprite(new Texture("background1.png"));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        heading =  new Label("Rooms", skin);
        heading.setFontScale(2);
        table.add(heading);
        table.row();

        int i = 0;
        for (Room room : repository.listOfRooms) {
            TextButton button = new TextButton("ROOM " + (i + 1), skin);
            int j = i;
            button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(repository.getRoom(j));
                }
            });
            table.add(button);
            table.getCell(button).size(1000, 150);
            table.row();
            i++;
        }
        buttonBack = new TextButton("BACK",skin);
        buttonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                repository.pop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(repository.peek());
            }
        });
        table.padBottom(50);
        table.add(buttonBack).bottom().left();
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