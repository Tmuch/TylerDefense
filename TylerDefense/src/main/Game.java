package main;

import map.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

public class Game extends BasicGame{
	private final int UPS = 60; //updates per second
	private final int MS_PER_UPDATE = (int)Math.ceil(1000D / UPS);
	
	String text = "";
	
	Map map;

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
			g.fillRect(w.getX(), w.gety(), 3, 3);
			w = w.getNext();
		}
	}

	public void init(GameContainer gc) throws SlickException 
	{
		gc.setShowFPS(true); //a handy fps counter
		gc.setVSync(true);
		//don't mess with this stuff quite yet
		//gc.setMinimumLogicUpdateInterval(20); //maximum 50 updates per second
		//gc.setMaximumLogicUpdateInterval(20); //set a maximum?
		
		map = new Map();
		
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

}
