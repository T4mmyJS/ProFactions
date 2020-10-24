package me.QuantumDev.ProFactions.utils;

import me.QuantumDev.ProFactions.ProFactions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtility {

    public static String chatFormat(String chat) {
        return ChatColor.translateAlternateColorCodes('&', chat);
    }

    public static String parse(String input, Player player, ProFactions plugin) {
        input = parsePlayerName(input, player, plugin);
        input = parsePlayerDisplayName(input, player, plugin);
        input = parseFactionName(input, player, plugin);
        input = parseFactionRank(input, player, plugin);
        input = parseFactionRankColour(input, player, plugin);
        input = parseFactionRankSuffix(input, player, plugin);

        return input;
    }

    private static String parsePlayerName(String input, Player player, ProFactions plugin) {
        return input.replace("{player}", player.getName());
    }

    private static String parsePlayerDisplayName(String input, Player player, ProFactions plugin) {
        return input.replace("{displayname}", player.getDisplayName());
    }

    private static String parseFactionName(String input, Player player, ProFactions plugin) {
        if (!plugin.inFaction(player.getUniqueId())) return "";
        return input.replace("{faction}", plugin.getPlayerFaction(player.getUniqueId()).getName());
    }

    private static String parseFactionRank(String input, Player player, ProFactions plugin) {
        if (!plugin.inFaction(player.getUniqueId())) return "";
        return input.replace("{rank}", plugin.getPlayerFaction(player.getUniqueId()).getPlayerRole(player.getUniqueId()).name);
    }

    private static String parseFactionRankColour(String input, Player player, ProFactions plugin) {
        if (!plugin.inFaction(player.getUniqueId())) return "";
        return input.replace("{rankcolour}", plugin.getPlayerFaction(player.getUniqueId()).getPlayerRole(player.getUniqueId()).colour);
    }

    private static String parseFactionRankSuffix(String input, Player player, ProFactions plugin) {
        if (!plugin.inFaction(player.getUniqueId())) return "";
        return input.replace("{ranksuffix}", plugin.getPlayerFaction(player.getUniqueId()).getPlayerRole(player.getUniqueId()).suffix);
    }

}




