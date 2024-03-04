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

    // MESSAGES
    public final String infectPlayerMessage = config.getString("messages.infect-player", "check config: messages.infect-player");
    public final String curePlayerMessage = config.getString("messages.cure-player", "check config: messages.cure-player");
    public final String infectedMessage = config.getString("messages.infected", "check config: messages.infected");
    public final String curedMessage = config.getString("messages.cured", "check config: messages.cured");
    public final String diseaseMessage = config.getString("messages.disease", "check config: messages.disease");
    public final String alreadyInfectedMessage = config.getString("messages.already-infected", "check config: messages.already-infected");
    public final String notInfectedMessage = config.getString("messages.not-infected", "check config: messages.not-infected");
    public final String noOneInfectedMessage = config.getString("messages.noOne-infected", "check config: messages.noOne-infected");
    public final String noTargetMessage = config.getString("messages.no-target", "check config: messages.no-target");
    public final String exampleCureMessage = config.getString("messages.example-cure", "check config: messages.example-cure");
    public final String exampleInfectMessage = config.getString("messages.example-infect", "check config: messages.example-infect");
    public final String checkedPlayerIsInfectedMessage = config.getString("messages.checkedPlayer-isInfected", "check config: messages.checkedPlayer-isInfected");
    public final String checkedPlayerNotInfectedMessage = config.getString("messages.checkedPlayer-notInfected", "check config: messages.checkedPlayer-notInfected");
    public final String noSuchPlayerMessage = config.getString("messages.noSuch-player", "check config: messages.noSuch-player");
    public final String noPermissionMessage = config.getString("messages.no-permission");

    // CHANCES
    public final double infectByHittingChance = config.getDouble("chances.infect-by-hitting");
    public final double infectByDeathChance = config.getDouble("chances.infect-by-death");
    public final double infectByPlayerChance = config.getDouble("chances.infect-by-player");
    public final double getDiseaseMessageChance = config.getDouble("chances.get-diseaseMessage");

    // UTILS
    public final double infectRadius = config.getDouble("utils.infect-radius");
    public final double infectCheckDelay = config.getDouble("utils.infect-check-delay");
    public final ArrayList<String> maskItemNames = (ArrayList<String>) config.getStringList("utils.maskItems-names");
    public final String checkItemMaterial = config.getString("utils.checkItem-type");
    public final int checkItemCustomModelID = config.getInt("utils.checkItem-customModel");
    public final List<Integer> checkItemCustomModelIDList = config.getIntegerList("utils.checkItem-customModel");
    public final String checkItemName = config.getString("utils.checkItem-name");
    public final String entityInfectorType = config.getString("utils.entityInfector", "Enderman");
}