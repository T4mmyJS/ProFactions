package me.QuantumDev.ProFactions.listeners;

import me.QuantumDev.ProFactions.ProFactions;
import me.QuantumDev.ProFactions.utils.InteractUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    protected InteractUtility interactCheck;

    public BlockBreakListener(ProFactions plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.interactCheck = plugin.getInteractCheck();
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        event.setCancelled(interactCheck.checkInteract(event.getPlayer(), event.getBlock()));
    }

}
