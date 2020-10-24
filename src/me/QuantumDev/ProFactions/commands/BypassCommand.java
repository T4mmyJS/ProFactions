package me.QuantumDev.ProFactions.commands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BypassCommand implements CommandExecutor {

    protected ProFactions plugin;

    public BypassCommand(ProFactions plugin) {
        this.plugin = plugin;
        plugin.getCommand("bypass").setPermissionMessage(ChatUtility.chatFormat("&cYou do not have permission to execute this command!"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatUtility.chatFormat("&cThis command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;
        if (plugin.getInteractCheck().playersBypassing.containsKey(player.getUniqueId())) {
            if (plugin.getInteractCheck().playersBypassing.get(player.getUniqueId())) {
                plugin.getInteractCheck().playersBypassing.put(player.getUniqueId(), false);
                player.sendMessage(ChatUtility.chatFormat("&6Set bypass to &cdisabled &6for " + player.getDisplayName()));
            } else {
                plugin.getInteractCheck().playersBypassing.put(player.getUniqueId(), true);
                player.sendMessage(ChatUtility.chatFormat("&6Set bypass to &cenabled &6for " + player.getDisplayName()));
            }
        } else {
            plugin.getInteractCheck().playersBypassing.put(player.getUniqueId(), true);
            player.sendMessage(ChatUtility.chatFormat("&6Set bypass to &cenabled &6for " + player.getDisplayName()));
        }

        return true;
    }
}
