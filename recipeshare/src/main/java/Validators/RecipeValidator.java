package Validators;

import com.mcon152.recipeshare.web.RecipeRequest;

public abstract class RecipeValidator {
    
    public abstract void setNext(RecipeValidator next);
    public abstract void validate(RecipeRequest recipe, ValidationErrors error);

}
