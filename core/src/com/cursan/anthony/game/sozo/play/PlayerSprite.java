package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cursan.anthony.game.sozo.tools.ASpriteAnimated;

/**
 * Created by cursan_a on 26/04/15.
 */
public class PlayerSprite extends ASpriteAnimated {

    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion rightNone;
    private TextureAtlas.AtlasRegion leftNone;
    private Animation rightAnimation;
    private Animation leftAnimation;
    private Animation rightJumpAnimation;
    private Animation leftJumpAnimation;
    private float animationCursor = 0;

    public enum e_control {
        RIGHT_NONE, LEFT_NONE, RIGHT, LEFT, RIGHT_JUMP, LEFT_JUMP
    };
    private e_control control = e_control.RIGHT_NONE;

    public PlayerSprite() {
        super();
        atlas = new TextureAtlas(Gdx.files.internal("atlas/pikachu.atlas"));
        setBounds(0, 0, 60, 60);
        rightNone = atlas.findRegion("0000");
        leftNone = atlas.findRegion("0001");
        float speed = 1 / 7f;
        rightAnimation = new Animation(speed,
                atlas.findRegion("0002"),
                atlas.findRegion("0003"),
                atlas.findRegion("0004"),
                atlas.findRegion("0005"));
        leftAnimation = new Animation(speed,
                atlas.findRegion("0006"),
                atlas.findRegion("0007"),
                atlas.findRegion("0008"),
                atlas.findRegion("0009"));
        rightJumpAnimation = new Animation(speed,
                atlas.findRegion("0010"),
                atlas.findRegion("0011"),
                atlas.findRegion("0012"),
                atlas.findRegion("0013"));
        leftJumpAnimation = new Animation(speed,
                atlas.findRegion("0014"),
                atlas.findRegion("0015"),
                atlas.findRegion("0016"),
                atlas.findRegion("0017"));
        }

    @Override
    public void draw(Batch batch) {
        switch (this.control) {
            case RIGHT_NONE:
                batch.draw(this.rightNone, this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
            case LEFT_NONE:
                batch.draw(this.leftNone, this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
            case RIGHT:
                batch.draw(rightAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
            case LEFT:
                batch.draw(leftAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
            case RIGHT_JUMP:
                batch.draw(rightJumpAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
            case LEFT_JUMP:
                batch.draw(leftJumpAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                break;
        }
        /*ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(this.getX(), this.getY(), 120, 120);
        sr.end();*/
    }

    @Override
    public void update(float delta) {
        animationCursor += delta;
    }

    private void setControl(e_control control) {
        this.control = control;
        animationCursor = 0;
    }

    public void leftDown() {
        setControl((control == e_control.RIGHT) ? e_control.RIGHT_JUMP : e_control.LEFT);
    }

    public void leftUp() {
        setControl((control == e_control.LEFT) ? e_control.LEFT_NONE : e_control.RIGHT);
    }

    public void rightDown() {
        setControl((control == e_control.LEFT) ? e_control.LEFT_JUMP : e_control.RIGHT);
    }

    public void rightUp() {
        setControl((control == e_control.RIGHT) ? e_control.RIGHT_NONE : e_control.LEFT);
    }

    public e_control getControl() {
        return control;
    }
}
