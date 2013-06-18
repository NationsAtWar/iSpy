package org.nationsatwar.ispy.Actions;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public class CancelAction {

	public static boolean execute(Trigger trigger, ISpy plugin) {
		
		plugin.eventUtil.setEventCancellation();
		return true;
	}
}