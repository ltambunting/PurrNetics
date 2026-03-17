package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneTest {
    private Trait t;
    private Gene g;
    private Allele a1;
    private Allele a2;
    
    @BeforeEach
    public void setup() {
        g = new Gene("L", t, InheritanceRule.AUTOSOMAL);
        a1 = new Allele(g, "N", 2);
        a2 = new Allele(g, "M4", 1);
    }

    @Test
    public void getterTests() {
        assertEquals("L", g.getSymbol());
        assertEquals(t, g.getTrait());
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
