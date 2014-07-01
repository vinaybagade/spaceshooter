package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class gameoverscreen implements Screen{
	MyGdxGame game;
	int score;
	OrthographicCamera camera;
	Texture background;
	public gameoverscreen(MyGdxGame game,int score){
		this.game=game;
		this.score=score;
		background=new Texture(Gdx.files.internal("space.png"));
		camera=new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(MyGdxGame.pref.getInteger("highscore")<score){
			MyGdxGame.pref.putInteger("highscore", score);
			MyGdxGame.pref.flush();
		}
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		game.spritebatch.setProjectionMatrix(camera.combined);
		game.spritebatch.begin();
		game.spritebatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.bitmapfont.draw(game.spritebatch,"Score: "+score,Gdx.graphics.getWidth()/2- game.bitmapfont.getBounds("Socre: "+score).width/2 , Gdx.graphics.getHeight()/2);
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
		
	}

}
