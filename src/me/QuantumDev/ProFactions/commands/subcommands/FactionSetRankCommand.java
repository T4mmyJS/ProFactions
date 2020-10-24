package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.PlayerUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionSetRankCommand {

    protected ProFactions plugin;

    public FactionSetRankCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 3) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f setrank <player> <admin|moderator|member>"));
            return;
        }

        Faction faction = plugin.getPlayerFaction(player.getUniqueId());
        RolesUtility role = faction.getPlayerRole(player.getUniqueId());

        UUID mentionedPlayer = PlayerUtility.getPlayerUUIDFromName(args[1], plugin);
        if (mentionedPlayer == null) {
            player.sendMessage(ChatUtility.chatFormat("&cThat player isn't in your faction!"));
            return;
        }

        if (!plugin.inFaction(mentionedPlayer)) {
            player.sendMessage(ChatUtility.chatFormat("&cThat player isn't in your faction!"));
            return;
        }

        Faction playerFaction = plugin.getPlayerFaction(mentionedPlayer);
        if (playerFaction != faction) {
            player.sendMessage(ChatUtility.chatFormat("&cThat player isn't in your faction!"));
            return;
        }

        if (mentionedPlayer == player.getUniqueId()) {
            player.sendMessage(ChatUtility.chatFormat("&cYou cannot set your own rank!"));
            return;
        }

        if (role == RolesUtility.MEMBER || role == RolesUtility.MODERATOR || faction.getPlayerRole(mentionedPlayer) == RolesUtility.LEADER) {
            player.sendMessage(ChatUtility.chatFormat("&cYou do not have permission to set this player's rank!"));
            return;
        }

        if (faction.getPlayerRole(player.getUniqueId()) == RolesUtility.ADMIN) {
            if (args[2].equalsIgnoreCase("Member")) {
                faction.setPlayerRole(mentionedPlayer, RolesUtility.MEMBER);
                player.sendMessage(ChatUtility.chatFormat("&6Set &c" + PlayerUtility.getPlayername(mentionedPlayer, plugin) + "'s &6rank to &7Member&6!"));

                try {
                    plugin.getServer().getPlayer(mentionedPlayer).sendMessage(ChatUtility.chatFormat("&6Your rank has been set to &7Member&6!"));
                } catch (Exception ignored){}
                return;
            }
            if (args[2].equalsIgnoreCase("Moderator")) {
                faction.setPlayerRole(mentionedPlayer, RolesUtility.MODERATOR);
                player.sendMessage(ChatUtility.chatFormat("&6Set &c" + PlayerUtility.getPlayername(mentionedPlayer, plugin) + "'s &6rank to &5Moderator&6!"));

                try {
                    plugin.getServer().getPlayer(mentionedPlayer).sendMessage(ChatUtility.chatFormat("&6Your rank has been set to &5Moderator&6!"));
                } catch (Exception ignored){}
                return;
            }
            if (args[2].equalsIgnoreCase("Leader")) {
                player.sendMessage(ChatUtility.chatFormat("&cYou do not have permission to grant this rank!"));
                return;
            }

            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f setrank <player> <admin|moderator|member>"));
            return;

        }

        if (faction.getPlayerRole(player.getUniqueId()) == RolesUtility.LEADER) {
            if (args[2].equalsIgnoreCase("Member")) {
                faction.setPlayerRole(mentionedPlayer, RolesUtility.MEMBER);
                player.sendMessage(ChatUtility.chatFormat("&6Set &c" + PlayerUtility.getPlayername(mentionedPlayer, plugin) + "'s &6rank to &7Member&6!"));
                try {
                    plugin.getServer().getPlayer(mentionedPlayer).sendMessage(ChatUtility.chatFormat("&6Your rank has been set to &7Member&6!"));
                } catch (Exception ignored){}
                return;
            }
            if (args[2].equalsIgnoreCase("Moderator")) {
                faction.setPlayerRole(mentionedPlayer, RolesUtility.MODERATOR);
                player.sendMessage(ChatUtility.chatFormat("&6Set &c" + PlayerUtility.getPlayername(mentionedPlayer, plugin) + "'s &6rank to &5Moderator&6!"));
                try {
                    plugin.getServer().getPlayer(mentionedPlayer).sendMessage(ChatUtility.chatFormat("&6Your rank has been set to &5Moderator&6!"));
                } catch (Exception ignored){}
                return;
            }
            if (args[2].equalsIgnoreCase("Leader")) {
                faction.setPlayerRole(mentionedPlayer, RolesUtility.LEADER);
                player.sendMessage(ChatUtility.chatFormat("&6Set &c" + PlayerUtility.getPlayername(mentionedPlayer, plugin) + "'s &6rank to &4Leader!"));
                try {
                    plugin.getServer().getPlayer(mentionedPlayer).sendMessage(ChatUtility.chatFormat("&6Your rank has been set to &4Leader&6!"));
                } catch (Exception ignored){}
                return;
            }

            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f setrank <player> <admin|moderator|member>"));
            return;

        }

        player.sendMessage(ChatUtility.chatFormat("&cYou do not have permission to set this player's rank!"));

    }

}
