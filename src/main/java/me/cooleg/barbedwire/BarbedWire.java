package me.cooleg.barbedwire;

import me.cooleg.barbedwire.Commands.GetWire;
import me.cooleg.barbedwire.Events.BlockBreak;
import me.cooleg.barbedwire.Events.BlockPlace;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BarbedWire extends JavaPlugin {

    private WireHandling handler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        handler = new WireHandling(this, getConfig());

        Bukkit.getPluginManager().registerEvents(new BlockBreak(handler), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(this, handler), this);
        getCommand("getwire").setExecutor(new GetWire(this, new Config(getConfig())));
    }

    @Override
    public void onDisable() {
        handler.saveWires();
    }
}
