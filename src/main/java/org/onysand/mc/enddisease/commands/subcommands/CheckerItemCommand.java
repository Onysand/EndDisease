package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.PluginConfig;
import org.onysand.mc.enddisease.utils.Utils;

import java.util.function.Consumer;

public class CheckerItemCommand implements SubCommand {
    PluginConfig pluginConfig = EndDisease.getPluginConfig();

    @Override
    public String getName() {
        return "checkeritem";
    }

    @Override
    public String getDescription() {
        return "Gives you disease checker item";
    }

    @Override
    public String getSyntax() {
        return "/disease checkeritem";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!(player.hasPermission("disease." + getName()))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(pluginConfig.noPermissionMessage, Placeholder.parsed("command", getName())));
            return;
        }

        player.getInventory().addItem(Utils.getCheckItem().clone());
    }
}
