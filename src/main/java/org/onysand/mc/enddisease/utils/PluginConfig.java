package org.onysand.mc.enddisease.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.onysand.mc.enddisease.EndDisease;

import java.util.ArrayList;
import java.util.List;

public class PluginConfig {
    //ТУТ СООБЩЕНИЯ ПЛАГИНА ЧЕРЕЗ https://docs.advntr.dev/minimessage/api.html
    private EndDisease plugin;
    private FileConfiguration config = EndDisease.getConfiguration();

    public PluginConfig(EndDisease plugin) {
        this.plugin = plugin;
    }


    public Component getMessage(MessageType messageType, String playerName) {
        String message = messageMap.get(messageType);

        return playerName == null ?
                miniMessage.deserialize(message, Placeholder.parsed("player", ""))
                : miniMessage.deserialize(message, Placeholder.parsed("player", playerName));
    }

    public Material getItemMaterial() {
        if (itemMaterial == null) {
            plugin.getLogger().severe("Неизвестный тип материала!");
            return Material.STONE;
        }
        return itemMaterial;
    }

    public EntityType getEntityInfectorType() {
        if (entityInfectorType == null) {
            plugin.getLogger().severe("Неизвестный тип моба!");
            return  EntityType.ENDERMAN;
        }
        return entityInfectorType;
    }

    public double getGetDiseaseMessageChance() {
        return getDiseaseMessageChance;
    }

    public double getInfectByDeathChance() {
        return infectByDeathChance;
    }

    public double getInfectByHittingChance() {
        return infectByHittingChance;
    }

    public double getInfectByPlayerChance() {
        return infectByPlayerChance;
    }
    public double getGetDebuffChance() {
        return getDebuffChance;
    }
    public int getConfusionDurationOrigin() {
        return confusionDurationOrigin;
    }
    public int getConfusionDurationBound() {
        return confusionDurationBound;
    }

    public double getInfectRadius() {
        return infectRadius;
    }

    public int getCheckCMD() {
        return checkItemCustomModelID;
    }

    public long getSchedulersDelay() {
        return schedulersDelay;
    }

    public String getCheckItemName() {
        return checkItemName;
    }

    public List<String> getMaskItemNames() {
        return maskItemNames;
    }

    private void initMessages() {
        messageMap.put(MessageType.infectPlayerMessage, config.getString("messages.infect-player", "check config: messages.infect-player"));
        messageMap.put(MessageType.curePlayerMessage, config.getString("messages.cure-player", "check config: messages.cure-player"));
        messageMap.put(MessageType.infectedMessage, config.getString("messages.infected", "check config: messages.infected"));
        messageMap.put(MessageType.curedMessage, config.getString("messages.cured", "check config: messages.cured"));
        messageMap.put(MessageType.diseaseMessage, config.getString("messages.disease", "check config: messages.disease"));
        messageMap.put(MessageType.alreadyInfectedMessage, config.getString("messages.already-infected", "check config: messages.already-infected"));
        messageMap.put(MessageType.notInfectedMessage, config.getString("messages.not-infected", "check config: messages.not-infected"));
        messageMap.put(MessageType.noOneInfectedMessage, config.getString("messages.noOne-infected", "check config: messages.noOne-infected"));
        messageMap.put(MessageType.noTargetMessage, config.getString("messages.no-target", "check config: messages.no-target"));
        messageMap.put(MessageType.exampleCureMessage, config.getString("messages.example-cure", "check config: messages.example-cure"));
        messageMap.put(MessageType.exampleInfectMessage, config.getString("messages.example-infect", "check config: messages.example-infect"));
        messageMap.put(MessageType.checkedPlayerIsInfectedMessage, config.getString("messages.checkedPlayer-isInfected", "check config: messages.checkedPlayer-isInfected"));
        messageMap.put(MessageType.checkedPlayerNotInfectedMessage, config.getString("messages.checkedPlayer-notInfected", "check config: messages.checkedPlayer-notInfected"));
        messageMap.put(MessageType.noSuchPlayerMessage, config.getString("messages.noSuch-player", "check config: messages.noSuch-player"));
        messageMap.put(MessageType.noPermissionMessage, config.getString("messages.no-permission"));
        messageMap.put(MessageType.notFoundPlayer, config.getString("messages.notFound-player"));
    }

    private void initConfig() {
        this.itemMaterial = Material.getMaterial(config.getString("utils.checkItem-type", "STONE").toUpperCase());
        this.entityInfectorType = EntityType.valueOf(config.getString("utils.entityInfector", "ENDERMAN").toUpperCase());
        this.infectByHittingChance = config.getDouble("chances.infect-by-hitting", 5);
        this.infectByDeathChance = config.getDouble("chances.infect-by-death", 50);
        this.infectByPlayerChance = config.getDouble("chances.infect-by-player", 1);
        this.getDiseaseMessageChance = config.getDouble("chances.get-diseaseMessage", 5);
        this.getDebuffChance = config.getDouble("chancesget-debuffChance", 5);
        this.infectRadius = config.getDouble("utils.infect-radius", 20);
        this.confusionDurationOrigin = config.getInt("utils.confusionDuration-origin", 3);
        this.confusionDurationBound = config.getInt("utils.confusionDuration-bound", 10);
        this.schedulersDelay = config.getLong("utils.schedulers-delay", 5) * 20;
        this.checkItemCustomModelID = config.getInt("utils.checkItem-customModel", 1);
        this.checkItemName = config.getString("utils.checkItem-name");
        this.maskItemNames = config.getStringList("utils.maskItems-names");
    }
}