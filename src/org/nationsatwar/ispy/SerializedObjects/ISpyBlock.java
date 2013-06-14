package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("Block")
public final class ISpyBlock implements ConfigurationSerializable {

	public String blockType;
	public int blockID;
	public int blockData;
		
	public ISpyBlock(Block block) {
		
		ConfigurationSerialization.registerClass(ISpyBlock.class, "ISpyBlock");
		
		blockType = block.getType().toString();
		blockID = block.getTypeId();
		
		blockData = (int) block.getData();
	}
	
	public ISpyBlock(Map<String, Object> map) {
		
		blockType = (String) map.get("Type");
		blockID = (Integer) map.get("ID");
		blockData = (Integer) map.get("Data");
	}
	
	@Override
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("Type", blockType);
        map.put("ID", blockID);
        map.put("Data", blockData);
        
		return map;
	}
}