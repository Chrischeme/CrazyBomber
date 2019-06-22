//this class a splash that is supposed to fade in/out before the main menu -- it doesn't do that yet but I will later.

package com.mygdx.bomberbois;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadScreen implements Screen {


    private Sprite splash; //the picture
    private SpriteBatch batch; //the frame we're drawing on
    @Override
    public void show() {
        StateRepository stateRepository = new StateRepository();

        Map map1 = new Map((new Texture("badlogic.jpg")),stateRepository); //map1 is the badlogic.jpg map
        Map map2 = new Map((new Texture("splash.png")),stateRepository); //map2 is the spash.png map
        //can add more maps later
        MapRepository mapRepository = new MapRepository();
        mapRepository.addMap(map1);
        mapRepository.addMap(map2);

        batch = new SpriteBatch(); // the paper
        Texture splashTexture = new Texture("splash.png"); // new texture splash.png
        splash = new Sprite(splashTexture); // makes a picture from the texture
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //the size of the graphics/picture we have

        MainMenu mainMenu = new MainMenu(mapRepository,stateRepository); //the mainmenu state
        stateRepository.push(mainMenu);
        ((Game)Gdx.app.getApplicationListener()).setScreen(mainMenu); //runs mainmenu
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,0); //sets color of background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //doing the color of the background
        batch.begin(); //have to begin and end (open the paper)
        splash.draw(batch); //draw splash on the batch / paper
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
