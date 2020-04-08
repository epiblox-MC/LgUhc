package org.epigeek.lguhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageCreator {

  private static String QuitJoin(Player player, boolean isjoin) {
    String message = ChatColor.DARK_GRAY + "[";
    message += isjoin ? ChatColor.GREEN: ChatColor.RED;
    message += isjoin ? "+": "-";
    message += ChatColor.DARK_GRAY + "] " + player.getName();
    message += ChatColor.GRAY + " (" + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size();
    message += ChatColor.GRAY + "/" + ChatColor.YELLOW + Bukkit.getMaxPlayers();
    message += ChatColor.GRAY + ")";
    return message;
  }

  public static String Join(Player player) {
    return QuitJoin(player, true);
  }

  public static String Quit(Player player) {
    return QuitJoin(player, true);
  }

  public static String PlayerMessage(Player player, String message) {
    return "";
  }

  public static void Message(String message) {
    Bukkit.broadcastMessage(
      ChatColor.BOLD + "" + ChatColor.GRAY + "[" +
      ChatColor.YELLOW + Main.pluginName +
      ChatColor.GRAY + "]" +
      ChatColor.RESET + message
    );
  }

  public static void PrivateMessage(Player player, String message) {
    player.sendMessage(
      ChatColor.BOLD + "" + ChatColor.GRAY + "[" +
      ChatColor.YELLOW + Main.pluginName +
      ChatColor.GRAY + "]" +
      ChatColor.DARK_GRAY + " (private) " +
      ChatColor.RESET + message
    );
  }

  public static void OpMessage(String message) {
    for (Player player: Bukkit.getOnlinePlayers()) {
      if(player.isOp()) player.sendMessage(
        ChatColor.BOLD + "" + ChatColor.GRAY + "[" +
        ChatColor.YELLOW + Main.pluginName +
        ChatColor.GRAY + "]" +
        ChatColor.DARK_GRAY + " (OP) " +
        ChatColor.RESET + message
      );
      }
  }

  public static void EpisodeMessage(int number) {
    Bukkit.broadcastMessage(ChatColor.AQUA + "--------Fin Episode " + number + "--------");
  }

  
}