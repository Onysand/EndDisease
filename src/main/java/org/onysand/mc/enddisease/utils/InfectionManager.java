package org.onysand.mc.enddisease.utils;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.onysand.mc.enddisease.EndDisease;

import java.io.*;
import java.util.*;

public class InfectionManager {
    private static final PluginConfig pluginConfig = EndDisease.getPluginConfig();

    private static final Set<UUID> infectedList = Collections.synchronizedSet(new HashSet<>());
    public static void addInfected(UUID player) {
        infectedList.add(player);
    }
    public static void cureInfected(UUID uuid) {
        infectedList.remove(uuid);
    }
    public static boolean isInfected(UUID uuid) {
        return infectedList.contains(uuid);
    }
    public static ArrayList<UUID> getAllInfected() {
        return new ArrayList<>(infectedList);
    }

    public static boolean isProtectedFromDisease(Player player) {
        ItemStack playerHelmet = player.getInventory().getHelmet();

        if (playerHelmet == null) return false;
        if (playerHelmet.getType() != Material.CARVED_PUMPKIN) return false;

        ItemMeta itemMeta = playerHelmet.getItemMeta();
        if (itemMeta == null) return false;

        Component nameComponent = itemMeta.displayName();
        if (nameComponent == null) return false;

        String itemName = ((TextComponent) nameComponent.children().get(1)).content();
        return pluginConfig.maskItemNames.contains(itemName);
    }

    public static void loadInfected() throws IOException {
        File file = new File(EndDisease.getPlugin().getDataFolder().getAbsolutePath(), "infected.json");
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            infectedList.clear();
            infectedList.addAll(Arrays.asList(new Gson().fromJson(reader, UUID[].class)));
        }
    }

    public static void saveInfected() throws IOException {
        File file = new File(EndDisease.getPlugin().getDataFolder().getAbsolutePath(), "infected.json");
        file.getParentFile().mkdir();

        try (Writer writer = new FileWriter(file)) {
            new Gson().toJson(infectedList, writer);
        }

        System.out.println("Infected.json saved.");
    }
}
