package GUI.Inventory.Languages;

import Languages.Language;
import Languages.Translation_Manager;
import Managers.File_Manager;

public class GUI_Language_Manager extends Translation_Manager<GUI_Translation>
{
    public GUI_Language_Manager()
    {
        super(File_Manager.CurrentPath()+"\\plugins\\JW_API\\GUI\\", GUI_Translation.class);
    }


    private static GUI_Language_Manager translations;

    public static GUI_Language_Manager  Instance()
    {
        if(translations==null)
        {
            translations = new GUI_Language_Manager();
            translations.add_Translation(GUI_Language_Manager.Polish());
            translations.add_Translation(GUI_Language_Manager.English());
            translations.Create_Files();
        }
        return translations;
    }

    public static GUI_Translation lang()
    {
        return GUI_Language_Manager.Instance().get_Translation();
    }

    private static GUI_Translation Polish()
    {
        GUI_Translation result = new GUI_Translation();
        result.Cancel = "Anuluj";
        result.Insert = "Wstaw";
        result.Delete = "Usuń";
        result.Edit ="Edytuj";
        result.Next ="Następna";
        result.Back ="Powrót";
        result.previous = "Poprzednia";
        result.Close ="Zamknij";
        result.Copy = "Kopiuj";
        result.Search ="Szukaj";
        result.Add = "Dodaj";
        result.Page = "Strona";
        result.Yes = "Tak";
        result.No = "Nie";
        result.Select = "Wybierz";
        result.Language ="Język";
        result.Polish = "Polski";
        result.English = "Angielski";
        result.Delete_all = "Usuń wszystko";
        result.Ask_box_Question = "Jesteś pewien?";
        result.language= Language.Polish;


        return result;
    }
    private static GUI_Translation English()
    {
        GUI_Translation result = new GUI_Translation();
        result.Cancel = "Cancel";
        result.Insert = "Insert";
        result.Delete = "Delete";
        result.Edit ="Edit";
        result.Next ="Next";
        result.Back ="Back";
        result.previous = "Previous";
        result.Close ="Close";
        result.Copy = "Copy";
        result.Search ="Search";
        result.Add = "Add";
        result.Page = "Page";
        result.Yes = "Yes";
        result.No = "No";
        result.Select = "Select";
        result.Language ="Language";
        result.Polish = "Polish";
        result.English = "English";
        result.Delete_all = "Delete all";
        result.Ask_box_Question = "Are you sure?";
        result.language=Language.English;


        return result;
    }








}