package org.onysand.mc.enddisease.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.onysand.mc.enddisease.EndDisease;
import org.onysand.mc.enddisease.commands.SubCommand;

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
        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(EndDisease.getConfiguration().getInt("utils.checkItem-customModel"));
        meta.setDisplayName(ChatColor.DARK_PURPLE + EndDisease.getConfiguration().getString("utils.checkItem-name"));
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
    }
}
