package de.pixeltitan.bungeeutils;

import de.pixeltitan.bungeeutils.sql.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Core {

    private Plugin instance;
    private MySQL sql;
    private File cfgfile;
    private Configuration cfg;

    public Core(Plugin instance){
        this.instance = instance;

        cfgfile = new File(Main.getInstance().getDataFolder() + File.separator, "config.yml");

        if(!cfgfile.exists()){
            cfgfile.getParentFile().mkdirs();
            try {
                cfgfile.createNewFile();

                cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(cfgfile);
                cfg.set("Config.Mysql.Host", "localhost");
                cfg.set("Config.Mysql.User", "root");
                cfg.set("Config.Mysql.Password", "password");
                cfg.set("Config.Mysql.Database", "bungeeutils");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, cfgfile);

                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(cfgfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String host = cfg.getString("Config.Mysql.Host");
        String db = cfg.getString("Config.Mysql.Database");
        String user = cfg.getString("Config.Mysql.User");
        String password = cfg.getString("Config.Mysql.Password");
        sql = new MySQL(host, db, user, password);

        if(!sql.isConnected()){
            ProxyServer.getInstance().getLogger().warning("Database Connection failed! Reason: " + sql.getConnErr());
            return;
        }
        sql.runSql2("CREATE TABLE IF NOT EXISTS `loginstate` (\n" +
                "  `uuid` varchar(36) NOT NULL,\n" +
                "  `report` int(1) NOT NULL,\n" +
                "  `teamchat` int(1) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        sql.runSql2("CREATE TABLE IF NOT EXISTS `reports` (\n" +
                " `id` int(11) NOT NULL,\n" +
                " `reporter` varchar(36) NOT NULL,\n" +
                " `reported` varchar(36) NOT NULL,\n" +
                " `reason` varchar(100) NOT NULL,\n" +
                " `server` varchar(40) NOT NULL,\n" +
                " PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1");

    }

    public void registerPlayer(ProxiedPlayer p) throws SQLException {
        UUID uuid = p.getUniqueId();
        ResultSet rs = sql.runSql("SELECT * FROM loginstate WHERE uuid = '" + uuid + "'");
        boolean registered = false;
        if(rs.next()){
            registered = true;
        }

        if(!registered){
            sql.runSql2("INSERT INTO loginstate (uuid, report, teamchat) VALUES ('" + uuid + "', '0', '0')");
        }
    }

    public MySQL getSql() {
        return sql;
    }

    public Configuration getConfig() {
        return cfg;
    }
}
