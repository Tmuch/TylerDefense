package enemies;

import main.Game;
import map.Waypoint;

import org.newdawn.slick.Graphics;

public class Enemy {
	
	private float x, y;
	private float width, height;
	private Waypoint targetWaypoint;
	private boolean switchedWaypoints;
	private float dx, dy;
	private float speed;
	
	public Enemy(int x, int y, Waypoint first)
	{
		this.x = x;
		this.y = y;
		width = height = 10;
		switchedWaypoints = true;
		targetWaypoint = first;
		speed = 100;
	}
	
	public void render(Graphics g)
	{
		g.drawOval(x - width/2, y - height/2, width, height);
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
		}
	}
	

}
