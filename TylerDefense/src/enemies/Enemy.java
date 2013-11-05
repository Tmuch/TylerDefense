package enemies;

import java.util.ArrayList;

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
	
	public ArrayList<Tower> attackers;
	
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
		attackers = new ArrayList<Tower>();
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
			
			//if the enemy has reached it's next waypoint
			if(Game.circlesCollide(this.x, this.y, 1, this.targetWaypoint.getX(), this.targetWaypoint.getY(), 1))
			{
				switchedWaypoints = true;
				this.targetWaypoint = this.targetWaypoint.getNext();
				return;
			}
			
			this.x += dx * speed * (delta/1000.0);
			this.y += dy * speed * (delta/1000.0);
			
			/*
			 * Determine if we have moved out of range of any towers and alert those towers.
			 */
			
			int size = attackers.size();
			for(int i = 0; i < size; i++)
			{
				if(!Game.enemyInRange(attackers.get(i), this))
				{
					attackers.get(i).outOfRange(this);
					attackers.remove(i);
					i--;
					size--;
				}
			}
			
		}
	}
	
	public void addAttacker(Tower t)
	{
		attackers.add(t);
	}
	
	public void doDamage(int damage)
	{
		
	}
	
	public void destroy()
	{
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

}
