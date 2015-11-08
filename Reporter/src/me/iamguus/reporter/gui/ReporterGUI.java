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
        int size = Bukkit.getOnlinePlayers().size() - (45 * page);

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

        Inventory inv = Bukkit.createInventory(null, invSize, (page == 1) ? "Report a player!" : "Report a player! (Page " + page + ")");

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
        List<Player> matchingPlayers = new ArrayList<Player>();
        for (Player loop : Bukkit.getOnlinePlayers()) {
            if (loop.getName().contains(search)) {
                matchingPlayers.add(loop);
            }
        }

        int size = matchingPlayers.size() - (page * 45);

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

        if (page * 45 < matchingPlayers.size()) {
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

    public Inventory getAgreeGUI(ItemStack skull, String reason) {
        Inventory inv = Bukkit.createInventory(null, 54, "Are you sure?");

        ItemStack backgroundGlass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = backgroundGlass.getItemMeta();
        backgroundGlass.setDurability((short) 3);
        backgroundMeta.setDisplayName(" ");
        backgroundGlass.setItemMeta(backgroundMeta);

        for (int i = 0; i < 4; i++) {
            inv.setItem(i, backgroundGlass);
        }

        for (int i = 5; i < 9; i++) {
            inv.setItem(i, backgroundGlass);
        }

        inv.setItem(4, skull);

        ItemStack green = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta greenMeta = green.getItemMeta();
        green.setDurability((short) 5);
        greenMeta.setDisplayName(ChatColor.GREEN + "Yes!");
        greenMeta.setLore(Arrays.asList(ChatColor.GRAY + "Yes, I do want to report", ChatColor.GRAY + ((SkullMeta)skull.getItemMeta()).getOwner() + " for " + reason));
        green.setItemMeta(greenMeta);

        inv.setItem(18, green);
        inv.setItem(27, green);
        inv.setItem(36, green);
        inv.setItem(45, green);
        inv.setItem(19, green);
        inv.setItem(28, green);
        inv.setItem(37, green);
        inv.setItem(46, green);
        inv.setItem(20, green);
        inv.setItem(29, green);
        inv.setItem(38, green);
        inv.setItem(47, green);
        inv.setItem(21, green);
        inv.setItem(30, green);
        inv.setItem(39, green);
        inv.setItem(48, green);

        ItemStack red = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta redMeta = green.getItemMeta();
        red.setDurability((short) 14);
        redMeta.setDisplayName(ChatColor.RED + "No!");
        redMeta.setLore(Arrays.asList(ChatColor.GRAY + "No, I don't want to report", ChatColor.GRAY + ((SkullMeta)skull.getItemMeta()).getOwner() + " for " + reason));
        red.setItemMeta(redMeta);

        inv.setItem(23, red);
        inv.setItem(32, red);
        inv.setItem(41, red);
        inv.setItem(50, red);
        inv.setItem(24, red);
        inv.setItem(33, red);
        inv.setItem(42, red);
        inv.setItem(51, red);
        inv.setItem(25, red);
        inv.setItem(34, red);
        inv.setItem(43, red);
        inv.setItem(52, red);
        inv.setItem(26, red);
        inv.setItem(35, red);
        inv.setItem(44, red);
        inv.setItem(53, red);

        return inv;
    }

    public static ReporterGUI get() {
        if (instance == null) {
            instance = new ReporterGUI();
        }

        return instance;
    }
}
