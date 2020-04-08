package org.epigeek.lguhc.moderation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.epigeek.lguhc.Main;
import org.epigeek.lguhc.MessageCreator;

public class GameRestriction implements Listener {

  private Main plugin;
  private World world;

  private boolean asPermission(Player player) {
    return plugin.isGameStart() || player.isOp();
  }

  public void launch() {
    world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
  }

  public GameRestriction(Main plugin, World world) {
    this.plugin = plugin;
    Bukkit.getPluginManager().registerEvents(this, plugin);
    this.world = world;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    event.setCancelled(!asPermission(event.getPlayer()));
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    event.setCancelled(!asPermission(event.getPlayer()));
  }

  @EventHandler
  public void OnChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    event.setCancelled(plugin.isGameStart() && !player.isOp());
    event.setMessage(MessageCreator.PlayerMessage(player, event.getMessage()));
  }

  @EventHandler
  public void OnDommange(EntityDamageEvent event) {
    Entity entity = event.getEntity();
    
    if (!(entity instanceof Player)) return;
    event.setCancelled(!plugin.isGameStart());
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
    plugin.onDeath(event.getEntity(), event.getEntity().getKiller(), event);
  }

  @EventHandler
  public void onKill(PlayerRespawnEvent event) {
    event.getPlayer().setGameMode(GameMode.SPECTATOR);
  }

}