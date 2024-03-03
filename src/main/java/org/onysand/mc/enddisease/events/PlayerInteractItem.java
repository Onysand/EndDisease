package org.onysand.mc.enddisease.events;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitScheduler;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;


public class PlayerInteractItem implements Listener {

    private final EndDisease plugin;
    private final BukkitScheduler scheduler;
    private final PluginConfig pluginConfig;
    private final MiniMessage mm = MiniMessage.miniMessage();


    public PlayerInteractItem() {
        this.plugin = EndDisease.getPlugin();
        this.pluginConfig = EndDisease.getPluginConfig();
        this.scheduler = Bukkit.getScheduler();
    }

    @EventHandler
    public void PlayerInteractItem (PlayerInteractEntityEvent e) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            if (e.getRightClicked() instanceof Player clickedPlayer && e.getHand() == EquipmentSlot.HAND) {
                Player player = e.getPlayer();

                if (pluginConfig.checkItemCustomModelIDList.contains(player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData())){

                    String message = InfectionManager.isInfected(clickedPlayer.getUniqueId())
                            ? pluginConfig.checkedPlayerIsInfectedMessage
                            : pluginConfig.checkedPlayerNotInfectedMessage;

                    player.sendMessage(mm.deserialize(message, Placeholder.parsed("player", clickedPlayer.getName())));
                }
            }
        });

    }
}
