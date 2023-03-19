package me.cooleg.barbedwire.Events;

import me.cooleg.barbedwire.BarbedWire;
import me.cooleg.barbedwire.WireHandling;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlace implements Listener {

    private final NamespacedKey wire;
    private final WireHandling handler;

    public BlockPlace(BarbedWire main, WireHandling handling) {
        wire = new NamespacedKey(main, "item");
        handler = handling;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().getItemMeta().getPersistentDataContainer().has(wire, PersistentDataType.STRING)) {
            handler.newWire(e.getBlockPlaced().getLocation());
        }
    }

}
