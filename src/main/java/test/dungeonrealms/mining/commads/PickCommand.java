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

        var enchs = List.of(new PickEnchant(Enchant.MINING_SUCCESS, 5));

        pick.setName("Tier 1");
        pick.setTier(1);
        pick.setXp(0);
        pick.setPickEnchs(enchs);

        if (commandSender instanceof Player player) {
            var woodenPick = new ItemStack(Material.WOODEN_PICKAXE);

            var itemMeta = woodenPick.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + pick.getName());
                var lore = new ArrayList<String>();

                pick.getPickEnchs().forEach(ench -> lore.add(ench.getEnchant().getName() + " " + ench.getPercentage() + "%"));

                lore.add(ChatColor.GRAY + "EXP: " + pick.getXp());

                itemMeta.setLore(lore);
            }

            woodenPick.setItemMeta(itemMeta);

            player.getInventory().addItem(woodenPick);
        }

        return true;
    }
}
