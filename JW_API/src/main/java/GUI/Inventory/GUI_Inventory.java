package GUI.Inventory;

import GUI.Components.GUI_Title;
import GUI.Inventory.Events.*;
import GUI.Inventory.TextBox.GUI_Textbox;
import GUI.Inventory.TextBox.Validation_Int;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI_Inventory
{


    public GUI_Acctions get_Acction()
    {
        return gui_acction;
    }
    public void set_Acction(GUI_Acctions gui_acction)
    {
        this.gui_acction = gui_acction;
    }

    private ArrayList<String> viewing = new ArrayList<>();
    private GUI_Button[] items;
    private GUI_Inventory parent;
    private boolean Is_visible=false;
    public boolean Is_closed = false;
    private boolean Is_Draggable =false;
    private boolean Is_closedManually= false;
    public boolean Is_firstTimeOpen = true;
    public boolean Is_TaskRunning = false;
    public boolean Is_ChatOpen =false;
    public boolean Is_BlockSelecting = false;
    public boolean updateScreen= false;
    public boolean debug = false;
    public String name="";
    public String alert ="";
    public int size = 1;
    public int height =1;
    public Material backgroundMaterial = Material.DIRT;

    public ArrayList<GUI_ClickEvent> onOpen = new ArrayList<>();
    public ArrayList<GUI_ClickEvent> onClose = new ArrayList<>();
    public ArrayList<GUI_ClickEvent>  onClick = new ArrayList<>();
    public ArrayList<GUI_ClickEvent>  onRefresh = new ArrayList<>();

    public GUI_TextEvent onChat = (a,b)->{};
    public GUI_BlockSelect onBlock = (a, b)->{};
    public  ArrayList<GUI_ClickEvent> onClickEdit = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickItem = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickDelete = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickInsert= new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickEmpty = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickSearch = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickExit  = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickBackground = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickNextPage= new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickBackPage = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClicekCancel = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onClickCopy= new ArrayList<>();
    public Inventory inventory;

    public Player player;

    private GUI_Acctions gui_acction;

    protected ArrayList<GUI_AcctionEvent> openEvent= new ArrayList<>();

    protected ArrayList<GUI_AcctionEvent> closeEvent=new ArrayList<>();

    protected ArrayList<GUI_AcctionEvent> refreshEvent=new ArrayList<>();

    protected ArrayList<GUI_AcctionEvent> clickEvent = new ArrayList<>();

    protected ArrayList<GUI_AcctionEvent> DragEvent = new ArrayList<>();


    public GUI_Inventory(String name , int height)
    {
        super();
        this.height = height>6?6:height;
        size = this.height*9;
        items = new GUI_Button[size];
        this.name =name.replaceAll("&","§");
        createEvents();
    }
    public void forceAcction(GUI_Acctions acctions)
    {
        GUI_Button button = new GUI_Button(Material.DIRT," ",acctions);
        this.OpenAcction(button);
    }

    private void createEvents()
    {

        this.openEvent.add((a,b,c)->{
            Is_firstTimeOpen=false;
            this.updateScreen=false;

        });

        this.clickEvent.add((a,b,c)->
        {

            if(Is_closed==true)return;

            InventoryClickEvent event = (InventoryClickEvent)a;

            if(Is_visible==true)
            {
                if(Is_Draggable==false)
                {
                    int slot = event.getRawSlot();


                   if(slot>size)
                        return;


                    GUI_Button item = this.get_Item(event.getRawSlot());

                    if(debug==true)
                      Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+this.toString()+" "+item.acction);

                    this.OpenAcction(item);
                }
            }

        });
        this.closeEvent.add((a,b,c)->
        {

            if(Is_closed==true)
                return;

          // Bukkit.getConsoleSender().sendMessage(this.toString()+" setclose  "+ !updateScreen + " ");
           if( (updateScreen==false)  )
           {
               if( Is_TaskRunning==true)
                   return;

               if(debug==true)
                   Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+this.toString()+" "+"Closed");

               GUI_EventHandler.Instnace().RemoveInventory(this);
               onClose.forEach(e -> e.Execute(GUI_Button_Factory.EmptyButton(),player,this));
               return;
           }
           else
           {
               updateScreen=false;
           }
        });

        this.onClickExit.add((a,b,c)->
        {
            this.stop_tasks(null);
        });
    }


    private class Task_Set
    {
        public  int task_id=-1;
        public  Runnable func= null;
        public  String tag = null;
        public  int start_after=20;
        public  int reapeat=20;
    }

    private ArrayList<Task_Set> tasks = new ArrayList<>();

    public void start_tasks(String tag)
    {
        if(tag == null)
            tasks.forEach((a)->
            {
                if(a.task_id==-1)
                  Bukkit.getScheduler().cancelTask(a.task_id);

               a.task_id= Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GUI_EventHandler.Instnace().plugin,a.func,a.start_after,a.reapeat);
            });
        else
            tasks.forEach((a)->
            {
                if(a.tag.equalsIgnoreCase(tag)==true)
                {
                    if(a.task_id==-1)
                        Bukkit.getScheduler().cancelTask(a.task_id);

                a.task_id =  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GUI_EventHandler.Instnace().plugin,a.func,a.start_after,a.reapeat);
                }
            });
    }
    public void stop_tasks(String  tag)
    {
        if(tag == null)
            tasks.forEach((a)-> {Bukkit.getScheduler().cancelTask(a.task_id);});
        else
            tasks.forEach((a)-> { if(a.tag.equalsIgnoreCase(tag))Bukkit.getScheduler().cancelTask(a.task_id);});
    }
    public void add_task(String tag,int start_after,int repeat,Runnable runnable)
    {
        Task_Set task_set = new Task_Set();
        task_set.start_after = start_after;
        task_set.reapeat = repeat;
        task_set.func =runnable;
        task_set.tag =tag;
        tasks.add(task_set);
    }

    private Inventory createInventory(Player player)
    {

        this.updateScreen=true;
        Inventory inv = Bukkit.createInventory(player, size,name+alert);
       for (int i = 0; i < items.length; i++)
        {
            if (items[i] != null)
            {
                inv.setItem(i, items[i]);
            }

        }

        return inv;
    }
    public void open(Player player)
    {

        if(parent!=null)
            parent.close();

        if(debug==true)
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+this.toString()+" "+"Opened");

       GUI_EventHandler.Instnace().AddInventory(this);


        this.player = player;
        this.Is_visible=true;
        this.Is_closed=false;
        this.Is_firstTimeOpen =true;

        onOpen.forEach(e -> e.Execute(GUI_Button_Factory.EmptyButton(),player,this));
        this.inventory = createInventory(player);
        player.openInventory(inventory);
    }

    public void opentextbox(String title,GUI_TextEvent textEvent)
    {
        this.updateScreen=true;
        this.Is_ChatOpen=true;
        onChat = textEvent;
        player.closeInventory();
        GUI_Title.sendTitle(player, 20, 40, 20, title, "");
    }
    public void openBlockSelecting(String title,GUI_BlockSelect event)
    {
        this.updateScreen=true;
        this.Is_BlockSelecting=true;
        onBlock = event;
        player.closeInventory();
        GUI_Title.sendTitle(player, 20, 40, 20, title, "");
    }

    public void refresh()
    {

        this.Is_closed=false;
        this.Is_visible=true;
        this.inventory = createInventory(player);
        player.openInventory(inventory);
        onRefresh.forEach(e -> e.Execute(GUI_Button_Factory.EmptyButton(),player,this));
        this.updateScreen=true;
    }
    public String getCurrentTitle()
    {
        return this.name+this.alert;
    }
    public void close()
    {

        if(debug==true)
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+this.toString()+" "+"closed manually");

        this.Is_closed = true;
        this.updateScreen=false;
       player.closeInventory();
       GUI_EventHandler.Instnace().RemoveInventory(this);

       GUI_Inventory _parent = this.parent;
       while (_parent!=null)
       {
           GUI_EventHandler.Instnace().RemoveInventory(_parent);
           _parent = _parent.parent;
       }


    }


    public GUI_Inventory getParent()
    {
        return this.parent;
    }
    public GUI_Button get_Item(int height,int width)
    {
        int pos = height * 9 + width%9;
        return items[pos]==null? GUI_Button_Factory.EmptyButton():items[pos];
    }
    public GUI_Button get_Item(int pos)
    {
        int index = pos >=items.length?items.length-1:pos;
        return items[index]==null? GUI_Button_Factory.EmptyButton():items[index];
    }
    public void setParent(GUI_Inventory parent)
    {
        this.parent =parent;
    }
    public void addItem(int height, int width, GUI_Button item)
    {
        int pos = height * 9 + width%9;
        items[pos] = item;
    }

    public void addButton(GUI_Button item)
    {
        int pos = item.position.getBlockY() * 9 + item.position.getBlockX()%9;
        items[pos] = item;
    }

    public void addItem_PlayerHead(int height,int width,String name, GUI_Button button)
    {
        Bukkit.getScheduler().runTask(GUI_EventHandler.Instnace().plugin,()->
        {
            GUI_Button head = GUI_Button_Factory.Player_head(name);
            head.tag = button.tag;
            head.acction = button.acction;
            head.Set_Text(button.getItemMeta().getDisplayName());
          //  head.setItemMeta(button.getItemMeta());
            this.addItem(height,width, head);
            this.refresh();
        });
    }

    public void removeItem(GUI_Button item)
    {
        for(int i=0;i<items.length;i++)
        {
            if(item==items[i])
            {
                items[i]=null;
            }
        }
    }
    public void removeItem(int height, int width)
    {
        int pos = height * 9 + width%9;
        items[pos] = null;
    }
    public void removeItem(String tag)
    {
        for(int i=0;i<items.length;i++)
        {
            if(items[i]!=null)
            {
                if(items[i].tag==tag)
                {
                    items[i]=null;
                }
            }
        }
    }
    private void OpenAcction(GUI_Button item)
    {
        if(item.OnClick!=null)
        {
            item.OnClick.Execute(item,player,this);
        }
        switch (item.acction)
        {
            case RIGHT:
                onClickNextPage.forEach(e -> e.Execute(item,player,this));
                break;
            case LEFT:
                onClickBackPage.forEach(e -> e.Execute(item,player,this));
                break;
            case SEARCH:
                onClickSearch.forEach(e -> e.Execute(item,player,this));
                break;
            case EDIT:
                onClickEdit.forEach(e -> e.Execute(item,player,this));
                break;
            case DELETE:
                onClickDelete.forEach(e -> e.Execute(item,player,this));
                break;
            case INSERT:
                onClickInsert.forEach(e -> e.Execute(item,player,this));
                break;
            case BACKGROUND:
                onClickBackground.forEach(e -> e.Execute(item,player,this));
                break;
            case CLICK:
                onClickItem.forEach(e -> e.Execute(item,player,this));
                break;
            case EXIT:
                onClickExit.forEach(e -> e.Execute(item,player,this));
                break;
            case COPY:
                onClickCopy.forEach(e -> e.Execute(item,player,this));
                break;
            case EMPTY:
                onClickEmpty.forEach(e -> e.Execute(item,player,this));
                break;
            case CANCEL:
                onClicekCancel.forEach(e -> e.Execute(item,player,this));
                break;
            case OPEN:
                onOpen.forEach(e -> e.Execute(item,player,this));
                break;
            case REFRESH:
                refresh();
                break;
        }
    }
    public void drawFilledBackground(Material material)
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < this.height; j++) {
                this.addItem(j, i, new GUI_Button(material, " ","",GUI_Acctions.BACKGROUND));
            }
        }
    }
    public void drawBorderBackground(Material material)
    {
        this.backgroundMaterial = material;
        if(items.length>0)
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<this.height;j++)
                {
                    if(i==0 || j==0 || j==this.height-1|| i==8)
                    {
                        GUI_Button itemStack = this.get_Item(j,i);
                        if(itemStack.acction == GUI_Acctions.BACKGROUND || itemStack.acction == GUI_Acctions.EMPTY)
                            this.addItem(j,i, new GUI_Button(material," "," ",GUI_Acctions.BACKGROUND));
                    }

                }
            }
        }
        else
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<this.height;j++)
                {
                    if(i==0 || j==0 || j==this.height-1|| i==8)
                        this.addItem(j,i, new GUI_Button(material," "," ",GUI_Acctions.BACKGROUND));
                    else
                        this.addItem(j,i, null);
                }
            }
        }


    }
    public void drawPatternBackground(String[] pattern, HashMap<String,Material> materials)
    {

    }
    public GUI_Textbox textbox = new GUI_Textbox("",this);

    public void openTextbox(String title,String value,GUI_TextEvent textEvent)
    {
        this.textbox.onText = textEvent;
        textbox.set_name(title);
        textbox.set_value(value);
        textbox.Open(player);
    }
    public void openTextBox(String title, String value)
    {
        textbox.set_name(title);
        textbox.set_value(value);
        textbox.Open(player);
    }
    public void openTextBoxINT(String title, String value,GUI_TextEvent textEvent)
    {
        this.textbox.onText = textEvent;
        textbox.set_name(title);
        textbox.set_value(value);
        textbox.add_validation(new Validation_Int());
        textbox.Open(player);
    }
    public void setAlert(String alert)
    {
        this.alert =alert;
    }

    public void setName(String name ){this.name = name;}
}
