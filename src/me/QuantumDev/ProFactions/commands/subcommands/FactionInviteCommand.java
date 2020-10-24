package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.RolesUtility;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionInviteCommand {

    protected ProFactions plugin;

    public FactionInviteCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f invite <player>"));
            return;
        }

        try {
            Player invitePlayer = player.getServer().getPlayer(args[1]);
            RolesUtility role = plugin.getPlayerFaction(player.getUniqueId()).getPlayerRole(player.getUniqueId());

            for (UUID uuid : plugin.getPlayerFaction(player.getUniqueId()).getPlayerRoles().keySet()) {
                try {
                    plugin.getServer().getPlayer(uuid).sendMessage(ChatUtility.chatFormat("&2[Faction] " + role.getColour() + player.getName() + role.getSuffix() + " &6has invited &7" + invitePlayer.getName() + " &6to the faction!"));
                } catch (Exception ignored) {}
            }

            invitePlayer.sendMessage(ChatUtility.chatFormat("&c" + player.getName() + "&6 has invited you to their faction, &c" + plugin.getPlayerFaction(player.getUniqueId()).getName() + "&6! Type &3/f accept " + player.getName() + "&6!"));
            plugin.addInvite(player, invitePlayer);
        } catch (Exception e) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f invite <player>"));
        }

    }

}
