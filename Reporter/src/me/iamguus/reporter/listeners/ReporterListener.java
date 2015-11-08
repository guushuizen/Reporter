package me.iamguus.reporter.listeners;

import me.iamguus.reporter.AnvilGUI;
import me.iamguus.reporter.Main;
import me.iamguus.reporter.gui.ReporterGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * Created by Guus on 7-11-2015.
 */
public class ReporterListener implements Listener {

    HashMap<String, String> reasons = new HashMap<String, String>();

    @EventHandler
    public void onInvClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getTitle().contains(ReporterGUI.get().getReporterGUI(1).getTitle())) {
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
                                            player.openInventory(ReporterGUI.get().getAgreeGUI(currentItem, event.getName()));
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));

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

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));

                        anvilGUI.open();
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
                                            player.openInventory(ReporterGUI.get().getAgreeGUI(currentItem, event.getName()));
                                        }
                                    }.runTaskLater(Main.getPlugin(), 1L);
                                }
                            }
                        });

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));

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

                        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.NAME_TAG));

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

                        }
                    }
                }
            }
        }
    }
}
