package ORM.Annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ORM_Varchat
{
    int min_lenght() default 0;
    int max_lenght() default 50;
}
