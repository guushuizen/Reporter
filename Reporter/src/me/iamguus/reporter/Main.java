package me.iamguus.reporter;

import me.iamguus.reporter.commands.OpenReporterCommand;
import me.iamguus.reporter.commands.ReportsCommand;
import me.iamguus.reporter.commands.ViewReportCommand;
import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.listeners.ReporterListener;
import me.iamguus.reporter.listeners.ReportsListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Guus on 7-11-2015.
 */
public class Main extends JavaPlugin {

    private static Plugin p;

    SettingsManager settingsManager = SettingsManager.getInstance();

    public void onEnable() {
        this.p = this;
        Bukkit.getLogger().info("Plugin enabled successfully!");

        saveDefaultConfig();

        settingsManager.setup(p);

        registerListeners(p, new ReporterListener(), new ReportsListener());

        getCommand("report").setExecutor(new OpenReporterCommand());
        getCommand("viewreport").setExecutor(new ViewReportCommand());
        getCommand("reports").setExecutor(new ReportsCommand());
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
