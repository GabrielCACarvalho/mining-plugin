package test.dungeonrealms.mining.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.SecureRandom;
import java.util.ArrayList;

public class BlockBreakListener implements Listener {

    @EventHandler
    private void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        var item = blockBreakEvent.getPlayer().getInventory().getItemInMainHand();

        var block = blockBreakEvent.getBlock();
        if (item.getType().equals(Material.WOODEN_PICKAXE)) {
            var meta = item.getItemMeta();

            if (
                    meta != null
                    && meta.getDisplayName().equals(ChatColor.GRAY.toString() + ChatColor.BOLD + "Noobie Pickaxe")
                    && block.getType().equals(Material.COAL_ORE)
            ) {
                var actualXp = getActualXp(meta);

                meta.setLore(setNewLore(actualXp));
                item.setItemMeta(meta);

                blockBreakEvent.getPlayer().getInventory().setItemInMainHand(item);
            }
        }
    }

    private ArrayList<String> setNewLore(Long actualXp) {
        var xp = new SecureRandom().nextLong(70, 100);

        var lore = new ArrayList<String>();

        lore.add(ChatColor.GRAY + "MINING SUCCESS 5%");
        lore.add(ChatColor.GRAY + "EXP: " + (actualXp + xp));

        return lore;
    }

    private Long getActualXp(ItemMeta meta) {
        var lore = meta.getLore();

        if (lore != null) {
            var exp = (lore.get(1).replace(ChatColor.GRAY + "EXP: ", ""));

            Bukkit.getLogger().info(exp);
            return Long.parseLong(exp);
        } else {
            return 0L;
        }
    }
}
