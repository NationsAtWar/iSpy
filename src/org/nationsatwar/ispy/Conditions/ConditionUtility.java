package org.nationsatwar.ispy.Conditions;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public class ConditionUtility {
	
	public static String playerName = "player name";
	
	public static boolean checkConditions(Trigger trigger) {
		
		File triggerFile = new File(trigger.getWorldName() + "/triggers/" + trigger.getTriggerName());
		FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
		
		String playerNamePath = ISpy.triggerConditionsPath + "." + playerName;
		
		for (String key : triggerConfig.getConfigurationSection(ISpy.triggerConditionsPath).getKeys(true))
			Logger.getLogger("Minecraft").info(key);
		
		if (triggerConfig.contains(playerNamePath) &&
				triggerConfig.getString(playerNamePath).equals(trigger.getPlayerName()))
			return true;
		
		return false;
	}
}