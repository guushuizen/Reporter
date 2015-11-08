package me.iamguus.reporter.commands;

import me.iamguus.reporter.data.Report;
import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.gui.ReportsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by Guus on 8-11-2015.
 */
public class ViewReportCommand implements CommandExecutor {

    SettingsManager settingsManager = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't run this command since you are not a player!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("viewreport")) {
            if (player.hasPermission(settingsManager.getConfig().getString("permissions.open-reports-gui"))) {
                if (args.length == 1) {
                    if (isInteger(args[0])) {
                        int ID = Integer.parseInt(args[0]);
                        Report report = Report.loadReportFromConfig(settingsManager.getReports().getConfigurationSection("reports." + ID));
                        player.openInventory(ReportsGUI.get().getViewReport(report));
                        player.sendMessage(ChatColor.GREEN + "Opening Report #" + ID + ": " + Bukkit.getPlayer(report.getReported()).getName());
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Sorry, but we could not recognize the ID you entered.");
                        return false;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Incorrect syntax; use /viewreports <ID>");
                    return false;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
                return false;
            }
        }

        return false;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
