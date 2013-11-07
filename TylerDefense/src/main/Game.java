package main;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import map.*;
import input.Input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

import towers.Tower;
import util.Button;
import enemies.Enemy;

public class Game extends BasicGame{
	private final int UPS = 60; //updates per second
	private final int MS_PER_UPDATE = (int)Math.ceil(1000D / UPS);
	private final int RIGHT_PANEL_WIDTH = 150;
	private final int BOTTOM_PANEL_HEIGHT = 50;
	private int wave;
	
	Image levelBackground;
	
	String text = "";
	
	Map map;
	Input input;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Tower> towers = new ArrayList<Tower>();
	
	Button button;
	
	enum GameState {
		INIT, STARTED, STOPPED, PAUSED, BETWEEN_WAVES, DURING_WAVE, MENU
	}
	GameState state;

	public Game(String title)
	{
		super(title);
		state = GameState.INIT;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException 
	{	
		switch(state)
		{
		case INIT:
			break;
		case STOPPED:
			break;
		case PAUSED:
			break;
		case MENU:
			button.draw(g);
			break;
		case BETWEEN_WAVES:
			
		case DURING_WAVE:
			
		case STARTED:
			/* Draw background */
			if(levelBackground != null)
			{
				levelBackground.draw();
			}
			
			/* Draw Enemies */
			for(Enemy e : enemies)
			{
				e.render(g);
			}
			
			/* Draw Towers */
			for(Tower t : towers)
			{
				t.debugRender(g);
			}
			
			/* Draw Mouse Position */
			g.drawString("(" + Mouse.getX() + ", " + Mouse.getY() + ")", 100, 10);
			
			/* TESTING */
			g.drawRect(Display.getWidth() - RIGHT_PANEL_WIDTH, 0, RIGHT_PANEL_WIDTH, Display.getHeight());
			g.drawRect(0, Display.getHeight() - BOTTOM_PANEL_HEIGHT, Display.getWidth(), BOTTOM_PANEL_HEIGHT);
		}
		
		if(state == GameState.PAUSED) g.drawString("Paused", 10, Display.getHeight() - 20);
	}

	public void init(GameContainer gc) throws SlickException 
	{
		gc.setShowFPS(true); //a handy fps counter
		gc.setVSync(true);
		gc.setAlwaysRender(true);
		//don't mess with this stuff quite yet
		//gc.setMinimumLogicUpdateInterval(20); //maximum 50 updates per second
		//gc.setMaximumLogicUpdateInterval(20); //set a maximum?
		
		input = new Input();
		
		map = new Map();
		enemies.add(new Enemy(0, 125, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-100, 125, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-200, 125, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-300, 125, map.getInitWaypoint(), this));
		
		try {
			levelBackground = new Image(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/test_level.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* PRINTING GAME INFO */
		Log.info("Level screen: " + (Display.getWidth() - RIGHT_PANEL_WIDTH) + " x " + (Display.getHeight() - BOTTOM_PANEL_HEIGHT));
		
		
		try {
			button = new Button(new Image(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/buttons/play_button.png"))), 150, 150);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//towers.add(new Tower(this, 400, 400));
		//towers.add(new Tower(this, 450, 400));
		changeState(GameState.MENU);
	}

	/*
	 * delta = number of milliseconds between calls of update
	 * 	Rather than trying to build the game around a fixed number of milliseconds between frames,
	 * 		want to alter the game to move/update/adjust each element/sprite/AI based on this
	 * 		delta value. This allows avoiding needing to change game logic based on the power 
	 * 		of the hardware. 
	 */
	public void update(GameContainer gc, int delta) throws SlickException
	{
		//	INIT, STARTED, STOPPED, PAUSED, BETWEEN_WAVES, DURING_WAVE, MENU
		switch(state)
		{
		case INIT:
			break;
		case STARTED:
			break;
		case STOPPED:
			break;
		case PAUSED:
			// do nothing
			break;
		case BETWEEN_WAVES:
			break;
		case DURING_WAVE:
			if(enemies.isEmpty()) {
				changeState(GameState.BETWEEN_WAVES);
				break;
			} else {
				for(Enemy e : enemies)
				{
					e.update(delta);
				}
				for(int size = enemies.size(), i = 0; i < size; i++)
				{
					if(enemies.get(i).finished) {
						removeEnemy(enemies.get(i));
						size--;
						i--;
						Log.info("Enemy reached the end of the path!");
					}
				}
				for(Tower t : towers)
				{
					t.shoot(delta);
				}
			}
			break;
		case MENU:
			break;
		}
		
		input();
		
	}
	
	private void input()
	{
		input.update();
		if(input.getMouseDown(0))
		{
			if(state == GameState.BETWEEN_WAVES || (state == GameState.STARTED))
				placeTower(input.getX(), input.getY());
			
			/*
			 * "Play" button on main menu
			 */
			if(state == GameState.MENU) {
				if(button.pointInBounds(Mouse.getX(), Display.getHeight() - Mouse.getY())) {
					changeState(GameState.STARTED);
				}
			}
		}
		
		if(input.getKeyDown(Keyboard.KEY_ESCAPE))
		{
			if(state == GameState.DURING_WAVE)
				changeState(GameState.PAUSED);
			else if(state == GameState.PAUSED)
				changeState(GameState.DURING_WAVE);
		}
		if(input.getKeyDown(Keyboard.KEY_SPACE))
		{
			if(state == GameState.STARTED)
				changeState(GameState.DURING_WAVE);
		}
	}
	
	
	
	public static void main(String[] args)
	{
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("TylerDefense"));
			appgc.setDisplayMode(1200, 675, false);
			appgc.start();	
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * Static helper method to detect collision between circles.
	 * 
	 */
	public static boolean circlesCollide(float x1, float y1, float r1, float x2, float y2, float r2)
	{
		float dx = x1 - x2;
		float dy = y1 - y2;
		float dist = (float)Math.sqrt(dx*dx + dy*dy); //lack of precision shouldn't be a problem here, but keep it in mind for later
		if(dist <= r1 + r2) return true;
		return false;
	}
	
	public static boolean enemyInRange(Tower t, Enemy e)
	{
		return circlesCollide(t.x, t.y, t.radius, e.getX(), e.getY(), e.getRadius());
	}
	
	
	/*
	 * Loop through the array of enemies (from lowest index to highest)
	 * 		This should give me the enemy that is farthest along the path. 
	 */
	public Enemy getNextTarget(Tower t)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemyInRange(t, enemies.get(i)))
			{
				return enemies.get(i);
			}
		}
		
		return null;
	}
	
	public void removeEnemy(Enemy e)
	{
		enemies.remove(e);
	}
	
	private void placeTower(int x, int y)
	{
		int xx = (x / 20) * 20; //round down to nearest multiple of 20
		int yy = (y / 20) * 20;
		if((x % 20) >= 10)
		{
			xx += 20;
		}
		if((y % 20) >= 10)
		{
			yy += 20;
		}
		
		yy = Display.getHeight() - yy;
		
		for(Tower t : towers)
		{
			if((t.x == xx) && (t.y == yy)) {
				Log.warn("Already a tower there!");
				return;
			}
		}
		towers.add(new Tower(this, xx, yy));
		Log.info("Added tower at (" + xx + ", " + yy + ")");
		
	}
	
	private void changeState(GameState next)
	{
		Log.info("Game State: " + state + " -> " + next);
		state = next;
	}

}
