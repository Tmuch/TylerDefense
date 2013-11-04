package map;

public class Map {
	
	private Waypoint initWaypoint;
	
	public Map()
	{
		initWaypoints();
	}
	
	private void initWaypoints()
	{
		Waypoint w1 = new Waypoint(100, 100, false);
		Waypoint w2 = new Waypoint(100, 200, false);
		Waypoint w3 = new Waypoint(200, 200, false);
		Waypoint w4 = new Waypoint(200, 400, true);
		
		initWaypoint = w1;
		w1.setNext(w2);
		w2.setNext(w3);
		w3.setNext(w4);
		
	}
	
	public Waypoint getInitWaypoint()
	{
		return initWaypoint;
	}
	

}
