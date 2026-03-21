package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private Gene.Allele dominantAllele;
    private Gene.Allele recessiveAllele;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("coatLength");
        autosomalInheritance = new AutosomalInheritance();
        trait.addTraitVariant("shortHair");
        gene = new Gene("L", trait, autosomalInheritance);
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

    @Test
    public void trivialResolvePhenotypeMonohybrid() {
        // If offspring allele pair is Aa or aA, expect dominant trait
        AllelePair hybrid1 = new AllelePair(recessiveAllele, dominantAllele);
        AllelePair hybrid2 = new AllelePair(dominantAllele, recessiveAllele);
        assertEquals(DOMINANT_TRAIT, autosomalInheritance.resolvePhenotype(hybrid1));
        assertEquals(DOMINANT_TRAIT, autosomalInheritance.resolvePhenotype(hybrid2));
    }

    @Test
    public void trivialResolvePhenotypeHomozygousDominant() {
        // If offspring allele pair is AA, expect dominant trait
        AllelePair hybrid = new AllelePair(dominantAllele, dominantAllele);
        assertEquals(DOMINANT_TRAIT, autosomalInheritance.resolvePhenotype(hybrid));
    }

    @Test
    public void trivialResolvePhenotypeHomozygousRecessive() {
        // If offspring allele pair is aa, expect recessive trait
        AllelePair hybrid = new AllelePair(recessiveAllele, recessiveAllele);
        assertEquals(RECESSIVE_TRAIT, autosomalInheritance.resolvePhenotype(hybrid));
    }

    @Test
    public void monohybridCrossDistributionTest() {
        // expect 3:1 ratio of dominant to recessive trait
        AllelePair hybrid1 = new AllelePair(recessiveAllele, dominantAllele);
        AllelePair hybrid2 = new AllelePair(dominantAllele, recessiveAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(hybrid1, hybrid2);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue(fractionDominantTrait > 0.7 && fractionDominantTrait < 0.8);
        assertTrue(fractionRecessiveTrait > 0.2 && fractionRecessiveTrait < 0.3);
    }

    @Test
    public void homozygousDominantHomozygousRecessiveCrossDistributionTest() {
        // expect 1:0 ratio of dominant to recessive trait
        AllelePair homozygousDominant = new AllelePair(dominantAllele, dominantAllele);
        AllelePair homozygousRecessive = new AllelePair(recessiveAllele, recessiveAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(homozygousDominant, homozygousRecessive);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue(fractionDominantTrait == 1.0);
        assertTrue(fractionRecessiveTrait == 0.0);
    }

    @Test
    public void bothHomozygousRecessiveCrossDistributionTest() {
        // expect 1:0 ratio of recessive to dominant trait
        AllelePair homozygousRecessive1 = new AllelePair(recessiveAllele, recessiveAllele);
        AllelePair homozygousRecessive2 = new AllelePair(recessiveAllele, recessiveAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(homozygousRecessive1, homozygousRecessive2);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue(fractionDominantTrait == 0.0);
        assertTrue(fractionRecessiveTrait == 1.0);
    }

    @Test
    public void bothHomozygousDominantCrossDistributionTest() {
        // expect 1:0 ratio of dominant to recessive trait
        AllelePair homozygousDominant1 = new AllelePair(dominantAllele, dominantAllele);
        AllelePair homozygousDominant2 = new AllelePair(dominantAllele, dominantAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(homozygousDominant1, homozygousDominant2);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue(fractionDominantTrait == 1.0);
        assertTrue(fractionRecessiveTrait == 0.0);
    }

    @Test
    public void homozygousDominanthybridCrossDistributionTest() {
        // expect 1:0 ratio of dominant to recessive trait
        AllelePair homozygousDominant = new AllelePair(dominantAllele, dominantAllele);
        AllelePair hybrid = new AllelePair(dominantAllele, recessiveAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(homozygousDominant, hybrid);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue(fractionDominantTrait == 1.0);
        assertTrue(fractionRecessiveTrait == 0.0);
    }

    @Test
    public void homozygousRecessiveDihybridCrossDistributionTest() {
        // expect 1:1 ratio of dominant to recessive trait
        AllelePair homozygousRecessive = new AllelePair(recessiveAllele, recessiveAllele);
        AllelePair hybrid = new AllelePair(dominantAllele, recessiveAllele);

        List<AllelePair> offspringAllelePairs = makeManyOffspringAllelePairsHelper(homozygousRecessive, hybrid);

        int dominantCount = 0;
        int recessiveCount = 0;

        for (AllelePair offspring : offspringAllelePairs) {
            String expressedSingularPhenotype = autosomalInheritance.resolvePhenotype(offspring);
            if (expressedSingularPhenotype.equals(DOMINANT_TRAIT)) {
                dominantCount++;
            } else if (expressedSingularPhenotype.equals(RECESSIVE_TRAIT)) {
                recessiveCount++;
            }
        }

        double fractionDominantTrait = (double) dominantCount / ITERATIONS;
        double fractionRecessiveTrait = (double) recessiveCount / ITERATIONS;

        assertTrue((fractionDominantTrait > 0.45) && (fractionDominantTrait < 0.55));
        assertTrue((fractionRecessiveTrait > 0.45) && (fractionRecessiveTrait < 0.55));
    }

    public List<AllelePair> makeManyOffspringAllelePairsHelper(AllelePair allelepair1, AllelePair allelepair2) {
        List<AllelePair> offspringAllelePairs = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspring = autosomalInheritance.inherit(allelepair1, allelepair2, random);
            offspringAllelePairs.add(offspring);
        }
        return offspringAllelePairs;
    }
}
