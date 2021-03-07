package Languages;

import GUI.Inventory.Languages.GUI_Language_Manager;

import java.util.ArrayList;

public class Language_Manager
{

    ArrayList<Translation_Manager> translation_managers = new ArrayList<>();

    public Language language = Language.English;

    public static Language_Manager instance;

     public Language_Manager()
     {
         Add_Translation_Manager(GUI_Language_Manager.Instance());
     }

     public static Language_Manager Instance()
     {
         if(instance==null)
             instance = new Language_Manager();

         return instance;
     }

    public void Add_Translation_Manager(Translation_Manager translation_manager)
    {
        if(translation_managers.contains(translation_manager)==false)
            translation_managers.add(translation_manager);
    }
    public void Set_Language(Language language)
    {
        this.language = language;
        translation_managers.forEach(t -> t.set_language(language));
    }
}
