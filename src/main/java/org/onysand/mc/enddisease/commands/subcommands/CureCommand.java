package org.onysand.mc.enddisease.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;


public class CureCommand implements SubCommand {

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
            Player target = Bukkit.getPlayer(args[1]);

            if (!InfectionManager.isInfected(target.getUniqueId())) {
                player.sendMessage(EndDisease.getConfiguration().getString("messages.not-infected").replace("{player}", target.getName()));
                return;
            }

            InfectionManager.cureInfected(target.getUniqueId());

            player.sendMessage(EndDisease.getConfiguration().getString("messages.cured-message").replace("{player}", target.getName()));
            target.sendMessage(EndDisease.getConfiguration().getString("messages.cured-by-staff"));
        } else if (args.length == 1) {
            player.sendMessage(EndDisease.getConfiguration().getString("messages.no-target"));
            player.sendMessage(EndDisease.getConfiguration().getString("messages.example-cure"));
        }
    }
}
