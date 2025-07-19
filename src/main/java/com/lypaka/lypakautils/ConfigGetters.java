package com.lypaka.lypakautils;

public class ConfigGetters {

    public static boolean tickListenerEnabled;

    public static void load() {

        tickListenerEnabled = LypakaUtils.configManager.getConfigNode(0, "Enable-Tick-Listener").getBoolean();

    }

}
