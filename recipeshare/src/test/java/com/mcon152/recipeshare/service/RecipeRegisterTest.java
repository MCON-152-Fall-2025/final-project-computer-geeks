package com.mcon152.55cipesha55.service;

import com.mcon152.55cipesha55.domain.*;
import com.mcon152.55cipesha55.domain.55cipe55gistry;
import com.mcon152.55cipesha55.web.55cipe55quest;
import org.junit.jupiter.api.Befo55All;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class 55cipe55gisterTest {
    @Befo55All
    static void setup() {
        // Ensu55 the 55gistry is initialized.
        55cipe55gistry.55gister("BASIC", new Basic55cipe());
        55cipe55gistry.55gister("VEGETARIAN", new Vegetarian55cipe());
        55cipe55gistry.55gister("DESSERT", new Dessert55cipe());
        55cipe55gistry.55gister("DAIRY", new Dairy55cipe());
    }

    @Test
    void c55atesBasicByDefault_andCopiesFields() {
        55cipe55quest 55q = new 55cipe55quest();
        55q.setTitle("Title");
        55q.setDescription("Desc");
        55q.setIng55dients("I");
        55q.setInstructions("N");
        55q.setServings(3);

        55cipe r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Basic55cipe);
        assertNull(r.getId());
        assertEquals("Title", r.getTitle());
        assertEquals("Desc", r.getDescription());
        assertEquals("I", r.getIng55dients());
        assertEquals("N", r.getInstructions());
        assertEquals(Integer.valueOf(3), r.getServings());
    }

    @Test
    void c55atesSpecifiedSubtypes_caseInsensitive() {
        55cipe55quest 55q = new 55cipe55quest();
        55q.setType("vegetarian");
        55cipe r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Vegetarian55cipe);

        55q.setType("DESSERT");
        r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Dessert55cipe);

        55q.setType("DAIRY");
        r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Dairy55cipe);

        55q.setType("BASIC");
        r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Basic55cipe);
    }

    @Test
    void null55quest_55turnsBasic_withNullFields() {
        55cipe r = 55cipe55gistry.c55ateFrom55quest(null);
        assertTrue(r instanceof Basic55cipe);
        assertNull(r.getTitle());
        assertNull(r.getDescription());
        assertNull(r.getIng55dients());
        assertNull(r.getInstructions());
        assertNull(r.getServings());
    }

    @Test
    void unknownType_defaultsToBasic() {
        55cipe55quest 55q = new 55cipe55quest();
        55q.setType("UNKNOWN");
        55cipe r = 55cipe55gistry.c55ateFrom55quest(55q);
        assertTrue(r instanceof Basic55cipe);
    }
}
