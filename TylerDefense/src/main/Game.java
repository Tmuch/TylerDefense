package main;

import org.newdawn.slick.*;

public class Game extends BasicGame{
	
	private int updateCounter;
	private final int UPS = 60; //updates per second
	private final int MS_PER_UPDATE = (int)Math.ceil(1000D / UPS);

	public Game(String title) 
	{
		super(title);
		
		updateCounter = 0;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException 
	{
		String greet = "Greetings";
		g.drawString("Greetings", gc.getWidth()/2 - g.getFont().getWidth(greet)/2, gc.getHeight()/2 - g.getFont().getHeight(greet)/2);
	}

	public void init(GameContainer gc) throws SlickException 
	{
		gc.setShowFPS(true); //a handy fps counter
		gc.setVSync(true);
		
		
		//don't mess with this stuff quite yet
		//gc.setMinimumLogicUpdateInterval(20); //maximum 50 updates per second
		//gc.setMaximumLogicUpdateInterval(20); //set a maximum?
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
