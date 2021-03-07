package ORM.Databases.MySQL;

import ORM.Annotations.ORM_ID;
import ORM.Entitis.SQL_Database_Entity;
import io.netty.util.internal.PriorityQueue;
import jw_api.jw_api.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public abstract class My_SQL
{

    public boolean debug = false;
    public Consumer OnConnection  = (t)->{};
    public Consumer OnDisconnection = (t)->{};
    public Consumer OnSQLquery = (t)-> {};
    public boolean Enable = false;
    public boolean Is_Initialized = false;

    SQL_Database_Entity entity;
    Connection connection;
     ArrayList<Consumer> sql_queue = new ArrayList<>();

    Queue<String> acctions = new LinkedList<>();

    public My_SQL(SQL_Database_Entity entity)
    {
        this.entity = entity;
        Refresh_Connection();
    }


    public <T> T Get_One()
    {
      return null;
    }

    public <T> T Delete_One()
    {
        return null;
    }


    public <T>void Refresh_list(ArrayList<T> objects,Class<T> tClass)
    {
            Find_Object_to_delete(objects,tClass);
            Insert_Or_Update(objects);
    }
    public <T>void Find_Object_to_delete(ArrayList<T> objects,Class class_)
    {


            String class_name = class_.getSimpleName();
            ArrayList<Field> fields = MySQL_Manager.Find_filds_with_Annotation(ORM_ID.class, class_);

            if (fields.size() == 0)
                return;

            Field Id_field = fields.get(0);
            Id_field.setAccessible(true);

            String select_ID_query = "SELECT " + Id_field.getName() + " FROM " + class_name;

            Execute_SQL_Query(select_ID_query, (x) ->
            {
                ArrayList<String> to_delete = new ArrayList<>();
                try
                {
                    while (x.next())
                    {
                        String query_id = x.getString(Id_field.getName());
                        //jesli nie znajdzie takiego id w liscie to bedzie ono usuniete
                        boolean found = false;
                        try
                        {
                            for (T obj : objects)
                            {
                                //pobieranie id z obiektu
                                String id = class_.getField(Id_field.getName()).get(obj).toString();
                                if (id.equalsIgnoreCase(query_id) == true)
                                {
                                    found=true;
                                    break;
                                }
                            }
                            if(found==false)
                                to_delete.add(query_id);
                        }
                        catch (NoSuchFieldException e) {
                            if(debug)
                                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                        }
                        catch (IllegalAccessException e) {
                            if(debug)
                                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                        }
                    }
                }
                catch (SQLException e)
                {
                    if(debug)
                        Bukkit.getConsoleSender().sendMessage(e.getMessage());
                }
                String query = MySQL_Manager.Delete_Many_By_Field(to_delete,class_,Id_field);
                this.Add_Sql_to_Queue(Execute_SQL(query,(a)->{}));
            }).accept(null);

    }
    public <T>void Insert_Or_Update(T object)
    {
            String sql = MySQL_Manager.Insert_or_Update_Obj(object,object.getClass());
            this.Add_Sql_to_Queue(Execute_SQL(sql,(a)->{}));
    }
    public <T>void Insert_Or_Update(ArrayList<T> objects)
    {
            for(T obj :objects)
            {
                String sql = MySQL_Manager.Insert_or_Update_Obj(obj,obj.getClass());
                this.Add_Sql_to_Queue(Execute_SQL(sql,(a)->{}));
            }
    }
    int table_to_create=0;
    public void Create_Table(Class classtype)
    {
        if(connection==null)
            return;

        String query = MySQL_Manager.Class_to_Table(classtype);
        table_to_create++;
        Execute_SQL(query,(b)->
        {
            table_to_create--;
            if(table_to_create==0)
            {
                Is_Initialized =true;
                sql_queue.forEach(c ->
                {
                    c.accept(null);
                });
            }
        }).accept(null);
    }

    public void Refresh_Connection()
    {
        try
        {
            Is_Initialized = false;
            if(connection!=null)
                connection.close();

            MySQL_Manager.Get_Connection(entity,(con)->
            {
                this.connection = con;
                if(con==null)
                {
                    entity.is_connected =false;
                    OnDisconnection.accept(false);
                }
                else
                {
                    entity.is_connected = true;
                    OnConnection.accept(true);
                }
            });
        }
        catch (SQLException ex)
        {
            if(debug)
                Bukkit.getConsoleSender().sendMessage("My_SQL.Refresh_Connection: "+ex.getMessage());
        }

    }

    public void Add_Sql_to_Queue(Consumer consumer)
    {
        if(Is_Initialized==false)
            sql_queue.add(consumer);
        else
            consumer.accept(null);
    }

    public Consumer Execute_SQL(String sql,Consumer work_done)
    {
        Consumer task  = new Consumer()
        {
            @Override
            public void accept(Object obj) {
                Bukkit.getScheduler().runTaskAsynchronously(Main.instance,()->
                {
                    try {
                        if(debug)
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+sql);
                        OnSQLquery.accept(sql);
                        work_done.accept(connection.createStatement().execute(sql));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        };

        return  task;

    }
    public Consumer Execute_SQL_Query(String sql,Consumer<ResultSet> work_done)
    {
        Consumer task = new Consumer() {
            @Override
            public void accept(Object o) {
                Bukkit.getScheduler().runTaskAsynchronously(Main.instance,()->
                {
                    try
                    {
                        if(debug)
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+sql);

                        ResultSet rs =connection.createStatement().executeQuery(sql);
                        OnSQLquery.accept(sql);
                        work_done.accept(rs);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        };

      return task;
    }

    public void Execute_SQL(String sql)
    {
        Execute_SQL(sql,(d)->{});
    }

    public void Disconnect()
    {
        try
        {
            if(connection!=null)
                connection.close();
        }
        catch (SQLException ex)
        {
            if(debug)
            Bukkit.getConsoleSender().sendMessage("My_SQL.Disconnect: "+ex.getMessage());
        }

    }





}
