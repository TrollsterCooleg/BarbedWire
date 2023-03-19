package me.cooleg.barbedwire.Events;

import me.cooleg.barbedwire.WireHandling;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final WireHandling handling;

    public BlockBreak(WireHandling handler) {
        handling = handler;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        handling.removeWire(e.getBlock().getLocation());
    }

}
