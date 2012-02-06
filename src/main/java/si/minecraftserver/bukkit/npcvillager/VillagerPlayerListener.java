/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import java.util.Collection;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;

/**
 *
 * @author Martin
 */
public class VillagerPlayerListener extends PlayerListener {

    private VillagerManagerImpl manager;

    public VillagerPlayerListener(VillagerManagerImpl manager) {
        this.manager = manager;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Player p = event.getPlayer();
        Location l = p.getLocation();
        if (event.isCancelled()) {
            return;
        }

        List<Entity> list = p.getNearbyEntities(l.getX(), l.getY(), l.getZ());
        Collection<Villager> villagers = manager.getVillagers();
        for (Villager v : villagers) {
            if (list.contains(v.getBukkitEntity())) {
                String msg = event.getMessage();
                if (msg.contains(v.getName())) {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "< " + v.getName() + "> Jaz?");
                }
            }
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        CraftEntity e = (CraftEntity) event.getRightClicked();
        Player p = event.getPlayer();
        net.minecraft.server.Entity entity = e.getHandle();
        if(entity instanceof Villager && !event.isCancelled()){
            Villager v = (Villager)entity;
            v.openVirtualChest(p);
        }
    }
}
