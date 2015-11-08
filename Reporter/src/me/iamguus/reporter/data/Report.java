package me.iamguus.reporter.data;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

/**
 * Created by Guus on 8-11-2015.
 */
public class Report {

    private SettingsManager settingsManager = SettingsManager.getInstance();

    private UUID reported;
    private UUID reporter;
    private String reason;

    public Report(UUID reported, UUID reporter, String reason) {
        this.reported = reported;
        this.reporter = reporter;
        this.reason = reason;
    }

    public UUID getReported() { return this.reported; }

    public UUID getReporter() { return this.reporter; }

    public String getReason() { return this.reason; }

    public void saveToConfig() {
        FileConfiguration configuration = settingsManager.getReports();
        int newID = configuration.getConfigurationSection("reports").getKeys(false).size() + 1;
        configuration.set("reports." + newID + ".reported", reported);
        configuration.set("reports." + newID + ".reporter", reporter);
        configuration.set("reports." + newID + ".reason", reason);
        settingsManager.saveReports();
    }
}
