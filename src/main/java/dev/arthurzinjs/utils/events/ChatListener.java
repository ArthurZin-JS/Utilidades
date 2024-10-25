package dev.arthurzinjs.utils.events;

import dev.arthurzinjs.utils.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChatListener implements Listener {
    private final Main plugin;
    private final HashMap<UUID, Long> lastMessageTime = new HashMap<>();
    private final HashMap<UUID, Boolean> chatEnabled = new HashMap<>();

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (chatEnabled.containsKey(player.getUniqueId()) && !chatEnabled.get(player.getUniqueId())) {
            player.sendMessage("§cSeu chat está desativado.");
            event.setCancelled(true);
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (lastMessageTime.containsKey(player.getUniqueId()) && currentTime - lastMessageTime.get(player.getUniqueId()) < 1000) {
            player.sendMessage("§cPor favor, não envie mensagens tão rápido!");
            event.setCancelled(true);
            return;
        }

        lastMessageTime.put(player.getUniqueId(), currentTime);
        event.setFormat("§7[§a" + player.getDisplayName() + "§7] §f" + event.getMessage());
    }

    public void toggleChat(Player player) {
        boolean isEnabled = chatEnabled.getOrDefault(player.getUniqueId(), true);
        chatEnabled.put(player.getUniqueId(), !isEnabled);
        player.sendMessage("§eChat " + (isEnabled ? "desativado" : "ativado") + ".");
    }
}
