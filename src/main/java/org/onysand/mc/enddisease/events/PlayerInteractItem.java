package org.onysand.mc.enddisease.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;


public class PlayerInteractItem implements Listener {
    @EventHandler
    public void PlayerInteractItem (PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player clickedPlayer && e.getHand() == EquipmentSlot.HAND) {
            Player player = e.getPlayer();

            if (EndDisease.getConfiguration().getIntegerList("utils.checkItem-customModel").contains(player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData())){

                String message = InfectionManager.isInfected(clickedPlayer.getUniqueId())
                        ? EndDisease.getConfiguration().getString("messages.checkedPlayer-isInfected")
                        : EndDisease.getConfiguration().getString("messages.checkedPlayer-notInfected");

                player.sendMessage(message.replace("{player}", clickedPlayer.getName()));
            }
        }
    }
}
