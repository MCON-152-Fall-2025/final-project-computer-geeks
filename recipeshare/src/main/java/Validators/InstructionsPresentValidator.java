package Validators;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.web.RecipeRequest;

//Develop a concrete validator that checks if the recipe contains instructions, extending RecipeValidator.
public class InstructionsPresentValidator extends RecipeValidator {
    private RecipeValidator next;
    
    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ValidationErrors error) {
        String instructions = recipe.getInstructions();
        if (instructions == null || instructions.trim().isEmpty()) {
            throw new ValidationErrors("Instructions are required and cannot be empty.");
        }
        if(instructions.length() > 6) {
            throw new ValidationErrors("Instructions cannot exceed 6 characters.");
        }
        if (next != null) {
            next.validate(recipe, error);
        }
    }

    public boolean validate(Recipe recipe) {
        String instructions = recipe.getInstructions();
        return instructions != null && !instructions.trim().isEmpty();
    }
}