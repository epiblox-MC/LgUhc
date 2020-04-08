package org.epigeek.lguhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.epigeek.lguhc.Main;

public class LgInit extends A_LgCommand implements CommandExecutor {

  private World world;

  public LgInit(Main plugin, World world) throws Exception {
    super(plugin, "lg-init");
    this.world = world;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (!player.hasPermission("lg-init.use") && !player.isOp()) return returnError(sender, "you didn't have the permission to excute this commande");
    }
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill 20 150 20 -20 150 -20 minecraft:barrier");
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill 20 150 -20 20 153 20 minecraft:barrier");
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill -20 150 -20 -20 153 20 minecraft:barrier");
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill -20 150 -20 20 153 -20 minecraft:barrier");
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fill -20 150 20 20 153 20 minecraft:barrier");
    
    this.world.setSpawnLocation(0, 151, 0);
    return false;
  }

  
}