package org.epigeek.lguhc.roles.village;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.epigeek.lguhc.LgPlayer;
import org.epigeek.lguhc.MessageCreator;
import org.epigeek.lguhc.roles.A_Role;
import org.epigeek.lguhc.roles.RoleType;

public class SimpleVillager extends A_Role {

  public static String name = "Simple Villager";
  public static String description = "Votre objectif est de tuer les Loups-Garous. "
      + "Vous ne disposez d'aucun pouvoire particulier";

  public static RoleType type = RoleType.VILLAGE;
  protected int maxHealth = 20;

  public SimpleVillager(Player player) {
    super(player);
  }

  @Override
  public void onStart() {
    MessageCreator.PrivateMessage(player, "Vous etes " + ChatColor.GREEN + name + ChatColor.RESET + '\n' + description);
  }

  @Override
  public void printRole() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onDay() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onNight() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean onDeath(LgPlayer killer, PlayerDeathEvent event) {
    return false;
    // TODO Auto-generated method stub
  }

  @Override
  public boolean onKill(LgPlayer killed, PlayerDeathEvent event) {
	return false;
    // TODO Auto-generated method stub

  }

  @Override
  public boolean onPlayerDeath(LgPlayer killed, LgPlayer killer, PlayerDeathEvent event) {
    return false;
    // TODO Auto-generated method stub

  }

  @Override
  public int maxHealth() {
    return maxHealth;
  }
  
}