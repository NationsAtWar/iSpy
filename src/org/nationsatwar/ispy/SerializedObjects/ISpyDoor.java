package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class ISpyDoor {

	public ISpyLocation doorTop;
	public ISpyLocation doorBottom;
		
	public ISpyDoor(ISpyLocation doorTop, ISpyLocation doorBottom) {
		
		this.doorTop = doorTop;
		this.doorBottom = doorBottom;
	}
	
	public ISpyDoor(Map<String, Object> map1, Map<String, Object> map2) {
		
		doorTop = new ISpyLocation(map1);
		doorBottom = new ISpyLocation(map2);
	}
	
	public ISpyDoor(Block block) {

		int topHalfOffset;
		int bottomHalfOffset;
		
		if (block.getData() >= 8) {
			topHalfOffset = block.getY();
			bottomHalfOffset = block.getY() - 1;
		} else {
			topHalfOffset = block.getY() + 1;
			bottomHalfOffset = block.getY();
		}
		
		doorTop = new ISpyLocation(new Location(block.getWorld(), block.getX(), topHalfOffset, block.getZ()));
		doorBottom = new ISpyLocation(new Location(block.getWorld(), block.getX(), bottomHalfOffset, block.getZ()));
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("Door Top", doorTop.serialize());
        map.put("Door Bottom", doorBottom.serialize());
        
		return map;
	}
	
	public boolean isDoorOpen() {
		
		World world = Bukkit.getServer().getWorld(doorBottom.worldName);
		int locationX = doorBottom.locationX;
		int locationY = doorBottom.locationY;
		int locationZ = doorBottom.locationZ;
		
		Location location = new Location(world, locationX, locationY, locationZ);
		
		if (location.getBlock().getType().equals(Material.WOODEN_DOOR) || 
				location.getBlock().getType().equals(Material.IRON_DOOR_BLOCK)) {
			
			if (location.getBlock().getData() >= 0 && location.getBlock().getData() <= 3)
				return false;
			else
				return true;
		}
		
		return false;
	}
}