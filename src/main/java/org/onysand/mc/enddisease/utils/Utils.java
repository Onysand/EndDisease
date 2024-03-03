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
        PluginConfig pluginConfig = EndDisease.getPluginConfig();
        Logger logger = EndDisease.getPlugin().getLogger();

        String configMaterial = pluginConfig.checkItemMaterial;
        String configName = pluginConfig.checkItemName;
        int customModel = pluginConfig.checkItemCustomModelID;

        Material material;

        try {
            material = Material.valueOf(configMaterial);

        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
            EndDisease.getPlugin().setEnabled(false);
            return;
        }

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
