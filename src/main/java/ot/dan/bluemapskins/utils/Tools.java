package ot.dan.bluemapskins.utils;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ot.dan.bluemapskins.Main;
import ot.dan.bluemapskins.type.DisplayType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class Tools {
    private final Main plugin;

    public Tools(Main plugin) {
        this.plugin = plugin;
    }

    public String getUUID(String playerName) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            String UUIDJson = IOUtils.toString(new URL(url), Charset.defaultCharset());
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void getPlayerPic(String uuid, String uuidCracked) {
        try {
            URL url;
            if (plugin.getConfigManager().getDisplayType().equals(DisplayType.HEAD2D)) {
                if (plugin.getConfigManager().isOverlay()) {
                    url = new URL("https://crafatar.com/avatars/" + uuid + "?overlay=true");
                } else {
                    url = new URL("https://crafatar.com/avatars/" + uuid);
                }
            } else {
                if (plugin.getConfigManager().isOverlay()) {
                    url = new URL("https://crafatar.com/renders/head/" + uuid + "?overlay=true");
                } else {
                    url = new URL("https://crafatar.com/renders/head/" + uuid);
                }
            }
            BufferedImage img = ImageIO.read(url);
            String path = plugin.getServer().getWorldContainer().getAbsolutePath() + "/bluemap/web/assets/playerheads";
            File file = new File(path, uuidCracked + ".png");
            if (!file.exists()) {
                file.mkdirs();
            }
            ImageIO.write(img, "png", file);
        } catch (IOException ignored) {
        }
    }

    public void changeHead(Player player) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            String uuid;
            if (plugin.getSkinsRestorerAPI() != null) {
                if (plugin.getSkinsRestorerAPI().getSkinName(player.getName()) != null) {
                    uuid = plugin.getTools().getUUID(plugin.getSkinsRestorerAPI().getSkinName(player.getName()));
                } else {
                    uuid = plugin.getTools().getUUID(player.getName());
                }
            } else {
                uuid = plugin.getTools().getUUID(player.getName());
            }

            plugin.getTools().getPlayerPic(uuid, player.getUniqueId().toString());
            plugin.getLog().getUuids().add(uuid);
        }, 20 * 5);
    }
}
