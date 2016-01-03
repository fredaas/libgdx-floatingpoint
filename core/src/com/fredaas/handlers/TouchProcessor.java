package com.fredaas.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.fredaas.game.Game;

public class TouchProcessor {
    
    private static boolean touching;
    private static Vector3 worldCoord = new Vector3();
    private static Vector3 screenCoord = new Vector3();
    
    public TouchProcessor() {
    }
    
    public static void setTouchState(boolean b) {
        touching = b;
    }
    
    public static boolean isTouching() {
        return touching;
    }
    
    public static void update() {
        screenCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        worldCoord.set(screenCoord);
        Game.b2dCam.unproject(worldCoord);
    }
    
    public static Vector3 getScreenCoord() {
        return screenCoord;
    }
    
    public static Vector3 getWorldCoord() {
        return worldCoord;
    }

}
