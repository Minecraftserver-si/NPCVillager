/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author Martin
 */
public class VillagerEntityListener extends EntityListener {

    private VillagerManagerImpl manager;

    public VillagerEntityListener(VillagerManagerImpl manager) {
        this.manager = manager;
    }

    @Override
    public void onEntityCombust(EntityCombustEvent event) {
        CraftEntity e = (CraftEntity) event.getEntity();
        if (e.getHandle() instanceof Villager) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        CraftEntity e = (CraftEntity) event.getEntity();
        if (e.getHandle() instanceof Villager) {
            Entity en = event.getEntity();
            Player p = null;
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
            event.setCancelled(true);
        }
    }

    @Override
    public void onEntityTarget(EntityTargetEvent event) {
        CraftEntity e = (CraftEntity) event.getTarget();
        
    }
}
