package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlleleTest {
    private Gene g;
    private Allele a1;
    private Trait t1;
    private AutosomalInheritance air;
    
    @BeforeEach
    public void setup() {
        air = new AutosomalInheritance();
        t1 = new Trait("coatLength");
        g = new Gene("L", t1, air);
        a1 = new Allele(g, "N", 2);
    }

    @Test
    public void getterTests() {
        assertEquals(g, a1.getGene());
        assertEquals("N", a1.getSymbol());
        assertEquals(a1.getRank(), 2);
    }
}
