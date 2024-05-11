package lightwolf.lwregions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.UUID;

public class RegionsManager {
   private static String getRegionName(Player player) {
      ProtectedRegion region = getRegionWithMaxpriority(player.getLocation());
      return region == null ? "__global__" : region.getId();
   }

   private static ProtectedRegion getRegionWithMaxpriority(Location loc) {
      ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld())).getApplicableRegions(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
      if (regions.size() == 0) {
         return null;
      } else {
         long highPriority = -1L;
         ProtectedRegion region = null;
         Iterator var5 = regions.iterator();

         while(var5.hasNext()) {
            ProtectedRegion protectedRegion = (ProtectedRegion)var5.next();
            if ((long)protectedRegion.getPriority() > highPriority) {
               highPriority = (long)protectedRegion.getPriority();
               region = protectedRegion;
            }
         }

         return region;
      }
   }

   private static void check(Player player, String region) {
      ProtectedRegion protectedRegion = getRegionWithMaxpriority(player.getLocation());
      if (protectedRegion != null) {
         UUID uuid = player.getUniqueId();
         String owners = protectedRegion.getOwners().toPlayersString();
         String members = protectedRegion.getMembers().toPlayersString();
         if (getRegionName(player).equals("spawn")) {
            LwRegions.sendActionBar(player, LwRegions.getInstance().getConfigManager().SPAWN.replace("%region", region));
            return;
         }

         Iterator var6 = LwRegions.getInstance().getConfigManager().getRegions().keySet().iterator();

         while(var6.hasNext()) {
            String key = (String)var6.next();
            if (getRegionName(player).equalsIgnoreCase(key)) {
               LwRegions.sendActionBar(player, (String)LwRegions.getInstance().getConfigManager().getRegions().get(key));
               return;
            }
         }

         if (!owners.contains(uuid.toString()) && !members.contains(uuid.toString())) {
            LwRegions.sendActionBar(player, LwRegions.getInstance().getConfigManager().FORMAT.replace("%region", region));
         } else {
            LwRegions.sendActionBar(player, LwRegions.getInstance().getConfigManager().OWNER.replace("%region", region));
         }
      } else {
         LwRegions.sendActionBar(player, LwRegions.getInstance().getConfigManager().GLOBAL);
      }

   }

   private static void run() {
      Iterator var0 = Bukkit.getOnlinePlayers().iterator();

      while(var0.hasNext()) {
         Player players = (Player)var0.next();
         String region = getRegionName(players);
         Iterator var3 = LwRegions.getInstance().getConfigManager().getRegions().keySet().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (region.equalsIgnoreCase(key)) {
               region = (String)LwRegions.getInstance().getConfigManager().getRegions().get(key);
            }
         }

         check(players, region);
      }

   }

   public static void start() {
      int update = LwRegions.getInstance().getConfigManager().UPDATE_TIME;
      Bukkit.getScheduler().scheduleAsyncRepeatingTask(LwRegions.getInstance(), RegionsManager::run, 0L, (long)update * 20L);
   }
}
