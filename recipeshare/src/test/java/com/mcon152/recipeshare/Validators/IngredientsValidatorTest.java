package com.mcon152.recipeshare.Validators;

import Validators.IngredientsValidator;
import Validators.ValidationErrors;
import com.mcon152.recipeshare.web.RecipeRequest;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientsValidatorTest {

    static IngredientsValidator validator;
    static RecipeRequest recipe;

    @BeforeAll
    static void setup() {
        validator = new IngredientsValidator();
        recipe = new RecipeRequest();
        recipe.setTitle("Test Title");
        recipe.setIngredients("2 cups flour, 1 cup sugar");
        recipe.setInstructions("Mix and bake");
        recipe.setServings(4);
    }

    @Test
    void testValidIngredients() {
        ArrayList<String> errors = new ArrayList<>();
        validator.validate(recipe, errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testEmptryIngredients() {
        RecipeRequest recipeB = new RecipeRequest();
        recipeB.setTitle("Test");
        recipeB.setIngredients("");
        recipeB.setInstructions("Test instructions");
        recipeB.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
    }

    @Test
    void testNullIngredients() {
        RecipeRequest recipeC = new RecipeRequest();
        recipeC.setTitle("Test");
        recipeC.setIngredients(null);
        recipeC.setInstructions("Test instructions");
        recipeC.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeC, errors));
    }



}