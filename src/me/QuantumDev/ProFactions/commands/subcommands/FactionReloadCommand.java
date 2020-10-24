package me.QuantumDev.ProFactions.commands.subcommands;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.ConfigUtility;
import org.bukkit.entity.Player;

public class FactionReloadCommand {

    protected ProFactions plugin;

    public FactionReloadCommand(ProFactions plugin) {
        this.plugin = plugin;
    }

    public void onCommand(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatUtility.chatFormat("&6Usage: &3/f reload"));
            return;
        }

        if (!player.hasPermission("profactions.reload")) {
            player.sendMessage(ChatUtility.chatFormat("&cYou do not have permission to execute this command!"));
            return;
        }

        ConfigUtility.reloadConfigs(plugin);
        player.sendMessage(ChatUtility.chatFormat("&aProFactions configs reloaded!"));

    }

}
