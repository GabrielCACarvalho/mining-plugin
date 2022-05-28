package test.dungeonrealms.mining;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import test.dungeonrealms.mining.commads.CoalOreCommand;
import test.dungeonrealms.mining.commads.PickCommand;
import test.dungeonrealms.mining.listeners.BlockBreakListener;

import java.util.Objects;

public final class Mining extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Initializating!");

        Objects.requireNonNull(getCommand("pick")).setExecutor(new PickCommand());
        Objects.requireNonNull(getCommand("coalore")).setExecutor(new CoalOreCommand());

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Shutting down!");
    }
}
