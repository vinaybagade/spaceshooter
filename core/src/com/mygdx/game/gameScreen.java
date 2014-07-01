package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class gameScreen implements Screen,GestureListener {
	MyGdxGame game;
	Texture player;
	OrthographicCamera camera;
	Rectangle playerrectangle;
	Array<Rectangle>bullets;
	Texture enemybullet;
	Level level;
	int bulletlevel;
	int noofenemies;
	boolean bosspresent;
	Texture background;
	Bullet bullet;
	int enemycount;
	int enemyescaped;
	boolean poweruppresent;
	int score;
	
	public gameScreen(MyGdxGame game){
		this.game=game;
		player=new Texture(Gdx.files.internal("speedship.png"));
		enemybullet=new Texture(Gdx.files.internal("enemybullet.png"));
		camera=new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		playerrectangle=new Rectangle();
		playerrectangle.y=Gdx.graphics.getHeight()/2-120/2;
		playerrectangle.x=20;
		playerrectangle.width=120;
		playerrectangle.height=120;
		bullets=new Array<Rectangle>();
		level=new Level(1);
		bulletlevel=1;
		noofenemies=0;
		bosspresent=false;
		background=new Texture(Gdx.files.internal("space.png"));
		bullet=new Bullet();
		level.spawnenemy(level.getvalue());
		enemycount=1;
		enemyescaped=0;
		poweruppresent=false;
		score=0;
		
	}
	@Override
	public void dispose() {
		player.dispose();
		enemybullet.dispose();
		level.boss1.dispose();
		level.boss2.dispose();
		level.boss3.dispose();
		level.boss4.dispose();
		level.enemy1.dispose();
		level.enemy2.dispose();
		level.enemy3.dispose();
		level.enemy4.dispose();
		bullet.level1.dispose();
		bullet.level2.dispose();
		bullet.level3.dispose();
		bullet.level4.dispose();
	}
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean tap(float x, float y, int count, int button) {
		bullet.spawnbullet(bulletlevel);
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Vector3 initialpos=new Vector3(x-deltaX,y-deltaY,0);
		camera.unproject(initialpos);
		if(playerrectangle.contains(initialpos.x,initialpos.y )){
			playerrectangle.x=initialpos.x-120/2;
			playerrectangle.y=initialpos.y-120/2;
			return true;
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		game.spritebatch.setProjectionMatrix(camera.combined);
		game.spritebatch.begin();
		game.spritebatch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		game.bitmapfont.draw(game.spritebatch,"Score : "+score+"     Escaped : "+enemyescaped+"     Level: "+level.getvalue(),0,Gdx.graphics.getHeight());
		game.spritebatch.draw(player,playerrectangle.x,playerrectangle.y);
		for(Rectangle bulletrectangle:bullets){
			game.spritebatch.draw(bullet.getBullettexture(bulletlevel),bulletrectangle.x,bulletrectangle.y);
		}
		for( Enemy enemy:level.enemies){
			game.spritebatch.draw(level.enemyTexture(level.getvalue()),enemy.enemyspaceship.x,enemy.enemyspaceship.y);
			for(Rectangle enemybulletrectangle:enemy.bullets){
				game.spritebatch.draw(enemybullet,enemybulletrectangle.x,enemybulletrectangle.y);
			}
		}
		if(poweruppresent){
			game.spritebatch.draw(level.powerup,level.poweruprectangle.x,level.poweruprectangle.y);
		}
		if(bosspresent){
			game.spritebatch.draw(level.bossTexture(level.getvalue()),level.boss.bossship.x,level.boss.bossship.y);
			for(Rectangle bossbullets:level.boss.bullets){
				game.spritebatch.draw(enemybullet,bossbullets.x,bossbullets.y);
			}
		}
		game.spritebatch.end();
		Iterator<Rectangle> bulletiterator=bullets.iterator();
		while(bulletiterator.hasNext()){
			Rectangle currentbullet=bulletiterator.next();
			currentbullet.x=currentbullet.x+200*Gdx.graphics.getDeltaTime();
			if(currentbullet.x>Gdx.graphics.getWidth()){
				bulletiterator.remove();
			}
			Iterator<Enemy>enemyiterator=level.enemies.iterator();
			while(enemyiterator.hasNext()){
				Enemy currentenemy=enemyiterator.next();
				if(currentenemy.enemyspaceship.overlaps(currentbullet)){
					currentenemy.health=currentenemy.health-bulletlevel*50;
					bulletiterator.remove();
					
					if(currentenemy.health<=0){
						enemyiterator.remove();
						score++;
					}
				}
			}
			if(bosspresent){
				if(level.boss.bossship.overlaps(currentbullet)){
					bulletiterator.remove();
					level.boss.health=level.boss.health-bulletlevel*50;
					if(level.boss.health<=0){
						level.boss.bossship=null;
						score=score+10*level.getvalue();
						if(level.getvalue()<4){
							level.setvalue(level.getvalue()+1);
							enemycount=0;
							bosspresent=false;
							level.enemies.clear();
						}else{
							game.setScreen(new gameoverscreen(game, score));
							dispose();
						}
					}
				}
				if(TimeUtils.nanoTime()-level.boss.spawntime>2000000000){
					level.boss.spawnbossbullets();
				}
				
			}
		}
		if((TimeUtils.nanosToMillis(TimeUtils.nanoTime())-TimeUtils.nanosToMillis(level.lastenemytime)>5000/level.getvalue()) && enemycount<20){
			level.spawnenemy(level.getvalue());
			enemycount++;
		}
		Iterator<Enemy> enemyiterator=level.enemies.iterator();
		while(enemyiterator.hasNext()){
			Enemy currentenemy=enemyiterator.next();
			currentenemy.enemyspaceship.x=currentenemy.enemyspaceship.x-20*level.getvalue()*Gdx.graphics.getDeltaTime();
			if(currentenemy.enemyspaceship.x<0){
				enemyiterator.remove();
				enemyescaped++;
			}
			if(TimeUtils.nanosToMillis(TimeUtils.nanoTime())-TimeUtils.nanosToMillis(currentenemy.spawntime)>6000/level.getvalue()){
				currentenemy.spawnenemybullet();
				currentenemy.spawntime=TimeUtils.nanoTime();
			}
			if(currentenemy.enemyspaceship.overlaps(playerrectangle)){
				game.setScreen(new gameoverscreen(game,score));
				dispose();
			}
			Iterator<Rectangle>currentenemybullets=currentenemy.bullets.iterator();
			while(currentenemybullets.hasNext()){
				Rectangle currentenemybulletrectangle=currentenemybullets.next();
				currentenemybulletrectangle.x=currentenemybulletrectangle.x-50*level.getvalue()*Gdx.graphics.getDeltaTime();
				if(currentenemybulletrectangle.x<0){
					currentenemybullets.remove();
				}
				if(currentenemybulletrectangle.overlaps(playerrectangle)){
					game.setScreen(new gameoverscreen(game,score));
					dispose();
				}
			}
		}
		if(enemycount==10 && poweruppresent==false){
			poweruppresent=true;
			level.spawnpowerup();
			enemycount++;
		}
		if(poweruppresent){
			level.poweruprectangle.x=level.poweruprectangle.x-200*Gdx.graphics.getDeltaTime();
			if(level.poweruprectangle.overlaps(playerrectangle)){
				if(bulletlevel<4){
					bulletlevel++;
				}
				
				poweruppresent=false;
			}
			if(level.poweruprectangle.x<0){
				poweruppresent=false;
				
			}
		}
		if(enemycount==20 && bosspresent==false){
			bosspresent=true;
			level.spawnboss(level.getvalue());
		}
		if(bosspresent){
			level.boss.bossship.y=level.boss.bossship.y+50*Gdx.graphics.getDeltaTime();
			if(level.boss.bossship.y>Gdx.graphics.getHeight()){
				level.boss.bossship.y=0;
			}
			Iterator<Rectangle>currentbossbullets=level.boss.bullets.iterator();
			while(currentbossbullets.hasNext()){
				Rectangle currentbossbulletrectangle=currentbossbullets.next();
				currentbossbulletrectangle.x=currentbossbulletrectangle.x-20*Gdx.graphics.getDeltaTime();
				if(currentbossbulletrectangle.x<0){
					currentbossbullets.remove();
				}
				if(currentbossbulletrectangle.overlaps(playerrectangle)){
					game.setScreen(new gameoverscreen(game,score));
					dispose();
				}
			}
		}
		if(enemyescaped>10){
			game.setScreen(new gameoverscreen(game,score));
			dispose();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		
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

	
	
	class Enemy{
		Rectangle enemyspaceship;
		int health;
		long spawntime;
		Array<Rectangle>bullets=new Array<Rectangle>();
		void spawnenemybullet(){
			Rectangle enemybullet= new Rectangle();
			enemybullet.width=32;
			enemybullet.height=32;
			enemybullet.x=enemyspaceship.x;
			enemybullet.y=enemyspaceship.y+enemyspaceship.height/2-16;
			bullets.add(enemybullet);
			
		}
	}
	class Boss{
		Rectangle bossship;
		int health;
		long spawntime;
		Array<Rectangle>bullets=new Array<Rectangle>();
		void spawnbossbullets(){
			Rectangle enemybullet= new Rectangle();
			enemybullet.width=32;
			enemybullet.height=32;
			enemybullet.x=bossship.x;
			enemybullet.y=bossship.y+bossship.height-16;
			bullets.add(enemybullet);
			spawntime=TimeUtils.nanoTime();
		}
	}
	class Level{
		int value;
		Array<Enemy>enemies=new Array<gameScreen.Enemy>();
		long lastenemytime;
		int enemycount;
		Boss boss;
		Rectangle poweruprectangle;
		Texture boss1,boss2,boss3,boss4,enemy1,enemy2,enemy3,enemy4,powerup;
		Level(int value){
			this.value=value;
			enemy1=new Texture(Gdx.files.internal("level1.png"));
			enemy2=new Texture(Gdx.files.internal("level2.png"));;
			enemy3=new Texture(Gdx.files.internal("level3.png"));
			enemy4=new Texture(Gdx.files.internal("level4.png"));
			boss1= new Texture(Gdx.files.internal("boss1.png"));
			boss2= new Texture(Gdx.files.internal("boss2.png"));
			boss3=new Texture(Gdx.files.internal("boss3.png"));
			boss4=new Texture(Gdx.files.internal("boss4.png"));
			powerup=new Texture(Gdx.files.internal("powerup.png"));
		}
		void setvalue(int value){
			this.value=value;
		}
		int getvalue(){
			return value;
		}
		void spawnpowerup(){
			poweruprectangle=new Rectangle();
			poweruprectangle.x=Gdx.graphics.getWidth();
			poweruprectangle.y=MathUtils.random(0,Gdx.graphics.getHeight()-18);
			poweruprectangle.width=27;
			poweruprectangle.height=36;
		}
		Texture enemyTexture(int value){
			switch (value) {
			case 1:
				return enemy1;
			case 2:
				return enemy2;
			case 3:
				return enemy3;
			case 4:
				return enemy4;
			}
			return null;
		}
		Texture bossTexture(int value){
			switch(value){
			case 1:
				return boss1;
			case 2:
				return boss2;
			case 3:
				return boss3;
			case 4:
				return boss4;
			}
			return null;
			
		}
		void spawnboss(int value){
			switch (value) {
			case 1:
				boss=new Boss();
				boss.bossship=new Rectangle();
				boss.bossship.width=120;
				boss.bossship.height=120;
				boss.bossship.y=Gdx.graphics.getHeight()-60;
				boss.bossship.x=Gdx.graphics.getWidth()-120;
				boss.health=1000;
				break;
			case 2:
				boss=new Boss();
				boss.bossship=new Rectangle();
				boss.bossship.width=172;
				boss.bossship.height=108;
				boss.bossship.y=Gdx.graphics.getHeight()-54;
				boss.bossship.x=Gdx.graphics.getWidth()-172;
				boss.health=2000;
			case 3:
				boss=new Boss();
				boss.bossship=new Rectangle();
				boss.bossship.width=200;
				boss.bossship.height=114;
				boss.bossship.y=Gdx.graphics.getHeight()-57;
				boss.bossship.x=Gdx.graphics.getWidth()-200;
				boss.health=3000;
				break;
			case 4:
				boss=new Boss();
				boss.bossship=new Rectangle();
				boss.bossship.width=188;
				boss.bossship.height=113;
				boss.bossship.y=Gdx.graphics.getHeight()-56;
				boss.bossship.x=Gdx.graphics.getWidth()-188;
				boss.health=4000;
				break;
			default:
				break;
			}
			
			
		}
		void spawnenemy(int value){
			switch (value) {
			case 1:
				Enemy enemy1=new Enemy();
				enemy1.enemyspaceship=new Rectangle();
				enemy1.enemyspaceship.width=59;
				enemy1.enemyspaceship.height=44;
				enemy1.enemyspaceship.x=Gdx.graphics.getWidth();
				enemy1.enemyspaceship.y=MathUtils.random(0, Gdx.graphics.getHeight()-44);
				enemy1.health=300;
				enemy1.spawntime=TimeUtils.nanoTime();
				this.lastenemytime=TimeUtils.nanoTime();
				enemies.add(enemy1);
				break;
			case 2:
				Enemy enemy2=new Enemy();
				enemy2.enemyspaceship=new Rectangle();
				enemy2.enemyspaceship.width=64;
				enemy2.enemyspaceship.height=64;
				enemy2.enemyspaceship.x=Gdx.graphics.getWidth();
				enemy2.enemyspaceship.y=MathUtils.random(0, Gdx.graphics.getHeight()-64);
				enemy2.health=600;
				enemy2.spawntime=TimeUtils.nanoTime();
				this.lastenemytime=TimeUtils.nanoTime();
				enemies.add(enemy2);
				break;
			case 3:
				Enemy enemy3=new Enemy();
				enemy3.enemyspaceship=new Rectangle();
				enemy3.enemyspaceship.width=85;
				enemy3.enemyspaceship.height=85;
				enemy3.enemyspaceship.x=Gdx.graphics.getWidth();
				enemy3.enemyspaceship.y=MathUtils.random(0, Gdx.graphics.getHeight()-85);
				enemy3.health=900;
				enemy3.spawntime=TimeUtils.nanoTime();
				this.lastenemytime=TimeUtils.nanoTime();
				enemies.add(enemy3);
				break;
			case 4:
				Enemy enemy4=new Enemy();
				enemy4.enemyspaceship=new Rectangle();
				enemy4.enemyspaceship.width=84;
				enemy4.enemyspaceship.height=72;
				enemy4.enemyspaceship.x=Gdx.graphics.getWidth();
				enemy4.enemyspaceship.y=MathUtils.random(0, Gdx.graphics.getHeight()-72);
				enemy4.health=1200;
				enemy4.spawntime=TimeUtils.nanoTime();
				this.lastenemytime=TimeUtils.nanoTime();
				enemies.add(enemy4);
				break;
			default:
				break;
			}
			
		}
	}
	class Bullet{
		Texture level1,level2,level3,level4;
		Bullet(){
			level1=new Texture(Gdx.files.internal("bullet.png"));
			level2=new Texture(Gdx.files.internal("bullet1.png"));
			level3=new Texture(Gdx.files.internal("bullet2.png"));
			level4=new Texture(Gdx.files.internal("bullet3.png"));
		}
		Texture getBullettexture(int value){
			switch (value) {
			case 1:
				return level1;
			case 2:
				return level2;
			case 3:
				return level3;
			case 4:
				return level4;
			default:
				break;
			}
			return null;
		}
		void spawnbullet(int value){
			switch (value) {
			case 1:
				Rectangle bullet1=new Rectangle();
				bullet1.width=32;
				bullet1.height=32;
				bullet1.x=playerrectangle.x+120;
				bullet1.y=playerrectangle.y+60-16;
				bullets.add(bullet1);
				break;
			case 2:
				Rectangle bullet2=new Rectangle();
				bullet2.width=21;
				bullet2.height=13;
				bullet2.x=playerrectangle.x+120;
				bullet2.y=playerrectangle.y+60-6;
				bullets.add(bullet2);
				break;
			case 3:
				Rectangle bullet3=new Rectangle();
				bullet3.width=90;
				bullet3.height=9;
				bullet3.x=playerrectangle.x+120;
				bullet3.y=playerrectangle.y+60-4;
				bullets.add(bullet3);
				break;
			case 4:
				Rectangle bullet4=new Rectangle();
				bullet4.width=48;
				bullet4.height=26;
				bullet4.x=playerrectangle.x+120;
				bullet4.y=playerrectangle.y+60-13;
				bullets.add(bullet4);
			default:
				break;
			}
			
		}
		
	}
}
