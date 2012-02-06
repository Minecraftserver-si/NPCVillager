/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import si.minecraftserver.bukkit.npcvillager.Villager;
import si.minecraftserver.bukkit.npcvillager.VillagerSkin;


/**
 *
 * @author Martin
 */
public interface VillagerManager {

    public Villager spawn(String name, Player player, VillagerSkin skin);

    public Villager spawn(String name, Player player);

    public Villager get(String name);

    public boolean despawn(String string, Player player);

    public void findPath(String villager, Location loc);

    public void findPath(String villager, Location loc, int maxIterations);
}
