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
import com.cursan.anthony.game.sozo.tools.GameData;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

/**
 * Created by cursan_a on 30/04/15.
 */
public class SelectLevelView implements IView {
    private Stage stage;
    private TextButton level1;
    private TextButton level2;
    private TextButton level3;
    private TextButton ret;
    private Sprite bg;

    public SelectLevelView() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        bg = new Sprite(ResourceManager.getInstance().getTexture("bg"));
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("menuButton"));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");
        style.font = new BitmapFont();

        this.level1 = new TextButton("1", style);
        this.level1.setPosition(85, 100);
        this.level1.setWidth(100);
        this.level1.setHeight(100);
        this.level1.setStyle(style);
        this.level1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("valid").play();
                GameData.getInstance().setLevel(1);
                GameMaster.getInstance().setState(GameMaster.e_state.PLAY);
            }
        });

        this.level2 = new TextButton("2", style);
        this.level2.setPosition(270, 100);
        this.level2.setWidth(100);
        this.level2.setHeight(100);
        this.level2.setStyle(style);
        this.level2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("valid").play();
                GameData.getInstance().setLevel(2);
                GameMaster.getInstance().setState(GameMaster.e_state.PLAY);
            }
        });

        this.level3 = new TextButton("3", style);
        this.level3.setPosition(440, 100);
        this.level3.setWidth(100);
        this.level3.setHeight(100);
        this.level3.setStyle(style);
        this.level3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("valid").play();
                GameData.getInstance().setLevel(3);
                GameMaster.getInstance().setState(GameMaster.e_state.PLAY);
            }
        });

        this.ret = new TextButton("<", style);
        this.ret.setPosition(25, 5);//GameMaster.GAME_HEIGHT - 30);
        this.ret.setWidth(50);
        this.ret.setHeight(50);
        this.ret.setStyle(style);
        this.ret.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("valid").play();
                GameMaster.getInstance().setState(GameMaster.e_state.MENU);
            }
        });

        stage.addActor(this.level1);
        stage.addActor(this.level2);
        stage.addActor(this.level3);
        stage.addActor(this.ret);
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
