package dev.arthurzinjs.utils.cmd;

import dev.arthurzinjs.utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendCommand implements CommandExecutor {

    private final Main plugin;

    public FriendCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Comando apenas para jogadores.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2 || !args[0].equalsIgnoreCase("add")) {
            player.sendMessage("§cUso correto: /amigo add <nome>");
            return true;
        }

        String friendName = args[1];
        addFriend(player.getName(), friendName);
        player.sendMessage("§aAmigo " + friendName + " adicionado!");
        return true;
    }

    private void addFriend(String playerName, String friendName) {
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO amigos (jogador, amigo) VALUES (?, ?)")) {
            ps.setString(1, playerName);
            ps.setString(2, friendName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
