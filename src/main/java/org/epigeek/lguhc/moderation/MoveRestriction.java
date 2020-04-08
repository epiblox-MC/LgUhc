package org.epigeek.lguhc.moderation;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.epigeek.lguhc.LgPlayer;
import org.epigeek.lguhc.Main;
import org.epigeek.lguhc.MessageCreator;

import net.md_5.bungee.api.ChatColor;

public class MoveRestriction extends BukkitRunnable {

  private Main plugin;
  private World world;
  private WorldBorder border;
  private double endSize;
  private long startShrink;
  private long shrinkDuration;

  private PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false);

  public MoveRestriction(Main plugin, World world, double initSize, double endSize, float startShrink, float shrinkDuration) {
    this.plugin = plugin;
    this.world = world;
    this.border = world.getWorldBorder();
    this.endSize = endSize;
    this.startShrink = (long) (20 * 60 * startShrink);
    this.shrinkDuration = (long) (60 * shrinkDuration);
    border.setCenter(0L, 0L);
    border.setSize(initSize);
  }


  public void launch() {
    this.runTaskLater(this.plugin, startShrink);
  }

  @Override
  public void run() {
    border.setSize(endSize, shrinkDuration);
  }

  public void randomTeleportation(LgPlayer lgPlayer) {
    Player player = lgPlayer.getPlayer();
    MessageCreator.OpMessage(player.getName() + ChatColor.GRAY + " teleportation");
    
    int x = (int) (Math.random() * (border.getSize() / 2.0));
    int z = (int) (Math.random() * (border.getSize() / 2.0));
    
    player.teleport(new Location(world, x, world.getHighestBlockYAt(x, z) + 2, z));
    player.addPotionEffect(blindness);
  }

  public void revil(LgPlayer player) {
    player.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
  }


  public int getBorder() {
    return (int) border.getSize();
  }

  

}