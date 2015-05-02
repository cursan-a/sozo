package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cursan.anthony.game.sozo.view.PlayView;

/**
 * Created by cursan_a on 02/05/15.
 */
public class Gold {
    private GoldSprite sprite;
    private Body body;

    public void createSprite(float x, float y) {
        this.sprite = new GoldSprite();
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() / PlayView.PPM, sprite.getY() / PlayView.PPM);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape .setRadius(8 / PlayView.PPM);
        fixtureDef.shape = circleShape ;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        circleShape .dispose();
    }

    public GoldSprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }
}
