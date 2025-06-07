package com.rukluf.kingdom.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class KingdomManager {

    private static final Map<UUID, String> playerKingdom = new HashMap<>();
    private static final Map<String, Integer> kingdomInfluence = new HashMap<>();
    private static final Map<UUID, String> playerRank = new HashMap<>();
    private static final Map<UUID, String> pendingInvites = new HashMap<>();
    private static final Map<String, Location> kingdomHomes = new HashMap<>();
    private static final Map<String, String> warStatus = new HashMap<>();
    private static final Map<UUID, Boolean> chatToggle = new HashMap<>();

    public static void createKingdom(String name, Player creator) {
        playerKingdom.put(creator.getUniqueId(), name);
        playerRank.put(creator.getUniqueId(), "Koning");
        kingdomInfluence.put(name, 100);
    }

    public static void invitePlayer(Player admin, Player target) {
        if (!admin.hasPermission("kingdom.admin")) return;
        String kingdom = getKingdom(admin);
        if (kingdom != null) {
            pendingInvites.put(target.getUniqueId(), kingdom);
        }
    }

    public static boolean acceptInvite(Player player) {
        if (pendingInvites.containsKey(player.getUniqueId())) {
            String kingdom = pendingInvites.remove(player.getUniqueId());
            playerKingdom.put(player.getUniqueId(), kingdom);
            playerRank.put(player.getUniqueId(), "Soldaat");
            return true;
        }
        return false;
    }

    public static void leaveKingdom(Player player) {
        playerKingdom.remove(player.getUniqueId());
        playerRank.remove(player.getUniqueId());
    }

    public static void setRank(Player admin, Player target, String rank) {
        if (!admin.hasPermission("kingdom.admin")) return;
        if (sameKingdom(admin, target)) {
            playerRank.put(target.getUniqueId(), rank);
        }
    }

    public static void setWar(String k1, String k2) {
        warStatus.put(k1, k2);
        warStatus.put(k2, k1);
    }

    public static String getWar(String kingdom) {
        return warStatus.getOrDefault(kingdom, "Geen Oorlog");
    }

    public static String getKingdom(Player player) {
        return playerKingdom.getOrDefault(player.getUniqueId(), null);
    }

    public static String getRank(Player player) {
        return playerRank.getOrDefault(player.getUniqueId(), "");
    }

    public static int getInfluence(Player player) {
        String kingdom = getKingdom(player);
        return kingdomInfluence.getOrDefault(kingdom, 0);
    }

    public static int getInfluence(String kingdom) {
        return kingdomInfluence.getOrDefault(kingdom, 0);
    }

    public static void setLocation(Player player) {
        String kingdom = getKingdom(player);
        if (kingdom != null) {
            kingdomHomes.put(kingdom, player.getLocation());
        }
    }

    public static Location getHome(Player player) {
        String kingdom = getKingdom(player);
        return kingdomHomes.getOrDefault(kingdom, null);
    }

    public static String getMembers(String kingdom) {
        List<String> members = new ArrayList<>();
        for (Map.Entry<UUID, String> entry : playerKingdom.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(kingdom)) {
                OfflinePlayer offline = Bukkit.getOfflinePlayer(entry.getKey());
                if (offline != null && offline.getName() != null) {
                    members.add(offline.getName());
                }
            }
        }
        return String.join(", ", members);
    }

    public static List<String> getAllKingdoms() {
        return new ArrayList<>(kingdomInfluence.keySet());
    }

    public static boolean sameKingdom(Player p1, Player p2) {
        String k1 = getKingdom(p1);
        String k2 = getKingdom(p2);
        return k1 != null && k1.equalsIgnoreCase(k2);
    }

    public static boolean isKing(Player player) {
        return getRank(player).equalsIgnoreCase("Koning");
    }

    public static void disband(Player player) {
        String kingdom = getKingdom(player);
        if (kingdom == null) return;
        playerKingdom.entrySet().removeIf(e -> e.getValue().equalsIgnoreCase(kingdom));
        kingdomInfluence.remove(kingdom);
        kingdomHomes.remove(kingdom);
        warStatus.remove(kingdom);
    }

    public static void setChatToggle(Player player, boolean toggle) {
        chatToggle.put(player.getUniqueId(), toggle);
    }

    public static boolean isChatToggled(Player player) {
        return chatToggle.getOrDefault(player.getUniqueId(), false);
    }
}