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
    public enum e_state {
        NONE,
        RUN,
        JUMP
    }
    public enum e_direction {
        RIGHT,
        LEFT
    }
    private e_state state = e_state.NONE;
    private e_direction direction = e_direction.RIGHT;
    private boolean leftDown = false;
    private boolean rightDown = false;

    public void createSprite(float x, float y) {
        sprite = new PlayerSprite(this);
        sprite.setScale(2, 2);
        sprite.setX(x);
        sprite.setY(y);
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
        fixtureDef.density = 0.001f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("foot");
        shape.dispose();
    }

    public void actualizePosition() {
        this.handleControl();
        Vector2 velocity = body.getLinearVelocity();
        if (state != e_state.NONE) {
            switch (direction) {
                case RIGHT:
                    velocity.x = 10f;
                    break;
                case LEFT:
                    velocity.x = -10f;
                    break;
            }
        } else
            velocity.x = 0f;
        body.setLinearVelocity(velocity);
        sprite.setPosition(body.getPosition().x * PlayView.PPM - 30, body.getPosition().y * PlayView.PPM - 30);
    }

    public PlayerSprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public void leftDown() {
        leftDown = true;
        switch (state) {
            case NONE:
                direction = e_direction.LEFT;
                state = e_state.RUN;
                break;
            case RUN:
                state = e_state.JUMP;
                body.applyLinearImpulse(new Vector2(0f, 1.2f), body.getWorldCenter(), true);
                break;
            case JUMP:
                direction = e_direction.LEFT;
                break;
        }
        sprite.resetAnimation();
    }

    public void leftUp() {
        leftDown = false;
        if (state == e_state.RUN)
            state = e_state.NONE;
    }

    public void rightDown() {
        rightDown = true;
        switch (state) {
            case NONE:
                direction = e_direction.RIGHT;
                state = e_state.RUN;
                break;
            case RUN:
                state = e_state.JUMP;
                body.applyLinearImpulse(new Vector2(0f, 1.0f), body.getWorldCenter(), true);
                break;
            case JUMP:
                direction = e_direction.RIGHT;
                break;
        }
        sprite.resetAnimation();
    }

    public void rightUp() {
        rightDown = false;
        if (state == e_state.RUN)
            state = e_state.NONE;
    }

    public e_state getState() {
        return state;
    }

    private void handleControl() {
        if (rightDown && !leftDown)
            direction = e_direction.RIGHT;
        if (!rightDown && leftDown)
            direction = e_direction.LEFT;
        if (state == e_state.NONE && (rightDown || leftDown))
            state = e_state.RUN;
    }

    public e_direction getDirection() {
        return direction;
    }

    public void isOnTheGround(boolean isOnTheGround) {
        if (isOnTheGround)
            state = (leftDown || rightDown) ? e_state.RUN : e_state.NONE;
    }
}
