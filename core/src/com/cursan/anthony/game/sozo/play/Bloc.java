package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cursan.anthony.game.sozo.view.PlayView;

/**
 * Created by cursan_a on 02/05/15.
 */
public class Bloc {
    public final static float TILED_SIZE = 32;
    private Body body;
    private float x;
    private float y;

    public void createBody(World world, float x, float y) {
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * TILED_SIZE / PlayView.PPM, y * TILED_SIZE / PlayView.PPM);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(TILED_SIZE / PlayView.PPM / 2, TILED_SIZE / PlayView.PPM / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("bloc");
        polygonShape.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
