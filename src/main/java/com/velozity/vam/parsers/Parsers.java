package com.velozity.vam.parsers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class Parsers {
    public String addVariables(String msg) {
        msg = msg.replace("{world}", Bukkit.getServer().getWorldType())
                .replace("{player_count}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("{max_players}", String.valueOf(Bukkit.getServer().getMaxPlayers()));

        if(Bukkit.getServer().getOnlinePlayers().size() > 0) {
            msg = msg.replace("{random_player}", ((Player)Bukkit.getServer().getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size())]).getDisplayName());
        }

        return msg;
    }

    public Integer digitsOnly(String line) {
        String toParse = line.toLowerCase().replaceAll("[^\\.0123456789]","").trim();
        if(toParse.isEmpty()) {
            return -2;
        }

        int price = -1;
        try {
            price = Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            return -1;
        }

        return price;
    }
}
