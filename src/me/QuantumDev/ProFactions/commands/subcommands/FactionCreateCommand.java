package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionCreateCommand {

    protected ProFactions plugin;

    public FactionCreateCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        String message;
        if (args.length != 2) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f create <name>"));
            return;
        }

        if (plugin.inFaction(player.getUniqueId())) {
            player.sendMessage(ChatUtility.chatFormat("&cYou are already in a faction! Type &3/f leave &6to leave it!"));
            return;
        }

        if (plugin.doesFactionExist(args[1])) {
            player.sendMessage(ChatUtility.chatFormat("&cThat faction already exists!"));
            return;
        }

        Map<UUID, RolesUtility> playerMap = new HashMap<>();
        playerMap.put(player.getUniqueId(), RolesUtility.LEADER);
        plugin.createFaction(args[1], player.getName() + "'s Faction", playerMap, 1);

        message = ChatUtility.parse(plugin.messagesFile.getString("faction_create").replace("{name}", args[1]), player, plugin);
        player.sendMessage(ChatUtility.chatFormat(message));
    }
}
