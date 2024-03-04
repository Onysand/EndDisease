package org.onysand.mc.enddisease.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.onysand.mc.enddisease.EndDisease;

import java.util.logging.Logger;

public class Utils {
    private static ItemStack checkItem;

    private static void initCheckItem() {
        PluginConfig pluginConfig = EndDisease.getPlugin().getPluginConfig();

        Material material = pluginConfig.getItemMaterial();
        String configName = pluginConfig.getCheckItemName();
        int customModel = pluginConfig.getCheckCMD();

        checkItem = new ItemStack(material);
        ItemMeta itemMeta = checkItem.getItemMeta();

        itemMeta.displayName(Component.text(configName));
        itemMeta.setCustomModelData(customModel);

        checkItem.setItemMeta(itemMeta);

    }

    public static ItemStack getCheckItem() {
        if (checkItem == null) initCheckItem();
        return checkItem;
    }
}
