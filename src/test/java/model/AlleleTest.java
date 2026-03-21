package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlleleTest {
    private Gene g;
    private Gene.Allele a;
    private Trait t1;
    private AutosomalInheritance air;
    
    @BeforeEach
    public void setup() {
        air = new AutosomalInheritance();
        t1 = new Trait("coatLength");
        g = new Gene("L", t1, air);
        a = g.addAllele("N", 1, "shortHair");
    }

    @Test
    public void getterTests() {
        assertEquals("N", a.getAlleleSymbol());
        assertEquals(1, a.getRank());
        assertEquals("shortHair", a.getVariant());
        
    }
}
