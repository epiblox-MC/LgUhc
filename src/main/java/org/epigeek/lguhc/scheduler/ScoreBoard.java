package org.epigeek.lguhc.scheduler;

import org.apache.commons.lang.time.StopWatch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.epigeek.lguhc.Main;


public class ScoreBoard extends BukkitRunnable implements Listener {

  private Main plugin;
  private Scoreboard scoreboard;
  private StopWatch time;
  private Objective objective;

  public ScoreBoard(final Main plugin) {
    this.plugin = plugin;
    scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    time = new StopWatch();

    objective = scoreboard.registerNewObjective("lgUhc", "", ChatColor.BOLD + "LG UHC");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
  }

  public void launch() {
    time.start();
    this.runTaskTimer(this.plugin, 0, 20);
    run();
  }

  @Override
  public void run() {
    for(OfflinePlayer player : scoreboard.getPlayers()) scoreboard.resetScores(player);
    
    Score scoreEpisode = objective.getScore(ChatColor.AQUA +"Episode " + plugin.getEpisode());
    Score scoreNbPlayer = objective.getScore(ChatColor.RED + "23" + ChatColor.DARK_RED + " player");
    Score scoretime = objective.getScore(ChatColor.GOLD + "Timer: " + ChatColor.YELLOW + format(time.getTime()/60000) + ":" + format(time.getTime() / 1000 % 60));
    Score scoreborder = objective.getScore(ChatColor.DARK_GREEN +"Border: " + ChatColor.GREEN + plugin.getBorder());


    objective.getScore(ChatColor.GREEN +"").setScore(7);
    scoreEpisode.setScore(6);
    scoreNbPlayer.setScore(5);
    objective.getScore(ChatColor.RED +"").setScore(4);
    scoretime.setScore(3);
    objective.getScore(ChatColor.BLUE +"").setScore(2);
    scoreborder.setScore(1);
  }

  public void addToPlayer(Player player) {
    player.setScoreboard(scoreboard);   
  }

  private String format(long value) {
    return (value < 10 && value >= 0 ? "0" : "") + value;
  }

  
}