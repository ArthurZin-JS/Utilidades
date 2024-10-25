package dev.arthurzinjs.utils;

import dev.arthurzinjs.utils.cmd.ChatCommand;
import dev.arthurzinjs.utils.cmd.FriendCommand;
import dev.arthurzinjs.utils.cmd.GamemodeCommand;
import dev.arthurzinjs.utils.events.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JavaPlugin {

    private Connection connection;

    @Override
    public void onEnable() {
        setupDatabase();

        getLogger().info("Plugin de Chat Iniciado!");
        this.getCommand("chat").setExecutor(new ChatCommand());
        this.getCommand("gm").setExecutor(new GamemodeCommand());
        this.getCommand("amigo").setExecutor(new FriendCommand(this));
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin de Chat Desativado!");
        closeDatabase();
    }

    private void setupDatabase() {
        File dataFolder = new File(getDataFolder(), "dados.db");
        if (!dataFolder.getParentFile().exists()) {
            dataFolder.getParentFile().mkdirs();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS amigos (jogador TEXT, amigo TEXT)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeDatabase() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
