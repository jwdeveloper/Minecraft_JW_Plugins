package ORM.Databases.MySQL;

import ORM.Annotations.ORM_ID;
import ORM.Annotations.ORM_Varchat;
import ORM.Entitis.SQL_Database_Entity;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MySQL_Manager
{


    public static HashMap<String,String> sql_types;

    public static HashMap<Class,String> annotations_types;

    public static String get_fiels_addnotations(Field field)
    {
        String result = new String();

        for(Annotation a : field.getAnnotations())
        {
            if(annotations_types().containsKey(a.annotationType()))
            {
              result += annotations_types().get(a.annotationType());
            }
       }
          return result;
    }

    public static HashMap<Class,String> annotations_types()
    {
        if(annotations_types ==null)
        {
            annotations_types = new HashMap<>();
            annotations_types.put(ORM_ID.class," NOT NULL PRIMARY KEY ");
            annotations_types.put(ORM_Varchat.class," VARCHAR(50) ");
        }
        return annotations_types;
    }

    public static ArrayList<Field> Find_filds_with_Annotation(Class annotation, Class class_)
    {
        ArrayList<Field> result = new ArrayList<>();
        for(Field field : class_.getFields())
        {
            for(Annotation a : field.getAnnotations())
            {
                if(a.annotationType().getSimpleName().equalsIgnoreCase(annotation.getSimpleName()))
                {
                    result.add(field);
                }
            }
        }
        return result;

    }
    public static String get_sql_types(Field field)
    {
        if(sql_types ==null)
        {
            sql_types = new HashMap<>();
            sql_types.put("long"," INT ");
            sql_types.put("int"," INT ");
            sql_types.put("boolean"," BOOLEAN ");
            sql_types.put("double"," float ");
            sql_types.put("string"," VARCHAR(50) ");
            sql_types.put("enum"," VARCHAR(50) ");
            sql_types.put("localdate"," DATETIME  ");
            sql_types.put("localdatetime"," DATETIME  ");

        }
        String type = new String();
        if(field.getType().isEnum())
        {
            type  = "enum";
        }
        else
        {
            type  = field.getType().getSimpleName().toLowerCase();
        }

        if(sql_types.containsKey(type))
        {
            return sql_types.get(type);
        }

        return "NULL";
    }

    public static String Class_to_Table(Class type)
    {
        Field[] fields = type.getDeclaredFields();
        String query = "CREATE TABLE  IF NOT EXISTS  "+type.getSimpleName()+" (";
        for(int i=0;i<fields.length;i++)
        {
            boolean type_found = false;
            String sql_type = get_sql_types(fields[i]);
            if(sql_type.equalsIgnoreCase("NULL")==false)
            {
                query += fields[i].getName() +sql_type+ get_fiels_addnotations(fields[i]);
                type_found=true;
            }
            else
            {
                type_found=false;
            }

            if(i != fields.length-1)
                if(type_found==true)
                query+=",";
        }

        if(query.charAt(query.length()-1)==',')
            query = query.substring(0,query.length()-1);

        query+=")";



        return query;
    }

    public static String Insert_Obj(Object obj,Class type)
    {
        String inset = "INSERT INTO "+type.getSimpleName()+" ( ";
        String values = " VALUES ( ";

        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            try
            {
                fields[i].setAccessible(true);
                String field_type = fields[i].getType().getSimpleName().toLowerCase();

                String sql_type = get_sql_types(fields[i]);
                if(sql_type.equalsIgnoreCase("NULL")==false)
                {
                    if(sql_type.contains("VARCHAR(50)"))
                        values+= "'"+fields[i].get(obj)+"', ";
                    else
                        values+= fields[i].get(obj)+" , ";

                    inset += fields[i].getName()+" , ";
                }

            }
            catch (IllegalAccessException e)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Insert obj  "+type.getName()+" "+e.getMessage());
            }
        }
        inset = inset.substring(0,inset.lastIndexOf(","));
        values = values.substring(0,values.lastIndexOf(","));

        inset+=" )";
        values+=" )";

        return inset+values;
    }

    public static String Insert_or_Update_Obj(Object obj,Class type)
    {
        String inset = "INSERT INTO "+type.getSimpleName()+" ( ";
        String values = " VALUES ( ";
        String update = " ON DUPLICATE KEY UPDATE ";

        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            try
            {
                fields[i].setAccessible(true);
                String field_type = fields[i].getType().getSimpleName().toLowerCase();
                String sql_type = get_sql_types(fields[i]);

                if(sql_type.equalsIgnoreCase("NULL")==false)
                {
                    String value  = fields[i].get(obj)+" , ";

                    if(sql_type.contains("VARCHAR(50)"))
                    {
                        value = "'"+fields[i].get(obj)+"', ";
                    }
                    if(sql_type.contains("DATETIME"))
                    {
                        String date = fields[i].get(obj).toString();

                        if(date.contains("T"))
                            date = date.replace("T"," ");

                        value =  "'"+date+"', ";
                    }

                    values +=value;
                    update+= " "+fields[i].getName()+" = VALUES("+fields[i].getName()+"),";
                    inset += fields[i].getName()+" , ";
                }

            }
            catch (IllegalAccessException e)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Insert obj  "+type.getName()+" "+e.getMessage());
            }
        }
        inset = inset.substring(0,inset.lastIndexOf(","));
        values = values.substring(0,values.lastIndexOf(","));
        update = update.substring(0,update.lastIndexOf(","));
        inset+=" )";
        values+=" )";

        return inset+values+update;
    }


    public static String Delete_Many_By_ID(ArrayList<Object> objects,Class type)
    {
        ArrayList<Field> ID_fields = Find_filds_with_Annotation(ORM_ID.class,type);

        if(ID_fields.size()==0) return new String();


        Field Id_Filed = ID_fields.get(0);
        String query = "DELETE FROM "+type.getSimpleName()+" WHERE "+Id_Filed.getName()+" IN (";
        String values = new String();
        for(Object obj :objects)
        {
            try
            {
                values+= "'"+Id_Filed.get(obj)+"', ";
            }
            catch (IllegalAccessException e)
            {

            }
        }
        values = values.substring(0,values.lastIndexOf(","));
        query+=values+")";
        return query;

    }
    public static String Delete_Many_By_Field(ArrayList<String> values_to_delete,Class type,Field field)
    {

        if(values_to_delete.size()==0)
            return "DELETE FROM "+type.getSimpleName()+" WHERE 'No data' = 'to delete'";

        String query = "DELETE FROM "+type.getSimpleName()+" WHERE "+field.getName()+" IN (";
        String values = new String();
        field.setAccessible(true);
        for(String obj :values_to_delete)
        {
                values+= "'"+obj+"', ";
        }
        values = values.substring(0,values.lastIndexOf(","));
        query+=values+")";
        return query;

    }

    public static void Get_Connection(SQL_Database_Entity database_entity, Consumer<Connection> result)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
        {
            final String url = "jdbc:mysql://"+database_entity.server+"/"+database_entity.database+"?autoReconnect=true&useSSL=false" +
                    "&autoReconnect=true&useUnicode=true&characterEncoding=utf-8"; //Enter URL w/db name
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
            }
            catch (ClassNotFoundException e)
            {
                System.err.println(ChatColor.RED+" jdbc driver unavailable!");
                result.accept(null);
                return;
            }
            try
            {
                Connection connection = DriverManager.getConnection(url,database_entity.user,database_entity.password);
                if (connection!=null && !connection.isClosed())
                {
                    result.accept(connection);
                    return;
                }
                result.accept(null);
            } catch (SQLException e)
            {

                result.accept(null);
            }
        });

    }
    public static void Check_Connection(SQL_Database_Entity database_entity, Consumer<Boolean> result)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
        {
            final String url = "jdbc:mysql://"+database_entity.server+"/"+database_entity.database+"?autoReconnect=true&useSSL=false"; //Enter URL w/db name
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
            }
            catch (ClassNotFoundException e)
            {
                System.err.println(ChatColor.RED+" jdbc driver unavailable!");
                result.accept(false);
                return;
            }
            try
            {
                Connection connection = DriverManager.getConnection(url,database_entity.user,database_entity.password);
                if (connection!=null && !connection.isClosed())
                {
                    connection.close();
                    result.accept(true);
                    return;
                }
                result.accept(false);
            } catch (SQLException e)
            {
                result.accept(false);
            }
        });

    }

}
