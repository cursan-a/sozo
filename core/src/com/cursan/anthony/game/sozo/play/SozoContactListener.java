package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;

/**
 * Created by cursan_a on 01/05/15.
 */
public class SozoContactListener implements ContactListener {
    private ArrayList<Gold> goldsCatched = new ArrayList<Gold>();
    private ArrayList<Mob> mobsSlew = new ArrayList<Mob>();
    private Mob playerSlewBy = null;
    private boolean gameTerminated = false;
    private boolean playerIsOnTheGround = true;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null || fa.getUserData() == null || fb.getUserData() == null)
            return;

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("bloc")) ||
            (fa.getUserData().equals("bloc") && fb.getUserData().equals("foot"))) {
            //pv.getPlayer().isOnTheGround(true);
            playerIsOnTheGround = true;
            return;
        }

        if (fa.getUserData().equals("foot") && fb.getUserData().getClass().equals(Mob.class)) {
            //kill enemy
            System.out.println("Enemy dead !");
            mobsSlew.add((Mob)fb.getUserData());
            return;
        }
        if (fa.getUserData().getClass().equals(Mob.class) && fb.getUserData().equals("foot")) {
            //kill enemy
            System.out.println("Enemy dead !");
            mobsSlew.add((Mob)fa.getUserData());
            return;
        }

        if (fa.getUserData().equals("body") && fb.getUserData().getClass().equals(Mob.class)) {
            //kill player
            System.out.println("Player dead !");
            playerSlewBy = (Mob)fb.getUserData();
            return;
        }
        if (fa.getUserData().getClass().equals(Mob.class) && fb.getUserData().equals("body")) {
            //kill player
            System.out.println("Player dead !");
            playerSlewBy = (Mob)fa.getUserData();
            return;
        }

        if (fa.getUserData().getClass().equals(Gold.class) && fb.getUserData().equals("body")) {
            //pv.playerCatchGold((Gold)fa.getUserData());
            goldsCatched.add((Gold)fa.getUserData());
            return;
        }
        if (fa.getUserData().equals("body") && fb.getUserData().getClass().equals(Gold.class)) {
            //pv.playerCatchGold((Gold)fb.getUserData());
            goldsCatched.add((Gold)fb.getUserData());
            return;
        }

        if ((fa.getUserData().equals("body") && fb.getUserData().equals("end")) ||
            (fa.getUserData().equals("end") && fb.getUserData().equals("body"))){
            //pv.endGame();
            gameTerminated = true;
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
            //pv.getPlayer().isOnTheGround(false);
            playerIsOnTheGround = false;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            //pv.getPlayer().isOnTheGround(false);
            playerIsOnTheGround = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public ArrayList<Gold> getGoldsCatched() {
        return goldsCatched;
    }

    public ArrayList<Mob> getMobsSlew() {
        return mobsSlew;
    }

    public Mob getPlayerSlewBy() {
        return playerSlewBy;
    }

    public boolean isGameTerminated() {
        return gameTerminated;
    }

    public boolean isPlayerIsOnTheGround() {
        return playerIsOnTheGround;
    }
}
