package com.cursan.anthony.game.sozo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cursan.anthony.game.sozo.GameMaster;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

/**
 * Created by cursan_a on 30/04/15.
 */
public class StartView implements IView {
    private Stage stage;
    private Button mainButton;

    public StartView() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("startScreenBtn"));
        TextButton.TextButtonStyle styleLeft = new TextButton.TextButtonStyle();
        styleLeft.up = skin.getDrawable("buttonUp");
        styleLeft.down = skin.getDrawable("buttonDown");
        this.mainButton = new Button();
        this.mainButton.setPosition(0, 0);
        this.mainButton.setWidth(GameMaster.GAME_WIDTH);
        this.mainButton.setHeight(GameMaster.GAME_HEIGHT);
        this.mainButton.setStyle(styleLeft);
        this.mainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ResourceManager.getInstance().getSound("mario_thank_you").play();
                GameMaster.getInstance().setState(GameMaster.e_state.MENU);
            }
        });
        stage.addActor(this.mainButton);
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
