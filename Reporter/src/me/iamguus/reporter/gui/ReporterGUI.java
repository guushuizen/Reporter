package me.iamguus.reporter.gui;

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
import java.util.Collection;
import java.util.List;

/**
 * Created by Guus on 7-11-2015.
 */
public class ReporterGUI {

    private static ReporterGUI instance;

    public Inventory getReporterGUI(int page) {
        Inventory inv = Bukkit.createInventory(null, 54, (page == 1) ? "Report a player!" : "Report a player! (Page " + page + ")");

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

        if (page * 45 < Bukkit.getOnlinePlayers().size()) {
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
        ArrayList<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());

        for (int i = begin; i < end; i++) {
            if (i - (45 * page) < onlinePlayers.size()) {
                Player loopPlayer = onlinePlayers.get(i - (45 * page));
                ItemStack head = new ItemStack(Material.SKULL_ITEM);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                head.setDurability((short) 3);
                headMeta.setOwner(loopPlayer.getName());
                headMeta.setDisplayName(ChatColor.GOLD + "Report " + loopPlayer.getName());
                headMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to report this player."));
                head.setItemMeta(headMeta);
                inv.setItem((i - (page * 45)) + 9, head);
            }
        }

        return inv;
    }

    public Inventory getSearchPlayersGUI(String search, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, (page == 1) ? "Search a player!" : "Search a player! (Page " + page + ")");

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

        List<Player> matchingPlayers = new ArrayList<Player>();
        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop.getName().contains(search)) {
                matchingPlayers.add(loop);
            }
        }

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

        if (page * 45 > matchingPlayers.size()) {
            ItemStack nextPage = new ItemStack(Material.SUGAR_CANE);
            ItemMeta nextMeta = nextPage.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GREEN + "Next Page >");
            nextMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to go to the next page."));
            nextPage.setItemMeta(nextMeta);
            inv.setItem(8, nextPage);
        } else {
            inv.setItem(8, glass);
        }

        ItemStack searchItem = new ItemStack(Material.EMPTY_MAP);
        ItemMeta searchMeta = searchItem.getItemMeta();
        searchMeta.setDisplayName(ChatColor.BLUE + "Search");
        searchMeta.setLore(Arrays.asList(ChatColor.GRAY + "Use this function to search", ChatColor.GRAY + "players to report."));
        searchItem.setItemMeta(searchMeta);
        inv.setItem(4, searchItem);

        int begin = page * 45;
        int end = (page * 45) + 45;

        for (int i = begin; i < end; i++) {
            if (i - (45 * page) < matchingPlayers.size()) {
                Player loopPlayer = matchingPlayers.get((i - (page * 45)));
                ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skull.setDurability((short) 3);
                skullMeta.setOwner(loopPlayer.getName());
                skullMeta.setDisplayName(ChatColor.GOLD + "Report " + loopPlayer.getName());
                skullMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to report this player."));
                skull.setItemMeta(skullMeta);
                inv.setItem((i - (page * 45)) + 9, skull);
            }
        }

        return inv;
    }

    public static ReporterGUI get() {
        if (instance == null) {
            instance = new ReporterGUI();
        }

        return instance;
    }
}
