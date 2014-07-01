package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Splashscreen implements Screen {

	private static final int NO_OF_ROWS=2;
	private static final int NO_OF_COLUMNS=3;
	Animation spaceship;
	Texture spacesheet;
	TextureRegion[]spaceframes;
	TextureRegion[]shipframes;
	TextureRegion currentstate;
	TextureRegion currentFrame;
	float statetime;
	OrthographicCamera camera;
	Texture background;
	Animation fire;
	MyGdxGame game;
	Texture withoutfuel;
	Texture fuel;
	Rectangle viewport;
	public Splashscreen(MyGdxGame gam){
		game=gam;
		camera=new OrthographicCamera(game.width,game.hieght);
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spacesheet=new Texture(Gdx.files.internal("animation.png"));
		background=new Texture(Gdx.files.internal("space.png"));
		shipframes=new TextureRegion[2];
		fuel=new Texture(Gdx.files.internal("fuel.png"));
		shipframes[0]=new TextureRegion(fuel, 0, 0, fuel.getWidth(), fuel.getHeight());
		withoutfuel=new Texture(Gdx.files.internal("withoutfuel.png"));
		shipframes[1]=new TextureRegion(withoutfuel, 0, 0, withoutfuel.getWidth(), withoutfuel.getHeight());
		spaceframes=new TextureRegion[NO_OF_COLUMNS*NO_OF_ROWS];
		TextureRegion[][]temp=TextureRegion.split(spacesheet, spacesheet.getWidth()/NO_OF_COLUMNS, spacesheet.getHeight()/NO_OF_ROWS);
		int index=0;
		for(int i=0;i<NO_OF_ROWS;i++){
			for(int j=0;j<NO_OF_COLUMNS;j++){
				spaceframes[index++]=temp[i][j];
			}
		}
		spaceship=new Animation(0.050f, spaceframes);
		fire=new Animation(0.010f,shipframes);
		game.spritebatch=new SpriteBatch();
		statetime=0;
		
	}
	
	@Override
	public void render(float delta) {
		statetime+=Gdx.graphics.getDeltaTime();
		camera.update();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,(int) viewport.width, (int) viewport.height);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	
		game.spritebatch.setProjectionMatrix(camera.combined);
		currentFrame=spaceship.getKeyFrame(statetime,true );
		currentstate=fire.getKeyFrame(statetime, true);
		game.spritebatch.begin();
		game.spritebatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.bitmapfont.draw(game.spritebatch,"Bagade Presents",Gdx.graphics.getWidth()/2- game.bitmapfont.getBounds("Bagade Presents").width/2,2*Gdx.graphics.getHeight()/5);
		game.spritebatch.draw(currentFrame,Gdx.graphics.getWidth()/2-currentFrame.getRegionWidth()/2,3*Gdx.graphics.getHeight()/5);
		game.spritebatch.draw(currentFrame,Gdx.graphics.getWidth()/3-currentFrame.getRegionWidth()/2,4*Gdx.graphics.getHeight()/5);
		game.spritebatch.draw(currentFrame,2*Gdx.graphics.getWidth()/3-currentFrame.getRegionWidth()/2,4*Gdx.graphics.getHeight()/5);
		game.spritebatch.draw(currentstate,Gdx.graphics.getWidth()/2-currentstate.getRegionWidth()/2,Gdx.graphics.getHeight()/5);
		game.spritebatch.end();
		if (Gdx.input.isTouched()) {
	       game.setScreen(new MenuScreen(game));
	       dispose();
	    }
		 
		
	}
	@Override
	public void resize(int width, int height) {
		float aspectratio=(float)width/(float)height;
		float scale=1f;
		Vector2 crop=new Vector2(0f,0f);
		if(aspectratio>game.aspectratio){
			scale = (float)height/(float)game.hieght;
			crop.x=(width - game.width*scale)/2f;
		}else if(aspectratio < game.aspectratio)
        {
            scale = (float)width/(float)game.width;
            crop.y = (height - game.hieght*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)game.width;
        }

        float w = (float)game.width*scale;
        float h = (float)game.hieght*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
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
		spacesheet.dispose();
		background.dispose();
		fuel.dispose();
		withoutfuel.dispose();
		
	}


	


}
