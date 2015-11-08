package me.iamguus.reporter.data;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {

    private SettingsManager() { }

    static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    Plugin p;
    FileConfiguration config;
    File cfile;

    FileConfiguration reports;
    File rfile;

    public void setup(Plugin p) {
        config = p.getConfig();
        config.options().copyDefaults(true);
        cfile = new File(p.getDataFolder(), "config.yml");
        saveConfig();

        rfile = new File(p.getDataFolder(), "reports.yml");
        if (!(rfile.exists())) {
            try {
                rfile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        reports = YamlConfiguration.loadConfiguration(rfile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public FileConfiguration getReports() { return reports; }

    public void saveReports() {
        try {
            reports.save(rfile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}

