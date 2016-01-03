package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fredaas.handlers.TouchProcessor;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends FloatingObject {
    
    private float dx;
    private float dy;
    private float speed;
    private boolean ready;
    
    public Player(float x, float y, World world) {
        super();
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        dx = 0;
        dy = 0;
        speed = 0.4f;
        
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        CircleShape cs = new CircleShape();
        cs.setRadius(20 / PPM);
        fdef.shape = cs;
        body = world.createBody(bdef);
        body.createFixture(fdef);
        
        // Sensor
        cs.setRadius(5 / PPM);
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("player");
    }
    
    public boolean isReady() {
        return ready;
    }
    
    public void setState(boolean b) {
        ready = b;
    }
    
    public void setBodyMovement() {
        setDirection(getTouchAngle() + MathUtils.PI);
    }
    
    public void stopBodyMovement() {
        dx = 0;
        dy = 0;
    }
    
    private void setDirection(float angle) {
        dx += speed * MathUtils.cos(angle);
        dy += speed * MathUtils.sin(angle);
    }
    
    private void setInertia() {
        dx *= 0.95;
        dy *= 0.95;
        dx = Math.abs(dx) < 0.1 ? 0 : dx;
        dy = Math.abs(dy) < 0.1 ? 0 : dy;
    }
    
    private float getTouchAngle() {
        float deltaX = TouchProcessor.getWorldCoord().x - body.getPosition().x;
        float deltaY = TouchProcessor.getWorldCoord().y - body.getPosition().y;
        return (float) Math.atan2(deltaY, deltaX);
    }
    
    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }
    
    @Override
    public void update(float dt) {
        setInertia();
        body.setLinearVelocity(dx, dy);
    }

    @Override
    public void draw(ShapeRenderer sr) {
    }

}
