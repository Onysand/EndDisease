package org.onysand.mc.enddisease.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void OnPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (InfectionManager.isInfected(player.getUniqueId())) {
            player.sendMessage(EndDisease.getConfiguration().getString("messages.onLogin-disease"));
        }
    }
}
