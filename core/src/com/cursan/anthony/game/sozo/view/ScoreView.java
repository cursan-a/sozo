package com.cursan.anthony.game.sozo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cursan.anthony.game.sozo.GameMaster;
import com.cursan.anthony.game.sozo.tools.CONFIG;
import com.cursan.anthony.game.sozo.tools.GameData;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

/**
 * Created by cursan_a on 02/05/15.
 */
public class ScoreView implements IView  {
    private Stage stage;
    private TextButton scoreButton;

    public ScoreView() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("scoreScreenBtn"));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");
        style.font = new BitmapFont();
        this.scoreButton = new TextButton("Score : " + GameData.getInstance().getScore(), style);
        this.scoreButton.setPosition(0, 0);
        this.scoreButton.setWidth(CONFIG.GAME_WIDTH);
        this.scoreButton.setHeight(CONFIG.GAME_HEIGHT);
        this.scoreButton.setStyle(style);
        this.scoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("mario_mushroom").play();
                GameMaster.getInstance().setState(GameMaster.e_state.SELECT_LEVEL, true);
            }
        });
        stage.addActor(this.scoreButton);
        Music music = ResourceManager.getInstance().getMusic("intro");
        music.setLooping(true);
        music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float timeElapsed) {
        GameMaster.getInstance().synchronizeCamera();
        stage.draw();
    }

    @Override
    public void reload() {
        Gdx.input.setInputProcessor(stage);
    }
}
