package de.pixeltitan.bungeeutils.commands;

import de.pixeltitan.bungeeutils.Main;
import de.pixeltitan.bungeeutils.utils.ReportUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ReportCommand extends Command {
    private ArrayList<String> incooldown = new ArrayList();

    public ReportCommand(String name) {
        super(name);
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            final ProxiedPlayer p = (ProxiedPlayer)sender;
            if (p.hasPermission("report.use")) {
                if (args.length == 2) {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if (p != target) {
                        if (this.incooldown.contains(p.getName())) {
                            p.sendMessage(new TextComponent(ReportUtils.getPrefix() + "§cDu musst mindestens §e5 Minuten §cwarten!"));
                            return;
                        }

                        if (target == null) {
                            p.sendMessage(new TextComponent(ReportUtils.getPrefix() + "§cDieser Spieler ist nicht Online!"));
                            return;
                        }

                        ReportUtils.createReport(p, target, args[1]);
                        this.incooldown.add(p.getName());
                        ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
                            public void run() {
                                ReportCommand.this.incooldown.remove(p.getName());
                            }
                        }, 5L, TimeUnit.MINUTES);
                    } else {
                        p.sendMessage(new TextComponent(ReportUtils.getPrefix() + "§cDu kannst dich nicht selbst reporten!"));
                    }
                } else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
                    if (p.hasPermission("report.staff")) {
                        if (ReportUtils.loggedin(p)) {
                            ReportUtils.logout(p);
                        } else {
                            ReportUtils.login(p);
                        }
                    } else {
                        p.sendMessage(new TextComponent(ReportUtils.getPrefix() + "§cKeine Rechte :/"));
                    }
                } else {
                    String messageString = ReportUtils.getPrefix() + "§bVerfügbare Gründe§8:\n";

                    for(int i = 0; ReportUtils.getReasons().size() > i; ++i) {
                        messageString = messageString + ReportUtils.getPrefix() + "§e" + (String)ReportUtils.getReasons().get(i) + "\n";
                    }

                    messageString = messageString + ReportUtils.getPrefix() + "§c/report <Spielername> <Grund>";
                    TextComponent message = new TextComponent(messageString);
                    p.sendMessage(message);
                }
            } else {
                p.sendMessage(new TextComponent(ReportUtils.getPrefix() + "§cKeine Rechte :/"));
            }
        } else {
            sender.sendMessage(new TextComponent("§cNur als Spieler"));
        }

    }
}
