package Managers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Task_Queue
{
    private ArrayList<Integer> tasks = new ArrayList<Integer>();

    public <T>void Add_Task(int id)
    {
        tasks.add(id);
    }
    public void Close_Tasks()
    {
        tasks.forEach(t ->
        {
            Bukkit.getScheduler().cancelTask(t);
        });
    }



}
