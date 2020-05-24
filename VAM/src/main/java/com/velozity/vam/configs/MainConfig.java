package com.velozity.vam.configs;

import com.velozity.vam.Global;
import com.velozity.vam.types.LogType;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainConfig {

    private static final String workPath = "plugins/VAM";
    @Getter
    private FileConfiguration mainConfig;
    private File mainConfigFile;

    public Boolean setupWorkspace() throws IOException {
        createMainConfig();
        return true;
    }

    public FileConfiguration getMainConfig() {
        return this.mainConfig;
    }

    private void createMainConfig() throws IOException {
        mainConfigFile = new File(workPath, "config.yml");
        if (!mainConfigFile.exists()) {
            mainConfigFile.getParentFile().mkdirs();
            mainConfigFile.createNewFile();
        }

        mainConfig = new YamlConfiguration();
        try {
            mainConfig.load(mainConfigFile);
            writeDefaultSettings();
        } catch (IOException | InvalidConfigurationException e) {
            Global.interact.logServer(LogType.error, "Could not load mainConfig file! Try restarting or checking file permissions");
        }

        Global.messages = readMessages();
    }

    public List<String> readMessages() {
        try {
            mainConfig.load(mainConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            Global.interact.logServer(LogType.error,"Something went wrong when trying to read messages in config.yml");
        }

        return (List<String>)getMainConfig().getConfigurationSection("").get("messages");
    }

    public void addMessage(String msg) throws IOException {
        List<String> messages = readMessages();
        messages.add(msg);

        getMainConfig().set("messages", messages);
        getMainConfig().save(mainConfigFile);

        Global.messages = readMessages();
    }

    public Object readSetting(String key) throws IOException, InvalidConfigurationException {
        mainConfig.load(mainConfigFile);

        return getMainConfig().getConfigurationSection("settings").get(key);
    }

    public void editSetting(String key, Object value) throws IOException, InvalidConfigurationException {
        mainConfig.load(mainConfigFile);

        getMainConfig().set("settings." + key, value);
        getMainConfig().save(mainConfigFile);
    }

    public void writeDefaultSettings() throws IOException {
        // Message settings
        getMainConfig().addDefault("messages", Arrays.asList("Message 1", "Message 2"));
        getMainConfig().addDefault("settings.timer", 60);

        getMainConfig().options().copyDefaults(true);
        getMainConfig().save(mainConfigFile);
    }
}

