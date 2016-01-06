package com.fredaas.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fredaas.game.Game;
import com.fredaas.handlers.GameStateManager;

public class TransitionState {
    
    private static float step;
    private static float x;
    private static float y;
    private static Color color;
    private GameState gs;
    private GameStateManager gsm;
    private boolean readyOut;
    private boolean readyIn;
    private boolean readyLoad;
    
    public TransitionState(GameStateManager gsm, GameState gs) {
        this.gsm = gsm;
        this.gs = gs;
        init();
    }
    
    private void init() {
        x = 0;
        y = 0;
        step = 0.1f;
        color = new Color(0, 1, 0, 0);
    }
    
    private void transitionIn() {
        color.a -= step;
        color.a = color.a < 0 ? 0 : color.a;
        readyIn = color.a == 0;
    }
    
    private void transitionOut() {
        color.a += step;
        color.a = color.a > 1 ? 1 : color.a;
        readyOut = color.a == 1;
    }
    
    public boolean finnished() {
        return readyOut && readyIn;
    }
    
    private void loadState() {
        if (!readyLoad) {
            readyLoad = true;
            gsm.setState(gs);
        }
    }
    
    public void update(float dt) {
        if (!readyOut) {
            transitionOut();
        }
        else {
            loadState();
            transitionIn();
        }
    }

    public void draw(ShapeRenderer sr) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.setColor(color);
        sr.begin(ShapeType.Filled);
        sr.rect(x, y, Game.WIDTH, Game.HEIGHT);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
