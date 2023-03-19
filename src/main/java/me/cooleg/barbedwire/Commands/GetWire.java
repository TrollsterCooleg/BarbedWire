package me.cooleg.barbedwire.Commands;

import me.cooleg.barbedwire.BarbedWire;
import me.cooleg.barbedwire.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GetWire implements CommandExecutor {

    private final ItemStack wire = new ItemStack(Material.COBWEB);

    public GetWire(BarbedWire main, Config config) {
        ItemMeta wireMeta = wire.getItemMeta();
        wireMeta.setDisplayName(config.name);
        wireMeta.getPersistentDataContainer().set(new NamespacedKey(main, "item"), PersistentDataType.STRING, "wire");
        wire.setItemMeta(wireMeta);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {return false;}
        if (args.length == 0) {
            ((Player) commandSender).getInventory().addItem(wire);
            commandSender.sendMessage(ChatColor.GREEN + "You were given a barbed wire!");
            return true;
        } else if (args.length == 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {commandSender.sendMessage(ChatColor.RED + "This player doesn't exist!"); return true;}
            p.getInventory().addItem(wire);
            commandSender.sendMessage(ChatColor.GREEN + args[0] + " was given a barbed wire!");
            return true;
        } else {
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {commandSender.sendMessage(ChatColor.RED + "This player doesn't exist!"); return true;}
            int count;
            try {
                count = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                commandSender.sendMessage(ChatColor.RED + "Not a valid number!");
                return true;
            }
            if (count > 64) {count = 64;}
            if (count < 1) {count = 1;}
            ItemStack newStack = wire.clone();
            newStack.setAmount(count);
            p.getInventory().addItem(newStack);
            commandSender.sendMessage(ChatColor.GREEN + args[0] + " was given barbed wires!");
            return true;
        }
    }
}
