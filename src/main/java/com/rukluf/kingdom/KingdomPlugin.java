package com.rukluf.kingdom;

import com.rukluf.kingdom.commands.KingdomCommand;
import com.rukluf.kingdom.listeners.ChatListener;
import com.rukluf.kingdom.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class KingdomPlugin extends JavaPlugin {

    private static KingdomPlugin instance;

    public static KingdomPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getCommand("kingdom").setExecutor(new KingdomCommand());

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getLogger().info("✅ Kingdom plugin geladen");
    }
}
