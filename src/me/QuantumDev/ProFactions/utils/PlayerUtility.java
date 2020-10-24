package me.QuantumDev.ProFactions.utils;

import me.QuantumDev.ProFactions.ProFactions;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtility {

    public static UUID getPlayerUUID(String uuid, ProFactions plugin) {
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            if (player.getUniqueId().toString().equals(uuid)) {  return player.getUniqueId(); }
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getUniqueId().toString().equals(uuid)) {  return player.getUniqueId(); }
        }

        return null;
    }

    public static String getPlayername(UUID uuid, ProFactions plugin) {
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            if (player.getUniqueId() == uuid) {
                return player.getName();
            }
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getUniqueId() == uuid) {
                return player.getName();
            }
        }

        return null;
    }

    public static UUID getPlayerUUIDFromName(String name, ProFactions plugin) {
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            if (player.getName().equals(name)) {  return player.getUniqueId(); }
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getName().equals(name)) {  return player.getUniqueId(); }
        }

        return null;
    }

}
