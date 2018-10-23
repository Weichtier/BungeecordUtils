package de.pixeltitan.bungeeutils.utils;

import de.pixeltitan.bungeeutils.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamchatUtils {

    private static List<ProxiedPlayer> list = new ArrayList<>();
    private static String prefix = "§8 §aTC §8| §7";


    public static void login(ProxiedPlayer p){
        if(!list.contains(p)){
            list.add(p);
            Main.getCore().getSql().runSql2("UPDATE loginstate SET teamchat='1' WHERE uuid='" + p.getUniqueId() + "' ");
            p.sendMessage(new TextComponent(getPrefix() + "§6Du hast dich §aeingeloggt"));
            for (ProxiedPlayer all : list){
                if(all != p){
                    all.sendMessage(new TextComponent(getPrefix() + "§6" + p.getName() + " §ehat sich §aeingeloggt!"));
                }
            }
        }
    }

    public static void logout(ProxiedPlayer p){
        if(list.contains(p)){
            list.remove(p);
            Main.getCore().getSql().runSql2("UPDATE loginstate SET teamchat='0' WHERE uuid='" + p.getUniqueId() + "' ");
            p.sendMessage(new TextComponent(getPrefix() + "§6Du hast dich §causgeloggt"));
            for (ProxiedPlayer all : list){
                if(all != p){
                    all.sendMessage(new TextComponent(getPrefix() + "§6" + p.getName() + " §ehat sich §causgeloggt!"));
                }
            }
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public static List<ProxiedPlayer> getList() {
        return list;
    }
}
