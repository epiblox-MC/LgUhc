package org.epigeek.lguhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.epigeek.lguhc.Main;

public class LgVote extends A_LgCommand implements CommandExecutor {

  public LgVote(Main plugin) throws Exception {
    super(plugin, "lg-start");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (!player.hasPermission("helloworld.use") && !player.isOp()) return returnError(sender, "you didn't have the permission to excute this commande");
    }

    plugin.startGame();

    return false;
  }

  
}