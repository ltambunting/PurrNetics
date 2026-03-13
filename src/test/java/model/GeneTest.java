package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneTest {
    private Gene g;
    private Allele a1;
    private Allele a2;
    
    @BeforeEach
    public void setup() {
        g = new Gene("L", "coatLength", InheritanceRule.AUTOSOMAL);
        a1 = new Allele(g, "N", DominanceRule.DOMINANT, "shortHair");
        a2 = new Allele(g, "M4", DominanceRule.RECESSIVE, "longHair");
    }

    @Test
    public void getterTests() {
        assertEquals("L", g.getSymbol());
        assertEquals("coatLength", g.getTraitName());
        assertEquals(InheritanceRule.AUTOSOMAL, g.getInheritanceRule());
    }

    @Test
    public void addAlleleTest() {
        assertTrue(g.getAlleles().isEmpty());
        // add one
        g.addAllele(a1);
        assertEquals(a1, g.getAlleles().get(0));
        // add more than one
        g.addAllele(a2);
        assertEquals(a1, g.getAlleles().get(0));
        assertEquals(a2, g.getAlleles().get(1));
    }

}
