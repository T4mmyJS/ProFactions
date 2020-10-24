package me.QuantumDev.ProFactions.listeners;

import me.QuantumDev.ProFactions.Faction;
import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.ChatUtility;
import me.QuantumDev.ProFactions.utils.InteractUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    protected ProFactions plugin;
    protected InteractUtility interactUtility;

    public PlayerMoveListener(ProFactions plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.interactUtility = plugin.getInteractCheck();
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {

        Faction playerFaction;

        try {
            playerFaction = plugin.playerCurrentFaction.get(event.getPlayer().getUniqueId());
        } catch (Exception e) {
            playerFaction = null;
        }

        Faction chunkFaction;

        try {
           chunkFaction = plugin.chunkFactionMap.get(event.getPlayer().getLocation().getChunk());
        } catch (Exception e) {
            chunkFaction = null;
        }

        if (plugin.playerCurrentFaction.containsKey(event.getPlayer().getUniqueId())) {

            if (!(playerFaction == chunkFaction))  {
                if (chunkFaction == null) {
                    event.getPlayer().sendMessage(ChatUtility.chatFormat("&6Entered &cWilderness&6!"));
                    plugin.playerCurrentFaction.put(event.getPlayer().getUniqueId(), null);
                    return;
                }
                event.getPlayer().sendMessage(ChatUtility.chatFormat("&6Entered &c" + chunkFaction.getName() + "&6!"));
                plugin.playerCurrentFaction.put(event.getPlayer().getUniqueId(), chunkFaction);
            }

         } else {

            if (chunkFaction == null) {
                event.getPlayer().sendMessage(ChatUtility.chatFormat("&6Entered &cWilderness&6!"));
                plugin.playerCurrentFaction.put(event.getPlayer().getUniqueId(), null);
                return;
            }
            event.getPlayer().sendMessage(ChatUtility.chatFormat("&6Entered &c" + chunkFaction.getName() + "&6!"));
            plugin.playerCurrentFaction.put(event.getPlayer().getUniqueId(), chunkFaction);
         }
    }

}
