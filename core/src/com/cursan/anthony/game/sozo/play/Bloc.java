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
    private Body body;
    private float x;
    private float y;

    public void createBody(World world, float x, float y) {
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * 60 / PlayView.PPM, y * 60 / PlayView.PPM);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(30 / PlayView.PPM, 30 / PlayView.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}