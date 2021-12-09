package ot.dan.bluemapskins.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ot.dan.bluemapskins.Main;

public class GeneralEvents implements Listener {
    public final Main plugin;

    public GeneralEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getTools().changeHead(event.getPlayer());
    }

    @EventHandler
    public void onSkinChange(InventoryCloseEvent event) {
        if (event.getView().getTitle().toLowerCase().contains("skins menu")) {
            plugin.getTools().changeHead((Player) event.getPlayer());
        }
    }
}