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
public class GoldSprite extends ASpriteAnimated {
    private float animationCursor = 0;
    private Animation animation;

    public GoldSprite() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/gold.atlas"));
        setBounds(0, 0, CONFIG.GOLD_WIDTH, CONFIG.GOLD_HEIGHT);
        animation = new Animation(1 / 7f, atlas.getRegions());
    }

    @Override
    public void update(float delta) {
        animationCursor += delta;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.animation.getKeyFrame(animationCursor, true), this.getX() * this.getScaleX(), this.getY() * this.getScaleY());
    }
}
