package me.cooleg.barbedwire;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class WireHandling {

    private final HashMap<Location, UUID> barbedWires = new HashMap<>();
    private final FileConfiguration configuration;
    private final BarbedWire main;

    public WireHandling(BarbedWire main, FileConfiguration configuration) {
        this.main = main;
        this.configuration = configuration;
        ConfigurationSection section = configuration.getConfigurationSection("wires");
        if (section != null) {
            for (String s : section.getKeys(false)) {
                barbedWires.put(section.getLocation(s), UUID.fromString(s));
            }
        }
        Config config = new Config(configuration);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (Entity ent : w.getEntities()) {
                        if (config.immune.contains(ent.getType())) {continue;}
                        if (!(ent instanceof LivingEntity)) {continue;}
                        LivingEntity living = (LivingEntity) ent;
                        for (Location loc : barbedWires.keySet()) {
                            if (living.getLocation().getBlock().getLocation().equals(loc)) {
                                living.damage(config.dmg);
                                for (PotionEffectType pot : config.potions.keySet()) {
                                    living.addPotionEffect(new PotionEffect(pot, config.potions.get(pot), 0, false, false));
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main, 10, 10);
    }

    public void newWire(Location loc) {
        Location l = loc.getBlock().getLocation();
        configuration.set("wires." + UUID.randomUUID(), l);
        barbedWires.put(loc.getBlock().getLocation(), UUID.randomUUID());
    }

    public void removeWire(Location loc) {
        if (!barbedWires.containsKey(loc.getBlock().getLocation())) {return;}
        configuration.set("wires." + barbedWires.get(loc.getBlock().getLocation()), null);
        barbedWires.remove(loc.getBlock().getLocation());
    }

    public void saveWires() {
        main.saveConfig();
    }

}
