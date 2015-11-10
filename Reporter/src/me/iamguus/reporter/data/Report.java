package me.iamguus.reporter.data;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

/**
 * Created by Guus on 8-11-2015.
 */
public class Report {

    private SettingsManager settingsManager = SettingsManager.getInstance();

    private int ID;
    private UUID reported;
    private UUID reporter;
    private String reason;

    public Report(UUID reported, UUID reporter, String reason) {
        this.ID = (settingsManager.getReports().getConfigurationSection("reports") == null) ? 1 : settingsManager.getReports().getConfigurationSection("reports").getKeys(false).size() + 1;
        this.reported = reported;
        this.reporter = reporter;
        this.reason = reason;
    }

    public Report(int id, UUID reported, UUID reporter, String reason) {
        this(reported, reporter, reason);
        this.ID = id;
    }

    public UUID getReported() { return this.reported; }

    public UUID getReporter() { return this.reporter; }

    public String getReason() { return this.reason; }

    public int getID() { return this.ID; }

    public void saveToConfig() {
        FileConfiguration configuration = settingsManager.getReports();
        configuration.set("reports." + this.ID + ".reported", reported.toString());
        configuration.set("reports." + this.ID + ".reporter", reporter.toString());
        configuration.set("reports." + this.ID + ".reason", reason);
        settingsManager.saveReports();
    }

    public static Report loadReportFromConfig(ConfigurationSection section) {
        int id = Integer.parseInt(section.getCurrentPath().split("\\.")[1]);
        UUID reported = UUID.fromString(section.getString("reported"));
        UUID reporter = UUID.fromString(section.getString("reporter"));
        String reason = section.getString("reason");
        return new Report(id, reported, reporter, reason);
    }
}
