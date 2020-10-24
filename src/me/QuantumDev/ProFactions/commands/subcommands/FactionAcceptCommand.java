package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.PlayerUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class FactionAcceptCommand {

    protected ProFactions plugin;

    public FactionAcceptCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f accept <player>"));
            return;
        }

        if (plugin.noInvite(player.getUniqueId(), PlayerUtility.getPlayerUUIDFromName(args[1], plugin))) {
            player.sendMessage(ChatUtility.chatFormat("&cYou do not have an invite from this player!"));
                return;
        }


        if (plugin.inFaction(player.getUniqueId())) {
            player.sendMessage(ChatUtility.chatFormat("&cYou are already in a faction! Type &3/f leave &cto leave it!"));
            return;
        }
        Faction faction;

        faction = plugin.getPlayerFaction(PlayerUtility.getPlayerUUIDFromName(args[1], plugin));

        for (UUID uuid : faction.getPlayerRoles().keySet()) {
            try {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatUtility.chatFormat("&2[Faction] &7" + player.getName() + " &6has joined the faction!"));
            } catch (Exception ignored) {

            }
        }

        List<UUID> newList = plugin.playerInvites.get(player.getUniqueId());
        newList.remove(PlayerUtility.getPlayerUUIDFromName(args[1], plugin));
        plugin.playerInvites.put(player.getUniqueId(), newList);

        faction.setPlayerRole(player.getUniqueId(), RolesUtility.MEMBER);
        player.sendMessage(ChatUtility.chatFormat("&6Joined &c" + faction.getName() + "&6!"));

    }

}
