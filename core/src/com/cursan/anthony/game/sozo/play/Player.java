package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cursan.anthony.game.sozo.view.PlayView;

/**
 * Created by cursan_a on 02/05/15.
 */
public class Player {
    private PlayerSprite sprite;
    private Body body;
    public enum e_control {
        RIGHT_NONE, LEFT_NONE, RIGHT, LEFT, RIGHT_JUMP, LEFT_JUMP
    };
    private e_control control = e_control.RIGHT_NONE;
    private boolean isJumping = false;

    public void createSprite(float x, float y) {
        sprite = new PlayerSprite(this);
        sprite.setScale(2, 2);
        sprite.setX(x);//100
        sprite.setY(y);//500
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() / PlayView.PPM, sprite.getY() / PlayView.PPM);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(60 / PlayView.PPM, 60 / PlayView.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.025f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

        shape = new PolygonShape();
        shape.setAsBox(58 / PlayView.PPM, 1 / PlayView.PPM, new Vector2(1 / PlayView.PPM, -60 / PlayView.PPM), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 0.025f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("foot");
        shape.dispose();
    }

    public void actualizePosition() {
        switch (control) {
            case RIGHT_NONE:
                break;
            case LEFT_NONE:
                break;
            case RIGHT:
                body.applyForceToCenter(new Vector2(1f, 0), true);
                break;
            case LEFT:
                body.applyForceToCenter(new Vector2(-1f, 0), true);
                break;
            case RIGHT_JUMP:
                body.applyForceToCenter(new Vector2(1f, 0), true);
                if (!isJumping)
                    body.applyLinearImpulse(new Vector2(0f, 1.5f), body.getWorldCenter(), true);
                break;
            case LEFT_JUMP:
                body.applyForceToCenter(new Vector2(-1f, 0), true);
                if (!isJumping)
                    body.applyLinearImpulse(new Vector2(0f, 1.5f), body.getWorldCenter(), true);
                break;
        }
        sprite.setPosition(body.getPosition().x * PlayView.PPM - 30, body.getPosition().y * PlayView.PPM - 30);
    }

    public PlayerSprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public void leftDown() {
        control = (control == e_control.RIGHT) ? e_control.RIGHT_JUMP : e_control.LEFT;
    }

    public void leftUp() {
        control = (control == e_control.LEFT) ? e_control.LEFT_NONE : e_control.RIGHT;
    }

    public void rightDown() {
        control = (control == e_control.LEFT) ? e_control.LEFT_JUMP : e_control.RIGHT;
    }

    public void rightUp() {
        control = (control == e_control.RIGHT) ? e_control.RIGHT_NONE : e_control.LEFT;
    }

    public void setControl(e_control control) {
        this.control = control;
        sprite.resetAnimation();
    }

    public e_control getControl() {
        return control;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
}
