//main menu class with the play / exit buttons
package com.mygdx.crazybomber;
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MainMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit, buttonMulti;
    private Label heading;
    private Skin skin;
    //private BitmapFont black, white; //color of font
    private TextureAtlas atlas; // for the button background
    private Sprite splash;
    private SpriteBatch batch;
    private MapRepository mapRepository;
    private StateRepository stateRepository;
    private TextField usernameTextField;
    private String text;

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

        //table that hold the buttons & name textbox
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack"); //texture of the buttons
        skin = new Skin(Gdx.files.internal("menuSkin.json"),atlas); //skin now holds a lot of parameters in skin json file
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //sets the bounds to full screen


        usernameTextField = new TextField("YEEAA BOIIIIIIIIIIIII", skin); //textfieldstyle in skin json file
        usernameTextField.setPosition(300,600);
        usernameTextField.setSize(1000, 30);


        //fonts
       // white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false); //this false is so the chars aren't flipped
       // black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);

        //buttons
        /*
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1; //when button is pressed, offset
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;
*/
        //heading
        heading =  new Label(BombermanWithFriends.TITLE, skin); //labelstyle
        heading.setFontScale(2); //doubles the font size

        buttonExit = new TextButton("EXIT",skin); // clickable exit button, read the textbuttonstyle out of skin
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15); //adds 15 pixels around the button padding - for later use, doesn't do anything atm

        buttonMulti = new TextButton ("MULTIPLAYER", skin); //multiplayer button


        buttonPlay = new TextButton("PLAY",skin); //clickable play button
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(mainMaps);
                stateRepository.push(mainMaps);
            }
        });
        buttonPlay.pad(15);



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
        stage.addActor(usernameTextField);
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
            stage.act(); //do stuff ?
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
        batch.dispose();
        splash.getTexture().dispose();
    }
}
