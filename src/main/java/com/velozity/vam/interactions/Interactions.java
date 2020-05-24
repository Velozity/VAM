package com.velozity.vam.interactions;

import com.google.common.collect.ObjectArrays;
import com.velozity.vam.Global;
import com.velozity.vam.types.LogType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Interactions {

    public void logServer(LogType type, String msg) {
        String logType = "";

        switch(type) {
            case info:
                logType = "INFO: ";
                break;
            case error:
                logType = "ERROR: ";
                break;
            case warning:
                logType = "WARNING: ";
                break;
            default:
                logType = "UNKNOWN: ";
                break;
        }

        Global.getMainInstance.getServer().getConsoleSender().sendMessage("§9[VAM] " + logType + msg);
    }

    public void msgPlayer(String msg, Player player) {
        player.sendMessage("§9[VAM]§r " + msg);
    }

    public void msgPlayer(String[] msg, Player player) {
        player.sendMessage(ObjectArrays.concat("§9[VAM]§r", msg));
    }

    public void msgServer(String msg) {
        Bukkit.getServer().broadcastMessage(msg);
    }

}
