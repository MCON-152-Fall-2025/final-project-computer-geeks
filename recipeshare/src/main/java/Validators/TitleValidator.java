package Validators;

import com.mcon152.recipeshare.web.RecipeRequest;

public class TitleValidator extends RecipeValidator {

    private RecipeValidator next;

    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ValidationErrors error) {
        String title = recipe.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationErrors("Title cannot be empty.");
        }
        if (next != null) {
            next.validate(recipe, error);
        }
    }
    
}
