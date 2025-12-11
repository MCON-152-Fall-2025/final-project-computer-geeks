package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.*;
import com.mcon152.recipeshare.domain.reciperegistry;
import com.mcon152.recipeshare.web.reciperequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class reciperegisterTest {
    @BeforeAll
    static void setup() {
        // Ensure the registry is initialized.
        reciperegistry.register("BASIC", new Basicrecipe());
        reciperegistry.register("VEGETARIAN", new Vegetarianrecipe());
        reciperegistry.register("DESSERT", new Dessertrecipe());
        reciperegistry.register("DAIRY", new Dairyrecipe());
    }

    @Test
    void createsBasicByDefault_andCopiesFields() {
        reciperequest req = new reciperequest();
        req.setTitle("Title");
        req.setDescription("Desc");
        req.setIngredients("I");
        req.setInstructions("N");
        req.setServings(3);

        recipe r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Basicrecipe);
        assertNull(r.getId());
        assertEquals("Title", r.getTitle());
        assertEquals("Desc", r.getDescription());
        assertEquals("I", r.getIngredients());
        assertEquals("N", r.getInstructions());
        assertEquals(Integer.valueOf(3), r.getServings());
    }

    @Test
    void createsSpecifiedSubtypes_caseInsensitive() {
        reciperequest req = new reciperequest();
        req.setType("vegetarian");
        recipe r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Vegetarianrecipe);

        req.setType("DESSERT");
        r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Dessertrecipe);

        req.setType("DAIRY");
        r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Dairyrecipe);

        req.setType("BASIC");
        r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Basicrecipe);
    }

    @Test
    void nullrequest_returnsBasic_withNullFields() {
        recipe r = reciperegistry.createFromrequest(null);
        assertTrue(r instanceof Basicrecipe);
        assertNull(r.getTitle());
        assertNull(r.getDescription());
        assertNull(r.getIngredients());
        assertNull(r.getInstructions());
        assertNull(r.getServings());
    }

    @Test
    void unknownType_defaultsToBasic() {
        reciperequest req = new reciperequest();
        req.setType("UNKNOWN");
        recipe r = reciperegistry.createFromrequest(req);
        assertTrue(r instanceof Basicrecipe);
    }
}
