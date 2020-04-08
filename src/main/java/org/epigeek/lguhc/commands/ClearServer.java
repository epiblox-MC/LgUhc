package org.epigeek.lguhc.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.epigeek.lguhc.Main;

public class ClearServer extends A_LgCommand implements CommandExecutor {

  public ClearServer(Main plugin) throws Exception {
    super(plugin, "clearServer");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (!player.hasPermission("clearServer.use") && !player.isOp()) return returnError(sender, "you didn't have the permission to excute this commande");
    }
    
    Bukkit.broadcastMessage(StringUtils.repeat(" \n", 100));

    return false;
  }

  
}