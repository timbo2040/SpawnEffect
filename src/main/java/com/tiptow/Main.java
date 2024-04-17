package com.tiptow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PermanentEffectPlugin has been enabled!");
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("PermanentEffectPlugin has been disabled!");
    }

    private static class JoinListener implements Listener {
        private final Main plugin;

        public JoinListener(Main plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            FileConfiguration config = plugin.getConfig();
            String effectName = config.getString("effect");
            PotionEffectType effectType = PotionEffectType.getByName(effectName);

            if (effectType != null) {
                int duration = config.getInt("duration");
                int amplifier = config.getInt("amplifier");

                player.addPotionEffect(new PotionEffect(effectType, duration, amplifier, true, false));
                player.sendMessage(ChatColor.GREEN + "You've been given a permanent " + effectName + " effect!");
            } else {
                plugin.getLogger().warning("Invalid effect name in config.yml: " + effectName);
            }
        }
    }
}
