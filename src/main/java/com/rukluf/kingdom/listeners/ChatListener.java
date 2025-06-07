package com.rukluf.kingdom.listeners;

import com.rukluf.kingdom.core.KingdomManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String kingdom = KingdomManager.getKingdom(e.getPlayer());
        String rank = KingdomManager.getRank(e.getPlayer());
        if (kingdom != null) {
            e.setFormat("§7[§b" + kingdom + "§7 | §e" + rank + "§7] §f" + e.getPlayer().getName() + ": §r" + e.getMessage());
        }
    }
}
