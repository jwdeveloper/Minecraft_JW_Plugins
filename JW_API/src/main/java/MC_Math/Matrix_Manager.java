package MC_Math;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class Matrix_Manager
{






    public  static  boolean test(Vector p,Vector a,Vector b,Vector c)
    {
        // Compute vectors
        Vector  v0 = c.clone().subtract(a);
        Vector  v1 = b.clone().subtract(a);
        Vector  v2 = p.clone().subtract(a);

// Compute dot products
        double   dot00 = v0.dot(v0);
        double   dot01 = v0.dot(v1);
        double   dot02 = v0.dot(v2);
        double  dot11 = v1.dot(v1);
        double  dot12 = v1.dot(v2);

// Compute barycentric coordinates
        double  invDenom = (1 / (dot00 * dot11 - dot01 * dot01));
        double  u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double  v = (dot00 * dot12 - dot01 * dot02) * invDenom;

// Check if point is in triangle
        return (u >= 0) && (v >= 0) && (u + v < 1);
    }


    public static boolean PointInTriangle( Vector P,Vector... TriangleVectors)
    {
        Vector A = TriangleVectors[0], B = TriangleVectors[1], C = TriangleVectors[2];
        if (SameSide(P, A, B, C) && SameSide(P, B, A, C) && SameSide(P, C, A, B))
        {
            Vector s1 = A.clone().subtract(B);
            Vector s2 = A.clone().subtract(C);
            Vector s3 = A.clone().subtract(P);


            Vector vc1 = s1.crossProduct(s2);
            if (Math.abs(s3.dot(vc1)) <= .01f)
                return true;
        }

        return false;
    }

    private static boolean SameSide(Vector p1, Vector p2, Vector A, Vector B)
    {
        Vector  v0 = B.clone().subtract(A);
        Vector  v1 = p1.clone().subtract(A);
        Vector  v2 = p2.clone().subtract(A);

        Vector cp1 = v0.crossProduct(v1);
        Vector cp2 = v0.crossProduct(v2);
        if (cp1.dot(cp2) >= 0) return true;
        return false;

    }

    public static  double Vector_Sum(Vector vector)
    {
        return  vector.getX()+vector.getZ()+vector.getY();
    }

    public static double[][] Vectors_to_matrix(Vector... vectors)
    {
       double[][] result  = new double[vectors.length][3];

       for(int i=0;i<vectors.length;i++)
       {
           result[i][0] = vectors[i].getX();
           result[i][1] = vectors[i].getY();
           result[i][2] = vectors[i].getZ();
       }

       return  result;
    }

    private static double DegToRad = Math.PI/180;
    public static Vector RotateRadians(Vector v, double radians)
    {
        double x  = Math.cos(radians);
        double y = Math.sin(radians);
        return new Vector(x*v.getX() - y*v.getY(), x*v.getX() + y*v.getY(),v.getZ());
    }

  //https://www.youtube.com/watch?v=YlyneXmUbic
    public static Vector VectorFindPlane(Vector v1, Vector v2)
    {
        double[] result = new double[3];

        double [][]matrix = Vectors_to_matrix(v1,v2);

        for (int block = 0; block < 3; block++)
        {
            double sum = 0;
            int j = 0;
            for (int i = (block + 1) % 3; i < block + 3; i++)
            {
                if (i != block)
                {
                    while (j == i || j == block)
                    {
                        j = (j + 1) % 3;
                    }

                    if (i == (block + 1) % 3)
                    {
                        sum += matrix[1][ j] * matrix[0][i % 3];
                        continue;
                    }
                    else
                    {
                        sum -= matrix[1][ j] * matrix[0][ i % 3];
                        break;
                    }

                }
            }

            result[block] = sum;
        }

        return new Vector(result[0],-result[1],result[2]);
    }
    public static Vector VectorFindPlane2(Vector v1, Vector v2)
    {
      //  double x = v1.getY()*v2.getZ() - v2.getY()*v1.getZ();
      //  double y = v1.getX()*v2.getZ() - v2.getX()*v1.getZ();
     //   double z = v1.getX()*v2.getY() - v1.getY()*v2.getX();
        return new Vector( v1.getY()*v2.getZ() - v2.getY()*v1.getZ() ,v1.getX()*v2.getZ() - v2.getX()*v1.getZ(),v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }



}
