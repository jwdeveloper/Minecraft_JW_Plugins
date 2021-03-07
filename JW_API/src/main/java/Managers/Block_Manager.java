package Managers;


import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Block_Manager
{
    private static int[] $SWITCH_TABLE$org$bukkit$World$Environment;





    public static List<Block> generateCuboidRegion(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<>();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    if(block.getType() == Material.BEDROCK) //RIGHT HERE :P DONT MISS THIS!
                        blocks.add(block);
                }
            }
        }
        return blocks;
    }
    public static int i=0;
    public static void generateCuboidRegion_task(Material material,Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<>();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0],()->
        {

            if(i >= blocks.size())
                return;

              blocks.get(i).setType(material);
          i++;
        },1l,1l);


    };
    public static Particle[] particles = Particle.values();
    public static World world = Bukkit.getWorlds().get(0);
    public static void ParticleEffect_task(Particle effect, Location loc1, Location loc2) {
        List<Location> locations = new ArrayList<>();
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    locations.add(new Location(world,x,y,z));
                }
            }
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0],()->
        {

            if(i>=particles.length)
                return;
            try
            {
                for(Location loc :locations)
                {
                    world.spawnParticle(particles[i],loc,5);
                }
            }catch (Exception e)
            {

            }

             Bukkit.getConsoleSender().sendMessage(particles[i].toString());
          i++;
        },1l,30l);


    };


    public static Collection<ItemStack> getEquipmentGuaranteedToDrop(LivingEntity entity) {
        ArrayList<ItemStack> itemsThatWillDrop = new ArrayList();
        EntityEquipment equipment = entity.getEquipment();
        if (equipment.getItemInMainHandDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getItemInMainHand());
        }

        if (equipment.getItemInOffHandDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getItemInOffHand());
        }

        if (equipment.getChestplateDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getChestplate());
        }

        if (equipment.getLeggingsDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getLeggings());
        }

        if (equipment.getHelmetDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getHelmet());
        }

        if (equipment.getBootsDropChance() >= 1.0F) {
            itemsThatWillDrop.add(equipment.getBoots());
        }

        return itemsThatWillDrop;
    }

    public static Collection<Advancement> getVanillaAdvancements(Player p) {
        return (Collection)StreamSupport.stream(Spliterators.spliteratorUnknownSize(p.getServer().advancementIterator(), 1024), true).filter((adv) -> {
            return adv.getKey().getNamespace().equals("minecraft") && p.getAdvancementProgress(adv).isDone();
        }).collect(Collectors.toList());
    }

    public static Collection<Advancement> getVanillaAdvancements(Collection<String> include) {
        return (Collection) StreamSupport.stream(Spliterators.spliteratorUnknownSize(Bukkit.getServer().advancementIterator(), 1024), true).filter((adv) -> {
            int i = adv.getKey().getKey().indexOf(47);
            return i != -1 && adv.getKey().getNamespace().equals("minecraft") && include.contains(adv.getKey().getKey().substring(0, i));
        }).collect(Collectors.toList());
    }

    public static Collection<Advancement> getVanillaAdvancements(Player p, Collection<String> include) {
        return (Collection)getVanillaAdvancements(p).stream().filter((adv) -> {
            int i = adv.getKey().getKey().indexOf(47);
            return i != -1 && include.contains(adv.getKey().getKey().substring(0, i));
        }).collect(Collectors.toList());
    }

    public static int maxCapacity(Inventory inv, Material item) {
        int sum = 0;
        ItemStack[] var6;
        int var5 = (var6 = inv.getContents()).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            ItemStack i = var6[var4];
            if (i != null && i.getType() != Material.AIR) {
                if (i.getType() == item) {
                    sum += item.getMaxStackSize() - i.getAmount();
                }
            } else {
                sum += item.getMaxStackSize();
            }
        }

        return sum;
    }

    public static String getRegionFolder(World world) {
        switch($SWITCH_TABLE$org$bukkit$World$Environment()[world.getEnvironment().ordinal()]) {
            case 1:
                return "./" + world.getName() + "/region/";
            case 2:
                return "./" + world.getName() + "/DIM-1/region/";
            case 3:
                return "./" + world.getName() + "/DIM1/region/";
            default:
                return null;
        }
    }

    public static double crossDimensionalDistanceSquared(Location a, Location b) {
        if (a != null && b != null) {
            if (a.getWorld().getUID().equals(b.getWorld().getUID())) {
                return a.distanceSquared(b);
            } else if (a.getWorld().getEnvironment() != World.Environment.THE_END && b.getWorld().getEnvironment() != World.Environment.THE_END) {
                if (!a.getWorld().getName().startsWith(b.getWorld().getName()) && !b.getWorld().getName().startsWith(a.getWorld().getName())) {
                    return Double.MAX_VALUE;
                } else {
                    return a.getWorld().getEnvironment() == World.Environment.NETHER ? (new Location(b.getWorld(), a.getX() * 8.0D, a.getY(), a.getZ() * 8.0D)).distanceSquared(b) : (new Location(a.getWorld(), b.getX() * 8.0D, b.getY(), b.getZ() * 8.0D)).distanceSquared(a);
                }
            } else {
                return Double.MAX_VALUE;
            }
        } else {
            return Double.MAX_VALUE;
        }
    }

    public static ArrayList<Player> getNearbyPlayers(Location loc, int max_dist, boolean allowCrossDimension) {
        max_dist *= max_dist;
        ArrayList<Player> ppl = new ArrayList();
        Iterator var5 = Bukkit.getServer().getOnlinePlayers().iterator();

        while(var5.hasNext()) {
            Player p = (Player)var5.next();
            double dist = allowCrossDimension ? crossDimensionalDistanceSquared(p.getLocation(), loc) : (p.getWorld().getUID().equals(loc.getWorld().getUID()) ? p.getLocation().distanceSquared(loc) : Double.MAX_VALUE);
            if (dist < (double)max_dist) {
                ppl.add(p);
            }
        }

        return ppl;
    }

    public static Location getClosestBlock(Location start, int MAX_DIST, Function<Block, Boolean> test) {
        if ((Boolean)test.apply(start.getBlock())) {
            return start;
        } else {
            World w = start.getWorld();
            int cX = start.getBlockX();
            int cY = start.getBlockY();
            int cZ = start.getBlockZ();

            for(int dist = 1; dist <= MAX_DIST; ++dist) {
                int mnX = cX - dist;
                int mxX = cX + dist;
                int mnZ = cZ - dist;
                int mxZ = cZ + dist;
                int mnY = Math.max(cY - dist, 0);
                int mxY = Math.min(cY + dist, 256);

                int x;
                int y;
                for(x = mxY; x >= mnY; --x) {
                    for(y = mnZ; y <= mxZ; ++y) {
                        if ((Boolean)test.apply(w.getBlockAt(mnX, x, y))) {
                            return new Location(w, (double)mnX, (double)x, (double)y);
                        }

                        if ((Boolean)test.apply(w.getBlockAt(mxX, x, y))) {
                            return new Location(w, (double)mxX, (double)x, (double)y);
                        }
                    }
                }

                for(x = mnX; x <= mxX; ++x) {
                    for(y = mnZ; y <= mxZ; ++y) {
                        if ((Boolean)test.apply(w.getBlockAt(x, mnY, y))) {
                            return new Location(w, (double)x, (double)mnY, (double)y);
                        }

                        if ((Boolean)test.apply(w.getBlockAt(x, mxY, y))) {
                            return new Location(w, (double)x, (double)mxY, (double)y);
                        }
                    }
                }

                for(x = mnX; x <= mxX; ++x) {
                    for(y = mxY; y >= mnY; --y) {
                        if ((Boolean)test.apply(w.getBlockAt(x, y, mnZ))) {
                            return new Location(w, (double)x, (double)y, (double)mnZ);
                        }

                        if ((Boolean)test.apply(w.getBlockAt(x, y, mxZ))) {
                            return new Location(w, (double)x, (double)y, (double)mxZ);
                        }
                    }
                }
            }

            return null;
        }
    }

    public static List<Block> getConnectedBlocks(Block block0, Function<Block, Boolean> test, List<BlockFace> dirs, int MAX_SIZE) {
        HashSet<Block> visited = new HashSet();
        List<Block> results = new ArrayList();
        ArrayDeque<Block> toProcess = new ArrayDeque();
        toProcess.addLast(block0);

        while(results.size() < MAX_SIZE && !toProcess.isEmpty()) {
            Block b = (Block)toProcess.pollFirst();
            if (b != null && (Boolean)test.apply(b) && !visited.contains(b)) {
                results.add(b);
                visited.add(b);
                Iterator var9 = dirs.iterator();

                while(var9.hasNext()) {
                    BlockFace dir = (BlockFace)var9.next();
                    toProcess.addLast(b.getRelative(dir));
                }
            }
        }

        return results;
    }

    // $FF: synthetic method
    static int[] $SWITCH_TABLE$org$bukkit$World$Environment() {
        int[] var10000 = $SWITCH_TABLE$org$bukkit$World$Environment;
        if (var10000 != null) {
            return var10000;
        } else {
            int[] var0 = new int[World.Environment.values().length];

            try {
                var0[World.Environment.NETHER.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
            }

            try {
                var0[World.Environment.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
            }

            try {
                var0[World.Environment.THE_END.ordinal()] = 3;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$org$bukkit$World$Environment = var0;
            return var0;
        }
    }
}
