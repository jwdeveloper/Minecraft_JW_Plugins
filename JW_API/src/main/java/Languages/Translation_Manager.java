package Languages;

import Managers.File_Manager;
import com.mysql.jdbc.NotImplemented;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Translation_Manager<T extends Translation>
{
    public ArrayList<T> translation = new ArrayList<>();
    public String path = new String();
    public Language language = Language.English;
    public Class<T> translationclass;

    public Translation_Manager(String path, Class<T> lanuageclass)
    {
              this.path =path;
              this.translationclass = lanuageclass;
    }
    public void set_language(Language language)
    {
      this.language =language;
    }

    public void add_Translation(T language)
    {
       translation.add(language);
    }
    public T get_Translation()
    {
        return translation.stream().filter(t -> t.language == this.language).findFirst().get();
    }
    public T get_Translation(Language language)
    {
      return translation.stream().filter(t -> t.language == language).findFirst().get();
    }
    public String get_path()
    {
      return path+"languages";
    }
    public boolean Load_Data() {
        try
        {
            Language[] languages = Language.values();
            for(int i=0;i<languages.length;i++)
            {
              T trans = File_Manager.Load(get_path(),languages[i].name()+".json",translationclass);
              translation.add(trans);
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public boolean Create_Files() {
        try {

            for (int i = 0; i < translation.size(); i++)
            {
                File_Manager.Save(translation.get(i), get_path(), translation.get(i).language.name() + ".json");
            }

            return true;
        } catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+e.getMessage());
            return false;
        }
    }
}
