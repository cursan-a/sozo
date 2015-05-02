package com.cursan.anthony.game.sozo.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by cursan_a on 30/04/15.
 */
public abstract class ASpriteAnimated extends Sprite {

    protected float animationCursor = 0;

    public void resetAnimation() {
        animationCursor = 0;
    }

    public void update(float delta) {
        animationCursor += delta;
    }

}
