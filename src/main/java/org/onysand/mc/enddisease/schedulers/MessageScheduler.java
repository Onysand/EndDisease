package org.onysand.mc.enddisease.schedulers;


import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.MessageType;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.*;

public class MessageScheduler {
    public static void sendMessages() {
        Random random = EndDisease.getRandom();
        PluginConfig pluginConfig = EndDisease.getPluginConfig();

        Audience.audience(
                Bukkit.getOnlinePlayers().stream()
                        .filter(it -> it.hasPermission("disease.getscheduledmessage"))
                        .filter(it -> InfectionManager.isInfected(it.getUniqueId()))
                        .filter(it -> random.nextInt(100) < pluginConfig.getDiseaseMessageChance)
                        .toList()
        ).sendMessage(MiniMessage.miniMessage().deserialize(pluginConfig.diseaseMessage));
    }
}
