package com.purrnetics.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlleleTest {
    private Gene g;
    private Gene.Allele a;
    private Trait t1;
    private AutosomalInheritance air;
    private ExpressionRule dominanceRule;
    
    @BeforeEach
    public void setup() {
        air = new AutosomalInheritance();
        dominanceRule = new CompleteDominance();
        t1 = new Trait("coatLength");
        g = new Gene("L", t1, air, dominanceRule);
        a = g.addAllele("N", 1, "shortHair");
    }

    @Test
    public void getterTests() {
        assertEquals("N", a.getAlleleSymbol());
        assertEquals(1, a.getRank());
        assertEquals("shortHair", a.getVariant());
    }
}
