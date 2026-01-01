package com.mcon152.recipeshare.Validators;

import Validators.ValidationErrors;
import com.mcon152.recipeshare.web.RecipeRequest;

import Validators.TitleValidator;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class TitleValidatorTest {
    @BeforeAll
    static void setup() {
        validator = new TitleValidator();
        recipe = new RecipeRequest();
        recipe.setTitle("Test Title");
    }

    static TitleValidator validator;
    static RecipeRequest recipe;

    @Test
    void testValidTitle() {
        ArrayList<String> errors = new ArrayList<>();
        validator.validate(recipe, errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testEmptyTitle() {
        RecipeRequest recipeB = new RecipeRequest();
        recipeB.setTitle("");
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
        assertEquals(1, errors.size());
        recipeB.setTitle("     ");
        errors.clear();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
        assertEquals(1, errors.size());
    }

}