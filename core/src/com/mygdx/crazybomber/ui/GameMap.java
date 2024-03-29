//TODO: Back button will prob be in a menu settings somewhere, think about this later
package com.mygdx.crazybomber.ui;
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
import com.mygdx.crazybomber.CrazyBomber;
import com.mygdx.crazybomber.model.block.Block;
import com.mygdx.crazybomber.model.block.BreakableBlock;
import com.mygdx.crazybomber.model.block.EmptyBlock;
import com.mygdx.crazybomber.model.block.UnbreakableBlock;
import com.mygdx.crazybomber.model.bomb.Bomb;
import com.mygdx.crazybomber.model.gameState.GameState;
import com.mygdx.crazybomber.model.item.Item;
import com.mygdx.crazybomber.model.player.Player;
import com.mygdx.crazybomber.model.client.CrazyBomberClient;

import java.io.IOException;

public class GameMap implements Screen {
    private Sprite background;
    private SpriteBatch batch = new SpriteBatch();
    private Stage stage;
    private Table table;
    private TextButton buttonBack;
    private Label heading;
    private Skin skin;
    private TextureAtlas atlas;
    private Texture splashTexture;
    private Repository repository;
    private Player player;
    private Texture playerTexture;
    boolean isPressedUP, isPressedDOWN, isPressedRIGHT, isPressedLEFT, isPressedW, isPressedS, isPressedD, isPressedA, isPressedSPACE;
    private GameState gameState;
    private CrazyBomberClient client;
    public GameMap (Texture texture, Repository repository) {
        this.splashTexture = texture;
        this.repository = repository;
    }

    @Override
    public void show() {
        background = new Sprite(splashTexture);
        background.setSize(720,720);
        background.setX(180);
        batch = new SpriteBatch();

        playerTexture = new Texture("object/player.png");
        client = new CrazyBomberClient("localhost", playerTexture);
        gameState = client.getGameState();
        player = client.getPlayer();
        player.setSize(48,48);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas);
        table = new Table(skin);
        table.setBounds(950, 660, 100,100 );

        buttonBack = new TextButton("BACK", skin);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(repository.getListOfStates().pop());
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
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        background.draw(batch);
        isPressedUP = Gdx.input.isKeyPressed(Input.Keys.UP);
        isPressedDOWN = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        isPressedRIGHT = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        isPressedLEFT =  Gdx.input.isKeyPressed(Input.Keys.LEFT);
        isPressedW = Gdx.input.isKeyPressed(Input.Keys.W);
        isPressedS = Gdx.input.isKeyPressed(Input.Keys.S);
        isPressedD = Gdx.input.isKeyPressed(Input.Keys.D);
        isPressedA = Gdx.input.isKeyPressed(Input.Keys.A);
        isPressedSPACE = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        try {
        if (isPressedUP == true || isPressedW == true)
        {
            player.translateY(player.getSpeed());
            client.sendOnPlayerCoordinateChange((player.getX() - 180) / 48,player.getY() / 48,(byte)2);
        }
        else if (isPressedDOWN == true || isPressedS == true)
        {
            player.translateY(-player.getSpeed());
            client.sendOnPlayerCoordinateChange((player.getX() - 180) / 48,player.getY() / 48,(byte)0);
        }
        else if (isPressedLEFT == true || isPressedA == true)
        {
            player.translateX(-player.getSpeed());
            client.sendOnPlayerCoordinateChange((player.getX() - 180) / 48,player.getY() / 48,(byte)1);
        }
        else if (isPressedRIGHT == true || isPressedD == true)
        {
            player.translateX(player.getSpeed());
            client.sendOnPlayerCoordinateChange((player.getX() - 180) / 48,player.getY() / 48,(byte)3);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.draw(batch);
        for(Block[] blocks : gameState.getMap().blockMatrix) {
            for (Block block : blocks){
                if (!(block instanceof EmptyBlock))
                block.draw(batch);
             }
        }

        for (Player player: gameState.getPlayerList()) {
            if (player.getTexture() == null){
                player.setTexture(new Texture("object/player.png"));
            }
            player.draw(batch);
        }
        for (Item item : gameState.getMap().getActiveItemArray()) {
            item.draw(batch);
        }
        for (Bomb bomb : gameState.getMap().getActiveBombArray()) {
            bomb.draw(batch);
        }
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
