//TODO: fade in & out before main menu
package com.mygdx.crazybomber.ui;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.crazybomber.ui.MainMenu;
import com.mygdx.crazybomber.ui.Repository;

public class SplashScreen implements Screen {
    private Sprite background;
    private SpriteBatch batch = new SpriteBatch();

    @Override
    public void show() {
        background = new Sprite(new Texture(("background.jpg")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Repository repository = new Repository();
        MainMenu mainMenu = new MainMenu(repository); //TODO: need to fade in/out before going to next stage
        repository.getListOfStates().push(mainMenu);
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
