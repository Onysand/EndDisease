package org.onysand.mc.enddisease.utils;


import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.onysand.mc.enddisease.EndDisease;

import java.util.*;

public class MessageScheduler {
    public static void sendMessages() {
        Random random = EndDisease.getRandom();
        PluginConfig pluginConfig = EndDisease.getPluginConfig();

        Audience.audience(
                Bukkit.getOnlinePlayers().stream()
                    .filter(it -> InfectionManager.isInfected(it.getUniqueId()))
                    .filter(it -> random.nextInt(100) < pluginConfig.getDiseaseMessageChance)
                    .toList()
        ).sendMessage(MiniMessage.miniMessage().deserialize(pluginConfig.diseaseMessage));
    }
}
