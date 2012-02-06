/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.tools.JavaFileManager;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import si.minecraftserver.bukkit.npcvillager.api.VillagerManager;

/**
 *
 * @author Martin
 */
public class VillagerManagerImpl implements VillagerManager{

    private HashMap<String, Villager> villagers;
    private JavaPlugin plugin;
    private Server server;
    private VillagerEntityListener entityListener;
    private VillagerPlayerListener playerListener;

    public VillagerManagerImpl(JavaPlugin plugin) {
        this.plugin = plugin;
        villagers = new HashMap<String, Villager>();
        this.server = plugin.getServer();
        entityListener = new VillagerEntityListener(this);
        playerListener = new VillagerPlayerListener(this);
        server.getPluginManager().registerEvent(Type.ENTITY_COMBUST, entityListener, Priority.Low, plugin);
        server.getPluginManager().registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Low, plugin);
        server.getPluginManager().registerEvent(Type.ENTITY_TARGET, entityListener, Priority.Low, plugin);
        server.getPluginManager().registerEvent(Type.PLAYER_INTERACT_ENTITY, playerListener, Priority.Low, plugin);
        server.getPluginManager().registerEvent(Type.PLAYER_CHAT, playerListener, Priority.Low, plugin);
        try {
            Class c = EntityTypes.class;
            Field[] fs = c.getDeclaredFields();
            System.out.print(fs.length);
            for (Field f : fs) {
                System.out.println(f);
                f.setAccessible(true);
                Map map = (Map) f.get(null);
                if (f.getName().equalsIgnoreCase("a")) {
                    map.put("Villager", Villager.class);
                } else if (f.getName().equalsIgnoreCase("b")) {
                    map.put(Villager.class, "Villager");
                } else if (f.getName().equalsIgnoreCase("c")) {
                    map.put(Integer.valueOf(120), Villager.class);
                } else if (f.getName().equalsIgnoreCase("d")) {
                    map.put(Villager.class, Integer.valueOf(120));
                }
            }
        } catch (Exception ex) {
            server.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Villager spawn(String name, Player player, VillagerSkin skin) {
        WorldServer sW = ((CraftWorld) player.getWorld()).getHandle();
        Villager v = new Villager(name, sW);
        Location l = player.getLocation();
        v.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        sW.addEntity(v, SpawnReason.CUSTOM);
        villagers.put(name, v);
        return v;
    }

    @Override
    public Villager spawn(String name, Player player) {
        return spawn(name, player, VillagerSkin.FARMER);
    }

    @Override
    public Villager get(String name) {
        return villagers.get(name);
    }

    @Override
    public boolean despawn(String string, Player player) {
        WorldServer sW = ((CraftWorld) player.getWorld()).getHandle();
        Villager v = get(string);
        if (v == null) {
            return false;
        }
        sW.removeEntity(v);
        villagers.remove(string);
        return true;
    }

    public void despawnAll() {
        for (Villager v : villagers.values()) {
            v.world.removeEntity(v);
        }
        villagers.clear();
    }

    public void despawnAll(World w) {
        CraftWorld cw = (CraftWorld) w;
        for (Villager v : villagers.values()) {
            if (cw.getHandle().getUUID().compareTo(v.world.getUUID()) == 0) {
                v.world.removeEntity(v);
                villagers.remove(v.getName());
            }
        }
    }

    public void chunkLoad(Chunk chunk) {
        for (Villager npc : villagers.values()) {
            if (npc != null && chunk == npc.getBukkitEntity().getLocation().getBlock().getChunk()) {
                npc.world.addEntity(npc);
            }
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }
    
    public Collection<Villager> getVillagers(){
        return villagers.values();
    }

    public void findPath(String villager, Location loc) {
        findPath(villager, loc, 1500);
    }
    
    public void findPath(String villager, Location loc, int maxIterations) {
        Villager v = get(villager);
        if(v != null) {
            v.pathFindTo(loc, maxIterations);
        }
    }
    
}
