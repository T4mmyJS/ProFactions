package me.QuantumDev.ProFactions.commands;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class FactionChatCommand implements CommandExecutor {

    protected ProFactions plugin;

    public FactionChatCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatUtility.chatFormat("&cThis command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;

        if (!plugin.inFaction(player.getUniqueId())) {
            player.sendMessage(ChatUtility.chatFormat("&6You don't appear to be in a faction! Type &3/f create &6to create one!"));
            return true;
        }

        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(" ").append(arg);
        }

        Faction faction = plugin.getPlayerFaction(player.getUniqueId());
        RolesUtility role = faction.getPlayerRoles().get(player.getUniqueId());

        for (UUID uuid : faction.getPlayerRoles().keySet()) {
            try {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatUtility.chatFormat("&2[Faction] " + role.getColour() + player.getName() + role.getSuffix() + "&f:" + msg));
            } catch (Exception ignored) {}
        }

        return true;
    }
}
