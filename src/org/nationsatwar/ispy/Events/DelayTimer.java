package org.nationsatwar.ispy.Events;

import org.bukkit.scheduler.BukkitRunnable;
import org.nationsatwar.ispy.ISpy;

public class DelayTimer extends BukkitRunnable {
	
	protected ISpy plugin;
	
	public DelayTimer(ISpy plugin) {
		
		this.plugin = plugin;
	}

	public void run() {
		
		plugin.eventUtil.eventDelay = false;
		cancel();
	}
}