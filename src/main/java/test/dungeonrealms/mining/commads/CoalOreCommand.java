package test.dungeonrealms.mining.commads;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CoalOreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            var coalOre = new ItemStack(Material.COAL_ORE, 64);

            player.getInventory().addItem(coalOre);
        }

        return true;
    }
}
