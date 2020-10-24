package me.QuantumDev.ProFactions;

import me.QuantumDev.ProFactions.commands.BypassCommand;
import me.QuantumDev.ProFactions.commands.ClaimCommand;
import me.QuantumDev.ProFactions.commands.FactionChatCommand;
import me.QuantumDev.ProFactions.commands.FactionCommand;
import me.QuantumDev.ProFactions.listeners.BlockBreakListener;
import me.QuantumDev.ProFactions.listeners.BlockPlaceListener;
import me.QuantumDev.ProFactions.listeners.PlayerMoveListener;
import me.QuantumDev.ProFactions.utils.ConfigUtility;
import me.QuantumDev.ProFactions.utils.InteractUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
public class ProFactions extends JavaPlugin {

    public InteractUtility interactCheck;
    public List<Listener> blockListeners = new ArrayList<>();
    public List<Faction> factionList = new ArrayList<>();
    public Map<UUID, Faction> playerCurrentFaction = new HashMap<>();
    public Map<UUID, Boolean> playersBypassing = new HashMap<>();
    public Map<Chunk, Faction> chunkFactionMap = new HashMap<>();
    public Map<UUID, List<UUID>> playerInvites = new HashMap<>();

    public Map<String, FileConfiguration> configs = new HashMap<>();
    public Map<String, File> files = new HashMap<>();
    public FileConfiguration messagesFile;

    @Override
    public void onEnable() {
        setInteractCheck(new InteractUtility(this));

        registerCommands();
        registerBlockListeners();

        createConfigs();
        ConfigUtility.importData(this);
        messagesFile = ConfigUtility.getConfig("messages.yml", this);
    }

    @Override
    public void onDisable() {
        ConfigUtility.saveData(this);
    }

    private void registerBlockListeners() {
        addBlockListener(new BlockBreakListener(this));
        addBlockListener(new BlockPlaceListener(this));
        addBlockListener(new PlayerMoveListener(this));
    }

    private void createConfigs() {
        ConfigUtility.createConfig("data.yml", this);
        ConfigUtility.createConfig("messages.yml", this);
    }

    private void registerCommands() {
        getServer().getPluginCommand("claim").setExecutor(new ClaimCommand(this));
        getServer().getPluginCommand("bypass").setExecutor(new BypassCommand(this));
        getServer().getPluginCommand("faction").setExecutor(new FactionCommand(this));
        getServer().getPluginCommand("factionchat").setExecutor(new FactionChatCommand(this));
    }

    public void addInvite(Player inviter, Player invitee) {

        try {
            List<UUID> playerList = playerInvites.get(invitee.getUniqueId());
            playerList.add(inviter.getUniqueId());
            playerInvites.put(invitee.getUniqueId(), playerList);

        }  catch (Exception e) {
            List<UUID> playerList = new ArrayList<>();
            playerList.add(inviter.getUniqueId());
            playerInvites.put(invitee.getUniqueId(), playerList);
        }

    }

    public void removeInvite(Player inviter, Player invitee) {

        try {
            List<UUID> playerList = playerInvites.get(invitee.getUniqueId());
            playerList.remove(inviter.getUniqueId());
            playerInvites.put(invitee.getUniqueId(), playerList);

        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean noInvite(UUID invitee, UUID inviter) {
        if (playerInvites.containsKey(invitee)) {
            try {
                return !playerInvites.get(invitee).contains(inviter);
            } catch (Exception e){
                return true;
            }
        }
        return true;
    }



    public InteractUtility getInteractCheck() {
        return interactCheck;
    }

    public List<Faction> getFactions() {
        return factionList;
    }

    private void setInteractCheck(InteractUtility interactCheck) {
        this.interactCheck = interactCheck;
    }

    private void addBlockListener(Listener listener) {
        blockListeners.add(listener);
    }

    public void createFaction(String name, String description, Map<UUID, RolesUtility> playerRolesMap, int level) {
        Faction faction = new Faction(name, description, playerRolesMap, level);
        factionList.add(faction);
    }

    public void deleteFaction(Faction faction) {
        factionList.remove(faction);
    }

    public Faction getFaction(String name) {
        for (Faction faction : factionList) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }

        return null;
    }

    public boolean doesFactionExist(String name) {
        for (Faction faction : factionList) {
            if (faction.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean inFaction(UUID player) {
        for (Faction faction : factionList) {
            if (faction.getPlayerRoles().containsKey(player)) {
                return true;
            }
        }
        return false;
    }

    public Faction getPlayerFaction(UUID player) {
        for (Faction faction : factionList) {
            if (faction.getPlayerRoles().containsKey(player)) {
                return faction;
            }
        }
        return null;
    }

}
