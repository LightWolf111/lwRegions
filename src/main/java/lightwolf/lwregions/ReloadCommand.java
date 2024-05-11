package lightwolf.lwregions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!sender.hasPermission("lwregions.reload")) {
         sender.sendMessage(LwRegions.getInstance().getConfigManager().NO_PERMISSIONS);
         return true;
      } else if (args.length == 0) {
         sender.sendMessage(LwRegions.getInstance().getConfigManager().NOT_ENOUGH_ARGS);
         return true;
      } else {
         if (args[0].equalsIgnoreCase("reload")) {
            LwRegions.getInstance().reload();
            sender.sendMessage(LwRegions.getInstance().getConfigManager().CONFIG_RELOADED);
         } else {
            sender.sendMessage(LwRegions.getInstance().getConfigManager().COMMAND_USAGE);
         }

         return true;
      }
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length == 1) {
         List<String> completions = new ArrayList<>();
         completions.add("reload");
         return completions;
      } else {
         return null;
      }
   }
}
