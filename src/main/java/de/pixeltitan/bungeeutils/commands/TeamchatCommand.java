package de.pixeltitan.bungeeutils.commands;

import de.pixeltitan.bungeeutils.Main;
import de.pixeltitan.bungeeutils.utils.TeamchatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamchatCommand extends Command {
    public TeamchatCommand(String name) {
        super(name);
    }

    public void execute(CommandSender sender, String[] args) {


        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(p.hasPermission("pt.teamchat")){
                if(args.length > 0){

                    if(args.length == 1 && (args[0].equalsIgnoreCase("login") || args[0].equalsIgnoreCase("logout"))){

                        if(args[0].equalsIgnoreCase("login")){
                            TeamchatUtils.login(p);
                        }else if(args[0].equalsIgnoreCase("logout")){
                            TeamchatUtils.logout(p);
                        }
                        return;
                    }

                    if(!TeamchatUtils.getList().contains(p)){
                        p.sendMessage(new TextComponent(TeamchatUtils.getPrefix() + "§cBitte logge dich zuerst ein!"));
                        return;
                    }

                    String message = "";
                    for (int i = 0; i < args.length; i++){
                        message = message + args[i] + " ";
                    }

                    for (ProxiedPlayer all : TeamchatUtils.getList()){
                        all.sendMessage(new TextComponent(TeamchatUtils.getPrefix() + "§6" + p.getName() + " §8:§7 " + message));
                    }

                }else {
                    p.sendMessage(new TextComponent(TeamchatUtils.getPrefix() + "§a§lEingeloggte Spieler:"));
                    for (ProxiedPlayer all : TeamchatUtils.getList()){
                        p.sendMessage(new TextComponent(TeamchatUtils.getPrefix() + all.getName()));
                    }
                }
            }else {
                p.sendMessage(Main.getNoPermsMessage());
            }

        }
    }
}
