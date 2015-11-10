package me.iamguus.reporter.commands;

import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.gui.ReporterGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Guus on 7-11-2015.
 */
public class OpenReporterCommand implements CommandExecutor {

    FileConfiguration config = SettingsManager.getInstance().getConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use this command since you are not a player!");
            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("report")) {
            if (player.hasPermission(config.getString("permissions.open-gui"))) {
                player.openInventory(ReporterGUI.get().getReporterGUI(0));
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have the right permissions to use this command!");
                return false;
            }
        }
        return false;
    }
}
