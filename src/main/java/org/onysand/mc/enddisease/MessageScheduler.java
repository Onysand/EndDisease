package org.onysand.mc.enddisease;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;
import java.util.Random;

public class MessageScheduler {

    private final Random random = new Random();
    public MessageScheduler(EndDisease plugin) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, bukkitTask -> {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        }, 0, 20L * 120L);
    }
}
