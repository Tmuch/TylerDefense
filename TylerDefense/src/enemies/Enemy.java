package enemies;

import map.Waypoint;

import org.newdawn.slick.Graphics;

public class Enemy {
	
	private float x, y;
	private float width, height;
	private Waypoint targetWaypoint;
	private boolean switchedWaypoints;
	private int dx, dy;
	private float speed;
	
	public Enemy(int x, int y, Waypoint first)
	{
		this.x = x;
		this.y = y;
		width = height = 6;
		switchedWaypoints = true;
		targetWaypoint = first;
		speed = 100;
	}
	
	public void render(Graphics g)
	{
		g.drawRect(x - width/2, y - height/2, width, height);
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
				/*float magnitude = (float)Math.sqrt(diffX*diffX + diffY*diffY);
				dx = diffX / magnitude;
				dy = diffY / magnitude;
				System.out.println("dx: " + dx + ", dy: " + dy);
				*/
				
				dx = dy = 0;
				
				if(diffX > 0) {
					dx = 1;
				} else if(diffX < 0) {
					dx = -1;
				} else dx = 0;
				
				if(diffY > 0) {
					dy = 1;
				} else if(diffY < 0) {
					dy = -1;
				} else dy = 0;
				
				switchedWaypoints = false;
			}
			
		} else {
			
			
			/*
			 * GET RID OF THIS SHIT.
			 * 		Just use a simple collision detection. Check if enemy is within 1 of the waypoint. 
			 */
				
			boolean s = false;
			switch(dx)
			{
			case 1:
				if(this.x >= targetWaypoint.getX()) s = true;
				break;
			case -1:
				if(this.x <= targetWaypoint.getX()) s = true;
				break;
			}
			
			if(!s)
			{
				switch(dy)
				{
				case 1:
					if(this.y >= targetWaypoint.getY()) s = true;
					break;
				case -1:
					if(this.y <= targetWaypoint.getY()) s = true;
					break;
				}
			}
			
			if(s)
			{
				this.x = targetWaypoint.getX();
				this.y = targetWaypoint.getY();
				targetWaypoint = targetWaypoint.getNext();
				switchedWaypoints = true;
				return;
			}
			
			this.x += dx * speed * (delta/1000.0);
			this.y += dy * speed * (delta/1000.0);
		}
	}
	

}
