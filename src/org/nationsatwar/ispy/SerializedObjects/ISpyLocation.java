package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Location;

public final class ISpyLocation {

	public String worldName;
	
	public int locationX;
	public int locationY;
	public int locationZ;
		
	public ISpyLocation(Location location) {
		
		worldName = location.getWorld().getName();
		
		locationX = location.getBlockX();
		locationY = location.getBlockY();
		locationZ = location.getBlockZ();
	}
	
	public ISpyLocation(Map<String, Object> map) {
		
		worldName = (String) map.get("World");
		locationX = (Integer) map.get("X");
		locationY = (Integer) map.get("Y");
		locationZ = (Integer) map.get("Z");
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("World", worldName);
        map.put("X", locationX);
        map.put("Y", locationY);
        map.put("Z", locationZ);
        
		return map;
	}
}