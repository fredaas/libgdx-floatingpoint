package com.fredaas.handlers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.states.GameState;
import com.fredaas.states.PlayState;

public class GameStateManager {
    
    private GameState gs;
    
    public static enum State {
        PLAY,
        MENU,
        GAMEOVER,
        SCORE, 
        CONTROLS;
    }
    
    public GameStateManager() {
        loadState(State.PLAY);
    }
    
    public void loadState(State state) {
        switch (state) {
        case PLAY:
            gs = new PlayState(this);
            break;
        case MENU:
            break;
        case GAMEOVER:
            break;
        case SCORE:
            break;
        case CONTROLS:
            break;
        }
    }
    
    public void update(float dt) {
        gs.update(dt);
    }
    
    public void draw(ShapeRenderer sr) {
        gs.draw(sr);
    }

}
