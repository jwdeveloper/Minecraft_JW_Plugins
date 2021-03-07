package MC_Math;

import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Math_Manager
{



    public static int MAX(int a , int b) {
        return  (((a) > (b)) ? (a) : (b));
    }

    public static int ABS(int a) {
        return (((a)<0) ? -(a) : (a));
    }

    public static int ZSGN(int a) {
        return (((a)<0) ? -1 : (a)>0 ? 1 : 0);
    }



    public  static  ArrayList<Vector> DrawLine(Vector v1,Vector v2)
    {
        ArrayList<Vector> result = new ArrayList<>();
        int x1, y1, x2, y2, z1, z2;
        x1 = v1.getBlockX();
        y1 = v1.getBlockY();
        z1 = v1.getBlockZ();

        x2 = v2.getBlockX();
        y2 = v2.getBlockY();
        z2 = v2.getBlockZ();


        int xd, yd, zd;
        int x, y, z;
        int ax, ay, az;
        int sx, sy, sz;
        int dx, dy, dz;

        dx = x2 - x1;
        dy = y2 - y1;
        dz = z2 - z1;

        ax = ABS(dx) << 1;
        ay = ABS(dy) << 1;
        az = ABS(dz) << 1;

        sx = ZSGN(dx);
        sy = ZSGN(dy);
        sz = ZSGN(dz);

        x = x1;
        y = y1;
        z = z1;

        if (ax >= MAX(ay, az))            /* x dominant */
        {
            yd = ay - (ax >> 1);
            zd = az - (ax >> 1);
            for (;;)
            {
                result.add(new Vector(x,y,z));
                if (x == x2)
                {
                    break;
                }

                if (yd >= 0)
                {
                    y += sy;
                    yd -= ax;
                }

                if (zd >= 0)
                {
                    z += sz;
                    zd -= ax;
                }

                x += sx;
                yd += ay;
                zd += az;
            }
        }
        else if (ay >= MAX(ax, az))            /* y dominant */
        {
            xd = ax - (ay >> 1);
            zd = az - (ay >> 1);
            for (;;)
            {
                result.add(new Vector(x,y,z));
                if (y == y2)
                {
                    break;
                }

                if (xd >= 0)
                {
                    x += sx;
                    xd -= ay;
                }

                if (zd >= 0)
                {
                    z += sz;
                    zd -= ay;
                }

                y += sy;
                xd += ax;
                zd += az;
            }
        }
        else if (az >= MAX(ax, ay))            /* z dominant */
        {
            xd = ax - (az >> 1);
            yd = ay - (az >> 1);
            for (;;)
            {
                result.add(new Vector(x,y,z));
                if (z == z2)
                {
                    break;
                }

                if (xd >= 0)
                {
                    x += sx;
                    xd -= az;
                }

                if (yd >= 0)
                {
                    y += sy;
                    yd -= az;
                }

                z += sz;
                xd += ax;
                yd += ay;
            }
        }
     return result;
    }

    public static void DrawLine(Vector v1, Vector v2, Consumer<Vector> point3d)
    {
        Bukkit.getScheduler().runTask(Main.instance,() ->
        {
            int x1, y1, x2, y2, z1, z2;
            x1 = v1.getBlockX();
            y1 = v1.getBlockY();
            z1 = v1.getBlockZ();

            x2 = v2.getBlockX();
            y2 = v2.getBlockY();
            z2 = v2.getBlockZ();


            int xd, yd, zd;
            int x, y, z;
            int ax, ay, az;
            int sx, sy, sz;
            int dx, dy, dz;

            dx = x2 - x1;
            dy = y2 - y1;
            dz = z2 - z1;

            ax = ABS(dx) << 1;
            ay = ABS(dy) << 1;
            az = ABS(dz) << 1;

            sx = ZSGN(dx);
            sy = ZSGN(dy);
            sz = ZSGN(dz);

            x = x1;
            y = y1;
            z = z1;

            if (ax >= MAX(ay, az))            /* x dominant */
            {
                yd = ay - (ax >> 1);
                zd = az - (ax >> 1);
                for (;;)
                {
                    point3d.accept(new Vector(x,y,z));
                    if (x == x2)
                    {
                        break;
                    }

                    if (yd >= 0)
                    {
                        y += sy;
                        yd -= ax;
                    }

                    if (zd >= 0)
                    {
                        z += sz;
                        zd -= ax;
                    }

                    x += sx;
                    yd += ay;
                    zd += az;
                }
            }
            else if (ay >= MAX(ax, az))            /* y dominant */
            {
                xd = ax - (ay >> 1);
                zd = az - (ay >> 1);
                for (;;)
                {
                    point3d.accept(new Vector(x,y,z));
                    if (y == y2)
                    {
                        break;
                    }

                    if (xd >= 0)
                    {
                        x += sx;
                        xd -= ay;
                    }

                    if (zd >= 0)
                    {
                        z += sz;
                        zd -= ay;
                    }

                    y += sy;
                    xd += ax;
                    zd += az;
                }
            }
            else if (az >= MAX(ax, ay))            /* z dominant */
            {
                xd = ax - (az >> 1);
                yd = ay - (az >> 1);
                for (;;)
                {
                    point3d.accept(new Vector(x,y,z));
                    if (z == z2)
                    {
                        break;
                    }

                    if (xd >= 0)
                    {
                        x += sx;
                        xd -= az;
                    }

                    if (yd >= 0)
                    {
                        y += sy;
                        yd -= az;
                    }

                    z += sz;
                    xd += ax;
                    yd += ay;
                }
            }
        });



    }




    public static ArrayList<Vector> locationAtRadious( Vector center, double radious)
    {
        ArrayList<Vector> result = new ArrayList<>();
        double center_x = center.getX();
        double center_y = center.getY();
        double center_z = center.getZ();
        for(float i=1;i<=360;i+=0.1f)
        {
            double px = center_x + radious*Math.cos(i);
            double pz = center_z + radious* Math.sin(i);
            double py = center_y + radious* Math.sin(i);
            //    double py = center_y + Math.floor(radious* 1/Math.abs(Math.cos(i)+1.5))-0.4;
            // double py = center_y + Math.floor(radious* -1*Math.abs(Math.sin(i-1.5))+1);
            result.add(new Vector(px,py,pz));
        }
        return result;
    }
    public static ArrayList<Location> locationAtRadious(boolean filled,Location center,double radious)
    {
       ArrayList<Location> result = new ArrayList<>();
        double center_x = center.getX();
        double center_y = center.getY();
        double center_z = center.getZ();
        World world = center.getWorld();

       for(float i=1;i<=360;i+=0.1f)
       {
           double px = center_x + Math.floor(radious*Math.cos(i));
           double pz = center_z + Math.floor(radious* Math.sin(i));
           double py = i/90;
       //    double py = center_y + Math.floor(radious* 1/Math.abs(Math.cos(i)+1.5))-0.4;
        // double py = center_y + Math.floor(radious* -1*Math.abs(Math.sin(i-1.5))+1);
           result.add(new Location(world,(int)px,(int)py,(int)pz));
       }
        return result;
    }
    public static ArrayList<Location> locationAtRectangle(boolean filled,Location l1,Location l2,Location l3)
    {
        return new ArrayList<>();
    }
    public static ArrayList<Location> locationAtTriangle(boolean filled,Location l1,Location l2,Location l3)
    {
        return new ArrayList<>();
    }






}
