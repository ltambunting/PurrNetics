package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {
    private Cat momCat;
    private Cat dadCat;
    private ParentPair parents;
    private SimulationEngine simulationEngine;
    private Random random;
    private static final String COAT_LENGTH_SHORT_HAIR = "Short hair";
    private static final String COAT_LENGTH_LONG_HAIR = "Long hair";
    private static final String EAR_CURL_CURLED = "Curled ears";
    private static final String EAR_CURL_STRAIGHT = "Straight ears";
    private Gene coatLengthGene;
    private Gene earCurlGene;
    private Trait coatLengthTrait;
    private Trait earCurlTrait;
    private InheritanceRule autosomalInheritance;
    private DominanceRule completeDominance;
    private Gene.Allele shortHairAllele;
    private Gene.Allele longHairAllele;
    private Gene.Allele curledEarsAllele;
    private Gene.Allele straightEarsAllele;
    private Genotype genotype;
    private Phenotype phenotype;
    private AllelePair heterozygousCoatLengthAllelePair;
    private AllelePair heterozygousEarlCurlAllelePair;
    private Map<Gene, AllelePair> genotypeMap;
    private Map<Trait, String> phenotypeMap;
    private int numCatsDistributionTest = 10000;
    private int expectedPhenotypesDihybrid = 4;
    private double alpha = 0.05;
    private static final double CHI_SQUARE_CRITICAL_VALUE_DF3_ALPHA_005 = 7.815;


    @BeforeEach
    void setup() {
        autosomalInheritance = new AutosomalInheritance();
        completeDominance = new CompleteDominance();
        coatLengthTrait = new Trait("coatLength");
        coatLengthTrait.addTraitVariant(COAT_LENGTH_SHORT_HAIR);
        coatLengthTrait.addTraitVariant(COAT_LENGTH_LONG_HAIR);
        earCurlTrait = new Trait("earCurl");
        earCurlTrait.addTraitVariant(EAR_CURL_CURLED);
        earCurlTrait.addTraitVariant(EAR_CURL_STRAIGHT);
        
        coatLengthGene = new Gene("L", coatLengthTrait, autosomalInheritance, completeDominance);
        shortHairAllele = coatLengthGene.addAllele("L", 1, COAT_LENGTH_SHORT_HAIR);
        longHairAllele = coatLengthGene.addAllele("l", 0, COAT_LENGTH_LONG_HAIR);
        heterozygousCoatLengthAllelePair = new AllelePair(shortHairAllele, longHairAllele);

        earCurlGene = new Gene("Cu", earCurlTrait, autosomalInheritance, completeDominance);
        curledEarsAllele = earCurlGene.addAllele("Cu", 1, EAR_CURL_CURLED);
        straightEarsAllele = earCurlGene.addAllele("cu+", 0, EAR_CURL_STRAIGHT);
        heterozygousEarlCurlAllelePair = new AllelePair(curledEarsAllele, straightEarsAllele);

        genotypeMap = new HashMap<>();
        genotypeMap.put(coatLengthGene, heterozygousCoatLengthAllelePair);
        genotypeMap.put(earCurlGene, heterozygousEarlCurlAllelePair);

        genotype = new Genotype(genotypeMap);

        phenotypeMap = new HashMap<>();
        phenotypeMap.put(coatLengthTrait, COAT_LENGTH_SHORT_HAIR);
        phenotypeMap.put(earCurlTrait, EAR_CURL_CURLED);

        phenotype = new Phenotype(phenotypeMap);

        momCat = new Cat("Lucy", Sex.FEMALE, null, genotype, phenotype);
        dadCat = new Cat("Jotaro", Sex.MALE, null, genotype, phenotype);
        parents = new ParentPair(momCat, dadCat);

        random = new Random(67);

        simulationEngine = new SimulationEngine(random);
        
    }

    @Test
    public void breedOneOffspringTrivialTest() {
        Cat kitten = simulationEngine.breed(parents);
        assertNotNull(kitten);
        assertEquals(parents, kitten.getParents());
        assertNotNull(kitten.getGenotype());
        assertNotNull(kitten.getPhenotype());
        assertNotNull(kitten.getSex());
        assertTrue(parents.getOffspring().contains(kitten));
    }

    // Looking at each Mendelian loci, we expect to have a 3:1 phenotype ratio of dominant to recessive traits (monohybrid)
    @Test
    public void monohybridCrossDistributionTest() {
        List<Cat> kittens = breedManyTimesHelper(parents, numCatsDistributionTest);
        int dominantCoatLengthCount = 0;
        int recessiveCoatLengthCount = 0;
        for (Cat kitten : kittens) {
            String coatLength = kitten.getPhenotype().getExpressedVariant(coatLengthTrait);
            if (coatLength.equals(COAT_LENGTH_SHORT_HAIR)) {
                dominantCoatLengthCount++;
            } else if (coatLength.equals(COAT_LENGTH_LONG_HAIR)) {
                recessiveCoatLengthCount++;
            }
        }
        double fractionDominantTrait = (double) dominantCoatLengthCount / numCatsDistributionTest;
        double fractionRecessiveTrait = (double) recessiveCoatLengthCount / numCatsDistributionTest;

        assertTrue(fractionDominantTrait > 0.7 && fractionDominantTrait < 0.8);
        assertTrue(fractionRecessiveTrait > 0.2 && fractionRecessiveTrait < 0.3);
    }

    // Looking at both Mendelian loci, we expect to have a 9:3:3:1 phenotype ratio (dihybrid cross)
    @Test
    public void dihybridCrossDistributionTest() {
        List<Cat> kittens = breedManyTimesHelper(parents, numCatsDistributionTest);
        int dominantCoatLengthDominantEarCurlCount = 0; // short hair, curled ears
        int dominantCoatLengthRecessiveEarCurlCount = 0; // short hair, non curled ears
        int recessiveCoatLengthDominantEarCurlCount = 0; // long hair, curled ears
        int recessiveCoatLengthRecessiveEarCurlCount = 0; // long hair, non curled ears

        for (Cat kitten : kittens) {
            Phenotype kittenPhenotype = kitten.getPhenotype();
            if (hasCurledEars(kittenPhenotype) && hasShortHair(kittenPhenotype)) {
                dominantCoatLengthDominantEarCurlCount++;
            } else if (!hasCurledEars(kittenPhenotype) && hasShortHair(kittenPhenotype)) {
                dominantCoatLengthRecessiveEarCurlCount++;
            } else if (hasCurledEars(kittenPhenotype) && !hasShortHair(kittenPhenotype)) {
                recessiveCoatLengthDominantEarCurlCount++;
            } else if (!hasCurledEars(kittenPhenotype) && !hasShortHair(kittenPhenotype)) {
                recessiveCoatLengthRecessiveEarCurlCount++;
            }
        }

    }

    public List<Cat> breedManyTimesHelper(ParentPair parents, int count) {
        for (int i = 0; i < count; i++) {
            simulationEngine.breed(parents);
        }
        return parents.getOffspring();
    }

    private boolean hasShortHair(Phenotype phenotype) {
    return phenotype.getExpressedVariant(coatLengthTrait)
            .equals(COAT_LENGTH_SHORT_HAIR);
    }

    private boolean hasCurledEars(Phenotype phenotype) {
    return phenotype.getExpressedVariant(earCurlTrait)
            .equals(EAR_CURL_CURLED);
    }

    private double calculateChiSquare(Map<String, Integer> observedCounts, Map<String, Double> expectedProportions) {
        int total = 0;
        for (Integer count : observedCounts.values()) {
            total += count;
        }

        double chiSquare = 0.0;

        for (String category : observedCounts.keySet()) {
            double observed = observedCounts.get(category);
            double expected = expectedProportions.get(category) * total;

            double difference = observed - expected;

            chiSquare += (difference * difference) / expected;
        }

        return chiSquare;
    }
}
