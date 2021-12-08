package ot.dan.bluemapskins.utils;

import ot.dan.bluemapskins.Main;
import ot.dan.bluemapskins.objects.DisplayType;

import java.util.Objects;

public class ConfigManager {
    private final Main plugin;
    private final DisplayType displayType;
    private final boolean overlay;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        if(Objects.equals(plugin.getActualConfig().getString("HeadType"), "2d")) {
            displayType = DisplayType.Head2d;
        }
        else {
            displayType = DisplayType.Head3d;
        }

        if(plugin.getActualConfig().isConfigurationSection("Overlay")) {
            overlay = plugin.getActualConfig().getBoolean("Overlay");
        }
        else {
            overlay = true;
            plugin.getActualConfig().set("Overlay", true);
            plugin.saveNewConfig();
        }
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public boolean isOverlay() {
        return overlay;
    }
}
