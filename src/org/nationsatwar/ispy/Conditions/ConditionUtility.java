package org.nationsatwar.ispy.Conditions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Actions.ActionUtility;
import org.nationsatwar.ispy.SerializedObjects.ISpyItemStack;
import org.nationsatwar.ispy.Utility.ConfigParser;

public final class ConditionUtility {
	
	public static void checkConditions(List<Trigger> triggers) {
		
		for (Trigger trigger : triggers) {
			
			// Load the configuration file for each trigger
			File triggerFile = new File(trigger.getTriggerFileName());
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			// Cycle through each condition
			for (String condition : triggerConfig.getStringList(ISpy.configConditionsPath))
				if (!isTrueStatement(condition, trigger))
					return;
			
			int triggerCounter = triggerConfig.getInt(ISpy.configCounterPath);
			
			// Tick counter down if eligible
			if (triggerCounter > 0) {
				
				triggerConfig.set(ISpy.configCounterPath, triggerCounter - 1);
				
				try { triggerConfig.save(triggerFile); }
			    catch (IOException e) { ISpy.log("Error saving config file: " + e.getMessage()); }
			}
			
			// Execute Action List if all conditions are true
			ActionUtility.executeActions(trigger, triggerConfig.getStringList(ISpy.configActionsPath));
		}
	}
	
	private static boolean isTrueStatement(String condition, Trigger trigger) {
		
		for (int i = 1; i < condition.length(); i++) {
			
			// Returns true if the first operand equals the second operand
			if (condition.charAt(i) == '=' && condition.charAt(i - 1) == '=') {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 2).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 1).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				if (firstValue.equals(secondValue))
					return true;
			}

			// Returns true if the first operand does not equal the second operand
			else if (condition.charAt(i) == '=' && condition.charAt(i - 1) == '!') {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 2).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 1).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				if (!firstValue.equals(secondValue))
					return true;
			}

			// Returns true if the first operand contains the second operand
			else if (condition.length() >= i + 3 && condition.substring(i, i + 3).toLowerCase().equals("has")) {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 1).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 3).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				// This will assume that the script is trying to see if a player has an item
				// TODO: This needs to be much more versatile, re-work it as appropriate
				if (firstValue instanceof String && secondValue instanceof Map<?, ?>) {

					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) secondValue;
					
					ItemStack itemstack = ISpyItemStack.getItemStack(map);
					
					Inventory playerInventory = Bukkit.getServer().getPlayer(firstValue.toString()).getInventory();
					
					if (playerInventory.contains(itemstack))
						return true;
				}
				
				if (firstValue.equals(secondValue))
					return true;
			}
		}
		
		return false;
	}
}