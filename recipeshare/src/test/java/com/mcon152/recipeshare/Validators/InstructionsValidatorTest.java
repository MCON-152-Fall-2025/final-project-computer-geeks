package com.mcon152.recipeshare.Validators;

import Validators.InstructionsPresentValidator;
import Validators.ValidationErrors;
import com.mcon152.recipeshare.web.RecipeRequest;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class InstructionsValidatorTest {
    @BeforeAll
    static void setup() {
        validator = new InstructionsPresentValidator();
        recipe = new RecipeRequest();
        recipe.setInstructions("Test Instructions");
    }

    static InstructionsPresentValidator validator;
    static RecipeRequest recipe;

    @Test
    void testValidInstructions() {
        ArrayList<String> errors = new ArrayList<>();
        validator.validate(recipe, errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testEmptyInstructions() {
        RecipeRequest recipeB = new RecipeRequest();
        recipeB.setInstructions("");
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
    }

}