package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cursan.anthony.game.sozo.view.PlayView;

/**
 * Created by cursan_a on 02/05/15.
 */
public class Mob {
    public enum e_direction {
        RIGHT,
        LEFT
    };
    public enum e_state {
        NONE,
        RUN
    };
    private e_direction direction = e_direction.RIGHT;
    private e_state state = e_state.NONE;
    private MobSprite sprite;
    private Body body;
    private boolean hunt = false;

    public void createSprite(float x, float y) {
        this.sprite = new MobSprite(this);
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() / PlayView.PPM, sprite.getY() / PlayView.PPM);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(30 / PlayView.PPM, 30 / PlayView.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.025f;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public e_direction getDirection() {
        return direction;
    }

    public e_state getState() {
        return state;
    }

    public MobSprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public void updateBehavior(Player player) {
        double distance = Math.sqrt(
                Math.pow((double)(player.getSprite().getX() - this.sprite.getX()), 2.0) +
                Math.pow((double)(player.getSprite().getY() - this.sprite.getY()), 2.0));
        if (distance < 400 && distance > 10) {
            if (!hunt) {
                this.state = e_state.RUN;
                this.sprite.resetAnimation();
                hunt = true;
            }
            this.direction = (player.getSprite().getX() < this.sprite.getX()) ? e_direction.LEFT : e_direction.RIGHT;
        } else {
            hunt = false;
            this.state = e_state.NONE;
        }
        Vector2 velocity = body.getLinearVelocity();
        if (state == e_state.RUN) {
            switch (direction) {
                case RIGHT:
                    velocity.x = 5f;
                    break;
                case LEFT:
                    velocity.x = -5f;
                    break;
            }
        } else
            velocity.x = 0f;
        body.setLinearVelocity(velocity);
        sprite.setPosition(body.getPosition().x * PlayView.PPM, body.getPosition().y * PlayView.PPM);
    }
}
