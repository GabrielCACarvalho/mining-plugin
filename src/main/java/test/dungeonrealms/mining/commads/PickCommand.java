package test.dungeonrealms.mining.commads;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            var pick = new ItemStack(Material.WOODEN_PICKAXE);

            var itemMeta = pick.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "Noobie Pickaxe");
                var lore = new ArrayList<String>();

                lore.add(ChatColor.GRAY + "MINING SUCCESS 5%");
                lore.add(ChatColor.GRAY + "EXP: 0");

                itemMeta.setLore(lore);
            }

            pick.setItemMeta(itemMeta);

            player.getInventory().addItem(pick);
        }

        return true;
    }
}
