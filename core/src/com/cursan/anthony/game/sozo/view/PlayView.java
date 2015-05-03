package com.cursan.anthony.game.sozo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
import com.cursan.anthony.game.sozo.play.Gold;
import com.cursan.anthony.game.sozo.play.Mob;
import com.cursan.anthony.game.sozo.play.Player;
import com.cursan.anthony.game.sozo.play.SozoContactListener;
import com.cursan.anthony.game.sozo.play.SozoMapRenderer;
import com.cursan.anthony.game.sozo.tools.CONFIG;
import com.cursan.anthony.game.sozo.tools.GameData;
import com.cursan.anthony.game.sozo.tools.ResourceManager;

import java.util.ArrayList;

/**
 * Created by cursan_a on 30/04/15.
 */
public class PlayView implements IView {

    private TiledMap tiledMap;
    private SozoMapRenderer mapRenderer;
    private Player player;
    private Button leftButton;
    private Button rightButton;
    private Stage stage;
    private World world;
    private OrthographicCamera gameCamera;
    private SozoContactListener contactListener;
    private ArrayList<Gold> golds = new ArrayList<Gold>();
    private ArrayList<Mob> mobs = new ArrayList<Mob>();
    private float startx = 100.0f;
    private float starty = 200.0f;
    private int currentScore = 0;

    public PlayView() {
        this.initCamera();
        this.initLevel();
        this.initGui();
        this.initCollision();
    }

    private void initCamera() {
        this.gameCamera = new OrthographicCamera();
        this.gameCamera.setToOrtho(false, CONFIG.GAME_WIDTH, CONFIG.GAME_HEIGHT);
        this.gameCamera.update();
    }

    private void initLevel() {
        tiledMap = new TmxMapLoader().load("level/level" + GameData.getInstance().getLevel() + ".tmx");
        mapRenderer = new SozoMapRenderer(tiledMap);
        mapRenderer.setView(GameMaster.getInstance().getCamera());
    }

    private void initPlayer() {
        player = new Player();
        player.createSprite(startx, starty);
        player.createBody(world);
        mapRenderer.addSprite(player.getSprite());
    }

    private void initCollision() {
        world = new World(new Vector2(0, -25f), true);
        contactListener = new SozoContactListener();
        world.setContactListener(contactListener);

        //Old collisions style
        /*TiledMapTileLayer blocsLayer = (TiledMapTileLayer)tiledMap.getLayers().get("blocs");
        for (int x = 0; x < blocsLayer.getWidth(); x++)
            for (int y = 0; y < blocsLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = blocsLayer.getCell(x, y);
                if (cell == null) continue;
                if (cell.getTile() == null) continue;
                Bloc bloc = new Bloc();
                bloc.createBody(world, x, y);
            }*/

        MapLayer collisionsLayer = tiledMap.getLayers().get("collisions");
        for (MapObject collisionObject : collisionsLayer.getObjects()) {
            Float x = (Float) collisionObject.getProperties().get("x") / CONFIG.PPM;
            Float y = (Float) collisionObject.getProperties().get("y") / CONFIG.PPM;
            Float width = (Float) collisionObject.getProperties().get("width") / 2.0f / CONFIG.PPM;
            Float height = (Float) collisionObject.getProperties().get("height") / 2.0f / CONFIG.PPM;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x + width, y + height);
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width, height);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData("bloc");
            polygonShape.dispose();
        }

        MapLayer goldsLayer = tiledMap.getLayers().get("golds");
        for (MapObject goldMapObject : goldsLayer.getObjects()) {
            Float x = (Float) goldMapObject.getProperties().get("x");
            Float y = (Float) goldMapObject.getProperties().get("y");
            //System.out.println("Gold (" + x + "; " + y + ")");
            Gold gold = new Gold();
            gold.createSprite(x, y);
            mapRenderer.addSprite(gold.getSprite());
            gold.createBody(world);
            golds.add(gold);
        }

        MapLayer mobsLayer = tiledMap.getLayers().get("mobs");
        for (MapObject mobMapObject : mobsLayer.getObjects()) {
            Mob mob = new Mob();
            mob.createSprite((Float) mobMapObject.getProperties().get("x"),
                    (Float) mobMapObject.getProperties().get("y"));
            mapRenderer.addSprite(mob.getSprite());
            mob.createBody(world);
            mobs.add(mob);
        }

        MapLayer itemsLayer = tiledMap.getLayers().get("items");
        for (MapObject itemMapObject : itemsLayer.getObjects()) {
            if (itemMapObject.getName() == null)
                continue;
            if (itemMapObject.getName().equals("start")) {
                startx = (Float)itemMapObject.getProperties().get("x");
                starty = (Float)itemMapObject.getProperties().get("y");
            }
            if (itemMapObject.getName().equals("end")) {
                Float x = (Float) itemMapObject.getProperties().get("x");
                Float y = (Float) itemMapObject.getProperties().get("y");
                Float width = (Float) itemMapObject.getProperties().get("width");
                Float height = (Float) itemMapObject.getProperties().get("height");
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(x / CONFIG.PPM, y / CONFIG.PPM);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(width / CONFIG.PPM / 2.0f, height / CONFIG.PPM / 2.0f);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = polygonShape;
                fixtureDef.isSensor = true;
                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef).setUserData("end");
                polygonShape.dispose();
            }
        }

        initPlayer();
    }

    private void initGui() {
        stage = new Stage(GameMaster.getInstance().getViewport(), GameMaster.getInstance().getSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        skin.addRegions(ResourceManager.getInstance().getTextureAtlas("button2"));
        TextButton.TextButtonStyle styleLeft = new TextButton.TextButtonStyle();
        styleLeft.up = skin.getDrawable("buttonUp");
        styleLeft.down = skin.getDrawable("buttonDownLeft");
        this.leftButton = new Button();
        this.leftButton.setPosition(0, 0);
        this.leftButton.setWidth(CONFIG.GAME_WIDTH / 2.0f);
        this.leftButton.setHeight(CONFIG.GAME_HEIGHT);
        this.leftButton.setStyle(styleLeft);
        this.leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean ret = super.touchDown(event, x, y, pointer, button);
                player.leftDown();
                return ret;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.leftUp();
            }
        });
        TextButton.TextButtonStyle styleRight = new TextButton.TextButtonStyle();
        styleRight.up = skin.getDrawable("buttonUp");
        styleRight.down = skin.getDrawable("buttonDownRight");
        this.rightButton = new Button();
        this.rightButton.setPosition(CONFIG.GAME_WIDTH / 2, 0);
        this.rightButton.setWidth(CONFIG.GAME_WIDTH / 2);
        this.rightButton.setHeight(CONFIG.GAME_HEIGHT);
        this.rightButton.setStyle(styleRight);
        this.rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean ret = super.touchDown(event, x, y, pointer, button);
                player.rightDown();
                return ret;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.rightUp();
            }
        });
        stage.addActor(leftButton);
        stage.addActor(rightButton);
    }

    @Override
    public void render(float timeElapsed) {
        world.step(timeElapsed, 6, 2);
        player.actualizePosition();
        handleCollision();
        for (Mob mob : mobs)
            mob.updateBehavior(player);
        gameCamera.position.set(player.getSprite().getX(), player.getSprite().getY() + 120, 0);
        gameCamera.update();
        mapRenderer.setView(gameCamera);
        mapRenderer.render(timeElapsed);
        stage.draw();
    }

    private void handleCollision() {
        Mob playerSlewBy = contactListener.getPlayerSlewBy();
        for (Mob mob : contactListener.getMobsSlew()) {
            if (mobs.contains(mob)) {
                world.destroyBody(mob.getBody());
                mapRenderer.removeSprite(mob.getSprite());
                mobs.remove(mob);
                currentScore += 35;
                ResourceManager.getInstance().getSound("mario_paf").play();
                if (mob.equals(playerSlewBy))
                    playerSlewBy = null;
            }
        }
        contactListener.getMobsSlew().clear();
        for (Gold gold : contactListener.getGoldsCatched()) {
            if (golds.contains(gold)) {
                world.destroyBody(gold.getBody());
                mapRenderer.removeSprite(gold.getSprite());
                golds.remove(gold);
                currentScore += 8;
                ResourceManager.getInstance().getSound("mario_coin").play();
            }
        }
        contactListener.getGoldsCatched().clear();
        player.isOnTheGround(contactListener.isPlayerIsOnTheGround());

        if (playerSlewBy != null) {
            ResourceManager.getInstance().getSound("mario_game_over").play();
            currentScore = 0;
            endGame();
        }

        if (player.getSprite().getY() < -1000) {
            ResourceManager.getInstance().getSound("mario_falling").play();
            currentScore = 0;
            endGame();
        }

        if (contactListener.isGameTerminated()) {
            ResourceManager.getInstance().getSound("mario_win").play();
            endGame();
        }
    }

    public void endGame() {
        GameData.getInstance().setScore(currentScore);
        GameMaster.getInstance().setState(GameMaster.e_state.SCORE_VIEW, true);
    }

    @Override
    public void reload() {
        Gdx.input.setInputProcessor(stage);
    }
}
