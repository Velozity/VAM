package com.velozity.vam;
import com.mojang.authlib.GameProfile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            Global.log.info("Only players are supported for VAM");
            return true;
        }

        Player player = (Player) sender;

        if (commandLabel.equals("vam")) {
            if(args.length == 0) {
                Global.interact.msgPlayer("Type /vam help for more information", player);
                return true;
            }

            if(args[0].equalsIgnoreCase("add")) {
                if(player.hasPermission("vam.createmessage")) {
                    String msg = Arrays.stream(args)
                            .skip(1)
                            .collect(Collectors.joining(" "));
                    try {
                        Global.mainConfig.addMessage(msg);
                    } catch (IOException e) {
                        Global.interact.msgPlayer("Error adding message. Check console!", player);
                        e.printStackTrace();
                    }

                    Global.interact.msgPlayer("Message successfully added!", player);
                } else {
                    Global.interact.msgPlayer("You do not have permission to do this", player);
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("timer")) {
                if(player.hasPermission("vam.edittimer")) {
                    if(args.length != 2 || Global.parsers.digitsOnly(args[1]) == -2) {
                        Global.interact.msgPlayer("Invalid time interval given", player);
                        return true;
                    }

                    try {
                        Global.mainConfig.editSetting("timer", Global.parsers.digitsOnly(args[1]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidConfigurationException e) {
                        e.printStackTrace();
                    }

                    Global.getMainInstance.restartTimer();
                    Global.interact.msgPlayer("Message timer has been set to " + Global.parsers.digitsOnly(args[1]) + " seconds", player);
                } else {
                    Global.interact.msgPlayer("You do not have permission to do this", player);
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("reload")) {
                if(player.hasPermission("vam.reload")) {
                    Global.getMainInstance.onEnable();
                    Global.interact.msgPlayer("Reloaded", player);
                }
            }
        }
        return true;
    }
}
