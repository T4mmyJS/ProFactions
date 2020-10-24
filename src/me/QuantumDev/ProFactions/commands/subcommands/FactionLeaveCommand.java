package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionLeaveCommand {

    protected ProFactions plugin;

    public FactionLeaveCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f leave"));
            return;
        }

        Faction faction = plugin.getPlayerFaction(player.getUniqueId());

        if (faction.getPlayerRole(player.getUniqueId()) == RolesUtility.LEADER) {
            player.sendMessage(ChatUtility.chatFormat("&cYou cannot your own faction! Type &3/f delete &cinstead!"));
            return;
        }

        faction.removePlayer(player);
        player.sendMessage(ChatUtility.chatFormat("&cLeft your current faction, &6" + faction.getName() + "&c!"));

        for (UUID uuid : faction.getPlayerRoles().keySet()) {
            try {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatUtility.chatFormat("&2[Faction] &7" + player.getName() + " &chas left the faction!"));
            } catch (Exception ignored) {
            }
        }

    }

}
