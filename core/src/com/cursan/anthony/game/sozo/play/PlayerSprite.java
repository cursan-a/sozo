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

    private TextureAtlas.AtlasRegion rightNone;
    private TextureAtlas.AtlasRegion leftNone;
    private Animation rightAnimation;
    private Animation leftAnimation;
    private Animation rightJumpAnimation;
    private Animation leftJumpAnimation;
    private float animationCursor = 0;
    private Player player;

    public PlayerSprite(Player player) {
        super();
        this.player = player;
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/pikachu.atlas"));
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
        switch (player.getControl()) {
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
    }

    @Override
    public void update(float delta) {
        animationCursor += delta;
    }

    public void resetAnimation() {
        animationCursor = 0;
    }
}
