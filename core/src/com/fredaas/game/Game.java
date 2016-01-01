package com.fredaas.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;
import static com.fredaas.handlers.Vars.PPM;

public class Game implements ApplicationListener {
	
    private GameStateManager gsm;
    public static int WIDTH;
    public static int HEIGHT;
    private ShapeRenderer sr;
    public static OrthographicCamera cam;
    public static OrthographicCamera hudCam;
    public static OrthographicCamera b2dCam;
    
	@Override
	public void create () {
	    WIDTH = Gdx.graphics.getWidth();
	    HEIGHT = Gdx.graphics.getHeight();
	    cam = new OrthographicCamera();
	    cam.setToOrtho(false, WIDTH, HEIGHT);
	    hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, WIDTH, HEIGHT);
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
        gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw(sr);
	}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    
}
