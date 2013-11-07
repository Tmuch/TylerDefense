package map;

public class Map {
	
	private Waypoint initWaypoint;
	
	public Map()
	{
		initWaypoints();
	}
	
	private void initWaypoints()
	{
		
		Waypoint w1 = new Waypoint(300, 125, false);
		Waypoint w2 = new Waypoint(300, 365, false);
		Waypoint w3 = new Waypoint(667, 365, false);
		Waypoint w4 = new Waypoint(667, 118, false);
		Waypoint w5 = new Waypoint(1050, 118, true);
		
		initWaypoint = w1;
		w1.setNext(w2);
		w2.setNext(w3);
		w3.setNext(w4);
		w4.setNext(w5);
		
		//initWaypoint = new Waypoint(600, 400, true);
		
	}
	
	public Waypoint getInitWaypoint()
	{
		return initWaypoint;
	}
	

}
