package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Goal extends FloatingObject {
    
    public Goal(float x, float y, World world) {
        super();
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }

    private void init() {
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.StaticBody;
        CircleShape cs = new CircleShape();
        cs.setRadius(20 / PPM);
        fdef.shape = cs;
        fdef.isSensor = true;
        body = world.createBody(bdef);
        body.createFixture(fdef);
        
        // Sensor
        cs.setRadius(5 / PPM);
        body.createFixture(fdef).setUserData("goal-sensor");
    }
    
    @Override
    public void update(float dt) {
    }

    @Override
    public void draw(ShapeRenderer sr) {
    }

}
