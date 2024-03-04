package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;


public class InfectCommand implements SubCommand {
    private final PluginConfig pluginConfig = EndDisease.getPluginConfig();
    private final MiniMessage mm = MiniMessage.miniMessage();

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
    public void perform(Player player, String[] args) {

        if (!(player.hasPermission("disease." + getName()))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(pluginConfig.noPermissionMessage, Placeholder.parsed("command", getName())));
            return;
        }

        if (args.length > 1) {
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(mm.deserialize(pluginConfig.noSuchPlayerMessage, Placeholder.parsed("player", args[1])));
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (InfectionManager.isInfected(target.getUniqueId())) {
                player.sendMessage(mm.deserialize(pluginConfig.alreadyInfectedMessage, Placeholder.parsed("player", target.getName())));
                return;
            }

            player.sendMessage(mm.deserialize(pluginConfig.infectPlayerMessage, Placeholder.parsed("player", target.getName())));


            target.sendMessage(mm.deserialize(pluginConfig.infectedMessage, Placeholder.parsed("player", player.getName())));

            InfectionManager.addInfected(target.getUniqueId());
        } else if (args.length == 1) {
            player.sendMessage(pluginConfig.noTargetMessage);
            player.sendMessage(pluginConfig.exampleInfectMessage);
        }
    }
}
