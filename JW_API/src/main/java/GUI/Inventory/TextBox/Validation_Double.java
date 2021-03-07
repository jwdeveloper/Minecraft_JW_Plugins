package GUI.Inventory.TextBox;

public class Validation_Double implements I_Validator {
    @Override
    public boolean validate(String input)
    {
        try
        {
            int first_dot = input.indexOf('.');
            int last_dot = input.lastIndexOf('.');



            if(first_dot!=last_dot)
                return false;


            for(int i=0;i<input.length();i++)
            {
                char c = input.charAt(i);
                if( (c <48 || c>57) && c != '.')
                    return false;
            }

           return true;
        }
        catch (Exception e)
        {
          return false;
        }

    }
}
