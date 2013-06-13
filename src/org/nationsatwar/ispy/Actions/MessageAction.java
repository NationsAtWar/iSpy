package org.nationsatwar.ispy.Actions;

import org.bukkit.Bukkit;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Utility.ConfigParser;

public class MessageAction {

	public static boolean execute(Trigger trigger, String action) {
		
		for (int i = 0; i < action.length(); i++) {
			
			if (action.charAt(i) == ' ') {
				
				String playerName = ConfigParser.getLiteral(action.substring(0, i), trigger).toString();
				String message = ConfigParser.getLiteral(action.substring(i + 1), trigger).toString();
				
				Bukkit.getServer().getPlayer(playerName).sendMessage(message);
				return true;
			}
		}
		
		return false;
	}
}