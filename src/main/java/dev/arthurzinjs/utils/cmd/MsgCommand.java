package dev.arthurzinjs.utils.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Uso correto: /msg <jogador> <mensagem>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("Jogador não encontrado!");
            return true;
        }

        String message = String.join(" ", args).substring(args[0].length() + 1);
        player.sendMessage("Você para " + target.getName() + ": " + message);
        target.sendMessage(player.getName() + " para você: " + message);

        return true;
    }
}
