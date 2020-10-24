package me.QuantumDev.ProFactions.utils;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class InteractUtility {

    public Map<UUID, Boolean> playersBypassing;
    public Map<Chunk, Faction> chunkFactionMap;
    protected ProFactions plugin;

    public InteractUtility(ProFactions plugin) {
        this.plugin = plugin;
        this.playersBypassing = plugin.playersBypassing;
        this.chunkFactionMap = plugin.chunkFactionMap;
    }

    public boolean checkInteract(Player player, Block block) {

        if (!playersBypassing.containsKey(player.getUniqueId())) {
            playersBypassing.put(player.getUniqueId(), false);
        } else if (playersBypassing.get(player.getUniqueId())) {
            return false;
        }

        if (chunkFactionMap.containsKey(block.getChunk())) {
            if (chunkFactionMap.get(block.getChunk()) == plugin.getPlayerFaction(player.getUniqueId())) {
                return false;
            } else {
                player.sendMessage(ChatUtility.chatFormat("&c&lHey! &7Sorry, but you cannot interact with blocks in other claims!"));
                return true;
            }

        } else {
            return false;
        }
    }

}
