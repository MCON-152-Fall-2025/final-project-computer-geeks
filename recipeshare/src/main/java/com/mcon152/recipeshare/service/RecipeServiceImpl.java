package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.domain.RecipeRegistry;
import com.mcon152.recipeshare.domain.Tag;
import com.mcon152.recipeshare.repository.RecipeRepository;
import com.mcon152.recipeshare.web.RecipeRequest;
import org.springframework.stereotype.Service;

import Validators.IngredientsValidator;
import Validators.InstructionsPresentValidator;
import Validators.TitleValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repo;

    public RecipeServiceImpl(RecipeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Recipe addRecipe(RecipeRequest request) {
        // Execute validation chain (throws ValidationErrors if invalid)
        validateRequest(request);

        // Convert request to Recipe and save
        Recipe recipe = RecipeRegistry.createFromRequest(request);
        recipe.setId(null);
        return repo.save(recipe);
    }

    @Override
    public Optional<Recipe> updateRecipe(long id, RecipeRequest request) {
        // Execute validation chain (throws ValidationErrors if invalid)
        validateRequest(request);

        Recipe updatedRecipe = RecipeRegistry.createFromRequest(request);
        return doUpdate(id, updatedRecipe);
    }

    @Override
    public Optional<Recipe> patchRecipe(long id, RecipeRequest request) {
        // Execute validation chain (throws ValidationErrors if invalid)
        validateRequest(request);

        Recipe partialRecipe = RecipeRegistry.createFromRequest(request);
        return doPatch(id, partialRecipe);
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

        titleValidator.validate(request, new java.util.ArrayList<>());
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return repo.findAll();
    }

    @Override
    public Optional<Recipe> getRecipeById(long id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private Optional<Recipe> doUpdate(long id, Recipe updatedRecipe) {
        return repo.findById(id).map(existing -> {
            // Preserve entity type (do not replace the DB row with a different subtype)
            existing.setTitle(updatedRecipe.getTitle());
            existing.setDescription(updatedRecipe.getDescription());
            existing.setIngredients(updatedRecipe.getIngredients());
            existing.setInstructions(updatedRecipe.getInstructions());
            existing.setServings(updatedRecipe.getServings());

            // Update author if provided
            if (updatedRecipe.getAuthor() != null) {
                existing.setAuthor(updatedRecipe.getAuthor());
            }

            // Update tags if provided (replace all tags)
            if (updatedRecipe.getTags() != null) {
                existing.clearTags();
                updatedRecipe.getTags().forEach(existing::addTag);
            }

            return repo.save(existing);
        });
    }

    private Optional<Recipe> doPatch(long id, Recipe partialRecipe) {
        return repo.findById(id).map(existing -> {
            if (partialRecipe.getTitle() != null) existing.setTitle(partialRecipe.getTitle());
            if (partialRecipe.getDescription() != null) existing.setDescription(partialRecipe.getDescription());
            if (partialRecipe.getIngredients() != null) existing.setIngredients(partialRecipe.getIngredients());
            if (partialRecipe.getInstructions() != null) existing.setInstructions(partialRecipe.getInstructions());
            if (partialRecipe.getServings() != null) existing.setServings(partialRecipe.getServings());

            // Patch author if provided
            if (partialRecipe.getAuthor() != null) {
                existing.setAuthor(partialRecipe.getAuthor());
            }

            // Patch tags if provided (replace all tags)
            if (partialRecipe.getTags() != null && !partialRecipe.getTags().isEmpty()) {
                existing.clearTags();
                partialRecipe.getTags().forEach(existing::addTag);
            }

            return repo.save(existing);
        });
    }

    // Tag-related operations

    @Override
    public Optional<Recipe> addTagToRecipe(long recipeId, Tag tag) {
        return repo.findById(recipeId).map(recipe -> {
            recipe.addTag(tag);
            return repo.save(recipe);
        });
    }

    @Override
    public Optional<Recipe> removeTagFromRecipe(long recipeId, Tag tag) {
        return repo.findById(recipeId).map(recipe -> {
            recipe.removeTag(tag);
            return repo.save(recipe);
        });
    }

    @Override
    public List<Recipe> findRecipesByTag(String tagName) {
        return repo.findAll().stream()
                .filter(recipe -> recipe.getTags().stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findRecipesByTagId(long tagId) {
        return repo.findAll().stream()
                .filter(recipe -> recipe.getTags().stream()
                        .anyMatch(tag -> tag.getId() != null && tag.getId().equals(tagId)))
                .collect(Collectors.toList());
    }
}
