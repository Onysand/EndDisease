package org.onysand.mc.enddisease.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.onysand.mc.enddisease.commands.subcommands.CheckerItemCommand;
import org.onysand.mc.enddisease.commands.subcommands.InfectCommand;
import org.onysand.mc.enddisease.commands.subcommands.CureCommand;
import org.onysand.mc.enddisease.commands.subcommands.ListInfectedCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager() {
        subcommands.add(new InfectCommand());
        subcommands.add(new CureCommand());
        subcommands.add(new ListInfectedCommand());
        subcommands.add(new CheckerItemCommand());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return true;

        if (args.length > 0) {
            for (SubCommand subcommand : subcommands) {
                if(args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.perform(player, args);
                    return true;
                }
            }
        }

        player.sendMessage("==============================");
        for (int i = 0; i < subcommands.size(); i++) {
            player.sendMessage(subcommands.get(i).getSyntax() + " - " + subcommands.get(i).getDescription());
        }
        player.sendMessage("==============================");


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length <= 1) return subcommands.stream()
                .map(SubCommand::getName)
                .toList();

        if (args.length == 2) return null;

        return new ArrayList<>();
    }
}