package org.onysand.mc.enddisease.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;

import java.util.*;

public class CheckInfected {
    public static void checkInfected() {
        ArrayList<UUID> listInfected = InfectionManager.getAllInfected();
        final PluginConfig pluginConfig = EndDisease.getPluginConfig();
        final double distanceSquared = Math.pow(pluginConfig.infectRadius, 2);

        for (UUID playerID : listInfected) {
            Player infector = Bukkit.getPlayer(playerID);

            if (infector == null) continue;
            if (InfectionManager.isProtectedFromDisease(infector)) continue;

            Location infectorLocation = infector.getLocation();

            infector.getWorld().getPlayers().stream()
                    .filter(target -> target.getLocation().distanceSquared(infectorLocation) < distanceSquared)
                    .filter(target -> !InfectionManager.isInfected(target.getUniqueId()))
                    .filter(target -> !InfectionManager.isProtectedFromDisease(target))
                    .filter(target -> Math.random() * 100 < pluginConfig.infectByPlayerChance)
                    .forEach(p -> {
                        InfectionManager.addInfected(p.getUniqueId());
                        p.sendMessage(pluginConfig.infectedMessage);
                    });
        }
    }
}
