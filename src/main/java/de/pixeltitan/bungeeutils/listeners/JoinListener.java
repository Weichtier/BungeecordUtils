package de.pixeltitan.bungeeutils.listeners;

import de.pixeltitan.bungeeutils.Main;
import de.pixeltitan.bungeeutils.utils.ReportUtils;
import de.pixeltitan.bungeeutils.utils.TeamchatUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinListener implements Listener {

    public JoinListener(){
    }

    @EventHandler
    public void onJoin(PostLoginEvent event){

        ProxiedPlayer p = event.getPlayer();

        if(p.hasPermission("pt.teamchat")){
            try {
                Main.getCore().registerPlayer(p);
                ResultSet rs = Main.getCore().getSql().runSql("SELECT * FROM loginstate WHERE uuid='" + p.getUniqueId() + "'");
                if(rs.next()){
                    if(rs.getInt("teamchat") == 1){
                        p.sendMessage(new TextComponent(Main.getPrefix() + "§a§lTeamchat§r§8: §aeingeloggt"));
                        TeamchatUtils.getList().add(p);
                    }else {
                        p.sendMessage(new TextComponent(Main.getPrefix() + "§a§lTeamchat§r§8: §causgeloggt"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
