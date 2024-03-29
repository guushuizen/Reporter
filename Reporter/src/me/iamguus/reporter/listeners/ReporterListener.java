package me.iamguus.reporter.listeners;

import me.iamguus.reporter.AnvilGUI;
import me.iamguus.reporter.Main;
import me.iamguus.reporter.data.Report;
import me.iamguus.reporter.data.SettingsManager;
import me.iamguus.reporter.gui.ReporterGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Guus on 7-11-2015.
 */
public class ReporterListener implements Listener {

    HashMap<UUID, String> reports = new HashMap<UUID, String>();
    HashMap<String, String> reasons = new HashMap<String, String>();

    SettingsManager settingsManager = SettingsManager.getInstance();

    @EventHandler
    public void onInvClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getTitle().contains("Report a player!")) {
                event.setCancelled(true);
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                    final ItemStack currentItem = event.getCurrentItem();
                    if (currentItem.getType() == Material.SKULL_ITEM) {
                        SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                            @Override
                            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                                if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    event.setWillClose(true);
                                    event.setWillDestroy(true);

                                    new BukkitRunnable() {
                                        public void run() {
                                            reasons.put(skullMeta.getOwner(), event.getName());
                                            reports.put(player.getUniqueId(), skullMeta.getOwner());
                                            player.openInventory(ReporterGUI.get().getAgreeGUI(currentItem, event.getName()));
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItemStackWithName(Material.NAME_TAG, "Why ban " + skullMeta.getOwner() + "?"));

                        anvilGUI.open();
                    } else
                    if (currentItem.getType() == Material.EMPTY_MAP) {
                        player.closeInventory();

                        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                            @Override
                            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                                if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    event.setWillClose(true);
                                    event.setWillDestroy(true);

                                    new BukkitRunnable() {
                                        public void run() {
                                            player.openInventory(ReporterGUI.get().getSearchPlayersGUI(event.getName(), 1));
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItemStackWithName(Material.NAME_TAG, "Who are you searching?"));

                        anvilGUI.open();
                    } else
                    if (currentItem.getType() == Material.SUGAR_CANE) {
                        int page = Integer.parseInt(event.getInventory().getTitle().split("#")[1].split("\\)")[0]) - 1;
                        if (currentItem.getItemMeta().getDisplayName().contains("Next")) {
                            player.closeInventory();
                            new BukkitRunnable() {
                                public void run() {
                                    player.openInventory(ReporterGUI.get().getReporterGUI(page + 1));
                                }
                            }.runTaskLater(Main.getPlugin(), 1L);
                        } else
                        if (currentItem.getItemMeta().getDisplayName().contains("Previous")) {
                            player.closeInventory();
                            new BukkitRunnable() {
                                public void run() {
                                    player.openInventory(ReporterGUI.get().getReporterGUI(page - 1));
                                }
                            }.runTaskLater(Main.getPlugin(), 1L);
                        }
                    }
                }
            } else
            if (event.getInventory().getTitle().contains(ReporterGUI.get().getSearchPlayersGUI(" ", 1).getTitle())) {
                event.setCancelled(true);
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem.getType() == Material.SKULL_ITEM) {
                        SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                            @Override
                            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                                if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    event.setWillClose(true);
                                    event.setWillDestroy(true);

                                    new BukkitRunnable() {
                                        public void run() {
                                            reasons.put(skullMeta.getOwner(), event.getName());
                                            reports.put(player.getUniqueId(), skullMeta.getOwner());
                                            skullMeta.setLore(null);
                                            currentItem.setItemMeta(skullMeta);
                                            player.openInventory(ReporterGUI.get().getAgreeGUI(currentItem, event.getName()));
                                            player.closeInventory();
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItemStackWithName(Material.NAME_TAG, "Why report " + skullMeta.getOwner() + "?"));

                        anvilGUI.open();
                    } else
                    if (currentItem.getType() == Material.EMPTY_MAP) {
                        player.closeInventory();

                        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                            @Override
                            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                                if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                                    event.setWillClose(true);
                                    event.setWillDestroy(true);

                                    new BukkitRunnable() {
                                        public void run() {
                                            player.openInventory(ReporterGUI.get().getSearchPlayersGUI(event.getName(), 1));
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItemStackWithName(Material.NAME_TAG, "Who are you searching?"));

                        anvilGUI.open();
                    }
                }
            } else
            if (event.getInventory().getTitle().contains("Are you sure?")) {
                event.setCancelled(true);
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem.getType() == Material.STAINED_GLASS_PANE) {
                        if (currentItem.getDurability() == (short) 5) { // YES
                            player.closeInventory();
                            String toReport = reports.get(player.getUniqueId());
                            String reason = reasons.get(toReport);
                            Player playerReport = Bukkit.getPlayer(toReport);
                            Report report = new Report(playerReport.getUniqueId(), player.getUniqueId(), reason);
                            report.saveToConfig();
                            //Send to staff
                            for (Player loop : Bukkit.getOnlinePlayers()) {
                                if (player.hasPermission(settingsManager.getConfig().getString("permissions.receive-reports"))) {
                                    String jsonToSend = "tellraw " + loop.getName() +" [\"\",{\"text\":\"" + player.getName() + " reported " + playerReport.getName() + "!\",\"color\":\"green\"},{\"text\":\" Click here to view the report!\",\"color\":\"gold\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/viewreport " + report.getID() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to review the report!\"}]}}}]";
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), jsonToSend);
                                }
                            }
                        } else
                        if (currentItem.getDurability() == (short) 14) {
                            player.closeInventory();
                            reasons.remove(reports.get(player.getUniqueId()));
                            reports.remove(player.getUniqueId());
                        }
                    }
                }
            }
        }
    }

    private ItemStack createItemStackWithName(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }
}
