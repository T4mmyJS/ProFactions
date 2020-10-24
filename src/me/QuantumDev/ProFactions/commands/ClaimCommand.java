package me.QuantumDev.ProFactions.commands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {

    protected ProFactions plugin;

    public ClaimCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatUtility.chatFormat("&cThis command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;

        if (plugin.chunkFactionMap.containsKey(player.getLocation().getChunk())) {
            if (plugin.chunkFactionMap.get(player.getLocation().getChunk()) == plugin.getPlayerFaction(player.getUniqueId())) {
                player.sendMessage(ChatUtility.chatFormat("&6" + plugin.getPlayerFaction(player.getUniqueId()).getName() + "&c already owns this claim!"));
            } else {
                player.sendMessage(ChatUtility.chatFormat("&cThis chunk is already claimed!"));
            }
            return true;
        }

        if (!plugin.inFaction(player.getUniqueId())) {
            player.sendMessage(ChatUtility.chatFormat("&6You don't appear to be in a faction! Type &3/f create &6to create one!"));
            return true;
        }

        plugin.chunkFactionMap.put(player.getLocation().getChunk(), plugin.getPlayerFaction(player.getUniqueId()));
        player.sendMessage(ChatUtility.chatFormat("&6Claimed chunk for &c" + plugin.getPlayerFaction(player.getUniqueId()).getName() + "&6!"));

        return true;
    }
}
