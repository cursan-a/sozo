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

        if(fa == null || fb == null || fa.getUserData() == null || fb.getUserData() == null)
            return;

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("bloc")) ||
            (fa.getUserData().equals("bloc") && fb.getUserData().equals("foot"))) {
            pv.getPlayer().isOnTheGround(true);
            return;
        }

        if (fa.getUserData().equals("foot") && fb.getUserData().getClass().equals(Mob.class)) {
            //kill enemy
            System.out.println("Enemy dead !");
            return;
        }
        if (fa.getUserData().getClass().equals(Mob.class) && fb.getUserData().equals("foot")) {
            //kill enemy
            System.out.println("Enemy dead !");
            return;
        }

        if (fa.getUserData().equals("body") && fb.getUserData().getClass().equals(Mob.class)) {
            //kill player
            System.out.println("Player dead !");
            return;
        }
        if (fa.getUserData().getClass().equals(Mob.class) && fb.getUserData().equals("body")) {
            //kill player
            System.out.println("Player dead !");
            return;
        }

        if (fa.getUserData().getClass().equals(Gold.class) && fb.getUserData().equals("body")) {
            pv.playerCatchGold((Gold)fa.getUserData());
            return;
        }
        if (fa.getUserData().equals("body") && fb.getUserData().getClass().equals(Gold.class)) {
            pv.playerCatchGold((Gold)fb.getUserData());
            return;
        }

        if ((fa.getUserData().equals("body") && fb.getUserData().equals("end")) ||
            (fa.getUserData().equals("end") && fb.getUserData().equals("body"))){
            pv.endGame();
            return;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null)
            return;

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            pv.getPlayer().isOnTheGround(false);
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            pv.getPlayer().isOnTheGround(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
