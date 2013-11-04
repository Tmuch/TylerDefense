package enemies;

import main.Game;
import map.Waypoint;

import org.newdawn.slick.Graphics;

import towers.Tower;

public class Enemy {
	
	private float x, y;
	private float radius;
	private Waypoint targetWaypoint;
	private boolean switchedWaypoints;
	private float dx, dy;
	private float speed;
	
	private int health;
	public Game game;
	
	Tower attacker;
	
	private int id;
	
	public Enemy(int x, int y, Waypoint first, Game g)
	{
		this.x = x;
		this.y = y;
		radius = 5;
		switchedWaypoints = true;
		targetWaypoint = first;
		speed = 100;
		game = g;
	}
	
	public void render(Graphics g)
	{
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
	}
	
	public void update(int delta)
	{
		if(switchedWaypoints)
		{
			if(targetWaypoint == null) {
				//done
				return;
			} else {
				//recalculate dx and dy
				float diffX, diffY;
				diffX = targetWaypoint.getX() - this.x;
				diffY = targetWaypoint.getY() - this.y;
				float magnitude = (float)Math.sqrt(diffX*diffX + diffY*diffY);
				dx = diffX / magnitude;
				dy = diffY / magnitude;
				//System.out.println("dx: " + dx + ", dy: " + dy);
				
				switchedWaypoints = false;
			}
			
		} else {	
			
			if(Game.circlesCollide(this.x, this.y, 1, this.targetWaypoint.getX(), this.targetWaypoint.getY(), 1))
			{
				switchedWaypoints = true;
				this.targetWaypoint = this.targetWaypoint.getNext();
				return;
			}
			
			this.x += dx * speed * (delta/1000.0);
			this.y += dy * speed * (delta/1000.0);
			
			/* TODO: SIGNAL TOWER IF WE HAVE MOVED OUT OF RANGE */
			if((attacker != null)){
				System.out.println("here");
				if (!Game.enemyInRange(attacker, this))
				{
					attacker.enemyOutOfRange(this);
				}
			}
		}
	}
	
	public void doDamage(int damage)
	{
		health -= damage;
		if(health <= 0)
		{
			//dead
			attacker.enemyHasDied(this); //signal tower to re-target
			this.destroy();
		}
	}
	
	public void destroy()
	{
		game.removeEnemy(this);
	}
	
	public int getID()
	{
		return id;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public void setAttacker(Tower t)
	{
		attacker = t;
	}

}
