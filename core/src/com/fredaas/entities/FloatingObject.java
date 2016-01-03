package com.fredaas.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class FloatingObject {

    protected float x;
    protected float y;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    protected Body body;
    protected World world;
    
    public FloatingObject() {
        init();
    }
    
    private void init() {
        bdef = new BodyDef();
        fdef = new FixtureDef();
    }
    
    public abstract void update(float dt);
    public abstract void draw(ShapeRenderer sr);
    
}
