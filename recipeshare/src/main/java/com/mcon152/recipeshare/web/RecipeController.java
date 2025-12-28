package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.domain.RecipeRegistry;
import com.mcon152.recipeshare.service.RecipeService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.LoggerFactory;

import Validators.IngredientsValidator;
import Validators.InstructionsPresentValidator;
import Validators.TitleValidator;
import Validators.ValidationErrors;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Builds and executes the validation chain.
     * Throws ValidationErrors if validation fails.
     */
    private void validateRequest(RecipeRequest request) {
        TitleValidator titleValidator = new TitleValidator();
        IngredientsValidator ingredientsValidator = new IngredientsValidator();
        InstructionsPresentValidator instructionsValidator = new InstructionsPresentValidator();

        titleValidator.setNext(ingredientsValidator);
        ingredientsValidator.setNext(instructionsValidator);

        titleValidator.validate(request, new ArrayList<>());
    }

    /**
     * Create a new recipe.
     * Returns 201 Created with Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<?> addRecipe(@RequestBody RecipeRequest recipeRequest) {
        logger.info("Received request to add recipe: {}", recipeRequest);
        try {
            validateRequest(recipeRequest);
            Recipe toSave = RecipeRegistry.createFromRequest(recipeRequest);
            Recipe saved = recipeService.addRecipe(toSave);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()           // /api/recipes
                    .path("/{id}")                  // /{id}
                    .buildAndExpand(saved.getId())
                    .toUri();
           logger.debug("Recipe created with ID: {}", saved.getId());
            return ResponseEntity.created(location).body(saved);
        } catch (ValidationErrors e) {
            logger.warn("Validation failed: {}", e.getErrors());
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            logger.error("Error adding recipe", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieve all recipes. 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        logger.info("Received request to get all recipes");
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    /**
     * Retrieve a recipe by id. 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        logger.info("Received request to get recipe by ID: {}", id);
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a recipe. 204 No Content if deleted, 404 Not Found otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        logger.info("Received request to delete recipe with ID: {}", id);
        try {
            boolean deleted = recipeService.deleteRecipe(id);
            logger.debug("Recipe deletion status for ID {}: {}", id, deleted);
            return deleted
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting recipe with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Replace a recipe (full update). 200 OK with updated entity or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable long id, @RequestBody RecipeRequest updatedRequest) {
        logger.info("Received request to update recipe with ID: {}", id);
        try {
        validateRequest(updatedRequest);
        Recipe updatedRecipe = RecipeRegistry.createFromRequest(updatedRequest);
        logger.debug("Updating recipe with data: {}", updatedRecipe);
            return recipeService.updateRecipe(id, updatedRecipe)
                    .map(recipe -> ResponseEntity.ok((Object) recipe))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ValidationErrors e) {
            logger.warn("Validation failed: {}", e.getErrors());
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            logger.error("Error updating recipe with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }

    }

    /**
     * Partial update. 200 OK with updated entity or 404 Not Found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchRecipe(@PathVariable long id, @RequestBody RecipeRequest partialRequest) {
        logger.info("Received request to patch recipe with ID: {}", id);
        try {
            Recipe partialRecipe = RecipeRegistry.createFromRequest(partialRequest);
            logger.debug("Patching recipe with data: {}", partialRecipe);
            return recipeService.patchRecipe(id, partialRecipe)
                    .map(recipe -> ResponseEntity.ok((Object) recipe))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error patching recipe with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
