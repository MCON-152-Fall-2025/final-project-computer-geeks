package Validators;

import java.util.ArrayList;

import com.mcon152.recipeshare.web.RecipeRequest;

public class ServingsValidator extends RecipeValidator {

    private RecipeValidator next;

    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ArrayList<String> errors) {
        Integer servings = recipe.getServings();
        if (servings == null || servings <= 0) {
            errors.add("Servings must be a positive number.");
        }
        if (next != null) {
            next.validate(recipe, errors);
        } else {
            if(errors.size() > 0) {
                throw new ValidationErrors(errors);
            }
        }
    }
    
}