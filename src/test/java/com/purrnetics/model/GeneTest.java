package com.purrnetics.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GeneTest {
    private Trait trait;
    private Gene gene;
    private InheritanceRule autosomalInheritance;
    private ExpressionRule completeDominance;
    
    @BeforeEach
    public void setup() {
        autosomalInheritance = new AutosomalInheritance();
        completeDominance = new CompleteDominance();
        trait = new Trait("Fur Length");
        gene = new Gene("L", trait, autosomalInheritance, completeDominance);
    }

    @Test
    public void getterTestsTrivial() {
        assertEquals("L", gene.getSymbol());
        assertEquals(trait, gene.getTrait());
        assertEquals(autosomalInheritance, gene.getInheritanceRule());
    }

    @Test
    public void addAlleleTest() {
        assertTrue(gene.getAlleles().isEmpty());
        // add one
        Gene.Allele a1 = gene.addAllele("S", 1, "ShortHair");
        assertTrue(gene.getAlleles().contains(a1));
        // add more than one
        Gene.Allele a2 = gene.addAllele("L", 0, "LongHair");
        assertTrue(gene.getAlleles().contains(a1));
        assertTrue(gene.getAlleles().contains(a2));
        assertEquals(2, gene.getAlleles().size());
    }

    @Test
    public void getAlleleBySymbolExistingAlleleTest() {
        Gene.Allele shortHair = gene.addAllele("S", 1, "ShortHair");
        Gene.Allele longHair = gene.addAllele("L", 0, "LongHair");

        assertEquals(shortHair, gene.getAlleleBySymbol("S"));
        assertEquals(longHair, gene.getAlleleBySymbol("L"));
    }

    @Test
    public void getAlleleBySymbolMissingAlleleThrowsTest() {
        gene.addAllele("S", 1, "ShortHair");
        try {
            gene.getAlleleBySymbol("X");
        } catch (IllegalArgumentException e) {
            return; // expected exception
        }
        fail("Expected IllegalArgumentException to be thrown");
    }
}
