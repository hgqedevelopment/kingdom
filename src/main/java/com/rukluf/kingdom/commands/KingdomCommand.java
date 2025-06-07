// === KingdomCommand.java ===
// Gemaakt door: HGQE - Valentino
// Discord: hgqe_development
// Voor: VortexKingdom

package com.rukluf.kingdom.commands;

import com.rukluf.kingdom.core.KingdomManager;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class KingdomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage("§e§m-----------§6 Kingdom Help §e§m-----------");
            player.sendMessage("§fPlugin door HGQE (Valentino) - Discord: hgqe_development");
            player.sendMessage("§7/kingdom create <naam> §8- Maak een nieuw kingdom aan");
            player.sendMessage("§7/kingdom invite <speler> §8- Nodig een speler uit voor je kingdom");
            player.sendMessage("§7/kingdom accept §8- Accepteer een uitnodiging voor een kingdom");
            player.sendMessage("§7/kingdom leave §8- Verlaat je huidige kingdom");
            player.sendMessage("§7/kingdom setrank <speler> <rank> §8- Verander de rank van een speler");
            player.sendMessage("§7/kingdom war <kingdom> §8- Start oorlog met een ander kingdom");
            player.sendMessage("§7/kingdom setlocation §8- Stel de home locatie van je kingdom in");
            player.sendMessage("§7/kingdom home §8- Teleporteer naar je kingdom home");
            player.sendMessage("§7/kingdom info [kingdom] §8- Toon informatie over een kingdom");
            player.sendMessage("§7/kingdom list §8- Bekijk alle bestaande kingdoms");
            player.sendMessage("§7/kingdom kick <speler> §8- Verwijder een speler uit je kingdom");
            player.sendMessage("§7/kingdom disband §8- Verwijder je kingdom (alleen koning)");
            player.sendMessage("§7/kingdom chat <aan/uit> §8- Zet kingdom chat aan of uit");
            return true;
        }

        if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            KingdomManager.createKingdom(args[1], player);
            player.sendMessage("§aKingdom '" + args[1] + "' is aangemaakt!");
            return true;
        }

        if (args[0].equalsIgnoreCase("invite") && args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                KingdomManager.invitePlayer(player, target);
                player.sendMessage("§aInvite verstuurd naar " + target.getName());
            } else {
                player.sendMessage("§cSpeler niet gevonden.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("accept")) {
            if (KingdomManager.acceptInvite(player)) {
                player.sendMessage("§aJe bent nu lid van het kingdom!");
            } else {
                player.sendMessage("§cJe hebt geen uitnodiging.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("leave")) {
            KingdomManager.leaveKingdom(player);
            player.sendMessage("§cJe hebt je kingdom verlaten.");
            return true;
        }

        if (args[0].equalsIgnoreCase("setrank") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                KingdomManager.setRank(player, target, args[2]);
                player.sendMessage("§aRank van " + target.getName() + " is nu: " + args[2]);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("war") && args.length == 2) {
            String own = KingdomManager.getKingdom(player);
            String enemy = args[1];
            KingdomManager.setWar(own, enemy);
            player.sendMessage("§cJe bent nu in oorlog met " + enemy);
            return true;
        }

        if (args[0].equalsIgnoreCase("setlocation")) {
            KingdomManager.setLocation(player);
            player.sendMessage("§aLocatie opgeslagen als kingdom home.");
            return true;
        }

        if (args[0].equalsIgnoreCase("home")) {
            Location loc = KingdomManager.getHome(player);
            if (loc != null) {
                player.teleport(loc);
                player.sendMessage("§aTeleporteer naar kingdom home.");
            } else {
                player.sendMessage("§cGeen home locatie ingesteld.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            String targetKingdom = args.length == 2 ? args[1] : KingdomManager.getKingdom(player);
            if (targetKingdom == null) {
                player.sendMessage("§cGeen kingdom gevonden.");
                return true;
            }
            player.sendMessage("§6Informatie over: §e" + targetKingdom);
            player.sendMessage("§7Members: " + KingdomManager.getMembers(targetKingdom));
            player.sendMessage("§7Influence: " + KingdomManager.getInfluence(targetKingdom));
            player.sendMessage("§7Oorlog met: " + KingdomManager.getWar(targetKingdom));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage("§eAlle kingdoms:");
            for (String name : KingdomManager.getAllKingdoms()) {
                player.sendMessage("§7- " + name);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("kick") && args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null && KingdomManager.sameKingdom(player, target)) {
                KingdomManager.leaveKingdom(target);
                player.sendMessage("§c" + target.getName() + " is gekicked uit het kingdom.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("disband")) {
            if (KingdomManager.isKing(player)) {
                KingdomManager.disband(player);
                player.sendMessage("§cJe kingdom is verwijderd.");
            } else {
                player.sendMessage("§cAlleen de koning kan het kingdom verwijderen.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("chat") && args.length == 2) {
            boolean toggle = args[1].equalsIgnoreCase("aan");
            KingdomManager.setChatToggle(player, toggle);
            player.sendMessage("§aKingdom chat is nu " + (toggle ? "aan" : "uit"));
            return true;
        }

        return true;
    }
}
