package Validators;

import java.util.ArrayList;

import com.mcon152.recipeshare.web.RecipeRequest;

public class TitleValidator extends RecipeValidator {

    private RecipeValidator next;

    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ArrayList<String> errors) {
        String title = recipe.getTitle();
        if (title == null || title.trim().isEmpty()) {
            errors.add("Title cannot be empty.");
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
