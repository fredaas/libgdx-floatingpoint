package com.fredaas.handlers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.states.GameState;
import com.fredaas.states.MenuState;
import com.fredaas.states.PlayState;
import com.fredaas.states.TransitionState;

public class GameStateManager {
    
    private GameState gs;
    private TransitionState ts;
    
    public static enum State {
        PLAY,
        MENU,
        GAMEOVER,
        SCORE;
    }
    
    public GameStateManager() {
        setState(new PlayState(this));
    }
    
    public void setState(GameState state) {
        gs = state;
    }
    
    public void loadState(State state) {
        switch (state) {
            case PLAY:
                ts = new TransitionState(this, new PlayState(this));
                break;
            case MENU:
                ts = new TransitionState(this, new MenuState(this));
                break;
            case GAMEOVER:
                break;
            case SCORE:
                break;
        }
    }
    
    public void render(float dt, ShapeRenderer sr) {
        gs.update(dt);
        gs.draw(sr);
        if (ts != null && !ts.finnished()) {
            ts.update(dt);
            ts.draw(sr);
        }
    }
    
}
