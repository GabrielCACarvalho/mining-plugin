package test.dungeonrealms.mining.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.dungeonrealms.mining.model.enums.Enchant;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler
    private void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        var item = blockBreakEvent.getPlayer().getInventory().getItemInMainHand();

        var block = blockBreakEvent.getBlock();

        if (!validPick(item)) return;

        var meta = item.getItemMeta();

        if (meta == null) return;

        if (block.getType().equals(Material.COAL_ORE)) {
            var random = new SecureRandom().nextInt(0, 10);

            if (random > 3) {
                blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(Material.COAL_ORE));

                meta.setLore(setNewLore(blockBreakEvent));
                item.setItemMeta(meta);

                item = upgradePick(item);

                blockBreakEvent.getPlayer().getInventory().setItemInMainHand(item);
            } else {
                blockBreakEvent.getPlayer().sendMessage("Failed to mine the ore!");
            }
        }
    }

    private List<String> setNewLore(BlockBreakEvent event) {
        var meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

        if (meta == null) return Collections.emptyList();

        var actualXp = getActualXp(meta);
        var xp = new SecureRandom().nextLong(70, 100);

        event.getPlayer().sendMessage("Successful ore mining! Exp: " + xp);

        var lore = meta.getLore();
        if (lore != null) {
            lore.remove(lore.size() - 1);
            lore.add(ChatColor.GRAY + "EXP: " + (actualXp + xp));
        }
        return lore;
    }

    private Integer getActualXp(ItemMeta meta) {
        var lore = meta.getLore();

        if (lore != null) {
            var exp = (lore.get(1).replace(ChatColor.GRAY + "EXP: ", ""));

            return Integer.parseInt(exp);
        } else {
            return 0;
        }
    }

    private ItemStack upgradePick(ItemStack item) {
        if (item.getItemMeta() == null) return null;

        int actualXp = getActualXp(item.getItemMeta());

        if (actualXp >= 1000 && item.getType().equals(Material.WOODEN_PICKAXE)) {
            var meta = item.getItemMeta();

            item = new ItemStack(Material.STONE_PICKAXE);

            var lore = meta.getLore();

            if (lore == null) return null;

            var random = new SecureRandom().nextInt(2, 6);
            var randomEnch =  Arrays.stream(Enchant.values()).findAny().get().getName();

            if (randomEnch.equals(Enchant.MINING_SUCCESS.getName())) {
                random += 5;
                lore.remove(0);
            }

            lore.add(0, ChatColor.GRAY + randomEnch + " " + random + "%");

            meta.setLore(lore);

            item.setItemMeta(meta);
        }

        if (actualXp >= 2000 && item.getType().equals(Material.STONE_PICKAXE)) {
            var meta = item.getItemMeta();

            item = new ItemStack(Material.IRON_PICKAXE);

            var lore = meta.getLore();

            if (lore == null) return null;

            var random = new SecureRandom().nextInt(3, 9);
            var randomEnch =  Arrays.stream(Enchant.values()).findAny().get().getName();

            if (lore.stream().anyMatch(l -> l.contains(randomEnch))) {
                String upgradedEnch = lore.stream().filter(l -> l.contains(randomEnch)).findFirst().get();
                lore.remove(upgradedEnch);
                lore.add(0, ChatColor.GRAY + randomEnch + " " + upgradedEnch.substring(upgradedEnch.length() - 3, upgradedEnch.length() -1) + "%");
            } else {
                lore.add(0, ChatColor.GRAY + randomEnch + " " + random + "%");
            }

            meta.setLore(lore);

            item.setItemMeta(meta);
        }

        return item;
    }

    private boolean validPick(ItemStack item) {
        return item.getType().equals(Material.WOODEN_PICKAXE)
                || item.getType().equals(Material.STONE_PICKAXE)
                || item.getType().equals(Material.IRON_PICKAXE)
                || item.getType().equals(Material.DIAMOND_PICKAXE)
                || item.getType().equals(Material.GOLDEN_PICKAXE);
    }
}
