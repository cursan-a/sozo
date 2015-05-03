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
    public static float GOLD_WIDTH = 32.0f;
    public static float GOLD_HEIGHT = 32.0f;
    private GoldSprite sprite;
    private Body body;

    public void createSprite(float x, float y) {
        this.sprite = new GoldSprite();
        this.sprite.setX(x - GOLD_WIDTH / 2);
        this.sprite.setY(y - GOLD_HEIGHT / 2);
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((sprite.getX() + GOLD_WIDTH / 2.0f)/ PlayView.PPM, (sprite.getY() + GOLD_HEIGHT / 2.0f) / PlayView.PPM);
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(GOLD_WIDTH / 2.0f / PlayView.PPM);
        fixtureDef.shape = circleShape ;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        circleShape.dispose();
    }

    public GoldSprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }
}
