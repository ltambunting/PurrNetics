package model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutosomalInheritanceTest {
    private static final int ITERATIONS =1000;
    private static final double LOWER_BOUND_ALLELE_DISTRIBUTION = 0.2;
    private static final double UPPER_BOUND_ALLELE_DISTRIBUTION = 0.3;
    private static final String DOMINANT_TRAIT = "Short hair";
    private static final String RECESSIVE_TRAIT = "Long hair";
    private Random random;
    private Gene gene;
    private Trait trait;
    private AutosomalInheritance autosomalInheritance;
    private DominanceRule completeDominance;
    private Gene.Allele dominantAllele;
    private Gene.Allele recessiveAllele;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("coatLength");
        autosomalInheritance = new AutosomalInheritance();
        trait.addTraitVariant("shortHair");
        gene = new Gene("L", trait, autosomalInheritance, completeDominance);
        dominantAllele = gene.addAllele("D", 1, DOMINANT_TRAIT);
        recessiveAllele = gene.addAllele("R", 0, RECESSIVE_TRAIT);
    }

    @Test
    public void inheritOnePairTrivialTest() {
        AllelePair maternalAllelePair = new AllelePair(dominantAllele, recessiveAllele);
        AllelePair paternalAllelePair = new AllelePair(dominantAllele, recessiveAllele);
        AllelePair offspring = autosomalInheritance.inherit(maternalAllelePair, paternalAllelePair, random);
        Gene.Allele maternal = offspring.getMaternalAllele();
        Gene.Allele paternal = offspring.getPaternalAllele();
        assertTrue((maternal == dominantAllele || maternal == recessiveAllele) && (paternal == dominantAllele || paternal == recessiveAllele));
    }

    @Test
    public void getAlleleDistributionTest() {
        // see if after many calls if allele combination distribution is 1:1:1:1 for
        // AA, Aa, aA, aa to verify independent assortment. Cross is represented by standard
        // Mendelian monohybrid cross Aa x Aa
        AllelePair maternalAllelePair = new AllelePair(dominantAllele, recessiveAllele);
        AllelePair paternalAllelePair = new AllelePair(dominantAllele, recessiveAllele);

        int numAA = 0, numAa = 0, numaA = 0, numaa = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspring = autosomalInheritance.inherit(maternalAllelePair, paternalAllelePair, random);
            Gene.Allele maternal = offspring.getMaternalAllele();
            Gene.Allele paternal = offspring.getPaternalAllele();
            if (maternal == dominantAllele && paternal == dominantAllele) {
                numAA++;
            } else if (maternal == dominantAllele && paternal == recessiveAllele) {
                numAa++;
            } else if (maternal == recessiveAllele && paternal == dominantAllele) {
                numaA++;
            } else if (maternal== recessiveAllele && paternal == recessiveAllele) {
                numaa++;
            }
        }

        double percentNumAA = (double) numAA / ITERATIONS; // cast to double to prevent integer division
        double percentNumAa = (double) numAa / ITERATIONS;
        double percentNumaA = (double) numaA / ITERATIONS;
        double percentNumaa = (double) numaa / ITERATIONS;

        assertTrue(percentNumAA > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumAA < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumAa > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumAa < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumaA > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumaA < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumaa > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumaa < UPPER_BOUND_ALLELE_DISTRIBUTION);

    }
}
