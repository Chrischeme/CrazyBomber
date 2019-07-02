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
    private Sprite splash;
    private SpriteBatch batch;
    private MapRepository mapRepository;
    private RoomRepository roomRepository;
    private StateRepository stateRepository;

    public MainRooms(MapRepository mapRepository, RoomRepository roomRepository, StateRepository stateRepository) {
        this.mapRepository = mapRepository;
        this.roomRepository = roomRepository;
        this.stateRepository = stateRepository;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        splash = new Sprite(new Texture("background1.png"));
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        buttonRoom1 = new TextButton("ROOM1",skin);
        buttonRoom1.setWidth(1000);
        buttonRoom1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(roomRepository.getRoom(0));
            }
        });
        buttonRoom2 = new TextButton("ROOM2",skin);
        buttonRoom2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(roomRepository.getRoom(1));
            }
        });
        buttonRoom3 = new TextButton("ROOM3",skin);
        buttonRoom3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(roomRepository.getRoom(2));
            }
        });
        buttonBack = new TextButton("BACK",skin);
        buttonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stateRepository.pop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(stateRepository.peek());
            }
        });
        heading =  new Label("Rooms", skin);
        heading.setFontScale(2);
        table.add(heading);
        table.row();
        table.row();
        table.padBottom(50);
        table.add(buttonRoom1);
        table.getCell(buttonRoom1).size(1000,150);
        table.row();
        table.add(buttonRoom2);
        table.getCell(buttonRoom2).size(1000,150);
        table.row();
        table.add(buttonRoom3);
        table.getCell(buttonRoom3).size(1000,150);
        table.row();
        table.add(buttonBack).bottom().left();
        table.debug();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        splash.draw(batch);
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