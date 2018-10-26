package de.pixeltitan.bungeeutils;

import de.pixeltitan.bungeeutils.commands.BroadcastCommand;
import de.pixeltitan.bungeeutils.commands.JoinmeCommand;
import de.pixeltitan.bungeeutils.commands.ReportCommand;

import de.pixeltitan.bungeeutils.commands.TeamchatCommand;
import de.pixeltitan.bungeeutils.listeners.JoinListener;
import de.pixeltitan.bungeeutils.utils.ReportUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;


public class Main extends Plugin {

    private static Main instance;
    private static PluginManager pm;
    private static Core core;
    private static String prefix = "§8┃ §9§lBungee §8» §7";
    private static TextComponent noPermsMessage = new TextComponent(getPrefix() + "§cDazu hast du keine Rechte!");

    @Override
    public void onEnable() {
        instance = this;
        core = new Core(instance);
        pm = instance.getProxy().getPluginManager();

        init();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling Plugin!");
    }

    public static Main getInstance() {
        return instance;
    }

    public static PluginManager getPluginManager() {
        return pm;
    }

    public static String getPrefix() {
        return prefix;
    }

    public void init(){

       pm.registerCommand(instance, new ReportCommand("report"));
       pm.registerCommand(instance, new TeamchatCommand("teamchat"));
       pm.registerCommand(instance, new TeamchatCommand("tc"));
       pm.registerCommand(instance, new JoinmeCommand("joinme"));
       pm.registerCommand(instance, new BroadcastCommand("broadcast"));
       pm.registerCommand(instance, new BroadcastCommand("bc"));
       pm.registerListener(instance, new JoinListener());

        ReportUtils.addReason("Hacking");
        ReportUtils.addReason("Teaming");
        ReportUtils.addReason("Beleidigung");
        ReportUtils.addReason("Skin");

    }

    public static TextComponent getNoPermsMessage() {
        return noPermsMessage;
    }

    public static Core getCore() {
        return core;
    }
}
