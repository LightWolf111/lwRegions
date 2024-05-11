package lightwolf.lwregions.utils;

import lightwolf.lwregions.LwRegions;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigManager {
   private final Map<String, String> regions = new HashMap();
   public final String NO_PERMISSIONS;
   public final String CONFIG_RELOADED;
   public final String COMMAND_USAGE;
   public final String NOT_ENOUGH_ARGS;
   public final String GLOBAL;
   public final String FORMAT;
   public final String OWNER;
   public final String SPAWN;
   public final int UPDATE_TIME;

   public ConfigManager(FileConfiguration configuration) {
      Iterator var2 = configuration.getConfigurationSection("custom-regions").getKeys(false).iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         this.regions.put(key, LwRegions.color(configuration.getString("custom-regions." + key)));
      }

      this.NO_PERMISSIONS = LwRegions.color(configuration.getString("messages.no-permissions"));
      this.CONFIG_RELOADED = LwRegions.color(configuration.getString("messages.config-reloaded"));
      this.COMMAND_USAGE = LwRegions.color(configuration.getString("messages.reload-usage"));
      this.NOT_ENOUGH_ARGS = LwRegions.color(configuration.getString("messages.not-enough-args"));
      this.GLOBAL = LwRegions.color(configuration.getString("global"));
      this.FORMAT = LwRegions.color(configuration.getString("format"));
      this.OWNER = LwRegions.color(configuration.getString("owner"));
      this.SPAWN = LwRegions.color(configuration.getString("spawn"));
      this.UPDATE_TIME = configuration.getInt("settings.update_time");
   }

   public Map<String, String> getRegions() {
      return this.regions;
   }
}
