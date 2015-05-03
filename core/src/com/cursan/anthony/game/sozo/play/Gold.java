package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cursan.anthony.game.sozo.tools.CONFIG;

/**
 * Created by cursan_a on 02/05/15.
 */
public class Gold {
    private GoldSprite sprite;
    private Body body;

    public void createSprite(float x, float y) {
        this.sprite = new GoldSprite();
        this.sprite.setX(x - CONFIG.GOLD_WIDTH / 2);
        this.sprite.setY(y - CONFIG.GOLD_HEIGHT / 2);
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((sprite.getX() + CONFIG.GOLD_WIDTH / 2.0f)/ CONFIG.PPM, (sprite.getY() + CONFIG.GOLD_HEIGHT / 2.0f) / CONFIG.PPM);
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(CONFIG.GOLD_WIDTH / 2.0f / CONFIG.PPM);
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
