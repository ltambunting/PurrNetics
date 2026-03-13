package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlleleTest {
    private Gene g;
    private Allele a1;
    
    @BeforeEach
    public void setup() {
        g = new Gene("L", "coatLength", InheritanceRule.AUTOSOMAL);
        a1 = new Allele(g, "N", DominanceRule.DOMINANT, "shortHair");
    }

    @Test
    public void getterTests() {
        assertEquals(g, a1.getGene());
        assertEquals("N", a1.getSymbol());
        assertEquals(DominanceRule.DOMINANT, a1.getDominanceRule());
        assertEquals("shortHair", a1.getTraitVariant());
    }
}
