package Validators;

import java.util.ArrayList;

import com.mcon152.recipeshare.web.RecipeRequest;

public abstract class RecipeValidator {
    
    public abstract void setNext(RecipeValidator next);
    public abstract void validate(RecipeRequest recipe, ArrayList<String> errors);

}
