package me.iamguus.reporter;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Guus on 7-11-2015.
 */
public class Main extends JavaPlugin {

    private static Plugin p;

    public void onEnable() {
        this.p = this;
        Bukkit.getLogger().info("Plugin enabled successfully!");
    }

    public void onDisable() {
        this.p = null;
        Bukkit.getLogger().info("Plugin disabled successfully!");
    }

    public static Plugin getPlugin() {
        return p;
    }

    public void registerListeners(Plugin p, Listener... listeners) {
        for (Listener list : listeners) {
            Bukkit.getPluginManager().registerEvents(list, p);
        }
    }
}
