package com.cursan.anthony.game.sozo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cursan.anthony.game.sozo.tools.ResourceManager;
import com.cursan.anthony.game.sozo.view.IView;
import com.cursan.anthony.game.sozo.view.MenuView;
import com.cursan.anthony.game.sozo.view.PlayView;
import com.cursan.anthony.game.sozo.view.ScoreView;
import com.cursan.anthony.game.sozo.view.SelectLevelView;
import com.cursan.anthony.game.sozo.view.StartView;

/**
 * Created by cursan_a on 30/04/15.
 */
public class GameMaster implements ApplicationListener, InputProcessor, GestureDetector.GestureListener {
    // define
    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 360;

    // Singleton
    private static GameMaster instance = new GameMaster();

    // batch
    private SpriteBatch spriteBatch;

    // View
    private IView currentView = null;
    private StartView startView = null;
    private MenuView menuView = null;
    private SelectLevelView selectLevelView = null;
    private PlayView playView = null;
    private ScoreView scoreView = null;
    public enum e_state {
        START,
        MENU,
        SELECT_LEVEL,
        PLAY,
        SCORE_VIEW
    };
    private e_state state;

    // Camera & Viewport
    OrthographicCamera camera;
    Viewport viewport;

    private GameMaster() {

    }

    public static GameMaster getInstance() {
        return GameMaster.instance;
    }

    @Override
    public void create() {
        this.initCamera();
        //this.initControl();
        this.loadResources();
        this.spriteBatch = new SpriteBatch();
        this.setState(e_state.START);
    }

    private void initCamera() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
        this.camera.update();
        this.viewport = new FillViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        this.viewport.apply();
    }

    private void loadResources() {
        ResourceManager.getInstance().loadTexture("bg", "img/bg.png");

        ResourceManager.getInstance().loadMusic("intro", "music/intro.mp3");

        ResourceManager.getInstance().loadSound("mario_thank_you", "sound/mario_thank_you.wav");
        ResourceManager.getInstance().loadSound("mario_coin", "sound/mario_coin.wav");
        ResourceManager.getInstance().loadSound("mario_falling", "sound/mario_falling.wav");
        ResourceManager.getInstance().loadSound("mario_game_over", "sound/mario_game_over.wav");
        ResourceManager.getInstance().loadSound("mario_win", "sound/mario_win.wav");
        ResourceManager.getInstance().loadSound("mario_paf", "sound/mario_paf.wav");
        ResourceManager.getInstance().loadSound("mario_jump_1", "sound/mario_jump_1.wav");
        ResourceManager.getInstance().loadSound("mario_jump_2", "sound/mario_jump_2.wav");
        ResourceManager.getInstance().loadSound("mario_jump_3", "sound/mario_jump_3.wav");
        ResourceManager.getInstance().loadSound("mario_jump_4", "sound/mario_jump_4.wav");
        ResourceManager.getInstance().loadSound("mario_jump_5", "sound/mario_jump_5.wav");
        ResourceManager.getInstance().loadSound("mario_mushroom", "sound/mario_mushroom.wav");

        ResourceManager.getInstance().loadTextureAtlas("startScreenBtn", "atlas/startScreenBtn.atlas");
        ResourceManager.getInstance().loadTextureAtlas("menuButton", "atlas/menuButton.atlas");
        ResourceManager.getInstance().loadTextureAtlas("button", "atlas/button.atlas");
        ResourceManager.getInstance().loadTextureAtlas("button2", "atlas/button2.atlas");
        ResourceManager.getInstance().loadTextureAtlas("gold", "atlas/gold.atlas");
        ResourceManager.getInstance().loadTextureAtlas("mob", "atlas/mob.atlas");
        ResourceManager.getInstance().loadTextureAtlas("mario","atlas/mario.atlas");
    }

    private void initControl() {
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);
        Gdx.input.setInputProcessor(im);
    }

    public void setState(e_state state) {
        this.state = state;
        switch (this.state) {
            case START:
                if (this.startView == null)
                    this.startView = new StartView();
                this.currentView = this.startView;
                break;
            case MENU:
                if (this.menuView == null)
                    this.menuView = new MenuView();
                this.currentView = this.menuView;
                break;
            case SELECT_LEVEL:
                if (this.selectLevelView == null)
                    this.selectLevelView = new SelectLevelView();
                this.currentView = this.selectLevelView;
                break;
            case PLAY:
                if (this.playView == null)
                    this.playView = new PlayView();
                this.currentView = this.playView;
                break;
            case SCORE_VIEW:
                if (this.scoreView == null)
                    this.scoreView = new ScoreView();
                this.currentView = this.scoreView;
                break;
        }
        currentView.reload();
    }

    public void setState(e_state state, boolean delete) {
        if (delete) {
            switch (this.state) {
                case START:
                    startView = null;
                    break;
                case MENU:
                    menuView = null;
                    break;
                case SELECT_LEVEL:
                    selectLevelView = null;
                    break;
                case PLAY:
                    playView = null;
                    break;
                case SCORE_VIEW:
                    scoreView = null;
                    break;
            }
        }
        setState(state);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentView.render(Gdx.graphics.getDeltaTime());
    }

    public void synchronizeCamera() {
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        currentView.reload();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Keys.BACK){
            System.out.println("BACKKK !");
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
