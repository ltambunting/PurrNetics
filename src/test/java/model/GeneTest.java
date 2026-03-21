package model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GeneTest {
    private Trait t;
    private Gene g;
    private AutosomalInheritance air;
    
    @BeforeEach
    public void setup() {
        g = new Gene("L", t, air);
    }

    @Test
    public void getterTestsTrivial() {
        assertEquals("L", g.getSymbol());
        assertEquals(t, g.getTrait());
        assertEquals(air, g.getInheritanceRule());
    }

    @Test
    public void addAlleleTest() {
        assertTrue(g.getAlleles().isEmpty());
        // add one
        Gene.Allele a1 = g.addAllele("S", 1, "ShortHair");
        assertTrue(g.getAlleles().contains(a1));
        // add more than one
        Gene.Allele a2 = g.addAllele("L", 0, "LongHair");
        assertTrue(g.getAlleles().contains(a1));
        assertTrue(g.getAlleles().contains(a2));
    }


}
