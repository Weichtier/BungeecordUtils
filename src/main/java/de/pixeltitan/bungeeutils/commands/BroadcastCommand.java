package de.pixeltitan.bungeeutils.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCommand extends Command {
    public BroadcastCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(commandSender.hasPermission("pt.broadcast")){
            String message = "";
            for (String s : args){
                message += "";
            }
        }
    }
}
