package org.onysand.mc.enddisease.events;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;

public class EndermansCombat implements Listener {

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
    public void playerKillingEvent(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        if (player.getLastDamageCause().getDamageSource().getCausingEntity().getType() == EntityType.ENDERMAN && !InfectionManager.isInfected(player.getUniqueId())) {
            if (Math.random() * 100 < EndDisease.getConfiguration().getDouble("chances.infect-by-death")) {
                InfectionManager.addInfected(player.getUniqueId());
                player.sendMessage(EndDisease.getConfiguration().getString("messages.infected-by-endermanKilling"));
            }
        }
    }
}
