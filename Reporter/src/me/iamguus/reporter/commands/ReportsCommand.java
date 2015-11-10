package me.iamguus.reporter.commands;

import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.gui.ReportsGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Guus on 9-11-2015.
 */
public class ReportsCommand implements CommandExecutor {

    SettingsManager settingsManager = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can not run this command since you are not a player!");
            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("reports")) {
            if (player.hasPermission(settingsManager.getConfig().getString("permissions.open-reports-gui"))) {
                player.openInventory(ReportsGUI.get().getReportList(1));
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return false;
            }
        }
        return false;
    }
}
