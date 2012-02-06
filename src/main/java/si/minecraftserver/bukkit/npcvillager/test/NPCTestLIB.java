package si.minecraftserver.bukkit.npcvillager.test;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import si.minecraftserver.bukkit.npcvillager.VillagerManagerImpl;
import si.minecraftserver.bukkit.npcvillager.VillagerSkin;
import si.minecraftserver.bukkit.npcvillager.api.VillagerManager;

public class NPCTestLIB extends JavaPlugin {

    private VillagerManager villagerManager;

    @Override
    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }

    @Override
    public void onEnable() {
        villagerManager = new VillagerManagerImpl(this);
        System.out.println(this + " is now enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();
        boolean isPlayer = sender instanceof Player;
        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Samo player lako spawn NPC");
            return true;
        }
        if (cmd.equalsIgnoreCase("npc")) {
            if (args.length >= 1) {
                String subcmd = args[0];
                if (subcmd.equalsIgnoreCase("spawn")) {
                    if (args.length >= 2) {
                        if (args.length == 3) {
                            villagerManager.spawn(args[1], (Player) sender, VillagerSkin.valueOf(args[2]));
                        } else {
                            villagerManager.spawn(args[1], (Player) sender);
                        }
                        sender.sendMessage(ChatColor.GREEN + "NPC: " + args[0]);
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Premalo argumentov");
                    return true;
                } else if (subcmd.equalsIgnoreCase("despawn")) {
                    if (args.length >= 2) {
                        if (villagerManager.despawn(args[1], (Player) sender)) {
                            sender.sendMessage(ChatColor.GREEN + "NPC: " + args[1]);
                            return true;
                        }
                        sender.sendMessage(ChatColor.RED + "Napacno ime NPC-ja!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Premalo argumentov");
                    return true;
                } else if (subcmd.equalsIgnoreCase("move")) {
                    if (args.length >= 2) {
                        if (args.length == 3) {
                            try {
                                villagerManager.findPath(args[1], ((Player) sender).getLocation(), Integer.valueOf(args[2]));
                                sender.sendMessage(ChatColor.GREEN + "Moving NPC: " + args[1]);
                                return true;
                            } catch (NumberFormatException ex) {
                                sender.sendMessage(ChatColor.RED + args[2] + " ni stevilo");
                                return true;
                            }
                        } else {
                            villagerManager.findPath(args[1], ((Player) sender).getLocation());
                            sender.sendMessage(ChatColor.GREEN + "Moving NPC: " + args[1]);
                            return true;
                        }
                    }
                    sender.sendMessage(ChatColor.RED + "Premalo argumentov");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Premalo argumentov");
            return true;
        }
        return false;
    }
}
