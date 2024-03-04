package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.MessageType;
import org.onysand.mc.enddisease.utils.PluginConfig;


public class CureCommand implements SubCommand {

    private final PluginConfig pluginConfig;
    public CureCommand(EndDisease plugin) {
        this.pluginConfig = plugin.getPluginConfig();
    }

    @Override
    public String getName() {
        return "cure";
    }

    @Override
    public String getDescription() {
        return "Cure some player from disease.";
    }

    @Override
    public String getSyntax() {
        return "/disease cure <player name>";
    }

    @Override
    public void perform(Player sender, String[] args) {

        String permission = "disease." + getName();
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(pluginConfig.getMessage(MessageType.noPermissionMessage, null));
            return;
        }

        if (args.length > 1) {

            String targetName = args[1];
            Player targetPlayer = Bukkit.getPlayer(targetName);


            if (targetPlayer == null) {
                sender.sendMessage(pluginConfig.getMessage(MessageType.noSuchPlayerMessage, args[1]));
                return;
            }

            if (!(InfectionManager.isInfected(targetPlayer.getUniqueId()))) {
                sender.sendMessage(pluginConfig.getMessage(MessageType.notInfectedMessage, targetName));
                return;
            }


            sender.sendMessage(pluginConfig.getMessage(MessageType.curePlayerMessage, targetName));

            if (targetPlayer.hasPermission(permission)) {
                targetPlayer.sendMessage(pluginConfig.getMessage(MessageType.curedMessage, sender.getName()));
            }

            InfectionManager.cureInfected(targetPlayer.getUniqueId());
        } else if (args.length == 1) {

            Component message = pluginConfig.getMessage(MessageType.noTargetMessage, null).append(Component.text("\n")).append(pluginConfig.getMessage(MessageType.exampleCureMessage, null));

            sender.sendMessage(message);
        }
    }
}
