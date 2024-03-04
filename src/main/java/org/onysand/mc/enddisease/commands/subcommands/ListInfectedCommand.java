package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListInfectedCommand implements SubCommand {
    private final PluginConfig pluginConfig = EndDisease.getPlugin().getPluginConfig();

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
            player.sendMessage(MiniMessage.miniMessage().deserialize(pluginConfig.noPermissionMessage, Placeholder.parsed("command", getName())));
            return;
        }

        if (diseaseList.isEmpty()) {
            player.sendMessage(pluginConfig.noOneInfectedMessage);
            return;
        }

        for (UUID uuid : diseaseList) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player.sendMessage(offlinePlayer.getName() != null ? offlinePlayer.getName() : uuid.toString());
        }
    }
}
