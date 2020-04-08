package org.epigeek.lguhc.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.epigeek.lguhc.Main;

public class StartCooldown extends BukkitRunnable {
  
  private static ChatColor[] colors = new ChatColor[] {
    ChatColor.DARK_GREEN,
    ChatColor.GREEN,
    ChatColor.AQUA,
    ChatColor.DARK_AQUA,
    ChatColor.DARK_BLUE,
    ChatColor.BLUE,
    ChatColor.LIGHT_PURPLE,
    ChatColor.DARK_PURPLE,
    ChatColor.DARK_RED,
    ChatColor.RED,
    ChatColor.GOLD,
    ChatColor.YELLOW,
  };
  
  private final Main plugin;
  private int counter;

  
  public StartCooldown(Main plugin, int counter) {
    this.plugin = plugin;
    if (counter <= 0) throw new IllegalArgumentException("counter must be greater than 0");
    this.counter = counter;
  }
  
  public void launch() {
    this.runTaskTimer(this.plugin, 0, 20);
  }

  @Override
  public void run() {
    // What you want to schedule goes here
    if (counter > 0) { 
      plugin.getServer().broadcastMessage(colors[counter % 12] + "" + counter--);
    } else {
      Bukkit.broadcastMessage(ChatColor.BOLD + "GO !!!");
      plugin.onStart();
      this.cancel();
      
    }
  }
  
}