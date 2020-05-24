package com.velozity.vam;

import com.velozity.vam.configs.MainConfig;
import com.velozity.vam.interactions.Interactions;
import com.velozity.vam.parsers.Parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Global {

    public static Main getMainInstance;

    public static final Logger log = Logger.getLogger("Minecraft");

    public static final MainConfig mainConfig = new MainConfig();
    public static final Interactions interact = new Interactions();
    public static final Parsers parsers = new Parsers();

    public static List<String> messages = new ArrayList<>();

}
