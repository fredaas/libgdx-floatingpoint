package com.fredaas.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.fredaas.states.PlayState;
import static com.fredaas.handlers.Vars.PPM;

public class Player extends FloatingObject {
    
    public Player(float x, float y) {
        super();
        this.x = x / PPM;
        this.y = y / PPM;
        init();
    }
    
    private void init() {
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        CircleShape cs = new CircleShape();
        cs.setRadius(20 / PPM);
        fdef.shape = cs;
        body = PlayState.world.createBody(bdef);
        body.createFixture(fdef);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void draw(ShapeRenderer sr) {
    }

}
