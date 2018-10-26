package de.pixeltitan.bungeeutils.commands;

import de.pixeltitan.bungeeutils.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class JoinmeCommand extends Command {
    public JoinmeCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(args.length == 2){
                if(args[0].equalsIgnoreCase("join")){
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                    if(target == null){
                        p.sendMessage(new TextComponent(Main.getPrefix() + "§cDieser Spieler ist Offline!"));
                        return;
                    }
                    p.connect(target.getServer().getInfo());
                    p.sendMessage(Main.getPrefix() + "§9Du wirst verbunden...");
                }
            }

            if(p.hasPermission("pt.joinme") && args.length == 0){
                TextComponent component = new TextComponent(Main.getPrefix() + "§8§k&&&&&&&&&&&&&&\n" + Main.getPrefix() + "\n" + Main.getPrefix() + "§d» §5" + p.getName() + " §dspielt auf §5" + p.getServer().getInfo().getName() + " §d!\n" + Main.getPrefix() + "\n" + Main.getPrefix() + "§8§k&&&&&&&&&&&&&&");
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinme join " + p.getName()));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a§lKlicke um §6§l" + p.getName() + " §a§lnach zu joinen").create()));
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                    all.sendMessage(component);
                }
            }
        }
    }
}
