package com.rukluf.kingdom.listeners;

import com.rukluf.kingdom.scoreboard.KingdomScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        KingdomScoreboard.show(e.getPlayer());
    }
}
