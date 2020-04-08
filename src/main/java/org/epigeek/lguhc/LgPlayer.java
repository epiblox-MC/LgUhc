package org.epigeek.lguhc;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.epigeek.lguhc.roles.A_Role;
import org.epigeek.lguhc.roles.State;

public class LgPlayer {
  private Player player;
  private A_Role role;
  private State state;

  LgPlayer(Player player) {
    this.player = player;
    this.role = null;
    this.state = State.ALIVE;
  }

  public Player getPlayer() {
    return player;
  }

  public A_Role getRole() {
    return role;
  }

  public void setRole(A_Role role) {
    this.role = role;
  }

  public void clearAllEffect() {
    for (PotionEffect effect : player.getActivePotionEffects())
      player.removePotionEffect(effect.getType());
  }

  public void setup() {
    clearAllEffect();
    player.setGameMode(GameMode.SURVIVAL);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    player.setHealth(20);
    player.setFoodLevel(20);
  }

  public void kill() {
    
  }
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}