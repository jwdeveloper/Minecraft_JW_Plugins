package GUI.Inventory.Forms.Basic;

import GUI.Inventory.Events.GUI_Acctions;
import GUI.Inventory.Events.GUI_ClickEvent;
import GUI.Inventory.Forms.GUI_AskBox;
import GUI.Inventory.GUI_Button;
import GUI.Inventory.GUI_Button_Factory;
import GUI.Inventory.GUI_Inventory;
import GUI.Inventory.Languages.GUI_Language_Manager;
import GUI.Inventory.TextBox.GUI_Textbox;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class GUI_List extends GUI_Inventory
{




    private GUI_Acctions crud_acction = GUI_Acctions.GET;

    public  ArrayList<GUI_ClickEvent> onSelect = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onDelete = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onEdit = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onInsert = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onCopy = new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onSearch= new ArrayList<>();
    public  ArrayList<GUI_ClickEvent> onDeleteAll= new ArrayList<>();
    protected int max_pages=1;
    protected int page =1;
    private int max_itemsonpage = 9*4;
    protected ArrayList<GUI_Button> content = new ArrayList<>();
    protected ArrayList<GUI_Button> filtred_content = new ArrayList<>();

    public boolean enableSelecting =true;
    public boolean enableEditing=false;
    public boolean enableDeleting=true;
    public boolean enableInsering=true;
    public boolean enableDeleteAll =true;
    public boolean enableCopy =false;
    public boolean hide_CopyButton=true;
    public boolean hide_InsertButton = false;
    public boolean hide_DeleteButting =false;
    public boolean hide_EditButton=true;
    public boolean hide_DeleteAll =true;


    private GUI_AskBox askBox;

    public GUI_List(GUI_Inventory parent, String name)
    {
        super(ChatColor.DARK_GRAY+name, 6);
        this.setParent(parent);
        Initilize();
    }
    public GUI_List(String name)
    {
        super(ChatColor.DARK_GRAY+name, 6);
        Initilize();
    }

    public void Initilize()
    {
        DrawBackGround();

        askBox = new GUI_AskBox(this,GUI_Language_Manager.lang().Ask_box_Question);

        this.onClickExit.add((a,b,c)->
        {
            crud_acction=GUI_Acctions.EMPTY;
            DrawBackGround();
            if(this.getParent()!=null)
            {
            this.close();
            this.getParent().open(b);
            }
            else
            {
                this.close();
            }

        });
        this.onOpen.add((a,b,c)->
        {
            if(hide_EditButton==false)
                this.addItem(0,5, GUI_Button_Factory.EditButton());
            if(hide_DeleteButting==false)
                this.addItem(0,7, GUI_Button_Factory.RemoveButton());
            if(hide_InsertButton==false)
                this.addItem(0,6, GUI_Button_Factory.InsertButton());
            if(hide_CopyButton==false)
                this.addItem(0,4, GUI_Button_Factory.CopyButton());
            if(hide_DeleteAll==false)
                this.addItem(0,8, GUI_Button_Factory.DeleteALLButton());
            this.OpenPage(this.page);
        });
        this.onClickNextPage.add((a,b,c)->{   this.NextPage();});
        this.onClickBackPage.add((a,b,c)->{ this.BackPage();});


        this.onClickItem.add((a,b,c)->{
            if(enableSelecting==false )
                return;

            if(crud_acction==GUI_Acctions.EMPTY)
           crud_acction = GUI_Acctions.GET;
        });
        this.onClickEdit.add((a,b,c)->
        {
            if(enableEditing==false)
                return;
            crud_acction = GUI_Acctions.EDIT;
            this.drawBorderBackground(Material.YELLOW_STAINED_GLASS_PANE);
            this.addItem(5,3, GUI_Button_Factory.BackButton(PotionType.FIRE_RESISTANCE));
            this.addItem(5,4, GUI_Button_Factory.CancelButton());
            this.addItem(5,5, GUI_Button_Factory.NextButton(PotionType.FIRE_RESISTANCE));
            this.setAlert(ChatColor.YELLOW+" ["+GUI_Language_Manager.lang().Edit+"]");
            this.refresh();
        });
        this.onClickCopy.add((a,b,c)->
        {
            if(enableCopy==false)
                return;
            crud_acction = GUI_Acctions.COPY;
            this.drawBorderBackground(Material.LIME_STAINED_GLASS_PANE);
            this.addItem(5,3, GUI_Button_Factory.BackButton(PotionType.JUMP));
            this.addItem(5,4, GUI_Button_Factory.CancelButton());
            this.addItem(5,5, GUI_Button_Factory.NextButton(PotionType.JUMP));
            this.setAlert(ChatColor.DARK_GREEN+" ["+ GUI_Language_Manager.lang().Copy+"]");
            this.refresh();

        });
        this.onClickDelete.add((a,b,c)->
        {

            if(enableDeleteAll==true && a.tag.equalsIgnoreCase("deleteall"))
            {
                askBox.onYes = (x,y,z)->
                {
                    onDeleteAll.forEach(e -> e.Execute(a,b,c));
                    this.open(y);
                    return;
                };
                askBox.onNo = (x,y,z)->
                {
                    this.open(y);
                    return;
                };
                askBox.open(b);
                return;
            }

            if(enableDeleting==true)
            {
                crud_acction = GUI_Acctions.DELETE;
                this.drawBorderBackground(Material.RED_STAINED_GLASS_PANE);
                this.addItem(5,3, GUI_Button_Factory.BackButton(PotionType.INSTANT_HEAL));
                this.addItem(5,4, GUI_Button_Factory.CancelButton());
                this.addItem(5,5, GUI_Button_Factory.NextButton(PotionType.INSTANT_HEAL));
                this.setAlert(ChatColor.DARK_RED+" ["+GUI_Language_Manager.lang().Delete+"]");
                this.refresh();
                return;
            }

        });



        this.onClicekCancel.add((a,b,c)->{
            crud_acction=GUI_Acctions.EMPTY;
            DrawBackGround();
            this.addItem(5,4, GUI_Button_Factory.BackGroundButton(this.backgroundMaterial));
            this.refresh();
        });
        this.onClickItem.add((a,b,c) ->
        {
            GUI_Acctions selected_acction = crud_acction;
            crud_acction=GUI_Acctions.EMPTY;

            switch (selected_acction)
            {
                case EDIT:
                    this.forceAcction(GUI_Acctions.CANCEL);
                    onEdit.forEach(e -> e.Execute(a,b,c));
                    break;
                case DELETE:
                    this.forceAcction(GUI_Acctions.CANCEL);
                    onDelete.forEach(e -> e.Execute(a,b,c));

                    break;
                case COPY:
                    this.forceAcction(GUI_Acctions.CANCEL);
                    onCopy.forEach(e -> e.Execute(a,b,c));
                    break;
                case GET:
                    onSelect.forEach(e -> e.Execute(a,b,c));
                    break;
                case SEARCH:
                    onSearch.forEach(e -> e.Execute(a,b,c));
            }
        });
        this.onClickSearch.add((a,b,c)->{


            this.close();
            GUI_Textbox text = new GUI_Textbox(GUI_Language_Manager.lang().Search,this);
            text.onText =(x,y)->
            {

                this.OpenPage(1);
                this.Filter_Content(x);
                this.forceAcction(GUI_Acctions.EMPTY);
                this.refresh();
            };
            text.onExit =(x,y)->
            {

               this.filtred_content=new ArrayList<>();
               this.OpenPage(1);
               this.forceAcction(GUI_Acctions.EMPTY);
                this.refresh();
            };
            text.Open(b);
        });

    }

    private void DrawBackGround()
    {
        this.drawBorderBackground(Material.BLACK_STAINED_GLASS_PANE);
        this.addItem(0,0, GUI_Button_Factory.SearchButton());



        this.addItem(5,5, GUI_Button_Factory.NextButton(PotionType.WEAKNESS));
        this.addItem(5,3, GUI_Button_Factory.BackButton(PotionType.WEAKNESS));
        this.addItem(5,8, GUI_Button_Factory.ExitButton());
    }
    public void Filter_Content(String value)
    {

        if(value==null)
        {

            filtred_content=content;
            this.OpenPage(1);
            return;
        }
         value =value.toLowerCase();
        filtred_content = new ArrayList<>();

        for(int i=0;i<content.size();i++)
        {
            if(content.get(i).getItemMeta().getDisplayName().toLowerCase().startsWith(value))
                filtred_content.add(content.get(i));
        }

        this.OpenPage(1);
    }
    public void ClearItems()
    {
        this.content.clear();
        this.filtred_content.clear();

        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.addItem(j, i,  null);
            }
        }
    }


    public GUI_List addItems(ArrayList<GUI_Button> items)
    {
        int new_max_size =  7*(this.height-2);
     this.max_itemsonpage = new_max_size==0?1:new_max_size;
     this.max_pages = (int) Math.ceil(items.size()/(double)max_itemsonpage);
     this.content = items;
     this.filtred_content =new ArrayList<>();
     OpenPage(page);
     return this;
    }

    public GUI_List NextPage()
    {
        if(this.page+1<=this.max_pages)
        {
            this.page+=1;
            OpenPage(this.page);
            this.refresh();
        }
        return this;
    }
    public GUI_List BackPage()
    {
        if(this.page-1>=1)
        {
            this.page-=1;
            OpenPage(this.page);
            this.refresh();
        }
        return this;
    }
    public GUI_List OpenPage(int page)
    {
        ArrayList<GUI_Button> content_to_show = this.filtred_content.size() == 0 ? this.content : this.filtred_content;





        int size = content_to_show.size() - 1;
        int start_index = max_itemsonpage * (page - 1);

        int end_index = max_itemsonpage * page > size ? size : (max_itemsonpage * page);
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {

                if (start_index <= end_index) {
                    GUI_Button itemStack = content_to_show.get(start_index);
                    this.addItem(j, i,  itemStack);
                    start_index += 1;
                } else {
                    this.addItem(j, i,null);
                }

            }
        }
       if(max_pages>1)
           this.setAlert( " " + ChatColor.DARK_GRAY + "["+ String.valueOf(page)+"/" + String.valueOf(max_pages) + "]");
       else
           this.setAlert( " " + ChatColor.DARK_GRAY );
      return this;
    }

}
