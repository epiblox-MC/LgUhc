package org.epigeek.lguhc.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.epigeek.lguhc.Main;

/**
 * LgCommand
 */
public class A_LgCommand {

  protected String commandName;
  protected Main plugin;

  public A_LgCommand(Main plugin, String name) throws Exception {
    this.commandName = name;
    this.plugin = plugin;

    PluginCommand command = plugin.getCommand(commandName);
    if (command == null)
      Main.returnError("commande name was not declared :" + commandName);

    command.setExecutor((CommandExecutor) this);
  }

  protected boolean returnError(CommandSender sender, String message) {
    sender.sendMessage("ERROR : " + commandName + " : " + message);
    return true;
  }
  
}