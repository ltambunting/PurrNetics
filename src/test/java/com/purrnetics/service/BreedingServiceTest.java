package com.purrnetics.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purrnetics.model.AllelePair;
import com.purrnetics.model.AutosomalInheritance;
import com.purrnetics.model.BreedingResult;
import com.purrnetics.model.Cat;
import com.purrnetics.model.CompleteDominance;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Genotype;
import com.purrnetics.model.ParentPair;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Sex;
import com.purrnetics.model.Trait;
import com.purrnetics.model.XLinkedInheritance;
import com.purrnetics.model.XLinkedMosaicExpression;

public class BreedingServiceTest {
    private Cat heterozygousMomCat;
    private Cat heterozygousOrangeDadCat;
    private ParentPair dihybridParentsOrangeDad;
    private BreedingService breedingService;
    private Random random;
    private static final String COAT_LENGTH_SHORT_HAIR = "Short hair";
    private static final String COAT_LENGTH_LONG_HAIR = "Long hair";
    private static final String AGOUTI_FUR = "Agouti fur";
    private static final String NON_AGOUTI_FUR = "Non-agouti fur";
    private static final String ORANGE_FUR = "Orange fur";
    private static final String NON_ORANGE_FUR = "Non-orange fur";
    private static final String MOSAIC_VARIANT = "Mosaic";
    private static final Gene.Allele NO_X_ALLELE = null;
    private static final String CAT_ID_1 = "cid1";
    private static final String CAT_ID_2 = "cid2";
    private Gene coatLengthGene;
    private Gene agoutiGene;
    private Gene orangeFurGene;
    private Trait coatLengthTrait;
    private Trait agoutiFurTrait;
    private Trait orangeFurTrait;
    private AutosomalInheritance autosomalInheritance;
    private CompleteDominance completeDominance;
    private XLinkedInheritance xLinkedInheritance;
    private XLinkedMosaicExpression xLinkedMosaicExpression;
    private Gene.Allele shortHairAllele;
    private Gene.Allele longHairAllele;
    private Gene.Allele agoutiAllele;
    private Gene.Allele nonAgoutiAllele;
    private Gene.Allele orangeFurAllele;
    private Gene.Allele nonOrangeFurAllele;
    private Genotype heterozygousFemaleGenotype;
    private Phenotype heterozygousFemalePhenotype;
    private Genotype heterozygousMaleOrangeGenotype;
    private Phenotype heterozygousMaleOrangePhenotype;
    private AllelePair heterozygousCoatLengthAllelePair;
    private AllelePair heterozygousAgoutiAllelePair;
    private AllelePair heterozygousOrangeFurAllelePair;
    private AllelePair orangeAlleleHemizygousAllelePair;
    private Map<Gene, AllelePair> heterozygousFemaleGenotypeMap;
    private Map<Trait, String> heterozygousFemalePhenotypeMap;
    private Map<Gene, AllelePair> heterozygousMaleOrangeGenotypeMap;
    private Map<Trait, String> heterozygousMaleOrangePhenotypeMap;
    private final int numCatsDistributionTest = 10000;
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

        agoutiFurTrait = new Trait("agoutiFur");
        agoutiFurTrait.addTraitVariant(AGOUTI_FUR);
        agoutiFurTrait.addTraitVariant(NON_AGOUTI_FUR);

        orangeFurTrait = new Trait("orangeFur");
        orangeFurTrait.addTraitVariant(MOSAIC_VARIANT);
        orangeFurTrait.addTraitVariant(ORANGE_FUR);
        orangeFurTrait.addTraitVariant(NON_ORANGE_FUR);
        
        coatLengthGene = new Gene("L", coatLengthTrait, autosomalInheritance, completeDominance);
        shortHairAllele = coatLengthGene.addAllele("L", 1, COAT_LENGTH_SHORT_HAIR);
        longHairAllele = coatLengthGene.addAllele("l", 0, COAT_LENGTH_LONG_HAIR);
        heterozygousCoatLengthAllelePair = new AllelePair(coatLengthGene, shortHairAllele, longHairAllele);

        agoutiGene = new Gene("A", agoutiFurTrait, autosomalInheritance, completeDominance);
        agoutiAllele = agoutiGene.addAllele("A", 1, AGOUTI_FUR);
        nonAgoutiAllele = agoutiGene.addAllele("a", 0, NON_AGOUTI_FUR);
        heterozygousAgoutiAllelePair = new AllelePair(agoutiGene, agoutiAllele, nonAgoutiAllele);

        orangeFurGene = new Gene("O", orangeFurTrait, xLinkedInheritance, xLinkedMosaicExpression);
        orangeFurAllele = orangeFurGene.addAllele("O", 0, ORANGE_FUR);
        nonOrangeFurAllele = orangeFurGene.addAllele("o", 0, NON_ORANGE_FUR);
        heterozygousOrangeFurAllelePair = new AllelePair(orangeFurGene, orangeFurAllele, nonOrangeFurAllele);
        orangeAlleleHemizygousAllelePair = new AllelePair(orangeFurGene, orangeFurAllele, NO_X_ALLELE);

        heterozygousFemaleGenotypeMap = new HashMap<>();
        heterozygousFemaleGenotypeMap.put(coatLengthGene, heterozygousCoatLengthAllelePair);
        heterozygousFemaleGenotypeMap.put(agoutiGene, heterozygousAgoutiAllelePair);
        heterozygousFemaleGenotypeMap.put(orangeFurGene, heterozygousOrangeFurAllelePair);
        heterozygousFemaleGenotype = new Genotype(heterozygousFemaleGenotypeMap);

        heterozygousMaleOrangeGenotypeMap = new HashMap<>();
        heterozygousMaleOrangeGenotypeMap.put(coatLengthGene, heterozygousCoatLengthAllelePair);
        heterozygousMaleOrangeGenotypeMap.put(agoutiGene, heterozygousAgoutiAllelePair);
        heterozygousMaleOrangeGenotypeMap.put(orangeFurGene, orangeAlleleHemizygousAllelePair);
        heterozygousMaleOrangeGenotype = new Genotype(heterozygousMaleOrangeGenotypeMap);


        heterozygousFemalePhenotypeMap = new HashMap<>();
        heterozygousFemalePhenotypeMap.put(coatLengthTrait, COAT_LENGTH_SHORT_HAIR);
        heterozygousFemalePhenotypeMap.put(agoutiFurTrait, AGOUTI_FUR);
        heterozygousFemalePhenotypeMap.put(orangeFurTrait, MOSAIC_VARIANT);
        heterozygousFemalePhenotype = new Phenotype(heterozygousFemalePhenotypeMap);


        heterozygousMaleOrangePhenotypeMap = new HashMap<>();
        heterozygousMaleOrangePhenotypeMap.put(coatLengthTrait, COAT_LENGTH_SHORT_HAIR);
        heterozygousMaleOrangePhenotypeMap.put(agoutiFurTrait, AGOUTI_FUR);
        heterozygousMaleOrangePhenotypeMap.put(orangeFurTrait, ORANGE_FUR);
        heterozygousMaleOrangePhenotype = new Phenotype(heterozygousMaleOrangePhenotypeMap);

        heterozygousMomCat = new Cat(CAT_ID_1, "Marina", Sex.FEMALE, null, heterozygousFemaleGenotype, heterozygousFemalePhenotype);
        heterozygousOrangeDadCat = new Cat(CAT_ID_2, "Jotaro", Sex.MALE, null, heterozygousMaleOrangeGenotype, heterozygousMaleOrangePhenotype);
        dihybridParentsOrangeDad = new ParentPair(heterozygousMomCat, heterozygousOrangeDadCat);

        random = new Random(67);

        breedingService = new BreedingService(random);
        
    }

    @Test
    public void breedOneOffspringTrivialTest() {
        Cat kitten = breedingService.breed(dihybridParentsOrangeDad);
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

        String dominantCoatLengthDominantEarCurlString = COAT_LENGTH_SHORT_HAIR + "_" + AGOUTI_FUR;
        String dominantCoatLengthRecessiveEarCurlString = COAT_LENGTH_SHORT_HAIR + "_" + NON_AGOUTI_FUR;
        String recessiveCoatLengthDominantEarCurlString = COAT_LENGTH_LONG_HAIR + "_" + AGOUTI_FUR;
        String recessiveCoatLengthRecessiveEarCurlString = COAT_LENGTH_LONG_HAIR + "_" + NON_AGOUTI_FUR;

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

    // test distribution of all possible offspring of tortie queen and orange tom
    @Test
    public void breedingResultTortieQueenOrangeTomTest() {
        BreedingResult breedingResult = breedingService.breedingResult(dihybridParentsOrangeDad);
        Map<Gene, Map<AllelePair, Double>> genotypeDistributionMap = breedingResult.genotypeDistribution();
        Map<Trait, Map<String, Double>> phenotypeDistributionMap = breedingResult.phenotypeDistribution();

        Map<AllelePair, Double> orangeGenotypeDistribution = genotypeDistributionMap.get(orangeFurGene);
        Map<AllelePair, Double> agoutiGenotypeDistribution = genotypeDistributionMap.get(agoutiGene);
        Map<AllelePair, Double> furLengthGenotypeDistribution = genotypeDistributionMap.get(coatLengthGene);

        AllelePair nonOrangeHemizygousAllelePair = new AllelePair(orangeFurGene, nonOrangeFurAllele, NO_X_ALLELE);
        AllelePair homozygousOrangeAllelePair = new AllelePair(orangeFurGene, orangeFurAllele, orangeFurAllele);
        AllelePair heterozygousMaternalNonOrangePaternalOrangeAllelePair = new AllelePair(orangeFurGene, nonOrangeFurAllele, orangeFurAllele);
        assertTrue(orangeGenotypeDistribution.size() == 4);
        assertTrue(orangeGenotypeDistribution.containsKey(nonOrangeHemizygousAllelePair));
        assertTrue(orangeGenotypeDistribution.containsKey(homozygousOrangeAllelePair));
        assertTrue(orangeGenotypeDistribution.containsKey(orangeAlleleHemizygousAllelePair));
        assertTrue(orangeGenotypeDistribution.containsKey(heterozygousMaternalNonOrangePaternalOrangeAllelePair));
        assertFalse(orangeGenotypeDistribution.containsKey(heterozygousOrangeFurAllelePair));

        AllelePair agoutiHomozygousAllelePair = new AllelePair(agoutiGene, agoutiAllele, agoutiAllele);
        AllelePair nonAgoutiHomozygousAllelePair = new AllelePair(agoutiGene, nonAgoutiAllele, nonAgoutiAllele);
        AllelePair maternalAgoutiPaternalNonAgoutiAllelePair = new AllelePair(agoutiGene, agoutiAllele, nonAgoutiAllele);
        AllelePair maternalNonAgoutiPaternalAgoutiAllelePair = new AllelePair(agoutiGene, nonAgoutiAllele, agoutiAllele);
        assertTrue(agoutiGenotypeDistribution.size() == 4);
        assertTrue(agoutiGenotypeDistribution.containsKey(agoutiHomozygousAllelePair));
        assertTrue(agoutiGenotypeDistribution.containsKey(nonAgoutiHomozygousAllelePair));
        assertTrue(agoutiGenotypeDistribution.containsKey(maternalAgoutiPaternalNonAgoutiAllelePair));
        assertTrue(agoutiGenotypeDistribution.containsKey(maternalNonAgoutiPaternalAgoutiAllelePair));

        AllelePair homozygousShortHairAllelePair = new AllelePair(coatLengthGene, shortHairAllele, shortHairAllele);
        AllelePair homozygousLongHairAllelePair = new AllelePair(coatLengthGene, longHairAllele, longHairAllele);
        AllelePair maternalShortHairPaternalLongHairAllelePair = new AllelePair(coatLengthGene, shortHairAllele, longHairAllele);
        AllelePair maternalLongHairPaternalShortHairAllelePair = new AllelePair(coatLengthGene, longHairAllele, shortHairAllele);
        assertTrue(furLengthGenotypeDistribution.size() == 4);
        assertTrue(furLengthGenotypeDistribution.containsKey(homozygousShortHairAllelePair));
        assertTrue(furLengthGenotypeDistribution.containsKey(homozygousLongHairAllelePair));
        assertTrue(furLengthGenotypeDistribution.containsKey(maternalShortHairPaternalLongHairAllelePair));
        assertTrue(furLengthGenotypeDistribution.containsKey(maternalLongHairPaternalShortHairAllelePair));

        List<Map<AllelePair, Double>> genotypeMapList = List.of(orangeGenotypeDistribution, agoutiGenotypeDistribution, furLengthGenotypeDistribution);
        for (Map<AllelePair, Double> genotypeMap : genotypeMapList) {
            assertTrue(probabilitiesSumToOne(genotypeMap));
            for (Double probability : genotypeMap.values()) {
                assertTrue(probability == 0.25);
            }
        }

        Map<String, Double> orangePhenotypeDistribution = phenotypeDistributionMap.get(orangeFurTrait);
        Map<String, Double> agoutiPhenotypeDistribution = phenotypeDistributionMap.get(agoutiFurTrait);
        Map<String, Double> coatLengthPhenotypeDistribution = phenotypeDistributionMap.get(coatLengthTrait);

        assertTrue(orangePhenotypeDistribution.size() == 3);
        assertTrue(orangePhenotypeDistribution.containsKey(MOSAIC_VARIANT));
        assertTrue(orangePhenotypeDistribution.containsKey(ORANGE_FUR));
        assertTrue(orangePhenotypeDistribution.containsKey(NON_ORANGE_FUR));
        assertEquals(0.5, orangePhenotypeDistribution.get(ORANGE_FUR));
        assertEquals(0.25, orangePhenotypeDistribution.get(MOSAIC_VARIANT));
        assertEquals(0.25, orangePhenotypeDistribution.get(NON_ORANGE_FUR));
        assertTrue(probabilitiesSumToOne(orangePhenotypeDistribution));

        assertTrue(agoutiPhenotypeDistribution.size() == 2);
        assertTrue(agoutiPhenotypeDistribution.containsKey(AGOUTI_FUR));
        assertTrue(agoutiPhenotypeDistribution.containsKey(NON_AGOUTI_FUR));
        assertEquals(0.75, agoutiPhenotypeDistribution.get(AGOUTI_FUR));
        assertEquals(0.25, agoutiPhenotypeDistribution.get(NON_AGOUTI_FUR));
        assertTrue(probabilitiesSumToOne(agoutiPhenotypeDistribution));

        assertTrue(coatLengthPhenotypeDistribution.size() == 2);
        assertTrue(coatLengthPhenotypeDistribution.containsKey(COAT_LENGTH_LONG_HAIR));
        assertTrue(coatLengthPhenotypeDistribution.containsKey(COAT_LENGTH_SHORT_HAIR));
        assertEquals(0.75, coatLengthPhenotypeDistribution.get(COAT_LENGTH_SHORT_HAIR));
        assertEquals(0.25, coatLengthPhenotypeDistribution.get(COAT_LENGTH_LONG_HAIR));
        assertTrue(probabilitiesSumToOne(coatLengthPhenotypeDistribution));
    }

    // HELPER FUNCTIONS

    public List<Cat> breedManyTimesHelper(ParentPair parents, int count) {
        for (int i = 0; i < count; i++) {
            breedingService.breed(parents);
        }
        return parents.getOffspring();
    }

    private boolean hasShortHair(Phenotype phenotype) {
    return phenotype.getExpressedVariant(coatLengthTrait)
            .equals(COAT_LENGTH_SHORT_HAIR);
    }

    private boolean hasCurledEars(Phenotype phenotype) {
    return phenotype.getExpressedVariant(agoutiFurTrait)
            .equals(AGOUTI_FUR);
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

    private boolean probabilitiesSumToOne(Map<?, Double> distributionMap) {
        double counter = 0.0;
        for (Double probability : distributionMap.values()) {
            counter += probability;
        }
        return counter == 1.0;
    }
}
