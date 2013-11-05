package main;

import java.util.ArrayList;

import map.*;
import input.*;
import input.Input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import towers.Tower;
import enemies.Enemy;

public class Game extends BasicGame{
	private final int UPS = 60; //updates per second
	private final int MS_PER_UPDATE = (int)Math.ceil(1000D / UPS);
	
	String text = "";
	
	Map map;
	Input input;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Tower> towers = new ArrayList<Tower>();
	
	ArrayList<Point> towerPoints = new ArrayList<Point>();

	public Game(String title) 
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException 
	{
		g.drawString(text, gc.getWidth()/2 - g.getFont().getWidth(text)/2, gc.getHeight()/2 - g.getFont().getHeight(text)/2);
		
		Waypoint w = map.getInitWaypoint();
		while(!(w == null))
		{
			g.fillOval(w.getX() - 1.5f, w.getY() - 1.5f, 3, 3);
			w = w.getNext();
		}
		
		for(Enemy e : enemies)
		{
			e.render(g);
		}
		for(Tower t : towers)
		{
			t.debugRender(g);
		}
		
		g.drawString("(" + Mouse.getX() + ", " + Mouse.getY() + ")", 100, 10);
		
		for(Point p : towerPoints)
		{
			g.drawRect(p.getX(), p.getY(), 5, 5);
		}
		
	}

	public void init(GameContainer gc) throws SlickException 
	{
		gc.setShowFPS(true); //a handy fps counter
		gc.setVSync(true);
		//don't mess with this stuff quite yet
		//gc.setMinimumLogicUpdateInterval(20); //maximum 50 updates per second
		//gc.setMaximumLogicUpdateInterval(20); //set a maximum?
		
		input = new Input();
		
		map = new Map();
		enemies.add(new Enemy(0, 400, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-100, 400, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-200, 400, map.getInitWaypoint(), this));
		enemies.add(new Enemy(-300, 400, map.getInitWaypoint(), this));
		
		towers.add(new Tower(this, 400, 400));
		towers.add(new Tower(this, 450, 400));
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
		for(Enemy e : enemies)
		{
			e.update(delta);
		}
		for(Tower t : towers)
		{
			t.shoot(delta);
		}
		
		input();
		
	}
	
	private void input()
	{
		input.update();
		if(input.getMouseDown(0))
		{
			//place tower
			int x = (input.getX() / 20) * 20;
			if((input.getX() % 20) >= 10)
			{
				x += 20;
			}
			
			int y = (input.getY() / 20) * 20;
			if((input.getY() % 20) >= 10)
			{
				y += 20;
			}
			towerPoints.add(new Point(x, Display.getHeight() - y));
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

}
