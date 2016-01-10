package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovingBlock extends FloatingObject {

    private float size;
    private double timer;
    private double timerDiff;
    private double timerDelay;
    private boolean ready;
    
    public MovingBlock(float x, float y, World world) {
        super();
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        size = 10 / PPM;
        dx = 0;
        dy = 0;
        speed = 40 / PPM;
        ready = true;
        timer = 0;
        timerDiff = 0;
        timerDelay = 50;
        
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(size, size);
        fdef.shape = ps;
        fdef.isSensor = true;
        body = world.createBody(bdef);
        body.createFixture(fdef).setUserData(this);
    }
    
    public void changeDirection() {
        if (ready) {
            timer = System.nanoTime();
            speed *= -1;
            ready = false;
        }
    }
    
    private void updateTimer() {
        timerDiff = (System.nanoTime() - timer) / 1000000;
        if (timerDiff > timerDelay) {
            ready = true;
        }
    }
    
    @Override
    public void update(float dt) {
        updateTimer();
        dy = speed;
        body.setLinearVelocity(dx, dy);
    }

    @Override
    public void draw(ShapeRenderer sr) {
    }
    
}
