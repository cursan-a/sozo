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
    private Sprite bg;

    public MenuView() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        bg = new Sprite(ResourceManager.getInstance().getTexture("bg"));
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("menuButton"));
        TextButton.TextButtonStyle styleLeft = new TextButton.TextButtonStyle();
        styleLeft.up = skin.getDrawable("buttonUp");
        styleLeft.down = skin.getDrawable("buttonDown");
        styleLeft.font = new BitmapFont();
        this.btnPlay = new TextButton("Select level", styleLeft);
        this.btnPlay.setPosition(160, 100);
        this.btnPlay.setWidth(320);
        this.btnPlay.setHeight(70);
        this.btnPlay.setStyle(styleLeft);
        this.btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("valid").play();
                GameMaster.getInstance().setState(GameMaster.e_state.SELECT_LEVEL);
            }
        });
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
