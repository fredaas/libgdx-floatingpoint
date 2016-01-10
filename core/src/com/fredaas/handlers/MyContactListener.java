package com.fredaas.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fredaas.entities.MovingBlock;

public class MyContactListener implements ContactListener {

    private static int goalContacts;
    private static int deadContacts;
    private static Object dataFixA;
    private static Object dataFixB;
    
    @Override
    public void beginContact(Contact c) {
        getFixtures(c);
        
        if (dataFixA == null || dataFixB == null) {
            return;
        }
        if (dataFixA.equals("collision") && 
                dataFixB instanceof MovingBlock) {
            ((MovingBlock) dataFixB).changeDirection();
        }
        if (dataFixB.equals("player-body") && 
                dataFixA instanceof MovingBlock) {
            deadContacts++;
        }
        if (dataFixA.equals("player-sensor") && 
                dataFixB.equals("goal-sensor")) {
            goalContacts++;
        }
    }

    @Override
    public void endContact(Contact c) {
        getFixtures(c);
        
        if (dataFixA == null || dataFixB == null) {
            return;
        }
        if (dataFixB.equals("player-body") && 
                dataFixA instanceof MovingBlock) {
            deadContacts--;
        }
        if (dataFixA.equals("player-sensor") && 
                dataFixB.equals("goal-sensor")) {
            goalContacts--;
        }
    }
    
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
    private void getFixtures(Contact c) {
        dataFixA = c.getFixtureA().getUserData();
        dataFixB = c.getFixtureB().getUserData();
    }
    
    public static boolean isGoalReached() {
        return goalContacts > 0;
    }
    
    public static boolean isPlayerDead() {
        return deadContacts > 0;
    }
    
}
