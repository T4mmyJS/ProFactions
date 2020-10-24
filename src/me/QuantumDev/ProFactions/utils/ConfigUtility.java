package me.QuantumDev.ProFactions.utils;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigUtility {

    public static FileConfiguration getConfig(String name, ProFactions plugin) {
        try {
            return plugin.configs.get(name);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void createConfig(String name, ProFactions plugin) {
        File file = new File(plugin.getDataFolder(), name);
        plugin.files.put(name, file);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        FileConfiguration configFile = new YamlConfiguration();
        plugin.configs.put(name, configFile);
        try {
            configFile.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfigs(ProFactions plugin) {
        ConfigUtility.saveData(plugin);

        for (String name : plugin.configs.keySet()) {
            createConfig(name, plugin);
        }

        ConfigUtility.importData(plugin);
        plugin.messagesFile = ConfigUtility.getConfig("messages.yml", plugin);
    }

    public static void saveConfigs(ProFactions plugin) {
        for (String name : plugin.configs.keySet()) {
            try {
                plugin.configs.get(name).save(plugin.files.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void importData(ProFactions plugin) {
        FileConfiguration config = ConfigUtility.getConfig("data.yml", plugin);
        assert config != null;

        plugin.chunkFactionMap = new HashMap<>();
        plugin.factionList = new ArrayList<>();
        plugin.playerInvites = new HashMap<>();

        try {
            for (String faction : config.getConfigurationSection("factions.").getKeys(false)) {
                String name = config.getString("factions." + faction + ".name");
                String description = config.getString("factions." + faction + ".description");
                int level = config.getInt("factions." + faction + ".level");

                Map<UUID, RolesUtility> playerRolesMap = new HashMap<>();

                for (String uuidString : config.getConfigurationSection("factions." + faction + ".players").getKeys(false)) {
                    UUID uuid = PlayerUtility.getPlayerUUID(uuidString, plugin);

                    String role = config.getString("factions." + faction + ".players." + uuidString);
                    assert role != null;
                    if (role.equals("Leader")) {
                        playerRolesMap.put(uuid, RolesUtility.LEADER);
                    }
                    if (role.equals("Admin")) {
                        playerRolesMap.put(uuid, RolesUtility.ADMIN);
                    }
                    if (role.equals("Moderator")) {
                        playerRolesMap.put(uuid, RolesUtility.MODERATOR);
                    }
                    if (role.equals("Member")) {
                        playerRolesMap.put(uuid, RolesUtility.MEMBER);
                    }

                }

                plugin.createFaction(name, description, playerRolesMap, level);

            }
        } catch (Exception ignored) {}

        try {
            for (String chunkNumber : config.getConfigurationSection("chunks.").getKeys(false)) {
                int xPos = config.getInt("chunks." + chunkNumber + ".x");
                int zPos = config.getInt("chunks." + chunkNumber + ".z");
                String factionName = config.getString("chunks." + chunkNumber + ".faction");

                Faction faction = plugin.getFaction(factionName);
                Chunk chunk = plugin.getServer().getWorlds().get(0).getChunkAt(xPos, zPos);

                plugin.chunkFactionMap.put(chunk, faction);

            }

        } catch (Exception ignored) {}

        try {
            for (String playerInvitedUUID : config.getConfigurationSection("invites.").getKeys(false)) {
                List<?> playersInvitedBy = config.getList("invites." + playerInvitedUUID);
                List<UUID> playersInvitedByUUID = new ArrayList<>();

                for (Object uuid : playersInvitedBy) {
                    playersInvitedByUUID.add(PlayerUtility.getPlayerUUID((String) uuid, plugin));
                }

                plugin.playerInvites.put(PlayerUtility.getPlayerUUID(playerInvitedUUID, plugin), playersInvitedByUUID);
            }

        } catch (Exception ignored) {}



    }

    public static void saveData(ProFactions plugin) {
        FileConfiguration config = ConfigUtility.getConfig("data.yml", plugin);
        assert config != null;

        Map<String, Object> configValues = config.getValues(false);
        for (Map.Entry<String, Object> entry : configValues.entrySet()) {
            config.set(entry.getKey(), null);
        }

        for (Faction faction : plugin.factionList) {
            config.set("factions." + faction.getName() + ".name", faction.getName());
            config.set("factions." + faction.getName() + ".description", faction.getDescription());
            config.set("factions." + faction.getName() + ".level", faction.getLevel());

            for (UUID uuid : faction.getPlayerRoles().keySet()) {
                RolesUtility role = faction.getPlayerRoles().get(uuid);
                config.set("factions." + faction.getName() + ".players." + uuid, role.name);
            }
        }

        int count = 1;
        for (Chunk chunk : plugin.chunkFactionMap.keySet()) {
            config.set("chunks." + count + ".x", chunk.getX());
            config.set("chunks." + count + ".z", chunk.getZ());
            config.set("chunks." + count + ".faction", plugin.chunkFactionMap.get(chunk).getName());
            count += 1;
        }

        for (UUID invitee : plugin.playerInvites.keySet()) {

            List<String> players = new ArrayList<>();
            for (UUID inviter : plugin.playerInvites.get(invitee)) {
                players.add(inviter.toString());
            }

            config.set("invites." + invitee, players);
        }

        ConfigUtility.saveConfigs(plugin);

    }

}
