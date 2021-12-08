package ot.dan.bluemapskins.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ot.dan.bluemapskins.Main;
import ru.csm.bukkit.event.SkinChangedEvent;
import ru.csm.bukkit.event.SkinResetEvent;

public class ListenersCustomSkinsManager implements Listener {
    public final Main plugin;

    public ListenersCustomSkinsManager(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSkinReset(SkinResetEvent event) {
        plugin.getTools().changeHead(Bukkit.getPlayer(event.getPlayer().getUUID()));
    }

    @EventHandler
    public void onSkinChange(SkinChangedEvent event) {
        plugin.getTools().changeHead(Bukkit.getPlayer(event.getPlayer().getUUID()));
    }

}
