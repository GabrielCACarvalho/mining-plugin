package test.dungeonrealms.mining.commads;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import test.dungeonrealms.mining.model.Pick;
import test.dungeonrealms.mining.model.PickEnchant;
import test.dungeonrealms.mining.model.enums.Enchant;

import java.util.ArrayList;
import java.util.List;

public class PickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        var pick = new Pick();

        var enchs = List.of(new PickEnchant(Enchant.MINING_SUCCESS, 5, true));

        pick.setName("Novice Pickaxe");
        pick.setTier(1);
        pick.setLevel(1);
        pick.setXp(0);
        pick.setNeededXp(10);
        pick.setPickEnchs(enchs);

        if (commandSender instanceof Player player) {
            var woodenPick = new ItemStack(Material.WOODEN_PICKAXE);

            var itemMeta = woodenPick.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(ChatColor.WHITE + pick.getName());
                var lore = new ArrayList<String>();

                lore.add(ChatColor.GRAY + "Level: " + ChatColor.WHITE + pick.getLevel());
                lore.add(ChatColor.GRAY + "XP: " + pick.getXp() + " / " + pick.getNeededXp());
                lore.add(
                        ChatColor.GRAY
                                + ChatColor.BOLD.toString()
                                + "Passive: "
                                + ChatColor.RED
                                + pick.getPickEnchs().get(0).getEnchant().getName()
                                + ": "
                                + "+"
                                + pick.getPickEnchs().get(0).getPercentage()
                                + "%"
                );
                lore.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "A pickaxe made of sturdy wood.");

                itemMeta.setLore(lore);
            }

            woodenPick.setItemMeta(itemMeta);

            player.getInventory().addItem(woodenPick);
        }

        return true;
    }
}
