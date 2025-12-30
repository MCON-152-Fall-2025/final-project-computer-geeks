package com.mcon152.recipeshare.Validators;

import Validators.ValidationErrors;
import com.mcon152.recipeshare.web.RecipeRequest;

import Validators.ServingsValidator;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServingsValidatorTest {

    static ServingsValidator validator;
    static RecipeRequest recipe;

    @BeforeAll
    static void setup() {
        validator = new ServingsValidator();
        recipe = new RecipeRequest();
        recipe.setServings(4);
    }

    @Test
    void testValidServings() {
        ArrayList<String> errors = new ArrayList<>();
        validator.validate(recipe, errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidServings() {
        RecipeRequest recipeB = new RecipeRequest();
        recipeB.setServings(0);
        ArrayList<String> errors = new ArrayList<>();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
        assertEquals(1, errors.size());
        recipeB.setServings(-1);
        errors.clear();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
        assertEquals(1, errors.size());
        recipeB.setServings(null);
        errors.clear();
        assertThrows(ValidationErrors.class, () -> validator.validate(recipeB, errors));
        assertEquals(1, errors.size());
    }

}