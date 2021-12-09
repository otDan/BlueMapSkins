package ot.dan.bluemapskins.utils;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private final List<String> uuids;

    public Log() {
        this.uuids = new ArrayList<>();
    }

    public List<String> getUuids() {
        return uuids;
    }
}
