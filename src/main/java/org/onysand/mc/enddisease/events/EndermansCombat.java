package org.onysand.mc.enddisease.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.Random;
import java.util.UUID;

public class EndermansCombat implements Listener {

    private final EndDisease plugin;
    private final PluginConfig pluginConfig;
    private final Random random;
    private final BukkitScheduler scheduler;
    private final EntityType infectorEntityType;

    public EndermansCombat() {
        this.plugin = EndDisease.getPlugin();
        this.pluginConfig = EndDisease.getPluginConfig();
        this.random = EndDisease.getRandom();
        this.scheduler = Bukkit.getScheduler();

    }


    @EventHandler
    public void endermanCombatEvent(EntityDamageByEntityEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (!(e.getEntity() instanceof Player player)) return;
            if (player.isDead()) return;
            if (InfectionManager.isInfected(player.getUniqueId())) return;

            Entity damager = e.getDamager();
            if (!(damager.getType() == infectorEntityType)) return;
            if (random.nextInt(100) >= pluginConfig.infectByHittingChance) return;

            InfectionManager.addInfected(player.getUniqueId());

            if (!(player.hasPermission("disease.")))
            player.sendMessage(pluginConfig.infectedMessage);
        });
    }

    @EventHandler
    public void playerKillingEvent(EntityDamageByEntityEvent e) {
        scheduler.runTaskLaterAsynchronously(plugin, bukkitTask -> {

            Entity entity = e.getEntity();
            if (!(entity instanceof Player player)) return;
            if (!player.isDead()) return;

            Entity damager = e.getDamager();
            if (!(damager.getType() == infectorEntityType)) return;

            UUID uuid = player.getUniqueId();
            if (InfectionManager.isInfected(uuid)) return;
            if (random.nextInt(100) >= pluginConfig.infectByDeathChance) return;

            InfectionManager.addInfected(player.getUniqueId());
            player.sendMessage(pluginConfig.infectedMessage);

        }, 2L);
    }
}
