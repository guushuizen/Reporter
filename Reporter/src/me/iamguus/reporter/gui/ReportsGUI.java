package me.iamguus.reporter.gui;

import me.iamguus.reporter.data.Report;
import me.iamguus.reporter.data.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Guus on 8-11-2015.
 */
public class ReportsGUI {

    private static ReportsGUI instance;

    private SettingsManager settingsManager = SettingsManager.getInstance();

    public Inventory getReportList(int page) {
        List<Report> allReports = new ArrayList<Report>();
        if (settingsManager.getReports().getConfigurationSection("reports.1") != null) {
            for (String s : settingsManager.getReports().getConfigurationSection("reports").getKeys(false)) {
                allReports.add(Report.loadReportFromConfig(settingsManager.getReports().getConfigurationSection("reports." + s)));
            }
        }

        int size = allReports.size() - (page * 45);
        int invSize = 54;

        if (size < 9) {
            invSize = 18;
        } else
        if (size < 9 && size > 18) {
            invSize = 27;
        } else
        if (size < 18 && size > 27) {
            invSize = 36;
        } else
        if (size < 27 && size > 36) {
            invSize = 45;
        } else
        if (size < 36 && size > 45) {
            invSize = 54;
        }

        Inventory inv = Bukkit.createInventory(null, invSize, (page == 1) ? "All Reports" : "All Reports (Page #" + page + ")");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 3);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);

        inv.setItem(1, glass);
        inv.setItem(2, glass);
        inv.setItem(3, glass);

        inv.setItem(5, glass);
        inv.setItem(6, glass);
        inv.setItem(7, glass);

        if (page != 1) {
            ItemStack previousPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta previousMeta = previousPage.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GREEN + "< Previous page");
            previousMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the previous page."));
            previousPage.setItemMeta(previousMeta);
            inv.setItem(0, previousPage);
        } else {
            inv.setItem(0, glass);
        }

        if (page * 45 < allReports.size()) {
            ItemStack nextPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next Page >");
            nextMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the next page."));
            nextPage.setItemMeta(nextMeta);
            inv.setItem(8, nextPage);
        } else {
            inv.setItem(8, glass);
        }

        if (page * 45 < allReports.size()) {
            ItemStack nextPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next Page >");
            nextMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the next page."));
            nextPage.setItemMeta(nextMeta);
            inv.setItem(8, nextPage);
        } else {
            inv.setItem(8, glass);
        }

        ItemStack search = new ItemStack(Material.EMPTY_MAP);
        ItemMeta searchMeta = search.getItemMeta();
        searchMeta.setDisplayName(ChatColor.BLUE + "Search");
        searchMeta.setLore(Arrays.asList(ChatColor.GRAY + "Use this function to search", ChatColor.GRAY + "players to report."));
        search.setItemMeta(searchMeta);
        inv.setItem(4, search);

        int begin = page * 45;
        int end = (page * 45) + 45;

        for (int i = begin; i < end; i++) {
            if (i - (45 * page) < allReports.size()) {
                Report report = allReports.get(i - (45 * page));
                Player loopPlayer = Bukkit.getPlayer(allReports.get(i - (45 * page)).getReported());
                ItemStack head = new ItemStack(Material.SKULL_ITEM);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                head.setDurability((short) 3);
                headMeta.setOwner(loopPlayer.getName());
                headMeta.setDisplayName(ChatColor.GOLD + "Report #" + report.getID() + ": " + loopPlayer.getName());
                headMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to view the report."));
                head.setItemMeta(headMeta);
                inv.setItem((i - (page * 45)) + 9, head);
            }
        }


        return inv;
    }

    public Inventory getSearchReports(int page, String searchQuery) {
        List<Report> allReports = new ArrayList<Report>();
        for (String s : settingsManager.getReports().getConfigurationSection("reports").getKeys(false)) {
            allReports.add(Report.loadReportFromConfig(settingsManager.getReports().getConfigurationSection("reports." + s)));
        }

        List<Report> results = new ArrayList<Report>();
        for (Report reportLoop : allReports) {
            Player reportedLoop = Bukkit.getPlayer(reportLoop.getReported());
            if (reportedLoop.getName().contains(searchQuery)) {
                results.add(reportLoop);
            }
            Player reporterLoop = Bukkit.getPlayer(reportLoop.getReporter());
            if (reporterLoop.getName().contains(searchQuery)) {
                results.add(reportLoop);
            }
        }

        int size = results.size();
        int invSize = 54;

        if (size < 9) {
            invSize = 18;
        } else
        if (size < 9 && size > 18) {
            invSize = 27;
        } else
        if (size < 18 && size > 27) {
            invSize = 36;
        } else
        if (size < 27 && size > 36) {
            invSize = 45;
        } else
        if (size < 36 && size > 45) {
            invSize = 54;
        }

        Inventory inv = Bukkit.createInventory(null, invSize, (page == 1) ? "Search Reports" : "Search Reports (Page #" + page + ")");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 3);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);

        inv.setItem(1, glass);
        inv.setItem(2, glass);
        inv.setItem(3, glass);

        inv.setItem(5, glass);
        inv.setItem(6, glass);
        inv.setItem(7, glass);

        if (page != 1) {
            ItemStack previousPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta previousMeta = previousPage.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GREEN + "< Previous page");
            previousMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the previous page."));
            previousPage.setItemMeta(previousMeta);
            inv.setItem(0, previousPage);
        } else {
            inv.setItem(0, glass);
        }

        if (page * 45 < allReports.size()) {
            ItemStack nextPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next Page >");
            nextMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the next page."));
            nextPage.setItemMeta(nextMeta);
            inv.setItem(8, nextPage);
        } else {
            inv.setItem(8, glass);
        }

        if (page * 45 < allReports.size()) {
            ItemStack nextPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next Page >");
            nextMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the next page."));
            nextPage.setItemMeta(nextMeta);
            inv.setItem(8, nextPage);
        } else {
            inv.setItem(8, glass);
        }

        ItemStack search = new ItemStack(Material.EMPTY_MAP);
        ItemMeta searchMeta = search.getItemMeta();
        searchMeta.setDisplayName(ChatColor.BLUE + "Search");
        searchMeta.setLore(Arrays.asList(ChatColor.GRAY + "Use this function to search", ChatColor.GRAY + "players to report."));
        search.setItemMeta(searchMeta);
        inv.setItem(4, search);

        int begin = page * 45;
        int end = (page * 45) + 45;

        for (int i = begin; i < end; i++) {
            if (i - (45 * page) < allReports.size()) {
                Report report = allReports.get(i - (45 * page));
                Player loopPlayer = Bukkit.getPlayer(allReports.get(i - (45 * page)).getReported());
                ItemStack head = new ItemStack(Material.SKULL_ITEM);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                head.setDurability((short) 3);
                headMeta.setOwner(loopPlayer.getName());
                headMeta.setDisplayName(ChatColor.GOLD + "Report #" + report.getID() + ": " + loopPlayer.getName());
                headMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to view the report."));
                head.setItemMeta(headMeta);
                inv.setItem((i - (page * 45)) + 9, head);
            }
        }


        return inv;
    }

    public Inventory getViewReport(Report report) {
        Inventory inv = Bukkit.createInventory(null, 54, "Report #" + report.getID() + ": " + Bukkit.getPlayer(report.getReported()).getName());

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

        ItemStack ban = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta banMeta = ban.getItemMeta();
        ban.setDurability((short) 14);
        banMeta.setDisplayName(ChatColor.RED + "Ban " + reportedPlayer.getName() + "");
        banMeta.setLore(Arrays.asList(ChatColor.GRAY + "for " + reasonString));
        ban.setItemMeta(banMeta);
        inv.setItem(38, ban);

        ItemStack customBan = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta customMeta = customBan.getItemMeta();
        customBan.setDurability((short) 11);
        customMeta.setDisplayName(ChatColor.BLUE + "Ban " + reportedPlayer.getName());
        customMeta.setLore(Arrays.asList(ChatColor.GRAY + "Fill in a custom reason for banning " + reportedPlayer.getName()));
        customBan.setItemMeta(customMeta);
        inv.setItem(40, customBan);

        ItemStack teleport = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta teleMeta = teleport.getItemMeta();
        teleport.setDurability((short) 5);
        teleMeta.setDisplayName(ChatColor.GREEN + "Teleport to " + reportedPlayer.getName());
        teleMeta.setLore(Arrays.asList(ChatColor.GRAY + "Teleport to " + reportedPlayer.getName() + " and check", ChatColor.GRAY + "if he/she is hacking or not."));
        teleport.setItemMeta(teleMeta);
        inv.setItem(42, teleport);

        return inv;
    }

    public static ReportsGUI get() {
        if (instance == null) {
            instance = new ReportsGUI();
        }

        return instance;
    }
}
