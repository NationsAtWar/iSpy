package org.nationsatwar.ispy.Conditions;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.SerializedObjects.ISpyItemStack;
import org.nationsatwar.ispy.Utility.ConfigParser;

public class HasCondition {
	
	public static boolean conditionHas(Trigger trigger, String[] condition, boolean isNot) {
		
		Object firstValue = ConfigParser.getLiteral(condition[0], trigger);
		Object secondValue = ConfigParser.getLiteral(condition[1], trigger);
		
		if (firstValue == null || secondValue == null)
			return false;

		if (firstValue instanceof String && secondValue instanceof String) {
			
			String firstString = (String) firstValue;
			String secondString = (String) secondValue;
			
			if (firstString.contains(secondString))
				return true;
			else
				return false;
		}
		
		// This will assume that the script is trying to see if a player has an item
		// TODO: This needs to be much more versatile, re-work it as appropriate
		if (firstValue instanceof String && secondValue instanceof Map<?, ?>) {

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) secondValue;
			ItemStack itemstack = ISpyItemStack.getItemStack(map);
			
			Inventory playerInventory = Bukkit.getServer().getPlayer(firstValue.toString()).getInventory();
			
			if (playerInventory.contains(itemstack.getTypeId(), itemstack.getAmount()) && !isNot)
				return true;
			else if (!playerInventory.contains(itemstack.getTypeId(), itemstack.getAmount()) && isNot)
				return true;
			else
				return false;
		}
		
		if (firstValue.toString().contains(secondValue.toString()) && !isNot)
			return true;
		else if (!firstValue.toString().contains(secondValue.toString()) && isNot)
			return true;
		else
			return false;
	}
}