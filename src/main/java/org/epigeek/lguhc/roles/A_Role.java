package org.epigeek.lguhc.roles;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.epigeek.lguhc.LgPlayer;

public abstract class A_Role {

  public static String name;
  public static String description;
  public static RoleType type;
  protected Player player;
  
  public void onStart() {
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth());
    player.setHealth(maxHealth());
  }

  public abstract int maxHealth();

  public abstract void printRole();
  
  public abstract void onDay();
  
  public abstract void onNight();

  public abstract boolean onDeath(LgPlayer killer, PlayerDeathEvent event);
  
  public abstract boolean onKill(LgPlayer killed, PlayerDeathEvent event);
  
  public abstract boolean onPlayerDeath(LgPlayer killed, LgPlayer killer, PlayerDeathEvent event);

  public A_Role(Player player) {
    this.player = player;
  }


}