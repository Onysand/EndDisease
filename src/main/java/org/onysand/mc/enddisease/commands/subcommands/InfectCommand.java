package org.onysand.mc.enddisease.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.InfectionManager;

public class InfectCommand implements SubCommand {

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
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);

            if (InfectionManager.isInfected(target.getUniqueId())) {
                player.sendMessage(EndDisease.getConfiguration().getString("messages.already-infected").replace("{player}", target.getName()));

                return;
            }

            player.sendMessage(EndDisease.getConfiguration().getString("messages.infected-message").replace("{player}", target.getName()));

            target.sendMessage(EndDisease.getConfiguration().getString("messages.infected-by-staff"));

            InfectionManager.addInfected(target.getUniqueId());
        } else if (args.length == 1) {
            player.sendMessage(EndDisease.getConfiguration().getString("messages.no-target"));
            player.sendMessage(EndDisease.getConfiguration().getString("messages.example-infect"));
        }
    }
}
