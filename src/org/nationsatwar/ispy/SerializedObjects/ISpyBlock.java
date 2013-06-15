package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.block.Block;

public final class ISpyBlock {

	public String blockType;
	public int blockID;
	public int blockData;
		
	public ISpyBlock(Block block) {
		
		blockType = block.getType().toString();
		blockID = block.getTypeId();
		
		blockData = (int) block.getData();
	}
	
	public ISpyBlock(Map<String, Object> map) {
		
		blockType = (String) map.get("Type");
		blockID = (Integer) map.get("ID");
		blockData = (Integer) map.get("Data");
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("Type", blockType);
        map.put("ID", blockID);
        map.put("Data", blockData);
        
		return map;
	}
}