package lightwolf.lwregions;

import lightwolf.lwregions.utils.ConfigManager;
import lightwolf.lwregions.utils.Metrics;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class LwRegions extends JavaPlugin {
    private static LwRegions instance;
    private ConfigManager configManager;

    public void onEnable() {
        instance = this;
        if (Bukkit.getBukkitVersion().contains("1.12")) {
            Bukkit.getLogger().log(Level.WARNING, "§c[" + this.getName() + "]");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.saveDefaultConfig();
            this.reloadConfig();
               this.configManager = new ConfigManager(this.getConfig());
            RegionsManager.start();

            getCommand("lwregions").setExecutor(new ReloadCommand());
            getCommand("lwregions").setTabCompleter(new ReloadCommand());
            new Metrics(this, 16538);
        }
    }

    public void reload() {
        this.reloadConfig();
        this.configManager = new ConfigManager(this.getConfig());
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendActionBar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(text)));
    }

    public static LwRegions getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }
}
