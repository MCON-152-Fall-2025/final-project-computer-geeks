package Validators;

import java.util.*;
import com.mcon152.recipeshare.web.RecipeRequest;

public class IngredientsValidator  extends RecipeValidator{
    private RecipeValidator next;

    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ArrayList<String> errors){
        String ingredients = recipe.getIngredients();

        if (ingredients == null || ingredients.trim().isEmpty()){
            errors.add("Ingredients cannot be empty.");
        }

        if (next != null) {
            next.validate(recipe, errors);
        } else {
            if (!errors.isEmpty()) {
                throw new ValidationErrors(errors);
            }
        }
    }
}
