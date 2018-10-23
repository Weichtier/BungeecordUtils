package de.pixeltitan.bungeeutils.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Iterator;

public class ReportUtils {
    private static String Prefix = "§8┃ §c§lReport §8» §7";
    private static ArrayList<String> loggedin = new ArrayList();
    private static ArrayList<String> reasons = new ArrayList();
    private static ArrayList<String> reported = new ArrayList();

    public ReportUtils() {
    }

    public static String getPrefix() {
        return Prefix;
    }

    public static void login(ProxiedPlayer p) {
        loggedin.add(p.getName());
        p.sendMessage(new TextComponent(getPrefix() + "§6Du §7hast dich §aeingeloggt"));
        Iterator var1 = ProxyServer.getInstance().getPlayers().iterator();

        while(var1.hasNext()) {
            ProxiedPlayer all = (ProxiedPlayer)var1.next();
            if (all.hasPermission("report.staff") && all != p) {
                all.sendMessage(new TextComponent(getPrefix() + "§6" + p.getName() + "§7 hat sich §aeingeloggt"));
            }
        }

    }

    public static void logout(ProxiedPlayer p) {
        loggedin.remove(p.getName());
        p.sendMessage(new TextComponent(getPrefix() + "§6Du §7hast dich §causgeloggt"));
        Iterator var1 = ProxyServer.getInstance().getPlayers().iterator();

        while(var1.hasNext()) {
            ProxiedPlayer all = (ProxiedPlayer)var1.next();
            if (all.hasPermission("report.staff") && all != p) {
                all.sendMessage(new TextComponent(getPrefix() + "§6" + p.getName() + "§7 hat sich §causgeloggt"));
            }
        }

    }

    public static boolean loggedin(ProxiedPlayer p) {
        return loggedin.contains(p.getName());
    }

    public static boolean reasonExists(String reason) {
        return reasons.contains(reason);
    }

    public static ArrayList<String> getReasons() {
        return reasons;
    }

    public static void createReport(ProxiedPlayer p, ProxiedPlayer target, String reason) {
        if (!reasonExists(reason)) {
            p.sendMessage(new TextComponent(getPrefix() + "§cGrund konnte nicht gefunden werden."));
        } else {
            TextComponent message = new TextComponent();
            message.addExtra(getPrefix() + "§c" + target.getName() + " §7wurde für §c" + reason + "§7 von §a" + p.getName() + " §7reportet! ");
            message.addExtra("§8[§4§lKLICK§r§8]");
            ((BaseComponent)message.getExtra().get(1)).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/staffreport accept " + target.getName()));
            int count = 0;
            Iterator var5 = ProxyServer.getInstance().getPlayers().iterator();

            while(var5.hasNext()) {
                ProxiedPlayer all = (ProxiedPlayer)var5.next();
                if (all.hasPermission("report.staff") && loggedin(all)) {
                    all.sendMessage(message);
                    ++count;
                }
            }

            if (!reported.contains(target.getName())) {
                reported.add(target.getName());
            }

            p.sendMessage(new TextComponent(getPrefix() + "§aEs wurden §e" + count + " §aTeammitglieder verständigt, sie §awerden sich §agleich um §adeinen Report kümmern."));
        }
    }

    public static void addReason(String reason) {
        reasons.add(reason);
    }

    public static ArrayList<String> getReported() {
        return reported;
    }
}
