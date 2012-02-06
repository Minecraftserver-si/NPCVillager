/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.npcvillager;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import net.minecraft.server.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import si.minecraftserver.bukkit.npcvillager.VillagerPath.Node;

/**
 *
 * @author Martin
 */
public class Villager extends EntityVillager {

    private String name;
    private InventoryLargeChest lc;
    private TileEntityChest ch1;
    private TileEntityChest ch2;
    private VillagerPath path;
    private Node last;
    private Timer timer = new Timer();
    private Location end;
    private int maxIter;

    public void pathFindTo(Location l, int maxIterations) {
        path = new VillagerPath(getBukkitEntity().getLocation(), l, maxIterations);
        end = l;
        maxIter = maxIterations;
        timer.schedule(new movePath(), 300);
    }

    public class movePath extends TimerTask {

        @Override
        public void run() {
            if (path != null) {
                Node n = path.getNextNode();
                Block b = null;
                float angle = yaw;
                float look = pitch;
                if (n != null) {
                    if (last == null || path.checkPath(n, last, true)) {
                        b = n.b;
                        if (last != null) {
                            angle = ((float) Math.toDegrees(Math.atan2(last.b.getX() - b.getX(), last.b.getZ() - b.getZ())));
                            look = (float) (Math.toDegrees(Math.asin(last.b.getY() - b.getY())) / 2);
                        }
                        setPositionRotation(b.getX() + 0.5, b.getY(), b.getZ() + 0.5, angle, look);
                        timer.schedule(new movePath(), 300);
                    } else {
                        pathFindTo(end, maxIter);
                    }
                } else if (last != null) {
                    setPositionRotation(end.getX(), end.getY(), end.getZ(), end.getYaw(), end.getPitch());
                }
                last = n;
            }
        }
    }

    public Villager(String name, World world, int i) {
        super(world, i);
        this.name = name;
        ch1 = new TileEntityChest();
        ch2 = new TileEntityChest();
        lc = new InventoryLargeChest(name, ch1, ch2);
    }

    public Villager(String name, World world) {
        super(world);
        this.name = name;
        ch1 = new TileEntityChest();
        ch2 = new TileEntityChest();
        lc = new InventoryLargeChest(name, ch1, ch2);
    }

    public void openVirtualChest(Player player) {
        CraftPlayer cp = (CraftPlayer) player;
        EntityPlayer ePlayer = cp.getHandle();
        ePlayer.a(lc);
    }

    public void changeSkin(VillagerSkin skin) {
        this.texture = "/mob/villager/villager.png";
        if (skin.getProfession() == 0) {
            this.texture = "/mob/villager/farmer.png";
        }

        if (skin.getProfession() == 1) {
            this.texture = "/mob/villager/librarian.png";
        }

        if (skin.getProfession() == 2) {
            this.texture = "/mob/villager/priest.png";
        }

        if (skin.getProfession() == 3) {
            this.texture = "/mob/villager/smith.png";
        }

        if (skin.getProfession() == 4) {
            this.texture = "/mob/villager/butcher.png";
        }
        try {
            Field fi = EntityVillager.class.getDeclaredField("profession");
            fi.setAccessible(true);
            fi.setInt(this, skin.getProfession());
        } catch (Exception ex) {
            world.getServer().getLogger().log(Level.INFO, null, ex);
        }
    }

    @Override
    protected void m_() {
        //IZKLOPI PREMIKANJE
    }
    //W_() -> FIZIKA
    //d() -> FIZIKA2?

    public String getName() {
        return name;
    }

    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        String s = this.getName();//this.aC();
        if (!this.dead && s != null) {
            return true;
        }
        return false;
    }

    @Override
    public void d(NBTTagCompound nbttagcompound) {
    }
}
