package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
	MyGdxGame game;
	TextureAtlas textureatlas;
	Skin skin;
	TextButtonStyle tbs;
	Stage stage;
	BitmapFont black;
	
	TextButton start,help,highscore;
	backgroundimage image;
	
	public MenuScreen(MyGdxGame gam){
		game=gam;
		stage=new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		textureatlas=new TextureAtlas(Gdx.files.internal("button.pack"));
		skin=new Skin();
		skin.addRegions(textureatlas);
		tbs=new TextButtonStyle();
		tbs.up=skin.getDrawable("buttonnormal");
		tbs.down=skin.getDrawable("buttonpressed");
		black=new BitmapFont(Gdx.files.internal("font.fnt"),false);
		tbs.font=black;
		image=new backgroundimage();
		image.setHeight(Gdx.graphics.getHeight());
		image.setWidth(Gdx.graphics.getWidth());
		image.setX(0);
		image.setY(0);
		stage.addActor(image);
		start=new TextButton("Start",tbs);
		start.setHeight(Gdx.graphics.getHeight()/15);
		start.setWidth(Gdx.graphics.getWidth()/4);
		start.setX(Gdx.graphics.getWidth()/2-start.getWidth()/2);
		start.setY(2*Gdx.graphics.getHeight()/5);
		stage.addActor(start);
		highscore=new TextButton("HighScore",tbs);
		highscore.setHeight(Gdx.graphics.getHeight()/15);
		highscore.setWidth(Gdx.graphics.getWidth()/4);
		highscore.setX(Gdx.graphics.getWidth()/2-highscore.getWidth()/2);
		highscore.setY(Gdx.graphics.getHeight()/5);
		stage.addActor(highscore);
		start.addListener(new InputListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new gameScreen(game));
				dispose();
			}
			
		});
		highscore.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new highscoreclass(game));
				dispose();
			}
		});
	}
	public class backgroundimage extends Actor{
		Texture texture;
		BitmapFont bmpfnt;
		public backgroundimage(){
			texture=new Texture(Gdx.files.internal("space.png"));
			bmpfnt=new BitmapFont(Gdx.files.internal("whitefont.fnt"));
		}
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(texture,0,0,getWidth(),getHeight());
			bmpfnt.setScale(2);
			bmpfnt.draw(batch, "LaserAge",getWidth()/2- bmpfnt.getBounds("laserAge").width/2,4*getHeight()/5);
		}
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		game.spritebatch.begin();
		stage.draw();
		game.spritebatch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height,false);
		stage.draw();
		
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
		
		textureatlas.dispose();
		skin.dispose();
		stage.dispose();
		black.dispose();
		
		
	}

}
