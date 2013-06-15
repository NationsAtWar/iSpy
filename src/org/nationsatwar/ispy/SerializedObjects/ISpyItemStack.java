package org.nationsatwar.ispy.SerializedObjects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.inventory.ItemStack;

public final class ISpyItemStack {

	public String itemType;
	public int itemID;
	public int itemAmount;
	public int itemDurability;
		
	public ISpyItemStack(ItemStack itemstack) {
		
		itemType = itemstack.getType().toString();
		itemID = itemstack.getTypeId();
		itemAmount = itemstack.getAmount();
		itemDurability = itemstack.getDurability();
	}
	
	public ISpyItemStack(Map<String, Object> map) {
		
		itemType = (String) map.get("Type");
		itemID = (Integer) map.get("ID");
		itemAmount = (Integer) map.get("Amount");
		itemDurability = (Integer) map.get("Durability");
	}
	
	public Map<String, Object> serialize() {
		
        Map<String, Object> map = new TreeMap<String, Object>();
        
        map.put("Type", itemType);
        map.put("ID", itemID);
        map.put("Amount", itemAmount);
        map.put("Durability", itemDurability);
        
		return map;
	}
	
	public static ItemStack getItemStack(Map<String, Object> map) {
		
		if (map.get("Durability") == null)
			return null;
		
		short durability = (short) Integer.parseInt(map.get("Durability") + "");
		
		return new ItemStack((Integer) map.get("ID"), (Integer) map.get("Amount"), durability);
	}
}