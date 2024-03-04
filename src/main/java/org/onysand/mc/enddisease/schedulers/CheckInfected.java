package org.onysand.mc.enddisease.schedulers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.MessageType;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.*;

public class CheckInfected {
    private EndDisease plugin;
    private PluginConfig pluginConfig;
    private BukkitScheduler scheduler;
    private Random random;
    private double distanceSquared;

    public CheckInfected(EndDisease plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
        this.scheduler = Bukkit.getScheduler();
        this.distanceSquared = Math.pow(pluginConfig.getInfectRadius(), 2);
        this.random = plugin.getRandom();

        start();
    }

    public void start() {
        ArrayList<UUID> listInfected = InfectionManager.getAllInfected();

        for (UUID playerID : listInfected) {
            Player infector = Bukkit.getPlayer(playerID);

            if (infector == null) continue;
            if (InfectionManager.isProtectedFromDisease(infector)) continue;

            for (Player target : infector.getWorld().getPlayers()) {
                UUID targetUUID = target.getUniqueId();

                if (target.getLocation().distanceSquared(infector.getLocation()) > distanceSquared) continue;
                if (InfectionManager.isInfected(targetUUID)) continue;
                if (InfectionManager.isProtectedFromDisease(target)) continue;
                if (!(random.nextInt(100) > pluginConfig.getInfectByPlayerChance())) continue;

                InfectionManager.addInfected(targetUUID);
                if (target.hasPermission("disease.getInfected-message")) target.sendMessage(pluginConfig.getMessage(MessageType.infectedMessage, infector.getName()));
            }
        }
    }
}
