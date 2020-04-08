package org.epigeek.lguhc.scheduler;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.epigeek.lguhc.Main;
import org.epigeek.lguhc.MessageCreator;

public class DayCycle extends BukkitRunnable {

  public static int DAY = 1000;
  public static int NIGHT = 13000;

  private int timeFactor;

  private Main plugin;
  private World world;
  private boolean day;
  private int nbDay;

  public DayCycle(Main plugin, World world, int dayTime) {
    this.plugin = plugin;
    if (world == null) MessageCreator.Message("world null");
    this.world = world;
    this.day = isDay();
    this.timeFactor = 20 / dayTime;
  }

  public int getNbDay() {
    return nbDay;
  }

  public void launch() {
    world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
  
    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    setTime(DAY);
    this.runTaskTimer(this.plugin, 0, 1);
  }

  @Override
  public void run() {

    setTime(getTime() + timeFactor);

    boolean newDayValue = isDay();
    if (newDayValue != day) {
      day = newDayValue;
      if (day) {
        ++nbDay;
        plugin.onDay();
        if ((nbDay&1) == 0) plugin.onEpisode();

      } else {
        plugin.onNight();
      } 
    }
  }

  public long getTime() {
    return world.getTime(); 
  }
  
  public void setTime(long time) {
    world.setTime(time); 
  }

  public boolean isDay() {
    long time = getTime();
    return time >= DAY && time < NIGHT;
  }

public int getEpisode() {
	return (nbDay / 2) + 1;
}
  
}