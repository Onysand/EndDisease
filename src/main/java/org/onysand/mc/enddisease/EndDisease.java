package org.onysand.mc.enddisease;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.onysand.mc.enddisease.commands.CommandManager;
import org.onysand.mc.enddisease.events.EndermansCombat;
import org.onysand.mc.enddisease.events.PlayerInteractItem;
import org.onysand.mc.enddisease.utils.*;

import java.io.IOException;
import java.util.Random;

public final class EndDisease extends JavaPlugin {
    private static EndDisease plugin;
    private PluginConfig pluginConfig;
    private Random random;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        this.pluginConfig = new PluginConfig(this);
        this.random = new Random();

        CommandManager commandManager = new CommandManager();
        this.getCommand("disease").setExecutor(commandManager);
        this.getCommand("disease").setTabCompleter(commandManager);

        getServer().getPluginManager().registerEvents(new EndermansCombat(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractItem(), this);

        try {
            InfectionManager.loadInfected();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getServer().getScheduler().runTaskTimerAsynchronously(this, MessageScheduler::sendMessages, 0, 20 * 120L);
        getServer().getScheduler().runTaskTimerAsynchronously(this, CheckInfected::checkInfected, 0L, (long) pluginConfig.infectCheckDelay);
    }

    @Override
    public void onDisable() {
        try {
            InfectionManager.saveInfected();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Random getRandom() {
        return getPlugin().random;
    }

    public static PluginConfig getPluginConfig() {
        return getPlugin().pluginConfig;
    }

    public static EndDisease getPlugin() { return plugin; }

    public static FileConfiguration getConfiguration() {
        return getPlugin().getConfig();
    }
}
