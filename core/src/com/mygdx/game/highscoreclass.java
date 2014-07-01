package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class highscoreclass implements Screen{
	MyGdxGame game;
	OrthographicCamera camera;
	Texture background;
	BitmapFont bmpfnt;
	public highscoreclass(MyGdxGame game){
		this.game=game;
		camera=new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		background=new Texture(Gdx.files.internal("space.png"));
		bmpfnt=new BitmapFont(Gdx.files.internal("whitefont.fnt"));
		bmpfnt.setScale(2);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		game.spritebatch.setProjectionMatrix(camera.combined);
		game.spritebatch.begin();
		game.spritebatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bmpfnt.draw(game.spritebatch, "Highscore: "+game.pref.getInteger("highscore"),Gdx.graphics.getWidth()/2- bmpfnt.getBounds("HighScore: "+70).width/2, Gdx.graphics.getHeight()/2);
		game.spritebatch.end();
		if (Gdx.input.isTouched()) {
		   game.setScreen(new MenuScreen(game));
		   dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		background.dispose();
		bmpfnt.dispose();
		
	}
	
}
