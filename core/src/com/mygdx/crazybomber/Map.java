package com.mygdx.crazybomber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.crazybomber.Player;


/* just notes for myself
make texture a field in player - done
in show method, new player instead of new sprite, copy over the draw method from sprite to player class - done
send to server whenever making a new map - gotta do this
use the game state data to populate and render
spawn the player class and move it
gotta pass state repository to map, for back button */

public class Map implements Screen {
    private Sprite splash;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private TextButton buttonBack;
    private Label heading;
    private Skin skin;
    private BitmapFont black, white;
    private TextureAtlas atlas;
    private Texture splashTexture;
    private StateRepository stateRepository;
    private Player player;
    private Texture playerTexture;
    boolean isPressedUP, isPressedDOWN, isPressedRIGHT, isPressedLEFT, isPressedW, isPressedS, isPressedD, isPressedA, isPressedSPACE, isPressedSHIFT;

    public Map (Texture texture, final StateRepository stateRepository) {
        this.splashTexture = texture; // gonna take in a texture instead of int
        this.stateRepository = stateRepository;
    }

    @Override
    public void show() {
        playerTexture = new Texture("player.png");
        batch = new SpriteBatch();
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        player = new Player(0f, 0f, playerTexture);
        player.setSize(50,50);
        player.setPosition(615,600);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas); //using this for the text button
        table = new Table(skin); //table with the skin's atlas
        table.setBounds(1050, 600, 100,100 );

        white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1; //when button is pressed, text moves to right by 1
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonBack = new TextButton("BACK", textButtonStyle);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(stateRepository.peek());
            }
        });


        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
        heading =  new Label(CrazyBomber.TITLE, headingStyle);
        heading.setFontScale(2); //doubles the font size
        table.add(heading);
        table.getCell(heading).spaceBottom(10);
        table.row();
        table.padBottom(1);
        table.row();
        table.add(buttonBack);
        table.debug();

        stage.addActor(table);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        splash.draw(batch);


        isPressedUP = Gdx.input.isKeyPressed(Input.Keys.UP);
        isPressedDOWN = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        isPressedRIGHT = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        isPressedLEFT =  Gdx.input.isKeyPressed(Input.Keys.LEFT);
        isPressedW = Gdx.input.isKeyPressed(Input.Keys.W);
        isPressedS = Gdx.input.isKeyPressed(Input.Keys.S);
        isPressedD = Gdx.input.isKeyPressed(Input.Keys.D);
        isPressedA = Gdx.input.isKeyPressed(Input.Keys.A);
        isPressedSHIFT = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT); //run
        isPressedSPACE = Gdx.input.isKeyPressed(Input.Keys.SPACE); //runevenfaster
        char direction = '\0';
        if (isPressedUP == true || isPressedW == true)
        {
            direction = 'W';
        }
        if (isPressedDOWN == true || isPressedS == true)
        {
            direction = 'S';
        }
        if (isPressedLEFT == true || isPressedA == true)
        {
            direction = 'A';
        }
        if (isPressedRIGHT == true || isPressedD == true)
        {
            direction = 'D';
        }
        if (direction != '\0') {
            player.move(direction);  //kek, kek
        }

        player.draw(batch); //doesnt work rn
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
