package Validators;

import java.util.ArrayList;

import com.mcon152.recipeshare.web.RecipeRequest;

//Develop a concrete validator that checks if the recipe contains instructions, extending RecipeValidator.
public class InstructionsPresentValidator extends RecipeValidator {
    private RecipeValidator next;
    
    @Override
    public void setNext(RecipeValidator next) {
        this.next = next;
    }

    @Override
    public void validate(RecipeRequest recipe, ArrayList<String> errors) {
        String instructions = recipe.getInstructions();
        if (instructions == null || instructions.trim().isEmpty()) {
            errors.add("Instructions are required and cannot be empty.");
        } else if (instructions.length() < 6) {
            errors.add("Instructions cannot be under 6 characters.");
        }
        if (next != null) {
            next.validate(recipe, errors);
        } else {
            if (errors.size() > 0) {
                throw new ValidationErrors(errors);
            }
        }
    }
}
