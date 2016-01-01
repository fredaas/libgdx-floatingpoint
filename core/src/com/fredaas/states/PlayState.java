package com.fredaas.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Player;
import com.fredaas.game.Game;
import com.fredaas.handlers.GameStateManager;

public class PlayState extends GameState {

    private Box2DDebugRenderer dr;
    public static World world;
    private Player player;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    protected void init() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        dr = new Box2DDebugRenderer();
        player = new Player(Game.WIDTH / 2, Game.HEIGHT / 2);
    }

    @Override
    public void update(float dt) {
        dr.render(world, Game.b2dCam.combined);
        player.update(dt);
    }

    @Override
    public void draw(ShapeRenderer sr) {
        player.draw(sr);
    }
    
}
