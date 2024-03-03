package org.onysand.mc.enddisease.commands.subcommands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;
import org.onysand.mc.enddisease.utils.PluginConfig;


public class CureCommand implements SubCommand {

    private final PluginConfig pluginConfig = EndDisease.getPlugin().getPluginConfig();
    private final MiniMessage mm = MiniMessage.miniMessage();

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
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(mm.deserialize(pluginConfig.noSuchPlayerMessage, Placeholder.parsed("player", args[1])));
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (!InfectionManager.isInfected(target.getUniqueId())) {
                player.sendMessage(mm.deserialize(pluginConfig.notInfectedMessage, Placeholder.parsed("player", target.getName())));
                return;
            }

            InfectionManager.cureInfected(target.getUniqueId());

            player.sendMessage(mm.deserialize(pluginConfig.curePlayerMessage, Placeholder.parsed("player", target.getName())));
            target.sendMessage(mm.deserialize(pluginConfig.curedMessage, Placeholder.parsed("player", player.getName())));
        } else if (args.length == 1) {
            player.sendMessage(pluginConfig.noTargetMessage);
            player.sendMessage(pluginConfig.exampleCureMessage);
        }
    }
}
