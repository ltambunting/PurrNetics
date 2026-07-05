package com.purrnetics.model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TraitTest {
    private Trait t;

    @BeforeEach
    public void setup() {
        t = new Trait("coatLength");
    }

    @Test
    public void traitTestTrivial() {
        assertEquals("coatLength", t.getKey());
        assertTrue(t.getTraitVariants().isEmpty()); // ensures empty set is created during instantiation
    }

    @Test
    public void traitTestAddVariants() {
        Set<String> variants = t.getTraitVariants();
        t.addTraitVariant("shortHair");
        assertTrue(variants.contains("shortHair"));
        t.addTraitVariant("longHair");
        assertTrue(variants.contains("shortHair"));
        assertTrue(variants.contains("longHair"));
    }

    @Test
    public void toStringTest() {
        assertEquals("coatLength", t.toString());
    }
}
