package com.purrnetics.model; 

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private ExpressionRule completeDominance;
    private Gene.Allele dominantAllele;
    private Gene.Allele recessiveAllele;
    private AllelePair AaAllelePair;
    private AllelePair aAAllelePair;
    private AllelePair aaAllelePair;
    private AllelePair AAAllelePair;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("coatLength");
        autosomalInheritance = new AutosomalInheritance();
        completeDominance = new CompleteDominance();
        trait.addTraitVariant("shortHair");
        gene = new Gene("L", trait, autosomalInheritance, completeDominance);
        dominantAllele = gene.addAllele("D", 1, DOMINANT_TRAIT);
        recessiveAllele = gene.addAllele("R", 0, RECESSIVE_TRAIT);
        AaAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        aAAllelePair = new AllelePair(gene, recessiveAllele, dominantAllele);
        aaAllelePair = new AllelePair(gene, recessiveAllele, recessiveAllele);
        AAAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
    }

    @Test
    public void inheritOnePairTrivialTest() {
        AllelePair maternalAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair paternalAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair offspring = autosomalInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.FEMALE, random);
        Gene.Allele maternal = offspring.getMaternalAllele();
        Gene.Allele paternal = offspring.getPaternalAllele();
        assertTrue((maternal == dominantAllele || maternal == recessiveAllele) && (paternal == dominantAllele || paternal == recessiveAllele));
    }

    @Test
    public void homozygousDominantCrossAlwaysProducesAAGenotypeTest() {
        AllelePair maternalAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
        AllelePair paternalAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);

        Map<String, Integer> genotypeCountMap = repeatInheritanceAndCountGenotypesHelper(maternalAllelePair, paternalAllelePair);

        assertTrue(genotypeCountMap.get("AA") == ITERATIONS);
        assertTrue(genotypeCountMap.get("Aa") == 0);
        assertTrue(genotypeCountMap.get("aA") == 0);
        assertTrue(genotypeCountMap.get("aa") == 0);

    }

    @Test
    public void homozygousRecessiveCrossAlwaysProducesaaGenotypeTest() {
        AllelePair maternalAllelePair = new AllelePair(gene, recessiveAllele, recessiveAllele);
        AllelePair paternalAllelePair = new AllelePair(gene, recessiveAllele, recessiveAllele);

        Map<String, Integer> genotypeCountMap = repeatInheritanceAndCountGenotypesHelper(maternalAllelePair, paternalAllelePair);

        assertTrue(genotypeCountMap.get("AA") == 0);
        assertTrue(genotypeCountMap.get("Aa") == 0);
        assertTrue(genotypeCountMap.get("aA") == 0);
        assertTrue(genotypeCountMap.get("aa") == ITERATIONS);
    }

    @Test
    public void AAXaaAlwaysProducesAaGenotypeTest() {
        AllelePair maternalAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
        AllelePair paternalAllelePair = new AllelePair(gene, recessiveAllele, recessiveAllele);

        Map<String, Integer> genotypeCountMap = repeatInheritanceAndCountGenotypesHelper(maternalAllelePair, paternalAllelePair);

        assertTrue(genotypeCountMap.get("AA") == 0);
        assertTrue(genotypeCountMap.get("Aa") + genotypeCountMap.get("aA") == ITERATIONS);
        assertTrue(genotypeCountMap.get("aa") == 0);
    }

    @Test
    public void getAlleleDistributionTest() {
        // see if after many calls if allele combination distribution is 1:1:1:1 for
        // AA, Aa, aA, aa to verify independent assortment. Cross is represented by standard
        // Mendelian monohybrid cross Aa x Aa
        AllelePair maternalAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair paternalAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);

        Map<String, Integer> genotypeCountMap = repeatInheritanceAndCountGenotypesHelper(maternalAllelePair, paternalAllelePair);

        double percentNumAA = (double) genotypeCountMap.get("AA") / ITERATIONS;
        double percentNumAa = (double) genotypeCountMap.get("Aa") / ITERATIONS;
        double percentNumaA = (double) genotypeCountMap.get("aA") / ITERATIONS;
        double percentNumaa = (double) genotypeCountMap.get("aa") / ITERATIONS;

        assertTrue(percentNumAA > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumAA < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumAa > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumAa < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumaA > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumaA < UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(percentNumaa > LOWER_BOUND_ALLELE_DISTRIBUTION && percentNumaa < UPPER_BOUND_ALLELE_DISTRIBUTION);

    }

    // GET POSSIBLE GAMETES TEST
    // possible gametes from Aa and aA are 50/50 A and a
    // possible gametes from AA is 100% A
    // possible gametes from aa is 100% a

    @Test
    public void getPossibleGametesfromAa() {
        AllelePair allelePairAa = new AllelePair(gene, dominantAllele, recessiveAllele);
        Map<Gene.Allele, Double> gameteDistributionMap = autosomalInheritance.getPossibleGametes(allelePairAa);
        assertTrue(gameteDistributionMap.containsKey(dominantAllele));
        assertTrue(gameteDistributionMap.containsKey(recessiveAllele));
        assertEquals(0.5, gameteDistributionMap.get(dominantAllele));
        assertEquals(0.5, gameteDistributionMap.get(recessiveAllele));
    }

    @Test
    public void getPossibleGametesfromAA() {
        AllelePair allelePairAa = new AllelePair(gene, dominantAllele, dominantAllele);
        Map<Gene.Allele, Double> gameteDistributionMap = autosomalInheritance.getPossibleGametes(allelePairAa);
        assertTrue(gameteDistributionMap.containsKey(dominantAllele));
        assertFalse(gameteDistributionMap.containsKey(recessiveAllele));
        assertEquals(1.0, gameteDistributionMap.get(dominantAllele));
    }

    @Test
    public void getPossibleGametesfromaa() {
        AllelePair allelePairAa = new AllelePair(gene, recessiveAllele, recessiveAllele);
        Map<Gene.Allele, Double> gameteDistributionMap = autosomalInheritance.getPossibleGametes(allelePairAa);
        assertFalse(gameteDistributionMap.containsKey(dominantAllele));
        assertTrue(gameteDistributionMap.containsKey(recessiveAllele));
        assertEquals(1.0, gameteDistributionMap.get(recessiveAllele));
    }

    @Test
    public void getPossibleGametesfromaASameAsAa() {
        AllelePair allelePairAa = new AllelePair(gene, recessiveAllele, dominantAllele);
        Map<Gene.Allele, Double> gameteDistributionMap = autosomalInheritance.getPossibleGametes(allelePairAa);
        assertTrue(gameteDistributionMap.containsKey(dominantAllele));
        assertTrue(gameteDistributionMap.containsKey(recessiveAllele));
        assertEquals(0.5, gameteDistributionMap.get(dominantAllele));
        assertEquals(0.5, gameteDistributionMap.get(recessiveAllele));
    }


    @Test
    public void getInheritanceDistributionTestForAaAa() {
        AllelePair maternAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair paternAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        Map<AllelePair, Double> inheritanceDistributionMap = autosomalInheritance.getInheritanceDistribution(gene, maternAllelePair, paternAllelePair, Sex.FEMALE);
        assertTrue(inheritanceDistributionMap.containsKey(AAAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(aAAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(aaAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(AaAllelePair));

        assertEquals(0.25, inheritanceDistributionMap.get(AAAllelePair));
        assertEquals(0.25, inheritanceDistributionMap.get(AaAllelePair));
        assertEquals(0.25, inheritanceDistributionMap.get(aAAllelePair));
        assertEquals(0.25, inheritanceDistributionMap.get(aaAllelePair));

    }

    @Test
    public void getInheritanceDistributionTestForAaAA() {
        AllelePair maternAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair paternAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
        Map<AllelePair, Double> inheritanceDistributionMap = autosomalInheritance.getInheritanceDistribution(gene, maternAllelePair, paternAllelePair, Sex.FEMALE);
        assertTrue(inheritanceDistributionMap.containsKey(AAAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(aAAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(aaAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(AaAllelePair));

        assertEquals(0.5, inheritanceDistributionMap.get(AAAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(aAAllelePair));
    }

    @Test
    public void getInheritanceDistributionTestForAaaa() {
        AllelePair maternAllelePair = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair paternAllelePair = new AllelePair(gene, recessiveAllele, recessiveAllele);
        Map<AllelePair, Double> inheritanceDistributionMap = autosomalInheritance.getInheritanceDistribution(gene, maternAllelePair, paternAllelePair, Sex.FEMALE);
        assertFalse(inheritanceDistributionMap.containsKey(AAAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(aAAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(aaAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(AaAllelePair));

        assertEquals(0.5, inheritanceDistributionMap.get(aaAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(AaAllelePair));
    }

    @Test
    public void getInheritanceDistributionTestforAAAA() {
        AllelePair maternAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
        AllelePair paternAllelePair = new AllelePair(gene, dominantAllele, dominantAllele);
        Map<AllelePair, Double> inheritanceDistributionMap = autosomalInheritance.getInheritanceDistribution(gene, maternAllelePair, paternAllelePair, Sex.FEMALE);
        assertTrue(inheritanceDistributionMap.containsKey(AAAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(aAAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(aaAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(AaAllelePair));

        assertEquals(1.0, inheritanceDistributionMap.get(AAAllelePair));
        assertTrue(inheritanceDistributionMap.size() == 1);
    }

    private Map<String, Integer> repeatInheritanceAndCountGenotypesHelper(AllelePair maternalAllelePair, AllelePair paternalAllelePair) {
        int numAA = 0, numAa = 0, numaA = 0, numaa = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspring = autosomalInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.FEMALE, random);
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

        Map<String, Integer> genotypeCountsMap = new HashMap<>();
        genotypeCountsMap.put("AA", numAA);
        genotypeCountsMap.put("Aa", numAa);
        genotypeCountsMap.put("aA", numaA);
        genotypeCountsMap.put("aa", numaa);

        return genotypeCountsMap;
    }
}
