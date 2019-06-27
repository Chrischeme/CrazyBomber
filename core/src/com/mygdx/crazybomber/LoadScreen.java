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
    private Sprite splash;
    private SpriteBatch batch;
    @Override
    public void show() {
        StateRepository stateRepository = new StateRepository();
        MapRepository mapRepository = new MapRepository();
        Map map = new Map((new Texture("background.jpg")),stateRepository);
        Map map1 = new Map((new Texture("background2.png")),stateRepository); //TODO: this should be room1,room2 etc
        mapRepository.addMap(map);
        mapRepository.addMap(map1);

        batch = new SpriteBatch();
        Texture splashTexture = new Texture("background.jpg");
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        MainMenu mainMenu = new MainMenu(mapRepository,stateRepository);
        stateRepository.push(mainMenu);
        ((Game)Gdx.app.getApplicationListener()).setScreen(mainMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        splash.draw(batch);
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
