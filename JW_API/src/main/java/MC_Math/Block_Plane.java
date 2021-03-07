package MC_Math;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Block_Plane
{
    public Vector origin;
    public Vector start;
    public Vector end;
    public float height;
    public float width;

    public float max_width= 5;

    private static double DegToRad = Math.PI/180;
    public Vector[] points = new Vector[4];

    public Block_Plane(Vector p1, Vector p2)
    {
          start = p1.clone();
          end = p2.clone();
          origin = p2.subtract(p1);

          double lenght = origin.length();
          if(origin.length() > max_width)
          {
              origin.divide(new Vector(lenght/max_width,lenght/max_width,lenght/max_width));
          }




        LoadPolygones();
    }


    public void  LoadPolygones()
    {

      double angle = start.angle(end);
      for(int i=0;i<=2;i+=2)
      {
          if(i==0)
          {
              points[i] = start.clone().add(origin.clone().rotateAroundY(270*DegToRad));
              points[i+1] =  start.clone().add(origin.clone().rotateAroundY(90*DegToRad));
          }
          else
          {
              points[i] = end.clone().add(origin.clone().rotateAroundY(270*DegToRad));
              points[i+1] =  end.clone().add(origin.clone().rotateAroundY(90*DegToRad));
          }
      }
    }


    public void Draw(World world,Material material)
    {
        Material[] materials = new Material[4];
        materials[0] = Material.RED_WOOL;
        materials[1] = Material.RED_WOOL;
        materials[2] = Material.GREEN_WOOL;
        materials[3] = Material.GREEN_WOOL;







       /* for(int i=0;i<=1;i++)
        {
            float invslope1 = (points[i+1].getBlockX() - points[i].getBlockX()) / (points[i+1].getBlockZ() - points[i].getBlockZ());
            float invslope2 = (points[i+2].getBlockX() - points[i].getBlockX()) / (points[i+2].getBlockZ() - points[i].getBlockZ());

            float curx1 =  points[i].getBlockX();
            float curx2 =  points[i].getBlockX();

            for (int scanlineY =  points[i].getBlockZ(); scanlineY <= points[i+1].getBlockZ(); scanlineY++)
            {
                Math_Manager.DrawLine(new Vector(curx1,points[i].getBlockY(),scanlineY),new Vector(curx2,points[i].getBlockY(),scanlineY), (vx) ->
                {
                    world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
                });

                curx1 += invslope1;
                curx2 += invslope2;
            }
        }*/


        for(int i=0;i<1;i++)
        {
      /*      Math_Manager.DrawLine(points[i], points[i+1], (vx) ->
        {
            world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
        });

            Math_Manager.DrawLine(points[i], points[i+2], (vx) ->
            {
                world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
            });

            Math_Manager.DrawLine(points[i+1], points[i+2], (vx) ->
            {
                world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
            });*/

            ArrayList<Vector> dl =  Math_Manager.DrawLine(points[0], points[i+2]);


            ArrayList<ArrayList<Vector>> endegs = new ArrayList<>();
            ArrayList<Vector> line_1 =  Math_Manager.DrawLine(points[i], points[i+1]);
            ArrayList<Vector> line_2 =  Math_Manager.DrawLine(points[i+1], points[i+2]);
            ArrayList<Vector> line_4 =  Math_Manager.DrawLine(points[i+2], points[i+3]);
            ArrayList<Vector> line_5 =  Math_Manager.DrawLine(points[i+1], points[i+3]);

            endegs.add(line_1);
            endegs.add(line_2);
            endegs.add(line_4);
            endegs.add(line_5);


            for ( ArrayList<Vector> ed :endegs)
            {
               for(int z =0;z< ed.size();z++)
               {
                   Math_Manager.DrawLine(ed.get(z), dl.get(z%dl.size()), (vx) ->
                   {
                       world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
                   });
               }
            }


        }


      /*  for(int i=0;i<line_1.size();i++)
        {
            Vector p = line_1.get(i);
            Vector p2 = line_2.get(i);
            Math_Manager.DrawLine(line_1.get(i),line_2.get(i%line_2.size()), (v) ->
           {
               world.getBlockAt(v.getBlockX(),v.getBlockY(),v.getBlockZ()).setType(Material.GREEN_WOOL);

               Math_Manager.DrawLine(p,v, (vx) ->
               {
                   world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
               });

           });
            Math_Manager.DrawLine(line_1.get(i),line_2.get((i+1)%line_2.size()), (v) ->
            {
                world.getBlockAt(v.getBlockX(),v.getBlockY(),v.getBlockZ()).setType(Material.GREEN_WOOL);

                Math_Manager.DrawLine(p2,v, (vx) ->
                {
                    world.getBlockAt(vx.getBlockX(),vx.getBlockY(),vx.getBlockZ()).setType(Material.PURPLE_WOOL);
                });
            });


        }*/





    }



}
