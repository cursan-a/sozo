package com.cursan.anthony.game.sozo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cursan.anthony.game.sozo.GameMaster;
import com.cursan.anthony.game.sozo.play.PlayerSprite;
import com.cursan.anthony.game.sozo.play.SozoContactListener;
import com.cursan.anthony.game.sozo.play.SozoMapRenderer;
import com.cursan.anthony.game.sozo.tools.GameData;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

/**
 * Created by cursan_a on 30/04/15.
 */
public class PlayView implements IView {
    private final static float PPM = 60;
    private TiledMap tiledMap;
    private SozoMapRenderer mapRenderer;
    private PlayerSprite playerSprite;
    private Body playerBody;
    private Button leftButton;
    private Button rightButton;
    private Stage stage;
    private World world;

    public PlayView() {
        this.initLevel();
        this.initPlayer();
        this.initGui();
        this.initCollision();
    }

    private void initLevel() {
        tiledMap = new TmxMapLoader().load("level/level" + GameData.getInstance().getLevel() + ".tmx");
        mapRenderer = new SozoMapRenderer(tiledMap);
        mapRenderer.setView(GameMaster.getInstance().getCamera());
    }

    private void initPlayer() {
        playerSprite = new PlayerSprite();
        playerSprite.setScale(2, 2);
        playerSprite.setY(500);
        playerSprite.setX(100);
        mapRenderer.addSprite(playerSprite);
    }

    private void initCollision() {
        world = new World(new Vector2(0, -25f), true);
        world.setContactListener(new SozoContactListener(this));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerSprite.getX() / PPM, playerSprite.getY() / PPM);
        bodyDef.fixedRotation = true;
        playerBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(60 / PPM, 60 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.025f;
        Fixture fixture = playerBody.createFixture(fixtureDef);
        shape.dispose();

        shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 1 / PPM, new Vector2(25 / PPM, -60 / PPM), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.025f;
        fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData("foot");
        shape.dispose();
        playerBody.setUserData("player");

        TiledMapTileLayer objects = (TiledMapTileLayer)tiledMap.getLayers().get("objets");
        for (int x = 0; x < objects.getWidth(); x++)
            for (int y = 0; y < objects.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = objects.getCell(x, y);
                if (cell == null) continue;
                if (cell.getTile() == null) continue;
                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(x * 60 / PPM, y * 60 / PPM);
                shape = new PolygonShape();
                shape.setAsBox(30 / PPM, 30 / PPM);
                fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 1f;
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                shape.dispose();
            }
    }

    private void initGui() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("button2"));
        TextButton.TextButtonStyle styleLeft = new TextButton.TextButtonStyle();
        styleLeft.up = skin.getDrawable("buttonUp");
        styleLeft.down = skin.getDrawable("buttonDownLeft");
        this.leftButton = new Button();
        this.leftButton.setPosition(0, 0);
        this.leftButton.setWidth(Gdx.graphics.getWidth() / 2);
        this.leftButton.setHeight(Gdx.graphics.getHeight());
        this.leftButton.setStyle(styleLeft);
        this.leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean ret = super.touchDown(event, x, y, pointer, button);
                playerSprite.leftDown();
                return ret;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                playerSprite.leftUp();
            }
        });
        TextButton.TextButtonStyle styleRight = new TextButton.TextButtonStyle();
        styleRight.up = skin.getDrawable("buttonUp");
        styleRight.down = skin.getDrawable("buttonDownRight");
        this.rightButton = new Button();
        this.rightButton.setPosition(Gdx.graphics.getWidth() / 2, 0);
        this.rightButton.setWidth(Gdx.graphics.getWidth() / 2);
        this.rightButton.setHeight(Gdx.graphics.getHeight());
        this.rightButton.setStyle(styleRight);
        this.rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean ret = super.touchDown(event, x, y, pointer, button);
                playerSprite.rightDown();
                return ret;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                playerSprite.rightUp();
            }
        });
        stage.addActor(leftButton);
        stage.addActor(rightButton);
    }

    public boolean isJumping = false;
    @Override
    public void render(float timeElapsed) {
        switch (playerSprite.getControl()) {

            case RIGHT_NONE:
                break;
            case LEFT_NONE:
                break;
            case RIGHT:
                playerBody.applyForceToCenter(new Vector2(1f, 0), true);
                break;
            case LEFT:
                playerBody.applyForceToCenter(new Vector2(-1f, 0), true);
                break;
            case RIGHT_JUMP:
                playerBody.applyForceToCenter(new Vector2(1f, 0), true);
                if (!isJumping) {
                    playerBody.applyLinearImpulse(new Vector2(0f, 1.5f), PlayView.this.playerBody.getWorldCenter(), true);
                    isJumping = true;
                }
                break;
            case LEFT_JUMP:
                playerBody.applyForceToCenter(new Vector2(-1f, 0), true);
                if (!isJumping) {
                    playerBody.applyLinearImpulse(new Vector2(0f, 1.5f), PlayView.this.playerBody.getWorldCenter(), true);
                    isJumping = true;
                }
                break;
        }
        world.step(timeElapsed, 6, 2);
        playerSprite.setPosition(playerBody.getPosition().x * PPM - 30, playerBody.getPosition().y * PPM - 30);
        OrthographicCamera camera = GameMaster.getInstance().getCamera();
        camera.position.set(playerSprite.getX(), playerSprite.getY() + 120, 0);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render(timeElapsed);
        stage.draw();
        //this.drawSquare();
    }

    /*private void drawSquare() {
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        TiledMapTileLayer objects = (TiledMapTileLayer)tiledMap.getLayers().get("objets");
        for (int x = 0; x < objects.getWidth(); x++)
            for (int y = 0; y < objects.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = objects.getCell(x, y);
                if (cell == null) continue;
                if (cell.getTile() == null) continue;
                sr.rect(x * 60, y * 60, 60, 60);
            }
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(playerBody.getPosition().x * PPM, playerBody.getPosition().y * PPM, 60, 60);
        sr.end();
    }*/
}
