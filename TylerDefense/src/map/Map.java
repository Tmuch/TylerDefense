package map;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import enemies.Enemy;

public class Map {
	
	private Waypoint initWaypoint;
	int msBetween; //number of ms between enemies being spawned
	Enemy enemy; //type of enemy to be spawned on this map
	Image background;
	
	public Map(ArrayList<Waypoint> waypoints)
	{
		initWaypoints(waypoints);
	}
	
	private void initWaypoints(ArrayList<Waypoint> waypoints)
	{
		if(waypoints == null) return;
		if(waypoints.size() == 0) return;
		
		initWaypoint = waypoints.get(0);
		
		Waypoint w = initWaypoint;
		
		for(int i = 1; i < waypoints.size(); i++)
		{
			w.setNext(waypoints.get(i));
			w = waypoints.get(i);
		}
		
		w.setNext(null);
		/*
		Waypoint w1 = new Waypoint(300, 125, false);
		Waypoint w2 = new Waypoint(300, 365, false);
		Waypoint w3 = new Waypoint(667, 365, false);
		Waypoint w4 = new Waypoint(667, 118, false);
		Waypoint w5 = new Waypoint(1050, 118, true);
		*/
		
	}
	
	public Waypoint getInitWaypoint()
	{
		return initWaypoint;
	}
	
	

}
