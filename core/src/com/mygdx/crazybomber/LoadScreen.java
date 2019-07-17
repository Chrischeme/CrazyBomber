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

    public static Repository populateRepository()
    {
        Repository repository = new Repository();
        Map map = new Map((new Texture("background.jpg")),repository);
        Map map1 = new Map((new Texture("background2.png")),repository); //keeping maps & maprepository in case future usemapRepository.addMap(map);
        repository.addMap(map);
        repository.addMap(map1);
        Room room = new Room((new Texture("background.jpg")),repository, repository);
        Room room1 = new Room((new Texture("background1.png")),repository, repository);
        Room room2 = new Room((new Texture("background2.png")), repository, repository);
        repository.addRoom(room);
        repository.addRoom(room1);
        repository.addRoom(room2);
        return repository;
    }

    @Override
    public void show() {
       /* Repository stateRepository = new Repository();
        Repository mapRepository = new Repository();
        Repository roomRepository = new Repository();

        Map map = new Map((new Texture("background.jpg")),stateRepository);
        Map map1 = new Map((new Texture("background2.png")),stateRepository); //keeping maps & maprepository in case future usemapRepository.addMap(map);
        mapRepository.addMap(map);
        mapRepository.addMap(map1);

        Room room = new Room((new Texture("background.jpg")),stateRepository, mapRepository);
        Room room1 = new Room((new Texture("background1.png")),stateRepository, mapRepository);
        Room room2 = new Room((new Texture("background2.png")), stateRepository, mapRepository);
        roomRepository.addRoom(room);
        roomRepository.addRoom(room1);
        roomRepository.addRoom(room2); */
        batch = new SpriteBatch();
        background = new Sprite(new Texture(("background.jpg")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        MainMenu mainMenu = new MainMenu(populateRepository()); //TODO: need to fade in/out before going to next stage
        populateRepository().push(mainMenu);
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
