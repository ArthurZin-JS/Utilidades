package dev.arthurzinjs.utils.cmd;

import dev.arthurzinjs.utils.events.ChatListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    private final ChatListener chatListener;

    public ChatCommand() {
        chatListener = new ChatListener(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Comando apenas para jogadores.");
            return true;
        }

        Player player = (Player) sender;
        chatListener.toggleChat(player);
        return true;
    }
}
