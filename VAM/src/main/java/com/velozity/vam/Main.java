package com.velozity.vam;

import com.velozity.vam.types.LogType;
import com.velozity.vam.types.TabComplete;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JavaPlugin {
    @Override
    public void onDisable() {
        Global.log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        Global.getMainInstance = this;
        registerCommands();

        getCommand("vam").setTabCompleter(new TabComplete());

        try {
            if(!Global.mainConfig.setupWorkspace()) {
                Global.log.severe(String.format("[%s] - Disabled due to insufficient file privileges!", getDescription().getName()));
                getServer().getPluginManager().disablePlugin(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendMessages();

        int interval = 60;
        try {
            interval = (Integer) Global.mainConfig.readSetting("timer");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if(timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(task, new Date(), interval * 1000);
        Global.interact.logServer(LogType.info,"Sending " + Global.mainConfig.readMessages().size() + " global messages every " + interval + " seconds");

    }

    public void registerCommands() {
        this.getCommand("vam").setExecutor(new Commands());
    }

    public void restartTimer() {
        timer.cancel();
        timer.purge();
        timer = new Timer();

        int interval = 60;
        try {
            interval = (Integer) Global.mainConfig.readSetting("timer");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        sendMessages();
        timer.schedule(task, new Date(), interval * 1000);
    }

    Timer timer;
    TimerTask task;
    public void sendMessages() {
        task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                int msgCount = Global.messages.size();
                if(i >= msgCount)
                    i = 0;

                Global.interact.msgServer(Global.parsers.addVariables(Global.messages.get(i)));
                i++;
            }
        };
    }
}
