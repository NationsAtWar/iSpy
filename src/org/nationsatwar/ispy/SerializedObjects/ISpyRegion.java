package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class ISpyRegion {

	public ISpyLocation location1;
	public ISpyLocation location2;
		
	public ISpyRegion(ISpyLocation location1, ISpyLocation location2) {
		
		this.location1 = location1;
		this.location2 = location2;
	}
	
	public ISpyRegion(Map<String, Object> map1, Map<String, Object> map2) {
		
		location1 = new ISpyLocation(map1);
		location2 = new ISpyLocation(map2);
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("Location 1", location1.serialize());
        map.put("Location 2", location2.serialize());
        
		return map;
	}
	
	public static Location getLocation1(Map<String, Object> map) {
		
		World world = Bukkit.getServer().getWorld((String) map.get("Location 1.World"));
		int locationX = (Integer) map.get("Location 1.X");
		int locationY = (Integer) map.get("Location 1.Y");
		int locationZ = (Integer) map.get("Location 1.Z");
		
		return new Location(world, locationX, locationY, locationZ);
	}
	
	public static Location getLocation2(Map<String, Object> map) {
		
		World world = Bukkit.getServer().getWorld((String) map.get("Location 2.World"));
		int locationX = (Integer) map.get("Location 2.X");
		int locationY = (Integer) map.get("Location 2.Y");
		int locationZ = (Integer) map.get("Location 2.Z");
		
		return new Location(world, locationX, locationY, locationZ);
	}
}