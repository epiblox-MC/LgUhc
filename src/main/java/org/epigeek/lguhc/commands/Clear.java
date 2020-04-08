package org.epigeek.lguhc.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.epigeek.lguhc.Main;

public class Clear extends A_LgCommand implements CommandExecutor {

  public Clear(Main plugin) throws Exception {
    super(plugin, "clear");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) return returnError(sender, "only players can use this commande");  
    Player player = (Player) sender;
     
    player.sendMessage(StringUtils.repeat(" \n", 100));

    return false;
  }

  
}