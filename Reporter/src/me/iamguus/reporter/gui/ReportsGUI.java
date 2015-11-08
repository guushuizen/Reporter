package me.iamguus.reporter.gui;

import me.iamguus.reporter.data.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Guus on 8-11-2015.
 */
public class ReportsGUI {

    private static ReportsGUI instance;

    public Inventory getViewReport(Report report) {
        Inventory inv = Bukkit.createInventory(null, 54, "Report #" + report.getID() + " " + Bukkit.getPlayer(report.getReported()).getName());

        Player reportedPlayer = Bukkit.getPlayer(report.getReported());
        Player reporterPlayer = Bukkit.getPlayer(report.getReporter());
        String reasonString = report.getReason();

        ItemStack back = new ItemStack(Material.SUGAR_CANE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.GREEN + "< Back to Reports");
        back.setItemMeta(backMeta);

        inv.setItem(0, back);

        ItemStack background = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        background.setDurability((short) 3);
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        for (int i = 1; i < 9; i++) {
            inv.setItem(i, background);
        }

        for (int i = 18; i < 27; i++) {
            inv.setItem(i, background);
        }

        ItemStack reported = new ItemStack(Material.SKULL_ITEM);
        SkullMeta reportedMeta = (SkullMeta) reported.getItemMeta();
        reported.setDurability((short) 3);
        reportedMeta.setOwner(reportedPlayer.getName());
        reportedMeta.setDisplayName(ChatColor.RED + "Player " + reportedPlayer.getName());
        reported.setItemMeta(reportedMeta);
        inv.setItem(11, reported);

        ItemStack reporter = new ItemStack(Material.SKULL_ITEM);
        SkullMeta reporterMeta = (SkullMeta) reported.getItemMeta();
        reporter.setDurability((short) 3);
        reporterMeta.setOwner(reporterPlayer.getName());
        reporterMeta.setDisplayName(ChatColor.BLUE + "Reported by " + reporterPlayer.getName());
        reporter.setItemMeta(reporterMeta);
        inv.setItem(13, reporter);

        ItemStack reason = new ItemStack(Material.NAME_TAG);
        ItemMeta reasonMeta = reason.getItemMeta();
        reasonMeta.setDisplayName(ChatColor.GREEN + "for " + reasonString);
        reason.setItemMeta(reasonMeta);
        inv.setItem(15, reason);

        return inv;
    }

    public static ReportsGUI get() {
        if (instance == null) {
            instance = new ReportsGUI();
        }

        return instance;
    }
}
