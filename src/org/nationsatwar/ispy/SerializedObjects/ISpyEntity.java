package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.entity.LivingEntity;

public final class ISpyEntity {

	public int entityID;
	public String entityType;
	public int entityHealth;
		
	public ISpyEntity(LivingEntity entity) {

		entityID = entity.getEntityId();
		entityType = entity.getType().getName();
		entityHealth = entity.getHealth();
	}
	
	public ISpyEntity(Map<String, Object> map) {

		entityID = (Integer) map.get("ID");
		entityType = (String) map.get("Type");
		entityHealth = (Integer) map.get("Health");
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("ID", entityID);
        map.put("Type", entityType);
        map.put("Health", entityHealth);
        
		return map;
	}
}