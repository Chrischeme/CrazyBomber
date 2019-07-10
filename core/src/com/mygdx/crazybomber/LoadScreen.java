//TODO: fade in & out before main menu

package com.mygdx.crazybomber;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadScreen implements Screen {
    private Sprite background;
    private SpriteBatch batch;
    @Override
    public void show() {
        StateRepository stateRepository = new StateRepository();
        MapRepository mapRepository = new MapRepository();
        RoomRepository roomRepository = new RoomRepository();

        Map map = new Map((new Texture("background.jpg")),stateRepository);
        Map map1 = new Map((new Texture("background2.png")),stateRepository); //keeping maps & maprepository in case future use
        mapRepository.addMap(map);
        mapRepository.addMap(map1);

        Room room = new Room((new Texture("background.jpg")),stateRepository, mapRepository);
        Room room1 = new Room((new Texture("background1.png")),stateRepository, mapRepository);
        Room room2 = new Room((new Texture("background2.png")), stateRepository, mapRepository);
        roomRepository.addRoom(room);
        roomRepository.addRoom(room1);
        roomRepository.addRoom(room2);
        batch = new SpriteBatch();
        background = new Sprite(new Texture(("background.jpg")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        MainMenu mainMenu = new MainMenu(mapRepository,roomRepository,stateRepository); //TODO: need to fade in/out before going to next stage
        stateRepository.push(mainMenu);
        ((Game)Gdx.app.getApplicationListener()).setScreen(mainMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
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
