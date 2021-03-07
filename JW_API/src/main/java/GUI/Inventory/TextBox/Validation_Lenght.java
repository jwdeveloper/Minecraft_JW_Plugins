package GUI.Inventory.TextBox;

public class Validation_Lenght implements I_Validator
{


    int min_lenght =0;
    int max_lenght=0;

    public Validation_Lenght(int min_lenght, int max_lenght)
    {
        this.min_lenght = min_lenght;
        this.max_lenght = max_lenght;
    }

    @Override
    public boolean validate(String input)
    {
        if(input.length()<min_lenght|| input.length()>max_lenght)
           return false;
        else
           return true;
    }
}
