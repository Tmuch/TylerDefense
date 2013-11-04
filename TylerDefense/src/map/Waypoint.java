package map;

public class Waypoint {
	
	private float x, y;
	private Waypoint nextWaypoint;
	private boolean last;
	
	public Waypoint(int x, int y, boolean l)
	{
		this.x = x;
		this.y = y;
		last = l;
	}
	
	public void setNext(Waypoint w)
	{
		nextWaypoint = w;
	}
	
	
	
	public Waypoint getNext()
	{
		return nextWaypoint;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public boolean isLast()
	{
		return (nextWaypoint == null);
	}

}
