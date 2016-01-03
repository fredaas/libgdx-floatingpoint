package com.fredaas.states;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Player;
import com.fredaas.game.Game;
import com.fredaas.handlers.B2DObjectProcessor;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.TouchProcessor;

public class PlayState extends GameState {

    private Box2DDebugRenderer dr;
    private Player player;
    private TiledMap tm;
    private TiledMapRenderer tr;
    private B2DObjectProcessor op;
    private World world;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    protected void init() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        dr = new Box2DDebugRenderer();
        tm = new TmxMapLoader().load("maps/map.tmx");
        tr = new OrthogonalTiledMapRenderer(tm);
        op = new B2DObjectProcessor(tm, world);
        op.loadObjects();
        player = new Player(Game.WIDTH / 2, Game.HEIGHT / 2, world);
    }
    
    @Override
    public void update(float dt) {
        TouchProcessor.update();
        if (TouchProcessor.isTouching()) { 
            player.setBodyMovement();
        }
        
        player.update(dt);
        
        Game.cam.position.set(
                player.getPosition().x * PPM, 
                player.getPosition().y * PPM, 0);
        Game.b2dCam.position.set(
                player.getPosition().x, 
                player.getPosition().y, 0);
        Game.cam.update();
        Game.b2dCam.update();
        
        tr.setView(Game.cam);
        tr.render();
        dr.render(world, Game.b2dCam.combined);
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void draw(ShapeRenderer sr) {
        player.draw(sr);
    }
    
}
