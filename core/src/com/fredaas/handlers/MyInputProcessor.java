package com.fredaas.handlers;

import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {
    
    public MyInputProcessor() {
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        TouchProcessor.setTouchState(true);
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        TouchProcessor.setTouchState(false);
        return false;
    }

}
