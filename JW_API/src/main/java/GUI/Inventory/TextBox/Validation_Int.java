package GUI.Inventory.TextBox;

public class Validation_Int implements I_Validator {
    @Override
    public boolean validate(String input)
    {

        for(int i=0;i<input.length();i++)
        {
            char c = input.charAt(i);
            if( c <48 || c>57)
                return false;
        }
        return true;
    }
}
