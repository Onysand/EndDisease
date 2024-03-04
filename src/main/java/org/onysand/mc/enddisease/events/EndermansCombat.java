package org.onysand.mc.enddisease.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.MessageType;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.Random;
import java.util.UUID;

public class EndermansCombat implements Listener {

    private final EndDisease plugin;
    private final PluginConfig pluginConfig;
    private final Random random;
    private final BukkitScheduler scheduler;

    public EndermansCombat(EndDisease plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
        this.random = plugin.getRandom();
        this.scheduler = Bukkit.getScheduler();

    }


    @EventHandler
    public void endermanCombatEvent(EntityDamageByEntityEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (!(e.getEntity() instanceof Player player)) return;
            if (player.isDead()) return;
            if (InfectionManager.isInfected(player.getUniqueId())) return;

            Entity damager = e.getDamager();
            if (!(damager.getType() == pluginConfig.getEntityInfectorType())) return;
            if (random.nextInt(100) >= pluginConfig.getInfectByHittingChance()) return;

            InfectionManager.addInfected(player.getUniqueId());

            if (player.hasPermission("disease.getInfected-message")) player.sendMessage(pluginConfig.getMessage(MessageType.infectedMessage, null));
        });
    }

    @EventHandler
    public void playerKillingEvent(EntityDamageByEntityEvent e) {
        scheduler.runTaskLaterAsynchronously(plugin, bukkitTask -> {

            Entity entity = e.getEntity();
            if (!(entity instanceof Player player)) return;
            if (!player.isDead()) return;

            Entity damager = e.getDamager();
            if (!(damager.getType() == pluginConfig.getEntityInfectorType())) return;

            UUID uuid = player.getUniqueId();
            if (InfectionManager.isInfected(uuid)) return;
            if (random.nextInt(100) >= pluginConfig.getInfectByDeathChance()) return;

            InfectionManager.addInfected(player.getUniqueId());
            if (player.hasPermission("disease.getInfected-message")) player.sendMessage(pluginConfig.getMessage(MessageType.infectedMessage, null));

        }, 2L);
    }
}
