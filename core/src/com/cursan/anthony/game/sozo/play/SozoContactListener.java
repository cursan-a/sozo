package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.cursan.anthony.game.sozo.view.PlayView;

/**
 * Created by cursan_a on 01/05/15.
 */
public class SozoContactListener implements ContactListener {
    PlayView pv;
    public SozoContactListener(PlayView pv) {
        this.pv = pv;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null)
            return;

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            pv.getPlayer().setJumping(false);
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            pv.getPlayer().setJumping(false);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null)
            return;

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            pv.getPlayer().setJumping(true);
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            pv.getPlayer().setJumping(true);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
