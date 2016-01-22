package com.fredaas.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.game.Game;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.GameStateManager.State;

public class GameOverState extends GameState {
    
    private SpriteBatch sb;
    private BitmapFont bmf;
    private GlyphLayout gl;
    private String text;
    
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    protected void init() {
        sb = new SpriteBatch();
        bmf = loadFont("fonts/bmf.ttf", 42);
        gl = new GlyphLayout();
        text = "GAME OVER";
    }
    
    /*
     * TODO: Make a class for handling fonts.
     */
    private BitmapFont loadFont(String path, int size) {
        FreeTypeFontParameter par = new FreeTypeFontParameter();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        par.size = size;
        BitmapFont font = gen.generateFont(par);
        gen.dispose();
        
        return font;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            gsm.loadState(State.PLAY);
        }
    }

    @Override
    public void draw(ShapeRenderer sr) {
        sb.begin();
        gl.setText(bmf, text);
        bmf.draw(sb, gl, (Game.WIDTH - gl.width) / 2, (Game.HEIGHT + gl.height) / 2);
        sb.end();
    }

}