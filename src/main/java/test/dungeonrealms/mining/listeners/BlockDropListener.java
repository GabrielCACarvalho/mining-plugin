package test.dungeonrealms.mining.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

public class BlockDropListener implements Listener {

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent event) {
        var location = event.getBlock().getLocation();

        location.getBlock().setType(Material.STONE);
        
        event.getItems().forEach(Item::remove);
    }
}
