package ot.dan.bluemapskins;

import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ot.dan.bluemapskins.events.Listeners;
import ot.dan.bluemapskins.events.ListenersCustomSkinsManager;
import ot.dan.bluemapskins.utils.ConfigManager;
import ot.dan.bluemapskins.utils.Log;
import ot.dan.bluemapskins.utils.Metrics;
import ot.dan.bluemapskins.utils.Tools;
import ru.csm.api.services.SkinsAPI;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin {
    private SkinsRestorerAPI skinsRestorerAPI;
    private SkinsAPI customSkinsManagerAPI;
    private Log log;
    private Tools tools;
    private ConfigManager configManager;
    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        loadConfig();
        log = new Log(this);
        tools = new Tools(this);
        configManager = new ConfigManager(this);
        if (getServer().getPluginManager().getPlugin("SkinsRestorer") != null) {
            skinsRestorerAPI = SkinsRestorerAPI.getApi();
        }
        else {
            if(getServer().getPluginManager().getPlugin("CustomSkinsManager") != null) {
                customSkinsManagerAPI = Objects.requireNonNull(getServer().getServicesManager().getRegistration(SkinsAPI.class)).getProvider();
            }
            skinsRestorerAPI = null;
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
        int pluginId = 10970; // <-- Replace with the id of your plugin!
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
        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
        if(customSkinsManagerAPI != null) {
            Bukkit.getPluginManager().registerEvents(new ListenersCustomSkinsManager(this), this);
        }
    }

    public SkinsRestorerAPI getSkinsRestorerAPI() {
        return skinsRestorerAPI;
    }

    public SkinsAPI getCustomSkinsManagerAPI() {
        return customSkinsManagerAPI;
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
