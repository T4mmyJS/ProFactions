package me.QuantumDev.ProFactions.commands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.commands.subcommands.*;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FactionCommand implements CommandExecutor {

    protected ProFactions plugin;

    public FactionCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatUtility.chatFormat("&cThis command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;
        
            if (args.length == 0) {

                if (plugin.inFaction(player.getUniqueId())) {
                    player.sendMessage(ChatUtility.chatFormat("&6Reached Faction GUI"));
                    // TODO
                } else {
                    player.sendMessage(ChatUtility.chatFormat("&6You don't appear to be in a faction! Type &3/f create &6to create one!"));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("create")) {

                FactionCreateCommand createCommand = new FactionCreateCommand(plugin);
                createCommand.onCommand(player, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {

                FactionReloadCommand reloadCommand = new FactionReloadCommand(plugin);
                reloadCommand.onCommand(player, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("accept")) {

                FactionAcceptCommand acceptCommand = new FactionAcceptCommand(plugin);
                acceptCommand.onCommand(player, args);
                return true;
            }

            if (!plugin.inFaction(player.getUniqueId())) {
                player.sendMessage(ChatUtility.chatFormat("&6You don't appear to be in a faction! Type &3/f create &6to create one!"));
                return true;
            }

            if (args[0].equalsIgnoreCase("delete")) {

                FactionDeleteCommand deleteCommand = new FactionDeleteCommand(plugin);
                deleteCommand.onCommand(player, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("invite")) {

                FactionInviteCommand inviteCommand = new FactionInviteCommand(plugin);
                inviteCommand.onCommand(player, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("leave")) {

                FactionLeaveCommand leaveCommand = new FactionLeaveCommand(plugin);
                leaveCommand.onCommand(player, args);
                return true;
            }

            if (args[0].equalsIgnoreCase("setrank")) {

                FactionSetRankCommand setRankCommand = new FactionSetRankCommand(plugin);
                setRankCommand.onCommand(player, args);
                return true;
            }

            player.sendMessage(ChatUtility.chatFormat("&cUnknown command. Sorry!"));
            return true;

    }

}
