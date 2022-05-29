package test.dungeonrealms.mining.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.dungeonrealms.mining.model.Pick;
import test.dungeonrealms.mining.model.PickEnchant;
import test.dungeonrealms.mining.model.enums.Enchant;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockBreakListener implements Listener {

    private final Pick pick = new Pick();

    @EventHandler
    private void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        var item = blockBreakEvent.getPlayer().getInventory().getItemInMainHand();

        var block = blockBreakEvent.getBlock();

        if (!validPick(item)) return;

        var meta = item.getItemMeta();

        if (meta == null) return;

        pick.setLevel(getPickLevel(meta.getLore()));
        pick.setTier(getPickTier(pick.getLevel()));
        pick.setName(getPickName(pick.getTier()));
        pick.setNeededXp(getNeededXp(pick.getLevel()));
        pick.setXp(getActualXp(meta));
        pick.setPickEnchs(getPickEnchs(meta.getLore()));

        Bukkit.getLogger().info("Pick name: " + pick.getName());
        Bukkit.getLogger().info("Pick level: " + pick.getLevel());
        Bukkit.getLogger().info("Pick tier: " + pick.getTier());
        Bukkit.getLogger().info("Pick XP: " + pick.getXp());
        Bukkit.getLogger().info("Pick Needed XP: " + pick.getNeededXp());

        if (block.getType().equals(Material.COAL_ORE)) {
            var random = new SecureRandom().nextInt(0, 10);

            if (pick.getLevel() < 20) {
                if (random > 3) {
                    mineOre(blockBreakEvent, meta, item);
                } else {
                    blockBreakEvent.getPlayer().sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "You fail to gather any ore.");
                }
            } else {
                mineOre(blockBreakEvent, meta, item);
            }
        }
    }

    private void mineOre(BlockBreakEvent event, ItemMeta meta, ItemStack item) {
        event.getPlayer().getInventory().addItem(new ItemStack(Material.COAL_ORE));

        meta.setLore(setNewLore(event));
        item.setItemMeta(meta);

        item = levelUpPick(item);

        item = upgradePick(item);

        event.getPlayer().getInventory().setItemInMainHand(item);
    }

    private ItemStack levelUpPick(ItemStack item) {
        var meta = item.getItemMeta();

        if (getActualXp(meta) > pick.getNeededXp()) {
            pick.setXp(0);
            pick.setLevel(pick.getLevel() + 1);
            pick.setNeededXp(getNeededXp(pick.getLevel()));
        }

        var lore = meta.getLore();
        if (lore != null) {
            lore.remove(0);
            lore.add(0, ChatColor.GRAY + "Level: " + ChatColor.WHITE + pick.getLevel());
            lore.remove(1);
            lore.add(1, ChatColor.GRAY + "XP: " + pick.getXp() + " / " + pick.getNeededXp());
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private List<String> setNewLore(BlockBreakEvent event) {
        var meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

        if (meta == null) return Collections.emptyList();
        var xp = new SecureRandom().nextInt(70, 130);

        pick.setXp(pick.getXp() + xp);

        event.getPlayer().sendMessage(ChatColor.YELLOW.toString() + xp + " EXP " + ChatColor.GRAY + "[" + pick.getXp() + "/" + pick.getNeededXp() + " EXP]");

        var lore = meta.getLore();
        if (lore != null) {
            lore.remove(1);
            lore.add(1, ChatColor.GRAY + "XP: " + pick.getXp() + " / " + pick.getNeededXp());
        }
        return lore;
    }

    private Integer getActualXp(ItemMeta meta) {
        var lore = meta.getLore();

        if (lore != null) {
            var xp = lore.get(1).replace(ChatColor.GRAY + "XP: ", "").replace(" / " + pick.getNeededXp(), "");

            return Integer.parseInt(xp);
        } else {
            return 0;
        }
    }

    private ItemStack upgradePick(ItemStack item) {
        if (item.getItemMeta() == null) return null;

        if (pick.getLevel() < 20)
            return item;

        if ((pick.getLevel() > 19 && pick.getLevel() < 40) && item.getType().equals(Material.WOODEN_PICKAXE)) {
            var meta = item.getItemMeta();

            item = new ItemStack(Material.STONE_PICKAXE);

            meta.setDisplayName(ChatColor.WHITE + "Stone Pickaxe");

            var lore = meta.getLore();

            if (lore == null) return null;

            var random = new SecureRandom().nextInt(2, 6);

            var randomEnch =  Arrays.stream(Enchant.values()).findAny();

            if (randomEnch.isEmpty()) return item;

            var pickEnchs = pick.getPickEnchs();

            pickEnchs.add(new PickEnchant(randomEnch.get(), random, false));

            lore.add(2,
                    ChatColor.RED
                    + randomEnch.get().getName()
                    + ": "
                    + "+"
                    + random
                    + "%");

            meta.setLore(lore);

            item.setItemMeta(meta);
        }

        if (pick.getLevel() > 39 && pick.getLevel() < 60 && item.getType().equals(Material.STONE_PICKAXE)) {
            var meta = item.getItemMeta();

            item = new ItemStack(Material.IRON_PICKAXE);

            meta.setDisplayName(ChatColor.WHITE + "Iron Pickaxe");

            var lore = meta.getLore();

            if (lore == null) return null;

            var random = new SecureRandom().nextInt(3, 9);
            var randomEnch =  Arrays.stream(Enchant.values()).findAny();

            if (randomEnch.isEmpty()) return item;

            var sameEnch = pick.getPickEnchs().stream().filter(pickEnchant -> randomEnch.get() == pickEnchant.getEnchant()).findFirst();

            if (sameEnch.isPresent()) {
                var enchLine = lore.stream().filter(line -> line.contains(sameEnch.get().getEnchant().getName())).findFirst();

                if (enchLine.isPresent()) {
                    var index = lore.indexOf(enchLine.get());

                    lore.remove(index);
                    lore.add(index, ChatColor.RED
                            + randomEnch.get().getName()
                            + ": "
                            + "+"
                            + (random + sameEnch.get().getPercentage())
                            + "%");
                }
            } else {
                lore.add(2,
                        ChatColor.RED
                                + randomEnch.get().getName()
                                + ": "
                                + "+"
                                + random
                                + "%");
            }

            meta.setLore(lore);

            item.setItemMeta(meta);
        }

        return item;
    }

    private Integer getPickLevel(List<String> lore) {
        var level = lore.stream().filter(line -> line.contains("Level")).findFirst();

        return level.map(line -> Integer.valueOf(line.replace(ChatColor.GRAY + "Level: " + ChatColor.WHITE, ""))).orElse(1);
    }

    private Integer getPickTier(Integer level) {
        if (level < 20)
            return 1;
        else if (level < 40)
            return 2;
        else if (level < 60)
            return 3;
        else if (level < 80)
            return 4;
        else
            return 5;
    }

    private String getPickName(Integer tier) {
        return  switch (tier) {
            case 1 -> "Novice Pickaxe";
            case 2 -> "Stone Pickaxe";
            case 3 -> "Iron Pickaxe";
            case 4 -> "Diamond Pickaxe";
            case 5 -> "Gold Pickaxe";
            default -> "";
        };
    }

    private Integer getNeededXp(Integer level) {
        Integer EXP_CONSTANT = 10;
        return level * EXP_CONSTANT;
    }

    private List<PickEnchant> getPickEnchs(List<String> lore) {
        var enchs = lore.stream().filter(line -> (line.contains("%") && !line.contains("Passive"))).toList();

        List<PickEnchant> pickEnchants = new ArrayList<>();

        if (!enchs.isEmpty()) {
            pickEnchants = enchs.stream().map(ench -> {

                Bukkit.getLogger().info(ench);

                var enchant = Arrays.stream(Enchant.values())
                        .filter(eachEnch -> ench.contains(eachEnch.getName()))
                        .findFirst();
                var percentage = 0;

                if (enchant.isPresent()) {
                    percentage = Integer.parseInt(ench.replace(ChatColor.RED + enchant.get().getName() + ": +", "").replace("%", ""));
                }

                Integer finalPercentage = percentage;

                if (enchant.isEmpty()) return null;

                return new PickEnchant(enchant.get(), finalPercentage, false);
            }).toList();
        }

        pickEnchants.add(new PickEnchant(Enchant.MINING_SUCCESS, 5, true));

        return pickEnchants;
    }

    private boolean validPick(ItemStack item) {
        return item.getType().equals(Material.WOODEN_PICKAXE)
                || item.getType().equals(Material.STONE_PICKAXE)
                || item.getType().equals(Material.IRON_PICKAXE)
                || item.getType().equals(Material.DIAMOND_PICKAXE)
                || item.getType().equals(Material.GOLDEN_PICKAXE);
    }
}
