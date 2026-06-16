package model;

import java.util.HashMap;
import java.util.Map;
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
    private ExpressionRule completeDominance;
    private Gene.Allele dominantAllele;
    private Gene.Allele recessiveAllele;

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

    public Map<String, Integer> repeatInheritanceAndCountGenotypesHelper(AllelePair maternalAllelePair, AllelePair paternalAllelePair) {
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
