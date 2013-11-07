package towers;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import main.Game;
import enemies.Enemy;


/*
 * Towers will be placed in a grid. They will only be able to assume set x and y values (integers).
 */
public class Tower {
	
	public int x, y;
	public int damage; //amount of damage caused by each connected shot
	public int fireRate; //number of milliseconds between shots
	public float radius;
	
	public Enemy target; //the enemy that this tower is currently targeting
	public Game game;
	public int ms;
	
	public int totalKills;
	
	public Tower(Game g, int x, int y)
	{
		game = g;
		this.x = x; 
		this.y = y;
		damage = 10;
		fireRate = 500;
		radius = 50;
		ms = fireRate; //we can fire right away
		totalKills = 0;
	}
	
	public void shoot(int delta)
	{
		/*
		 * If we don't have an enemy to shoot at, get one.
		 */
		if(target == null) retarget();
		else {
			//shoot if we can based on fireRate
			ms += delta;
			if(ms >= fireRate) {
				//System.out.println("shoot");
				target.doDamage(damage);
				ms = 0;
			}
		}
	}
	
	public void targetDead(Enemy e)
	{
		outOfRange(e);
	}
	
	public void outOfRange(Enemy e)
	{
		//TODO:check if e == target
		target = null;
	}
	
	private void retarget()
	{
		/* For now, just find any enemy that is in range and select that enemy as a target. */
		target = game.getNextTarget(this);
		if(target != null) target.addAttacker(this);
	}
	
	public void debugRender(Graphics g)
	{
		Color c = g.getColor();
		if(target == null) g.setColor(Color.red);
		else g.setColor(Color.green);
		g.drawOval(this.x - radius, this.y - radius, radius*2, radius*2);
		g.setColor(c);
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}

}
