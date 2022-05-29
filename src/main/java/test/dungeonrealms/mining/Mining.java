package test.dungeonrealms.mining;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import test.dungeonrealms.mining.commads.CoalOreCommand;
import test.dungeonrealms.mining.commads.PickCommand;
import test.dungeonrealms.mining.listeners.BlockBreakListener;
import test.dungeonrealms.mining.listeners.BlockDropListener;

import java.util.Objects;

public final class Mining extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Initializating!");

        Objects.requireNonNull(getCommand("pick")).setExecutor(new PickCommand());
        Objects.requireNonNull(getCommand("coalore")).setExecutor(new CoalOreCommand());

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockDropListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Shutting down!");
    }
}
