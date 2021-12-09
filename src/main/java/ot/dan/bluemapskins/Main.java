package ot.dan.bluemapskins;

import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ot.dan.bluemapskins.listener.GeneralEvents;
import ot.dan.bluemapskins.manager.ConfigManager;
import ot.dan.bluemapskins.utils.Log;
import ot.dan.bluemapskins.utils.Metrics;
import ot.dan.bluemapskins.utils.Tools;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private SkinsRestorerAPI skinsRestorerAPI;
    private Log log;
    private Tools tools;
    private ConfigManager configManager;
    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        loadConfig();
        log = new Log();
        tools = new Tools(this);
        configManager = new ConfigManager(this);
        if (getServer().getPluginManager().getPlugin("SkinsRestorer") != null) {
            skinsRestorerAPI = SkinsRestorerAPI.getApi();
        }
        registerListeners();
        startMetrics();
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig() {
        createConfig();
        saveNewConfig();
    }

    private void startMetrics() {
        int pluginId = 13527;
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    public FileConfiguration getActualConfig() {
        return this.config;
    }

    private void createConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().header("Currently available head types: 2d, 3d");
        config.options().copyHeader(true);
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveNewConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GeneralEvents(this), this);
    }

    public SkinsRestorerAPI getSkinsRestorerAPI() {
        return skinsRestorerAPI;
    }

    public Log getLog() {
        return log;
    }

    public Tools getTools() {
        return tools;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
