package com.fredaas.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    private static int numContacts;
    private Fixture fixA;
    private Fixture fixB;
    
    @Override
    public void beginContact(Contact c) {
        getFixtures(c);
        
        if (fixA.getUserData() != null && 
                fixB.getUserData() != null && 
                fixA.getUserData().equals("player") && 
                fixB.getUserData().equals("goal")) {
            numContacts++;
        }
    }

    @Override
    public void endContact(Contact c) {
        getFixtures(c);
        
        if (fixA.getUserData() != null && 
                fixB.getUserData() != null && 
                fixA.getUserData().equals("player") && 
                fixB.getUserData().equals("goal")) {
            numContacts--;
        }
    }
    
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
    private void getFixtures(Contact c) {
        fixA = c.getFixtureA();
        fixB = c.getFixtureB();
    }
    
    public static boolean isGoalReached() {
        return numContacts > 0;
    }
    
}
