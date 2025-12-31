package com.mcon152.recipeshare.Validators;

import Validators.IngredientsValidator;
import Validators.ValidationErrors;
import com.mcon152.recipeshare.web.RecipeRequest;
import Validators.InstructionsPresentValidator;
import Validators.ServingsValidator;
import Validators.TitleValidator;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllValidatorsTest {
    static TitleValidator titleValidator;
    static IngredientsValidator ingredientsValidator;
    static InstructionsPresentValidator instructionsValidator;
    static ServingsValidator servingsValidator;

    @BeforeAll
    static void setup() {
        titleValidator = new TitleValidator();
        ingredientsValidator = new IngredientsValidator();
        instructionsValidator = new InstructionsPresentValidator();
        servingsValidator = new ServingsValidator();

        titleValidator.setNext(ingredientsValidator);
        ingredientsValidator.setNext(instructionsValidator);
        instructionsValidator.setNext(servingsValidator);
    }

    @Test
    void testOnlyTitleEmpty() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("");
        recipe.setIngredients("2 cups flour, 1 cup sugar");
        recipe.setInstructions("Mix and bake");
        recipe.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> titleValidator.validate(recipe, errors));
        assertEquals(1, errors.size());
        assertEquals("Title cannot be empty.", errors.get(0));
    }

    @Test
    void testOnlyIngredientsEmpty() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("Chocolate Cake");
        recipe.setIngredients("");
        recipe.setInstructions("Mix and bake");
        recipe.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> titleValidator.validate(recipe, errors));
        assertEquals(1, errors.size());
        assertEquals("Ingredients cannot be empty.", errors.get(0));
    }

    @Test
    void testOnlyInstructionsEmpty() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("Chocolate Cake");
        recipe.setIngredients("2 cups flour, 1 cup sugar");
        recipe.setInstructions("");
        recipe.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> titleValidator.validate(recipe, errors));
        assertEquals(1, errors.size());
        assertEquals("Instructions are required and cannot be empty.", errors.get(0));
    }

    @Test
    void testOnlyServingsEmpty() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("Chocolate Cake");
        recipe.setIngredients("2 cups flour, 1 cup sugar");
        recipe.setInstructions("Mix and bake");
        recipe.setServings(0);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> titleValidator.validate(recipe, errors));
        assertEquals(1, errors.size());
        assertEquals("Servings must be a positive number.", errors.get(0));
    }

    @Test
    void testAllFieldsEmpty() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("");
        recipe.setIngredients("");
        recipe.setInstructions("");
        recipe.setServings(0);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> titleValidator.validate(recipe, errors));
        assertEquals(4, errors.size());
        assertTrue(errors.contains("Title cannot be empty."));
        assertTrue(errors.contains("Ingredients cannot be empty."));
        assertTrue(errors.contains("Instructions are required and cannot be empty."));
        assertTrue(errors.contains("Servings must be a positive number."));
    }

    @Test
    void testValidRecipe() {
        RecipeRequest recipe = new RecipeRequest();
        recipe.setTitle("Chocolate Cake");
        recipe.setIngredients("2 cups flour, 1 cup sugar");
        recipe.setInstructions("Mix and bake");
        recipe.setServings(4);
        ArrayList<String> errors = new ArrayList<>();
        assertDoesNotThrow(() -> titleValidator.validate(recipe, errors));
        assertEquals(0, errors.size());
    }

}