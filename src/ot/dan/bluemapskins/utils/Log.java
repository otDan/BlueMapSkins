package ot.dan.bluemapskins.utils;

import ot.dan.bluemapskins.Main;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private final Main plugin;
    private List<String> uuids;

    public Log(Main plugin) {
        this.plugin = plugin;
        this.uuids = new ArrayList<>();
    }

    public List<String> getUuids() {
        return uuids;
    }
}
