package ORM.Entitis;


import java.util.ArrayList;

public class Crafting_Recepture_Entity extends I_Entity
{
    public Crafting_entity[] items_to_craft =new Crafting_entity[9];
    public Crafting_entity item_result  = new Crafting_entity();

  public Crafting_entity Get_Item(int index)
  {
      if(items_to_craft[index]==null)
      {
          Crafting_entity entity = new Crafting_entity();
          items_to_craft[index] = entity;
          return entity;
      }
      else
          return items_to_craft[index];
  }

}
