/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import org.bukkit.Chunk;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldUnloadEvent;

/**
 *
 * @author Martin
 */
public class VillagerWorldListener extends WorldListener {

    private VillagerManagerImpl manager;

    public VillagerWorldListener(VillagerManagerImpl manager) {
        this.manager = manager;
    }

    @Override
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        manager.chunkLoad(chunk);
    }

    @Override
    public void onWorldUnload(WorldUnloadEvent event) {
        if (!event.isCancelled()) {
            manager.despawnAll(event.getWorld());
        }
    }
}
