package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameInfo;

public class GameMainExampleAndNote extends Game {
	SpriteBatch batch;
	Texture img;
	Sprite player;
	
	@Override
	public void create () {
		System.out.println("CREATE WAS CALLED");
		batch = new SpriteBatch();
		img = new Texture("Game BG.png");
		//img2 = new Texture("badlogic.jpg");
		player = new Sprite(new Texture("Player 1.png"));
		player.setPosition((GameInfo.WIDTH / 2) - player.getWidth() / 2, (GameInfo.HEIGHT / 2) - player.getHeight() / 2);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.setPosition(player.getX() , player.getY()); // x+ 1 to the right 5 is faster
		batch.begin();
		batch.draw(img, 0, 0);
		//batch.draw(img2, (GameInfo.WIDTH / 2) - img2.getWidth() / 2, (GameInfo.HEIGHT / 2) - img2.getHeight() / 2);  //draw in the center
		batch.draw(player, player.getX(), player.getY());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
