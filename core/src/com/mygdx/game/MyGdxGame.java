package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends Game{
	SpriteBatch spritebatch;
	BitmapFont bitmapfont;
	public static Preferences pref;
	public static final int width=800;
	public static final int hieght=480;
	public static final float aspectratio=(float)width/(float)hieght;
	@Override
	public void create() {
		spritebatch=new SpriteBatch();
		bitmapfont=new BitmapFont(Gdx.files.internal("whitefont.fnt"));
		bitmapfont.setScale(1);
		pref=Gdx.app.getPreferences("spaceshooter");
		if(!pref.contains("highscore")){
			pref.putInteger("highscore", 0);
			pref.flush();
		}
		this.setScreen(new Splashscreen(this));
		
		
	}
	@Override
	public void render() {
		super.render();
		
	}
	@Override
	public void dispose() {
		spritebatch.dispose();
		bitmapfont.dispose();
	}
	
	

	
 
		
	
	
}