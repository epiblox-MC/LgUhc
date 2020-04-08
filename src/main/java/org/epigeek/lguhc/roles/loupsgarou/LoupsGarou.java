package org.epigeek.lguhc.roles.loupsgarou;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.epigeek.lguhc.LgPlayer;
import org.epigeek.lguhc.MessageCreator;
import org.epigeek.lguhc.roles.A_Role;
import org.epigeek.lguhc.roles.RoleType;

public class LoupsGarou extends A_Role {

  public static String name = "Loup-Garou";
  public static String description = "Votre objectif est de tuer les villageois. "
      + "Pour ce faire, vous disposez des effets " + "Strenght I (la nuit) et Night Vision. A chaque "
      + "kill, vous gagnez 1 minute de Speed et 2 " + "coeurs d'absorption pendant 1 minute.";

  public static RoleType type = RoleType.LOUPS_GAROU;
  protected int maxHealth = 30;

  private static PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false,
      false);
  private static PotionEffect killSpeed = new PotionEffect(PotionEffectType.SPEED, 20 * 60, 0, false, false);
  private static PotionEffect killAbso = new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60, 0, false, false);
  private static PotionEffect nightStrength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,
      false, false);

  public LoupsGarou(Player player) {
    super(player);
  }

  @Override
  public void printRole() {
    MessageCreator.PrivateMessage(player, "Vous etes " + ChatColor.RED + name + ChatColor.RESET + '\n' + description);
  }

  @Override
  public void onStart() {
    super.onStart();
    player.addPotionEffect(nightVision);
  }


  @Override
  public void onDay() {
    player.removePotionEffect(nightStrength.getType());
  }

  @Override
  public void onNight() {
    player.addPotionEffect(nightStrength);
  }

  @Override
  public boolean onDeath(LgPlayer killer, PlayerDeathEvent event) {
    return false;
    // TODO Auto-generated method stub
  }

  @Override
  public boolean onKill(LgPlayer killed, PlayerDeathEvent event) {
    player.addPotionEffect(killSpeed);
    player.addPotionEffect(killAbso);
    return false;
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