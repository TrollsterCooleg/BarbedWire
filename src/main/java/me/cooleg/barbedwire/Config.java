package me.cooleg.barbedwire;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    public final HashMap<PotionEffectType, Integer> potions = new HashMap<>();
    public final ArrayList<EntityType> immune = new ArrayList<>();
    public final double dmg;
    public final String name;

    public Config(FileConfiguration config) {
        dmg = config.getDouble("config.damage");
        name = ChatColor.translateAlternateColorCodes('&', config.getString("config.name"));
        for (String s : config.getStringList("config.effects")) {
            PotionEffectType ent;
            try {
                String[] items = s.split(",");
                ent = PotionEffectType.getByName(items[0].toUpperCase().trim());
                int duration = 20;
                if (items.length == 2) {
                    try {
                        duration *= Integer.parseInt(items[1]);
                    } catch (NumberFormatException ex) {
                        Bukkit.getLogger().severe(ChatColor.RED + "Duration on potion " + items[0] + " invalid!");
                    }
                }
                potions.put(ent, duration);
            } catch (IllegalArgumentException ex) {
                Bukkit.getLogger().severe("Potion called " + s + " does not exist! \nRefer to this page for effect names: " +
                        "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html");
            }
        }
        for (String s : config.getStringList("config.immunemobs")) {
            EntityType ent;
            try {
                ent = EntityType.valueOf(s.toUpperCase().trim());
                immune.add(ent);
            } catch (IllegalArgumentException ex) {
                Bukkit.getLogger().severe("Entity called " + s + " does not exist! \nRefer to this page for mob names: " +
                        "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html");
            }
        }
    }
}
