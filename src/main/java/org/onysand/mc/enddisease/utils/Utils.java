package org.onysand.mc.enddisease.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.onysand.mc.enddisease.EndDisease;

import java.util.logging.Logger;

public class Utils {

    private final FileConfiguration config;
    private final Logger logger;

    private ItemStack checkItem;

    public Utils(EndDisease plugin) {
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
        initCheckItem();
    }

    private void initCheckItem() {
        String configMaterial = config.getString("utils.checkItem-type", "STONE");
        String configName = config.getString("utils.checkItem-name", "NULL");
        int customModel = config.getInt("utils.checkItem-customModel", 1);

        Material material;

        try {
            material = Material.valueOf(configMaterial);

        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());//ПОТОМ ВЫРУБАТЬ ПЛАГИН
            return;
        }

        checkItem = new ItemStack(material); //П
        ItemMeta itemMeta = checkItem.getItemMeta();

        itemMeta.displayName(Component.text(configName));
        itemMeta.setCustomModelData(customModel);

        checkItem.setItemMeta(itemMeta);

    }

    public ItemStack getCheckItem() {
        return checkItem;
    }
}
