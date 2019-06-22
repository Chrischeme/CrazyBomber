//main menu class with the play / exit buttons
package com.mygdx.bomberbois;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin; //appearance of button
    private BitmapFont black, white; //color of font
    private TextureAtlas atlas; // for the button background
    private Sprite splash;
    private SpriteBatch batch;
    private MapRepository mapRepository;
    private StateRepository stateRepository;

    public MainMenu(MapRepository mapRepository, StateRepository stateRepository) {
        this.mapRepository = mapRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public void show() {
        final MainMaps mainMaps = new MainMaps(mapRepository,stateRepository);

        //background
        batch = new SpriteBatch();
        Texture splashTexture = new Texture("splash.png");
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //table that hold the buttons
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack"); //texture of the buttons
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //sets the bounds to full screen

        //fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false); //this false is so the chars aren't flipped
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);

        //buttons
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1; //when button is pressed, offset
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("EXIT",textButtonStyle); // clickable exit button
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15); //adds 15 pixels around the button padding - for later use, doesn't do anything atm

        buttonPlay = new TextButton("PLAY",textButtonStyle); //clickable play button
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(mainMaps);
                stateRepository.push(mainMaps);
            }
        });
        buttonPlay.pad(15);


       //heading
        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE); //
        heading =  new Label(BombermanWithFriends.TITLE, headingStyle);
        heading.setFontScale(2); //doubles the font size

        //adding things into the table
        table.add(heading);
        table.getCell(heading).spaceBottom(10); //get the cell that contains heading,space 10 pixels down
        table.row(); // nextline
        table.padBottom(1); //padding space
        table.add(buttonPlay);
        table.row();
        table.add(buttonExit);
        table.debug();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(1,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(delta); //update everything in it
            batch.begin();
            splash.draw(batch);
            batch.end();
            stage.draw(); // draws the buttons and table etc
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        batch.dispose();
        splash.getTexture().dispose();
    }
}
