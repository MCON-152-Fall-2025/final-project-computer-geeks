package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.domain.Tag;
import com.mcon152.recipeshare.web.RecipeRequest;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe addRecipe(RecipeRequest request);
    Optional<Recipe> updateRecipe(long id, RecipeRequest request);
    Optional<Recipe> patchRecipe(long id, RecipeRequest request);
    List<Recipe> getAllRecipes();
    Optional<Recipe> getRecipeById(long id);
    boolean deleteRecipe(long id);

    // Tag-related operations
    Optional<Recipe> addTagToRecipe(long recipeId, Tag tag);
    Optional<Recipe> removeTagFromRecipe(long recipeId, Tag tag);
    List<Recipe> findRecipesByTag(String tagName);
    List<Recipe> findRecipesByTagId(long tagId);
}
