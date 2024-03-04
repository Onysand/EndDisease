package org.onysand.mc.enddisease.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.MessageType;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.ArrayList;
import java.util.UUID;

public class ListInfectedCommand implements SubCommand {
    private final PluginConfig pluginConfig;
    public ListInfectedCommand(EndDisease plugin) {
        this.pluginConfig = plugin.getPluginConfig();
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Get list of all infected players.";
    }

    @Override
    public String getSyntax() {
        return "/disease list";
    }

    @Override
    public void perform(Player player, String[] args) {
        ArrayList<UUID> diseaseList = InfectionManager.getAllInfected();

        if (!(player.hasPermission("disease." + getName()))) {
            player.sendMessage(pluginConfig.getMessage(MessageType.noPermissionMessage, null));
            return;
        }

        if (diseaseList.isEmpty()) {
            player.sendMessage(pluginConfig.getMessage(MessageType.noOneInfectedMessage, null));
            return;
        }

        for (UUID uuid : diseaseList) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player.sendMessage(offlinePlayer.getName() != null ? offlinePlayer.getName() : uuid.toString());
        }
    }
}
