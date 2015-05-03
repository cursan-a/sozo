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
    public static float MOB_WIDTH = 32.0f;
    public static float MOB_HEIGHT = 32.0f;
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
        this.sprite.setX(x - MOB_WIDTH / 2.0f);
        this.sprite.setY(y - MOB_HEIGHT / 2.0f);
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        float x = (sprite.getX() + MOB_WIDTH / 2.0f) / PlayView.PPM;
        float y = (sprite.getY() + MOB_HEIGHT / 2.0f) / PlayView.PPM;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(MOB_WIDTH / 2.0f / PlayView.PPM, MOB_HEIGHT / 2.0f / PlayView.PPM);
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
                this.direction = (player.getSprite().getX() < this.sprite.getX()) ? e_direction.LEFT : e_direction.RIGHT;
            }
        } else {
            hunt = false;
            this.state = e_state.NONE;
        }
        Vector2 velocity = body.getLinearVelocity();
        if (state == e_state.RUN) {
            switch (direction) {
                case RIGHT:
                    velocity.x = 1f;
                    break;
                case LEFT:
                    velocity.x = -1f;
                    break;
            }
        } else
            velocity.x = 0f;
        body.setLinearVelocity(velocity);
        sprite.setPosition(body.getPosition().x * PlayView.PPM - MOB_WIDTH / 2.0f, body.getPosition().y * PlayView.PPM - MOB_HEIGHT / 2.0f);
    }
}
