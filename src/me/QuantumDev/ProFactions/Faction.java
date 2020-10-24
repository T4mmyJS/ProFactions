package me.QuantumDev.ProFactions;

import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Faction {

    private String name;
    private String description;
    private final Map<UUID, RolesUtility> playerRolesMap;
    private int level;

    public Faction(String name, String description, Map<UUID, RolesUtility> playerRolesMap, int level) {
        this.name = name;
        this.description = description;
        this.playerRolesMap = playerRolesMap;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<UUID, RolesUtility> getPlayerRoles() {
        return playerRolesMap;
    }

    public void setPlayerRole(UUID player, RolesUtility role) {
        this.playerRolesMap.put(player, role);
    }

    public RolesUtility getPlayerRole(UUID player) {
        return this.playerRolesMap.get(player);
    }

    public void removePlayer(Player player) {
        this.playerRolesMap.remove(player.getUniqueId());
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int levelsToAdd) {
        this.level += levelsToAdd;
    }

}
