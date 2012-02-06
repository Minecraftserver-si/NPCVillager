/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListener;

/**
 *
 * @author Martin
 */
public class VillagerServerListener extends ServerListener {
    private VillagerManagerImpl manager;

    public VillagerServerListener(VillagerManagerImpl manager) {
        this.manager = manager;
    }
    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if(manager.getPlugin() == event.getPlugin()){
            manager.despawnAll();
        }
    }
    
}
