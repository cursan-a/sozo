package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cursan.anthony.game.sozo.tools.ASpriteAnimated;
import com.cursan.anthony.game.sozo.tools.CONFIG;

/**
 * Created by cursan_a on 02/05/15.
 */
public class MobSprite extends ASpriteAnimated {
    private TextureAtlas.AtlasRegion rightNone;
    private TextureAtlas.AtlasRegion leftNone;
    private Animation leftAnimation;
    private Animation rightAnimation;
    private Mob mob;

    public MobSprite(Mob mob) {
        this.mob = mob;
        setBounds(0, 0, CONFIG.MOB_WIDTH, CONFIG.MOB_HEIGHT);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/mob.atlas"));
        rightNone = atlas.findRegion("0000");
        leftNone = atlas.findRegion("0000");
        float speed = 1 / 7f;
        leftAnimation = new Animation(speed,
                atlas.findRegion("0001"),
                atlas.findRegion("0002"));
        rightAnimation = new Animation(speed,
                atlas.findRegion("0003"),
                atlas.findRegion("0004"));
    }


    @Override
    public void draw(Batch batch) {
        switch (mob.getDirection()) {
            case RIGHT:
                switch (mob.getState()) {
                    case NONE:
                        batch.draw(this.rightNone, this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                        break;
                    case RUN:
                        batch.draw(rightAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                        break;
                }
                break;
            case LEFT:
                switch (mob.getState()) {
                    case NONE:
                        batch.draw(this.leftNone, this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                        break;
                    case RUN:
                        batch.draw(leftAnimation.getKeyFrame(animationCursor, true), this.getX(), this.getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
                        break;
                }
                break;
        }
    }
}
