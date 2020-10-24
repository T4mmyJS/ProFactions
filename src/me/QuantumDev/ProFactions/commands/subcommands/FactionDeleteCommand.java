package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class FactionDeleteCommand {

    protected ProFactions plugin;

    public FactionDeleteCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f delete"));
            return;
        }

        if (!(plugin.getPlayerFaction(player.getUniqueId()).getPlayerRole(player.getUniqueId()) == RolesUtility.LEADER)) {
            player.sendMessage(ChatUtility.chatFormat("&cOnly leaders can delete the faction!"));
            return;
        }

        String message = plugin.getPlayerFaction(player.getUniqueId()).getName();

        if (plugin.chunkFactionMap == null) {
            plugin.deleteFaction(plugin.getPlayerFaction(player.getUniqueId()));
            player.sendMessage(ChatUtility.chatFormat("&4Deleted faction &6" + message + "&4!"));
            return;
        }

        for (Faction faction : plugin.chunkFactionMap.values()) {
            if (faction == plugin.getPlayerFaction(player.getUniqueId())) {
                for (Chunk chunk : plugin.chunkFactionMap.keySet()) {
                    if (plugin.chunkFactionMap.get(chunk) == plugin.getPlayerFaction(player.getUniqueId())) {
                        plugin.chunkFactionMap.remove(chunk);
                    }
                }
            }
        }

        plugin.deleteFaction(plugin.getPlayerFaction(player.getUniqueId()));
        player.sendMessage(ChatUtility.chatFormat("&4Deleted faction &6" + message + "&4!"));
    }

}
