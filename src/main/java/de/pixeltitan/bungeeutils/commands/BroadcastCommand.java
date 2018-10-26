package de.pixeltitan.bungeeutils.commands;

import de.pixeltitan.bungeeutils.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCommand extends Command {

    private static String prefix = "§8┃ §c§lDURCHSAGE §8» §7";

    public BroadcastCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(commandSender.hasPermission("pt.broadcast")){

            if(args.length > 0){
                String message = "";
                for (String s : args){
                    message += s + "";
                }

                TextComponent component = new TextComponent(prefix + message);

                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                    all.sendMessage(component);
                }
            }else {
                commandSender.sendMessage(new TextComponent(Main.getPrefix() + "§c/broadcast <Nachricht>"));
            }

        }
    }
}
