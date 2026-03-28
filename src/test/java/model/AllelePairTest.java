package model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllelePairTest {
    private Random random;
    private Gene gene;
    private Trait trait;
    private AutosomalInheritance autosomalInheritance;
    private DominanceRule dominanceRule;
    private Gene.Allele allele1;
    private Gene.Allele allele2;
    private AllelePair allelePair;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("coatLength");
        autosomalInheritance = new AutosomalInheritance();
        dominanceRule = new CompleteDominance();
        gene = new Gene("L", trait, autosomalInheritance, dominanceRule);
        allele1 = gene.addAllele("Ls", 1, "shortHair");
        allele2 = gene.addAllele("Ll", 0, "longHair");
        allelePair = new AllelePair(allele1, allele2);
    }

    @Test
    public void trivialAllelePairTest() {
        assertEquals(allele1, allelePair.getMaternalAllele());
        assertEquals(allele2, allelePair.getPaternalAllele());
    }

    @Test
    public void getRandomAlleleTest() {
        // test to see if fixed seed has deterministic behaviour and calls
        // either maternal or paternal allele
        Gene.Allele result = allelePair.getRandomAllele(random);
        assertTrue(result == allele1 || result == allele2);
    }

    @Test
    public void getRandomAlleleDistributionTest() {
        // see if after many calls if distribution roughly 50/50 as expected
        int maternalCount = 0;
        int paternalCount = 0;

        for(int i = 0; i < 1000; i++) {
            Gene.Allele result = allelePair.getRandomAllele(random);
            if (result == allele1) {
                maternalCount++;
            } else if (result == allele2) {
                paternalCount++;
            }
        }
        assertTrue(maternalCount > 400 && maternalCount < 600);
        assertTrue(paternalCount > 400 && maternalCount < 600);
    }
}
