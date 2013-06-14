package org.nationsatwar.ispy.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Commands.CreateCommand;
import org.nationsatwar.ispy.Commands.LoadCommand;
import org.nationsatwar.ispy.Commands.RecordCommand;

public final class CommandParser implements CommandExecutor {

	protected final ISpy plugin;

	public final CreateCommand createCommand;
	public final LoadCommand loadCommand;
	public final RecordCommand recordCommand;
	
	public CommandParser(ISpy plugin) {
		
		this.plugin = plugin;
		
		createCommand = new CreateCommand(plugin);
		loadCommand = new LoadCommand(plugin);
		recordCommand = new RecordCommand(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String argsLabel, String[] args) {
		
		// -ispy OR -ispy help
		if (args.length == 0 || args[0].equals("help"))
			helpCommand(sender);
		
		// -ispy create
		else if (args[0].equals("create"))
			createCommand(sender, args);
		
		// -ispy load
		else if (args[0].equals("load"))
			loadCommand(sender, args);
		
		// -ispy record
		else if (args[0].equals("record"))
			recordCommand(sender, args);
		
		// -ispy <non-applicable command>
		else {
			
			sender.sendMessage(ChatColor.DARK_RED + "Invalid command: type '/ispy' for help.");
			return false;
		}
		
		return true;
	}

	/**
	 * Handles the 'Help' command.
	 * <p>
	 * Returns a list of all possible commands to the command sender.
	 * 
	 * @param sender  Person sending the command
	 */
	private void helpCommand(CommandSender sender) {

		sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[ISPY]=-");
		sender.sendMessage(ChatColor.YELLOW + "Allows you to create and manage triggers.");
		sender.sendMessage(ChatColor.YELLOW + "Command List: Create");
	}

	/**
	 * Returns help and parses the 'Create' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void createCommand(CommandSender sender, String[] args) {

		if (args.length <= 1 || args[1].equals("help")) {
			
			sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[CREATE]=-");
			sender.sendMessage(ChatColor.DARK_AQUA + "i.e. '/ispy create <trigger name>");
			sender.sendMessage(ChatColor.YELLOW + "Creates a new trigger.");
			return;
		}
		
		// Cancel if command is sent from console
		if (!isPlayer(sender))
			return;
		
		// Gets the player sending the command
		Player player = (Player) sender;
		
		// Stores the full trigger name
		String triggerName = getRemainingString(1, args);
		
		// Execute Create Command
		createCommand.execute(player, triggerName);
	}

	/**
	 * Returns help and parses the 'Load' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void loadCommand(CommandSender sender, String[] args) {

		if (args.length <= 1 || args[1].equals("help")) {
			
			sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[LOAD]=-");
			sender.sendMessage(ChatColor.DARK_AQUA + "i.e. '/ispy load <trigger name>");
			sender.sendMessage(ChatColor.YELLOW + "Loads a trigger for future commands. (Default: Last created trigger.)");
			return;
		}
		
		// Cancel if command is sent from console
		if (!isPlayer(sender))
			return;
		
		// Gets the player sending the command
		Player player = (Player) sender;
		
		// Stores the full trigger name
		String triggerName = getRemainingString(1, args);
		
		// Execute Create Command
		loadCommand.execute(player, triggerName);
	}

	/**
	 * Returns help and parses the 'Record' command for execution.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	private void recordCommand(CommandSender sender, String[] args) {

		if (args.length <= 1 || args[1].equals("help")) {
			
			sender.sendMessage(ChatColor.DARK_RED + "[Nations at War]" + ChatColor.DARK_AQUA + " -=[RECORD]=-");
			sender.sendMessage(ChatColor.DARK_AQUA + "i.e. '/ispy record [location/item/block]");
			sender.sendMessage(ChatColor.YELLOW + "Records information into the config file.");
			return;
		}
		
		// Cancel if command is sent from console
		if (!isPlayer(sender))
			return;
		
		// Gets the player sending the command
		Player player = (Player) sender;
		
		// Gets the 'Record' sub-data
		String commandData = getRemainingString(1, args);
		
		// Execute Create Command
		recordCommand.execute(player, commandData);
	}

	/**
	 * Checks to see if the sender is an in-game player
	 * <p>
	 * Gives the console a message if false.
	 * 
	 * @param sender  The sender to check
	 * @return true if a player, false otherwise
	 */
	private static boolean isPlayer(CommandSender sender) {
		
		if (sender instanceof Player)
			return true;
		else {
			
			sender.sendMessage(ChatColor.YELLOW + "Must be a player to issue this command.");
			return false;
		}
	}

	/**
	 * Combines the remaining arguments into a single string
	 * 
	 * @param arrayStart  The start of the array to form the string at
	 * @param args  The command arguments to parse
	 * 
	 * @return fullString  The full parsed string of the remainder of the command arguments.
	 */
	private static String getRemainingString(int arrayStart, String[] args) {
		
		String fullString = (args.length > arrayStart ? args[arrayStart] : "");
		
		for (int i = arrayStart + 1; i < args.length; i++)
			fullString += " " + args[arrayStart];
		
		return fullString;
	}
}