package com.purrnetics.model;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhenotypeTest {
    private Trait trait1;
    private Trait trait2;
    private Trait trait3;
    private Map<Trait, String> phenotypeMap1;
    private Map<Trait, String> phenotypeMap2;
    private Phenotype phenotype1;
    private Phenotype phenotype2;

    @BeforeEach
    public void setup() {
        trait1 = new Trait("CoatLength");
        trait1.addTraitVariant("ShortHair");
        trait1.addTraitVariant("LongHair");

        trait2 = new Trait("EyeColour");
        trait2.addTraitVariant("Gold");
        trait2.addTraitVariant("Green");
        trait2.addTraitVariant("Blue");

        trait3 = new Trait("CoatPattern");
        trait3.addTraitVariant("Solid");
        trait3.addTraitVariant("Striped");
        trait3.addTraitVariant("Spots");

        phenotypeMap1 = new HashMap<>();
        phenotypeMap1.put(trait1, "ShortHair");
        phenotypeMap1.put(trait2, "Gold");
        phenotypeMap1.put(trait3, "Solid");

        phenotypeMap2 = new HashMap<>();
        phenotypeMap2.put(trait1, "LongHair");
        phenotypeMap2.put(trait2, "Blue");
        phenotypeMap2.put(trait3, "Solid");

        phenotype1 = new Phenotype(phenotypeMap1);
        phenotype2 = new Phenotype(phenotypeMap2);
    }

    @Test
    public void phenotypeGetterTest() {
        assertEquals(phenotypeMap1, phenotype1.getExpressedVariants());
        assertEquals(phenotypeMap2, phenotype2.getExpressedVariants());
    }

    @Test
    public void getExpressedVariantOneTrait() {
        assertTrue(phenotype1.getExpressedVariant(trait1).equals("ShortHair"));
        assertFalse(phenotype1.getExpressedVariant(trait1).equals("LongHair"));
    }

    @Test
    public void getExpressedVariantMultipleTraitsTest() {
        assertTrue(phenotype1.getExpressedVariant(trait1).equals("ShortHair"));
        assertFalse(phenotype1.getExpressedVariant(trait1).equals("LongHair"));
        assertTrue(phenotype1.getExpressedVariant(trait2).equals("Gold"));
        assertFalse(phenotype1.getExpressedVariant(trait1).equals("Green"));
        assertFalse(phenotype1.getExpressedVariant(trait1).equals("Blue"));
    }
}
