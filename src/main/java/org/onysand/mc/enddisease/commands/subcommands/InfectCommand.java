package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;


public class InfectCommand implements SubCommand {

    private final PluginConfig pluginConfig;
    public InfectCommand(EndDisease plugin) {
        this.pluginConfig = plugin.getPluginConfig();
    }

    @Override
    public String getName() {
        return "infect";
    }

    @Override
    public String getDescription() {
        return "Infect some player";
    }

    @Override
    public String getSyntax() {
        return "/disease infect <player name>";
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

            if (InfectionManager.isInfected(targetPlayer.getUniqueId())) {
                sender.sendMessage(pluginConfig.getMessage(MessageType.alreadyInfectedMessage, targetName));
                return;
            }


            sender.sendMessage(pluginConfig.getMessage(MessageType.infectPlayerMessage, targetName));

            if (targetPlayer.hasPermission(permission)) {
                targetPlayer.sendMessage(pluginConfig.getMessage(MessageType.infectedMessage, sender.getName()));
            }

            InfectionManager.addInfected(targetPlayer.getUniqueId());
        } else if (args.length == 1) {
            player.sendMessage(pluginConfig.noTargetMessage);
            player.sendMessage(pluginConfig.exampleInfectMessage);
        }
    }
}
