package org.onysand.mc.enddisease.events;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;

public class EndermansCombat implements Listener {

    private final EndDisease plugin;
    private final Messages messages;
    private final Random random;
    private final BukkitScheduler scheduler;
    public EndermansCombat(EndDisease plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.random = new Random();
        this.scheduler = Bukkit.getScheduler();

    }


    @EventHandler
    public void endermanCombatEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player player && !InfectionManager.isInfected(player.getUniqueId()) && e.getDamager().getType() == EntityType.ENDERMAN) {
            if (Math.random() * 100 < EndDisease.getConfiguration().getDouble("chances.infect-by-hitting")) {
                InfectionManager.addInfected(player.getUniqueId());
                player.sendMessage(EndDisease.getConfiguration().getString("messages.infected-by-endermanHit"));
            }
        }
    }

    @EventHandler
    public void playerKillingEvent(EntityDamageByEntityEvent e) {
        scheduler.runTaskLaterAsynchronously(plugin, bukkitTask -> {

            Entity entity = e.getEntity();
            if (!(entity instanceof Player player)) return;
            if (!player.isDead()) return;

            Entity damager = e.getDamager();
            if (!(damager instanceof Enderman)) return;

            UUID uuid = player.getUniqueId();
            if (InfectionManager.isInfected(uuid)) return;
            if (Math.random() * 100 <= EndDisease.getConfiguration().getDouble("chances.infect-by-death")) return;

            InfectionManager.addInfected(player.getUniqueId());
            player.sendMessage(EndDisease.getConfiguration().getString("messages.infected-by-endermanKilling", "check config: messages.infected-by-endermanKilling"));

        }, 2L);
    }
}
