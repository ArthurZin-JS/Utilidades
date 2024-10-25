package dev.arthurzinjs.utils.cmd;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Comando apenas para jogadores.");
            return true;
        }

        Player player = (Player) sender;
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage("§aGamemode alterado para Criativo!");
        return true;
    }
}
