package me.iamguus.reporter.listeners;

import me.iamguus.reporter.AnvilGUI;
import me.iamguus.reporter.Main;
import me.iamguus.reporter.data.Report;
import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.gui.ReportsGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Guus on 8-11-2015.
 */
public class ReportsListener implements Listener {

    SettingsManager settingsManager = SettingsManager.getInstance();

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getTitle().equalsIgnoreCase(ReportsGUI.get().getReportList(1).getTitle())) {
                event.setCancelled(true);
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem.getType() == Material.SKULL_ITEM) {
                        SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                        int ID = Integer.parseInt(skullMeta.getDisplayName().split("#")[1].split(":")[0]);
                        Report report = Report.loadReportFromConfig(settingsManager.getReports().getConfigurationSection("reports." + ID));
                        player.closeInventory();
                        new BukkitRunnable() {
                            public void run() {
                                player.openInventory(ReportsGUI.get().getViewReport(report));
                            }
                        }.runTaskLater(Main.getPlugin(), 1L);
                    } else
                    if (currentItem.getType() == Material.EMPTY_MAP) {
                        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                            @Override
                            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                                if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    String output = event.getName();
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
