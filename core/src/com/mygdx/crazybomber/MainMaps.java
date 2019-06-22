//stage where you see all the diff maps to choose from
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

public class MainMaps implements Screen {
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit, buttonPlay2,buttonPlay3,buttonPlay4,buttonPlay5,buttonPlay6,buttonPlay7,buttonPlay8; //8 maps?
    private Label heading;
    private Skin skin;
    private BitmapFont black, white;
    private TextureAtlas atlas;
    private Sprite splash;
    private SpriteBatch batch;
    private MapRepository mapRepository;
    private StateRepository stateRepository;

    public MainMaps(MapRepository mapRepository, StateRepository stateRepository) {
        this.mapRepository = mapRepository;
        this.stateRepository = stateRepository;
    }


    @Override
    public void show() {
        //everything below is copied from MainMenu, will change the background/looks of things later
        batch = new SpriteBatch();
        Texture splashTexture = new Texture("splash.png");
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/button.pack"); //defines the region of sprite image
        skin = new Skin(atlas); //using this for the text button
        table = new Table(skin); //table with the skin's atlas
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //sets the bounds to full screen


        white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false); //this false is so the chars aren't flipped
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);


        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1; //when button is pressed, text moves to right by 1
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;
        //everything above is copied from MainMenu, will change the background/looks of things later



        buttonExit = new TextButton("EXIT",textButtonStyle);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15); // for later use


        // for now, assuming we have 8 maps, can choose however many later
        buttonPlay = new TextButton("MAP1",textButtonStyle); //clickable map button for choosing maps
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(mapRepository.getMap(0));
            }
        });
        buttonPlay.pad(15); // for later use

        buttonPlay2 = new TextButton("MAP2",textButtonStyle);
        buttonPlay2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(mapRepository.getMap(1));
            }
        });


        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
        heading =  new Label(BombermanWithFriends.TITLE, headingStyle);
        heading.setFontScale(2); //doubles the font size


        table.add(heading);
        table.getCell(heading).spaceBottom(10);
        table.row();
        table.padBottom(1);
        table.add(buttonPlay);
        table.add(buttonPlay2);
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
        batch.begin(); //have to begin and end (open the paper) --- copied from splash
        splash.draw(batch); //draw splash on the batch / paper  --- copied from splash
        batch.end(); // --- copied from splash
        stage.draw(); //
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

