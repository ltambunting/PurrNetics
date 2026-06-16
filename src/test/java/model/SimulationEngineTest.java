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
    private Cat heterozygousMomCat;
    private Cat heterozygousOrangeDadCat;
    private ParentPair dihybridParentsOrangeDad;
    private SimulationEngine simulationEngine;
    private Random random;
    private static final String COAT_LENGTH_SHORT_HAIR = "Short hair";
    private static final String COAT_LENGTH_LONG_HAIR = "Long hair";
    private static final String EAR_CURL_CURLED = "Curled ears";
    private static final String EAR_CURL_STRAIGHT = "Straight ears";
    private static final String ORANGE_FUR = "Orange fur";
    private static final String NON_ORANGE_FUR = "Non-orange fur";
    private static final String MOSAIC_VARIANT = "Mosaic";
    private static final Gene.Allele NO_X_ALLELE = null;
    private Gene coatLengthGene;
    private Gene earCurlGene;
    private Gene orangeFurGene;
    private Trait coatLengthTrait;
    private Trait earCurlTrait;
    private Trait orangeFurTrait;
    private AutosomalInheritance autosomalInheritance;
    private CompleteDominance completeDominance;
    private XLinkedInheritance xLinkedInheritance;
    private XLinkedMosaicExpression xLinkedMosaicExpression;
    private Gene.Allele shortHairAllele;
    private Gene.Allele longHairAllele;
    private Gene.Allele curledEarsAllele;
    private Gene.Allele straightEarsAllele;
    private Gene.Allele orangeFurAllele;
    private Gene.Allele nonOrangeFurAllele;
    private Genotype heterozygousFemaleGenotype;
    private Phenotype heterozygousFemalePhenotype;
    private Genotype heterozygousMaleOrangeGenotype;
    private Phenotype heterozygousMaleOrangePhenotype;
    private AllelePair heterozygousCoatLengthAllelePair;
    private AllelePair heterozygousEarlCurlAllelePair;
    private AllelePair heterozygousOrangeFurAllelePair;
    private AllelePair orangeAlleleHemizygousAllelePair;
    private Map<Gene, AllelePair> heterozygousFemaleGenotypeMap;
    private Map<Trait, String> heterozygousFemalePhenotypeMap;
    private Map<Gene, AllelePair> heterozygousMaleOrangeGenotypeMap;
    private Map<Trait, String> heterozygousMaleOrangePhenotypeMap;
    private int numCatsDistributionTest = 10000;
    private static final double CHI_SQUARE_CRITICAL_VALUE_DF3_ALPHA_005 = 7.815;
    private static final double CHI_SQUARE_CRITICAL_VALUE_DF1_ALPHA_005 = 3.841;

    @BeforeEach
    void setup() {
        autosomalInheritance = new AutosomalInheritance();
        completeDominance = new CompleteDominance();
        xLinkedInheritance = new XLinkedInheritance();
        xLinkedMosaicExpression = new XLinkedMosaicExpression();
        
        coatLengthTrait = new Trait("coatLength");
        coatLengthTrait.addTraitVariant(COAT_LENGTH_SHORT_HAIR);
        coatLengthTrait.addTraitVariant(COAT_LENGTH_LONG_HAIR);

        earCurlTrait = new Trait("earCurl");
        earCurlTrait.addTraitVariant(EAR_CURL_CURLED);
        earCurlTrait.addTraitVariant(EAR_CURL_STRAIGHT);

        orangeFurTrait = new Trait("orangeFur");
        orangeFurTrait.addTraitVariant(MOSAIC_VARIANT);
        orangeFurTrait.addTraitVariant(ORANGE_FUR);
        orangeFurTrait.addTraitVariant(NON_ORANGE_FUR);
        
        coatLengthGene = new Gene("L", coatLengthTrait, autosomalInheritance, completeDominance);
        shortHairAllele = coatLengthGene.addAllele("L", 1, COAT_LENGTH_SHORT_HAIR);
        longHairAllele = coatLengthGene.addAllele("l", 0, COAT_LENGTH_LONG_HAIR);
        heterozygousCoatLengthAllelePair = new AllelePair(coatLengthGene, shortHairAllele, longHairAllele);

        earCurlGene = new Gene("Cu", earCurlTrait, autosomalInheritance, completeDominance);
        curledEarsAllele = earCurlGene.addAllele("Cu", 1, EAR_CURL_CURLED);
        straightEarsAllele = earCurlGene.addAllele("cu+", 0, EAR_CURL_STRAIGHT);
        heterozygousEarlCurlAllelePair = new AllelePair(earCurlGene, curledEarsAllele, straightEarsAllele);

        orangeFurGene = new Gene("O", orangeFurTrait, xLinkedInheritance, xLinkedMosaicExpression);
        orangeFurAllele = orangeFurGene.addAllele("XO", 0, ORANGE_FUR);
        nonOrangeFurAllele = orangeFurGene.addAllele("Xo", 0, NON_ORANGE_FUR);
        heterozygousOrangeFurAllelePair = new AllelePair(orangeFurGene, orangeFurAllele, nonOrangeFurAllele);
        orangeAlleleHemizygousAllelePair = new AllelePair(orangeFurGene, orangeFurAllele, NO_X_ALLELE);

        heterozygousFemaleGenotypeMap = new HashMap<>();
        heterozygousFemaleGenotypeMap.put(coatLengthGene, heterozygousCoatLengthAllelePair);
        heterozygousFemaleGenotypeMap.put(earCurlGene, heterozygousEarlCurlAllelePair);
        heterozygousFemaleGenotypeMap.put(orangeFurGene, heterozygousOrangeFurAllelePair);
        heterozygousFemaleGenotype = new Genotype(heterozygousFemaleGenotypeMap);

        heterozygousMaleOrangeGenotypeMap = new HashMap<>();
        heterozygousMaleOrangeGenotypeMap.put(coatLengthGene, heterozygousCoatLengthAllelePair);
        heterozygousMaleOrangeGenotypeMap.put(earCurlGene, heterozygousEarlCurlAllelePair);
        heterozygousMaleOrangeGenotypeMap.put(orangeFurGene, orangeAlleleHemizygousAllelePair);
        heterozygousMaleOrangeGenotype = new Genotype(heterozygousMaleOrangeGenotypeMap);


        heterozygousFemalePhenotypeMap = new HashMap<>();
        heterozygousFemalePhenotypeMap.put(coatLengthTrait, COAT_LENGTH_SHORT_HAIR);
        heterozygousFemalePhenotypeMap.put(earCurlTrait, EAR_CURL_CURLED);
        heterozygousFemalePhenotypeMap.put(orangeFurTrait, MOSAIC_VARIANT);
        heterozygousFemalePhenotype = new Phenotype(heterozygousFemalePhenotypeMap);


        heterozygousMaleOrangePhenotypeMap = new HashMap<>();
        heterozygousMaleOrangePhenotypeMap.put(coatLengthTrait, COAT_LENGTH_SHORT_HAIR);
        heterozygousMaleOrangePhenotypeMap.put(earCurlTrait, EAR_CURL_CURLED);
        heterozygousMaleOrangePhenotypeMap.put(orangeFurTrait, ORANGE_FUR);
        heterozygousMaleOrangePhenotype = new Phenotype(heterozygousMaleOrangePhenotypeMap);

        heterozygousMomCat = new Cat("Marina", Sex.FEMALE, null, heterozygousFemaleGenotype, heterozygousFemalePhenotype);
        heterozygousOrangeDadCat = new Cat("Jotaro", Sex.MALE, null, heterozygousMaleOrangeGenotype, heterozygousMaleOrangePhenotype);
        dihybridParentsOrangeDad = new ParentPair(heterozygousMomCat, heterozygousOrangeDadCat);

        random = new Random(67);

        simulationEngine = new SimulationEngine(random);
        
    }

    @Test
    public void breedOneOffspringTrivialTest() {
        Cat kitten = simulationEngine.breed(dihybridParentsOrangeDad);
        assertNotNull(kitten);
        assertEquals(dihybridParentsOrangeDad, kitten.getParents());
        assertNotNull(kitten.getGenotype());
        assertNotNull(kitten.getPhenotype());
        assertNotNull(kitten.getSex());
        assertTrue(dihybridParentsOrangeDad.getOffspring().contains(kitten));
    }

    // Looking at each Mendelian loci, we expect to have a 3:1 phenotype ratio of dominant to recessive traits (monohybrid)
    @Test
    public void monohybridCrossDistributionTest() {
        List<Cat> kittens = breedManyTimesHelper(dihybridParentsOrangeDad, numCatsDistributionTest);
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
        List<Cat> kittens = breedManyTimesHelper(dihybridParentsOrangeDad, numCatsDistributionTest);
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

        String dominantCoatLengthDominantEarCurlString = COAT_LENGTH_SHORT_HAIR + "_" + EAR_CURL_CURLED;
        String dominantCoatLengthRecessiveEarCurlString = COAT_LENGTH_SHORT_HAIR + "_" + EAR_CURL_STRAIGHT;
        String recessiveCoatLengthDominantEarCurlString = COAT_LENGTH_LONG_HAIR + "_" + EAR_CURL_CURLED;
        String recessiveCoatLengthRecessiveEarCurlString = COAT_LENGTH_LONG_HAIR + "_" + EAR_CURL_STRAIGHT;

        Map<String, Integer> observedCountMap = new HashMap<>();
        observedCountMap.put(dominantCoatLengthDominantEarCurlString, dominantCoatLengthDominantEarCurlCount);
        observedCountMap.put(dominantCoatLengthRecessiveEarCurlString, dominantCoatLengthRecessiveEarCurlCount);
        observedCountMap.put(recessiveCoatLengthDominantEarCurlString, recessiveCoatLengthDominantEarCurlCount);
        observedCountMap.put(recessiveCoatLengthRecessiveEarCurlString, recessiveCoatLengthRecessiveEarCurlCount);

        Map<String, Double> expectedPhenotypeProportionMap = new HashMap<>();
        expectedPhenotypeProportionMap.put(dominantCoatLengthDominantEarCurlString, 9.0/16.0);
        expectedPhenotypeProportionMap.put(dominantCoatLengthRecessiveEarCurlString, 3.0/16.0);
        expectedPhenotypeProportionMap.put(recessiveCoatLengthDominantEarCurlString, 3.0/16.0);
        expectedPhenotypeProportionMap.put(recessiveCoatLengthRecessiveEarCurlString, 1.0/16.0);

        double chiSquareValue = calculateChiSquare(observedCountMap, expectedPhenotypeProportionMap);
        assertTrue(chiSquareValue < CHI_SQUARE_CRITICAL_VALUE_DF3_ALPHA_005); // ensure that chi square value is below critical value therefore not reject null hypothesis
                                                                                // and the result is consistent with the expected 9:3:3:1 ratio
    }

    // expect 1:1:1:1 distribution of phenotypes of orange female, mosaic female, male orange, male nonorange
    @Test
    public void testMosaicFemaleOrangeMaleCrossProducesExpectedPhenotypes() {
        List<Cat> kittens = breedManyTimesHelper(dihybridParentsOrangeDad, numCatsDistributionTest);
        int orangeFemaleCount = 0;
        int mosaicFemaleCount = 0;
        int orangeMaleCount = 0;
        int nonOrangeMaleCount = 0;

        int femaleKittensCount = 0;
        int maleKittensCount = 0;

        for (Cat kitten : kittens) {
            Phenotype kittenPhenotype = kitten.getPhenotype();
            Sex kittenSex = kitten.getSex();
            if (hasOrangeFur(kittenPhenotype) && kittenSex == Sex.FEMALE) {
                orangeFemaleCount++;
                femaleKittensCount++;
            } else if (hasMosaicOrangeFur(kittenPhenotype) && kittenSex == Sex.FEMALE) {
                mosaicFemaleCount++;
                femaleKittensCount++;
            } else if (hasOrangeFur(kittenPhenotype) && kittenSex == Sex.MALE) {
                orangeMaleCount++;
                maleKittensCount++;
            } else if (hasNonOrangeFur(kittenPhenotype) && kittenSex == Sex.MALE) {
                nonOrangeMaleCount++;
                maleKittensCount++;
            }
        }

        String femaleKittenString = "Female";
        String maleKittenString = "Male";

        Map<String, Integer> observedSexCountMap = new HashMap<>();
        observedSexCountMap.put(femaleKittenString, femaleKittensCount);
        observedSexCountMap.put(maleKittenString, maleKittensCount);

        Map<String, Double> expectedSexProportionMap = new HashMap<>();
        expectedSexProportionMap.put(femaleKittenString, 1.0/2.0);
        expectedSexProportionMap.put(maleKittenString, 1.0/2.0);

        double chiSquareValueSex = calculateChiSquare(observedSexCountMap, expectedSexProportionMap);
        assertTrue(chiSquareValueSex < CHI_SQUARE_CRITICAL_VALUE_DF1_ALPHA_005);


        String orangeFemaleString = ORANGE_FUR + "_" + Sex.FEMALE;
        String mosaicFemaleString = MOSAIC_VARIANT + "_" + Sex.FEMALE;
        String orangeMaleString = ORANGE_FUR + "_" + Sex.MALE;
        String nonOrangeMaleString = NON_ORANGE_FUR + "_" + Sex.MALE;

        Map<String, Integer> observedCountMap = new HashMap<>();
        observedCountMap.put(orangeFemaleString, orangeFemaleCount);
        observedCountMap.put(mosaicFemaleString, mosaicFemaleCount);
        observedCountMap.put(orangeMaleString, orangeMaleCount);
        observedCountMap.put(nonOrangeMaleString, nonOrangeMaleCount);

        Map<String, Double> expectedPhenotypeProportionMap = new HashMap<>();
        expectedPhenotypeProportionMap.put(orangeFemaleString, 1.0/4.0);
        expectedPhenotypeProportionMap.put(mosaicFemaleString, 1.0/4.0);
        expectedPhenotypeProportionMap.put(orangeMaleString, 1.0/4.0);
        expectedPhenotypeProportionMap.put(nonOrangeMaleString, 1.0/4.0);

        double chiSquareValue = calculateChiSquare(observedCountMap, expectedPhenotypeProportionMap);
        assertTrue(chiSquareValue < CHI_SQUARE_CRITICAL_VALUE_DF3_ALPHA_005);
    }

    // HELPER FUNCTIONS

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

    private boolean hasOrangeFur(Phenotype phenotype) {
        return phenotype.getExpressedVariant(orangeFurTrait).equals(ORANGE_FUR);
    }

    private boolean hasNonOrangeFur(Phenotype phenotype) {
        return phenotype.getExpressedVariant(orangeFurTrait).equals(NON_ORANGE_FUR);
    }

    private boolean hasMosaicOrangeFur(Phenotype phenotype) {
        return phenotype.getExpressedVariant(orangeFurTrait).equals(MOSAIC_VARIANT);
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
