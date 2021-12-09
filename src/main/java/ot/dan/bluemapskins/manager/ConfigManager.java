package ot.dan.bluemapskins.manager;

import ot.dan.bluemapskins.Main;
import ot.dan.bluemapskins.type.DisplayType;

import java.util.Objects;

public class ConfigManager {
    private final DisplayType displayType;
    private final boolean overlay;

    public ConfigManager(Main plugin) {
        if (Objects.equals(plugin.getActualConfig().getString("HeadType"), "2d")) {
            displayType = DisplayType.HEAD2D;
        } else {
            displayType = DisplayType.HEAD3D;
        }

        if (plugin.getActualConfig().isConfigurationSection("Overlay")) {
            overlay = plugin.getActualConfig().getBoolean("Overlay");
        } else {
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
