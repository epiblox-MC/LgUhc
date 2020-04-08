package org.epigeek.lguhc;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.epigeek.lguhc.commands.Clear;
import org.epigeek.lguhc.commands.ClearServer;
import org.epigeek.lguhc.commands.HelloWorld;
import org.epigeek.lguhc.commands.LgStart;
import org.epigeek.lguhc.commands.LgInit;
import org.epigeek.lguhc.moderation.GameRestriction;
import org.epigeek.lguhc.moderation.MoveRestriction;
import org.epigeek.lguhc.roles.A_Role;
import org.epigeek.lguhc.roles.loupsgarou.LoupsGarou;
import org.epigeek.lguhc.roles.village.SimpleVillager;
import org.epigeek.lguhc.scheduler.DayCycle;
import org.epigeek.lguhc.scheduler.ScoreBoard;
import org.epigeek.lguhc.scheduler.StartCooldown;


public class Main extends JavaPlugin implements Listener {

  public static String pluginName = "LgUhc";

  private static int nbLoups = 2;
  private static int datTime = 1;
  private static String worldName = "World";
  private static double initSize = 1500;
  private static double endSize = 600;
  private static float startShrink = 2.5f;
  private static float shrinkDuration = 1;
  private static int revileEpisode = 1;
  private static int cooldownTime = 10;

  private boolean gameStart = false;
  private World world;
  private GameRestriction gameRestriction;
  private DayCycle dayCycle;
  private MoveRestriction moveRestriction;
  private Vector<LgPlayer> players;

  public int getEpisode() {
    return dayCycle.getEpisode();
  }

  public Main() {
    players = new Vector<LgPlayer>();
    world = getServer().getWorld(worldName);
  }

  @Override
  public void onEnable() {
    
    for (Player player : Bukkit.getOnlinePlayers()) players.add(new LgPlayer(player));
    try {
      new HelloWorld(this);
      new Clear(this);
      new ClearServer(this);
      new LgStart(this);
      new LgInit(this, world);
      this.gameRestriction = new GameRestriction(this, world);
    } catch (final Exception e) {
      e.printStackTrace();
    }

    Bukkit.getPluginManager().registerEvents(this, this);
  }

  public boolean isGameStart() {
    return gameStart;
  }

  public void startGame() {
    if (gameStart) return;
    gameStart = true;
    
    matchMaking();
    dayCycle = new DayCycle(this, world, datTime);
    StartCooldown cooldown = new StartCooldown(this, cooldownTime);
    moveRestriction = new MoveRestriction(this, world, initSize, endSize, startShrink, shrinkDuration);
    ScoreBoard scoreBoard = new ScoreBoard(this);

    dayCycle.launch();
    moveRestriction.launch();
    gameRestriction.launch();
    scoreBoard.launch();
    for (LgPlayer lgPlayer : players) {
      lgPlayer.setup();
      scoreBoard.addToPlayer(lgPlayer.getPlayer());
      moveRestriction.randomTeleportation(lgPlayer);
    }
    cooldown.launch();
  }

  private void matchMaking() {
    Vector<Integer> noRolePlayer = identityVector(players.size());

    for (int i = 0; i < nbLoups;) {
      int idVecorId = randomInt(noRolePlayer.size());
      LgPlayer player = players.get(noRolePlayer.get(idVecorId));
      noRolePlayer.remove(idVecorId);

      if (player.getRole() != null) continue;
      
      player.setRole(new LoupsGarou(player.getPlayer()));
      i++;
    }

    for (Integer i : noRolePlayer) {
      LgPlayer player = players.get(i);
      player.setRole(new SimpleVillager(player.getPlayer()));
    }
  }

  private Vector<Integer> identityVector(int size) {
    Vector<Integer> vector = new Vector<Integer>();
    for (int i = 0; i < size; i++) vector.add(i);
    return vector;
  }

  private static int randomInt(int max) {
    int var = (int) (Math.random() * (double)max);
    return  var;
  }

  public static void returnError(final String message) throws Exception {
    throw new Exception("[" + pluginName + "] ERROR : " + message);
  }

  private boolean removePlayer(Player player) {
    for (int i = 0; i < players.size(); i++) {
      LgPlayer lgPlayer = players.get(i);
      if (lgPlayer.getPlayer() != player) continue;
      players.remove(i);
      return true;
    }
    return false;
  }

  private LgPlayer findPlayer(Player player) {
    for (LgPlayer lgPlayer : players) if (lgPlayer.getPlayer() == player) return lgPlayer;      
    return null;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(MessageCreator.Join(player));
    if(!gameStart) players.add(new LgPlayer(player));
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.setQuitMessage(MessageCreator.Quit(player));
    if(!gameStart) removePlayer(player);
  }

  public void onDay() {
    if (getEpisode() < revileEpisode) return;
    for (LgPlayer player : players) {
      A_Role role = player.getRole();
      if (role != null) role.onDay();
    }
  }

  public void onNight() {
    if (getEpisode() < revileEpisode) return;
    for (LgPlayer player : players) {
      A_Role role = player.getRole();
      if (role != null) role.onNight();
    }
  }

  public void onStart() {
    for (LgPlayer player : players) {
      A_Role role = player.getRole();
      if (role != null) {
        moveRestriction.revil(player);
      }
    }
  }

  public void onRoleRevile() {
    for (LgPlayer player : players) {
      A_Role role = player.getRole();
      if (role != null) {
        role.printRole();
        role.onStart();
      }
    }
  }

  public void onDeath(Player killed, Player killer, PlayerDeathEvent event) {
    killed.setGameMode(GameMode.SPECTATOR);
    LgPlayer killedPlayer = findPlayer(killed);
    LgPlayer killerPlayer = findPlayer(killer);
    if (killedPlayer.getRole().onDeath(killerPlayer, event)) {return;}
    if (killerPlayer.getRole().onKill(killedPlayer, event)) {return;}
    
    
    for (LgPlayer player : players) {
      A_Role role = player.getRole();
      if (role != null) {
        role.onPlayerDeath(killedPlayer, killerPlayer, event);
      }
    }
  }

  public void onEpisode() {
    MessageCreator.EpisodeMessage(getEpisode() - 1);
    if (getEpisode() == revileEpisode + 1) onRoleRevile();
  }

public int getBorder() {
  return moveRestriction.getBorder();
}
  

}