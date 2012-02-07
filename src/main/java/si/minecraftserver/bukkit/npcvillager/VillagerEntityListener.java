/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author Martin
 */
public class VillagerEntityListener implements Listener {

    private VillagerManagerImpl manager;

    public VillagerEntityListener(VillagerManagerImpl manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        CraftEntity e = (CraftEntity) event.getEntity();
        if (e.getHandle() instanceof Villager) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        CraftEntity e = (CraftEntity) event.getEntity();
        if (e.getHandle() instanceof Villager) {
            /*Entity en = event.getEntity();
            Player p;
            if (en instanceof Player) {
                p = (Player) en;
            } else {
                return;
            }
            net.minecraft.server.Entity entity = e.getHandle();
            if (entity instanceof Villager && !event.isCancelled()) {
                Villager v = (Villager) entity;
                v.openVirtualChest(p);
            }
            event.setCancelled(true);*/
            //Bukkit.getServer().broadcastMessage("");
        } else {
            
        }
        Bukkit.getServer().broadcastMessage("Entity -> " + e);
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        CraftEntity e = (CraftEntity) event.getTarget();
        //TODO On left click
    }
}
