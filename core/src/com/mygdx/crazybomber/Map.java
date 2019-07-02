//TODO: do we need a back button here? if we do, should it go back to the room or all the way to the mainrooms? 
package com.mygdx.crazybomber;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Map implements Screen {
    private Sprite splash;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private TextButton buttonBack;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Texture splashTexture;
    private StateRepository stateRepository;
    private Player player;
    private Texture playerTexture;
    boolean isPressedUP, isPressedDOWN, isPressedRIGHT, isPressedLEFT, isPressedW, isPressedS, isPressedD, isPressedA, isPressedSPACE, isPressedSHIFT;

    public Map (Texture texture, final StateRepository stateRepository) {
        this.splashTexture = texture;
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
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(1050, 600, 100,100 );

        buttonBack = new TextButton("BACK", skin);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(stateRepository.peek());
            }
        });

        heading =  new Label(CrazyBomber.TITLE, skin);
        heading.setFontScale(2);
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
            player.move(direction);
        }

        player.draw(batch);
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
