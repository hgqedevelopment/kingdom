package com.rukluf.kingdom.scoreboard;

import com.rukluf.kingdom.core.KingdomManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class KingdomScoreboard {

    public static void show(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kingdom", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.RED + "Informatie");

        String kingdom = KingdomManager.getKingdom(player);
        String rank = KingdomManager.getRank(player);
        int influence = KingdomManager.getInfluence(player);
        String oorlog = "Geen Oorlog";
        String locatie = "[Onbekend]";

        int score = 9;

        obj.getScore("§fChatkanaal").setScore(score--);
        obj.getScore("§b" + (kingdom != null ? kingdom : "Geen")).setScore(score--);
        obj.getScore(" ").setScore(score--);

        obj.getScore("§fInfluence").setScore(score--);
        obj.getScore("§e" + influence).setScore(score--);
        obj.getScore("  ").setScore(score--);

        obj.getScore("§fOorlog?").setScore(score--);
        obj.getScore("§7" + oorlog).setScore(score--);
        obj.getScore("   ").setScore(score--);

        obj.getScore("§fLocatie").setScore(score--);
        obj.getScore("§7" + locatie).setScore(score--);

        obj.getScore("    ").setScore(score--);
        obj.getScore(ChatColor.GRAY + "vortexkingdom.com").setScore(score--);

        player.setScoreboard(board);
    }
}
