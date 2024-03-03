package org.onysand.mc.enddisease.commands.subcommands;

import org.bukkit.entity.Player;
import org.onysand.mc.enddisease.commands.SubCommand;
import org.onysand.mc.enddisease.utils.Utils;

public class CheckerItemCommand implements SubCommand {
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
        player.getInventory().addItem(Utils.getCheckItem().clone());
    }
}
