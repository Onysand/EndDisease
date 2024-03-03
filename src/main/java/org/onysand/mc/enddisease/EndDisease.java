package org.onysand.mc.enddisease;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.onysand.mc.enddisease.commands.CommandManager;
import org.onysand.mc.enddisease.events.EndermansCombat;
import org.onysand.mc.enddisease.events.OnPlayerJoin;
import org.onysand.mc.enddisease.events.PlayerInteractItem;
import org.onysand.mc.enddisease.utils.CheckInfected;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.Messages;

import java.io.IOException;
import java.util.Random;

public final class EndDisease extends JavaPlugin {

    private static EndDisease plugin;
    private Messages messages;
    private Random random;


    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        CommandManager commandManager = new CommandManager();
        this.getCommand("disease").setExecutor(commandManager);
        this.getCommand("disease").setTabCompleter(commandManager);
        this.messages = new Messages(this);
        //init Utils

        getServer().getPluginManager().registerEvents(new EndermansCombat(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractItem(), this);

        try {
            InfectionManager.loadInfected();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getServer().getScheduler().runTaskTimerAsynchronously(this, CheckInfected::checkInfected, 0L, getConfiguration().getLong("utils.infect-check-delay"));
    }

    @Override
    public void onDisable() {
        try {
            InfectionManager.saveInfected();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Random getRandom() {
        return random;
    }

    public Messages getMessages() {
        return messages;
    }

    public static EndDisease getPlugin() { return plugin; }

    public static FileConfiguration getConfiguration() {
        return getPlugin().getConfig();
    }
}
