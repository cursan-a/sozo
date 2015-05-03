package com.cursan.anthony.game.sozo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cursan.anthony.game.sozo.GameMaster;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

/**
 * Created by cursan_a on 30/04/15.
 */
public class MenuView implements IView {
    private Stage stage;
    private TextButton btnPlay;
    private TextButton btnExit;
    private Sprite bg;

    public MenuView() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        bg = new Sprite(ResourceManager.getInstance().getTexture("bg"));
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("menuButton"));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");
        style.font = new BitmapFont();

        this.btnPlay = new TextButton("Select Level", style);
        this.btnPlay.setPosition(160, 150);
        this.btnPlay.setWidth(320);
        this.btnPlay.setHeight(70);
        this.btnPlay.setStyle(style);
        this.btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("mario_mushroom").play();
                GameMaster.getInstance().setState(GameMaster.e_state.SELECT_LEVEL);
            }
        });

        this.btnExit = new TextButton("Exit", style);
        this.btnExit.setPosition(160, 50);
        this.btnExit.setWidth(320);
        this.btnExit.setHeight(70);
        this.btnExit.setStyle(style);
        this.btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameMaster.getInstance().setState(GameMaster.e_state.START);
                Gdx.app.exit();
            }
        });
        stage.addActor(this.btnExit);
        stage.addActor(this.btnPlay);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float timeElapsed) {
        GameMaster.getInstance().synchronizeCamera();
        GameMaster.getInstance().getSpriteBatch().begin();
        bg.draw(GameMaster.getInstance().getSpriteBatch());
        GameMaster.getInstance().getSpriteBatch().end();
        stage.draw();
    }

    @Override
    public void reload() {
        Gdx.input.setInputProcessor(stage);
    }
}
