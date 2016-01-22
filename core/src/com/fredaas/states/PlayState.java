package com.fredaas.states;

import static com.fredaas.handlers.Vars.PPM;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Goal;
import com.fredaas.entities.MovingBlock;
import com.fredaas.entities.Player;
import com.fredaas.game.Game;
import com.fredaas.handlers.B2DObjectProcessor;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.GameStateManager.State;
import com.fredaas.handlers.MyContactListener;
import com.fredaas.handlers.TouchProcessor;

public class PlayState extends GameState {

    private Box2DDebugRenderer dr;
    private Player player;
    private Goal goal;
    private ArrayList<MovingBlock> blocks;
    private TiledMap tm;
    private TiledMapRenderer tr;
    private B2DObjectProcessor op;
    private World world;
    private double timer;
    private double timerDiff;
    private double timerDelay;
    private boolean debug;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    protected void init() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new MyContactListener());
        dr = new Box2DDebugRenderer();
        tm = new TmxMapLoader().load("maps/map.tmx");
        tr = new OrthogonalTiledMapRenderer(tm);
        op = new B2DObjectProcessor(tm, world);
        op.loadObjects();
        blocks = new ArrayList<MovingBlock>();
        createEntities();
        setVariables();
    }
    
    private void setVariables() {
        timer = 0;
        timerDiff = 0;
        timerDelay = 1000;
        debug = true;
    }
    
    private void createEntities() {
        for (MapObject obj : tm.getLayers().get("player").getObjects()) {
            createPlayer((EllipseMapObject) obj);
        }
        for (MapObject obj : tm.getLayers().get("goal").getObjects()) {
            createGoal((EllipseMapObject) obj);
        }
        for (MapObject obj : tm.getLayers().get("moving-blocks").getObjects()) {
            createObstacles((EllipseMapObject) obj);
        }
    }
    
    private void createPlayer(EllipseMapObject obj) {
        player = new Player(
                obj.getEllipse().x, 
                obj.getEllipse().y, 
                world);
    }
    
    private void createGoal(EllipseMapObject obj) {
        goal = new Goal(
                obj.getEllipse().x, 
                obj.getEllipse().y, 
                world);
    }
    
    private void createObstacles(EllipseMapObject obj) {
        blocks.add(new MovingBlock(
                obj.getEllipse().x,
                obj.getEllipse().y,
                world));
    }
    
    private void handleCameras() {
        Game.cam.position.set(
                player.getPosition().x * PPM, 
                player.getPosition().y * PPM, 0);
        Game.b2dCam.position.set(
                player.getPosition().x, 
                player.getPosition().y, 0);
        Game.cam.update();
        Game.b2dCam.update();
    }
    
    private void preparePlayer() {
        player.setReady(true);
        world.destroyBody(player.getBody());
        timer = System.nanoTime();
    }
    
    private void disposeBodies() {
        for (MovingBlock block : blocks) {
            world.destroyBody(block.getBody());
        }
    }
    
    private boolean updateTimer() {
        timerDiff = (System.nanoTime() - timer) / 1000000;
        return timerDiff > timerDelay;
    }
    
    private void loadNewState() {
        if (updateTimer()) {
            player.setReady(false);
            gsm.loadState(State.GAMEOVER);
            disposeBodies();
        }
    }
    
    private void handleDebugger() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            debug = !debug;
        }
        if (debug) {
            dr.render(world, Game.b2dCam.combined);
        }
    }
    
    @Override
    public void update(float dt) {
        TouchProcessor.update();
        
        if (!player.isReady()) {
            if (TouchProcessor.isTouching()) { 
                player.setBodyMovement();
            }
            if (MyContactListener.isGoalReached() || 
                    MyContactListener.isPlayerDead()) {
                preparePlayer();
            }
        }
        else if (player.isReady()) {
            loadNewState();
        }
        
        for (MovingBlock block : blocks) {
            block.update(dt);
        }
        
        player.update(dt);
        goal.update(dt);
        
        handleCameras();
        handleDebugger();
        
        tr.setView(Game.cam);
        tr.render();
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void draw(ShapeRenderer sr) {
        for (MovingBlock block : blocks) {
            block.draw(sr);
        }
        
        player.draw(sr);
        goal.draw(sr);
    }
    
}
