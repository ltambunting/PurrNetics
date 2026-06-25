package com.purrnetics.model;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XLinkedInheritanceTest {
    private static final Gene.Allele NO_X_ALLELE = null;
    private static final int ITERATIONS = 10000;
    private static final String ORANGE_TRAIT = "Orange Fur";
    private static final String NON_ORANGE_TRAIT = "Non-Orange Fur";
    private static final double LOWER_BOUND_ALLELE_DISTRIBUTION = 0.45;
    private static final double UPPER_BOUND_ALLELE_DISTRIBUTION = 0.55;
    
    private Random random;
    private Gene gene;
    private Trait trait;
    private XLinkedInheritance xLinkedInheritance;
    private ExpressionRule completeDominance;
    private Gene.Allele orangeAllele;
    private Gene.Allele nonOrangeAllele;
    private AllelePair maternalOrangePaternalNonOrangeFemaleAllelePair;
    private AllelePair maternalNonOrangePaternalOrangeFemaleAllelePair;
    private AllelePair orangeMaleAllelePair;
    private AllelePair nonOrangeMaleAllelePair;
    private AllelePair homozygousOrangeFemaleAllelePair;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("Fur Colour");
        xLinkedInheritance = new XLinkedInheritance();
        completeDominance = new CompleteDominance();
        gene = new Gene("O", trait, xLinkedInheritance, completeDominance);
        orangeAllele = gene.addAllele("O", 1, ORANGE_TRAIT);
        nonOrangeAllele = gene.addAllele("o", 1, NON_ORANGE_TRAIT);
        maternalOrangePaternalNonOrangeFemaleAllelePair = new AllelePair(gene, orangeAllele, nonOrangeAllele);
        maternalNonOrangePaternalOrangeFemaleAllelePair = new AllelePair(gene, nonOrangeAllele, orangeAllele);
        homozygousOrangeFemaleAllelePair = new AllelePair(gene, orangeAllele, orangeAllele);
        // paternal X-linked chromosome: second allele is absent (hemizygous Y-side)
        orangeMaleAllelePair = new AllelePair(gene, orangeAllele, NO_X_ALLELE);
        nonOrangeMaleAllelePair = new AllelePair(gene, nonOrangeAllele, NO_X_ALLELE);
    }

    // verify daughters inherit a maternal X and father's X chromosome -> invariant
    @Test
    public void femaleOffspringInheritsMaternalXPaternalX() {
        AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalOrangePaternalNonOrangeFemaleAllelePair, orangeMaleAllelePair, Sex.FEMALE, random);

        Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();
        Gene.Allele paternalInheritedAllele = offspringAllelePair.getPaternalAllele();

        assertTrue(maternalInheritedAllele == maternalOrangePaternalNonOrangeFemaleAllelePair.getMaternalAllele() || maternalInheritedAllele == maternalOrangePaternalNonOrangeFemaleAllelePair.getPaternalAllele());
        assertEquals(paternalInheritedAllele, orangeMaleAllelePair.getMaternalAllele());
    }

    // verify sons inherit a maternal X and father's Y chromosme -> invariant
    @Test
    public void maleOffspringInheritsMaternalXPaternalY() {
        AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalOrangePaternalNonOrangeFemaleAllelePair, orangeMaleAllelePair, Sex.MALE, random);

        Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();
        Gene.Allele paternalInheritedAllele = offspringAllelePair.getPaternalAllele();

        assertTrue(maternalInheritedAllele == maternalOrangePaternalNonOrangeFemaleAllelePair.getMaternalAllele() || maternalInheritedAllele == maternalOrangePaternalNonOrangeFemaleAllelePair.getPaternalAllele());
        assertNull(paternalInheritedAllele);
    }

    // verify maternal X alleles are inherited with approximately equal probability in daughters
    @Test
    public void xLinkedInheritanceMaternalAlleleDistributionOfFemaleOffspring() {
        int numMaternalOrangeAlleles = 0, numMaternalNonOrangeAlleles = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalOrangePaternalNonOrangeFemaleAllelePair, orangeMaleAllelePair, Sex.FEMALE, random);
            Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();

            if (maternalInheritedAllele == orangeAllele) {
                numMaternalOrangeAlleles++;
            } else if (maternalInheritedAllele == nonOrangeAllele) {
                numMaternalNonOrangeAlleles++;
            }
        }
        double maternalOrangeAllelesProportion = (double) numMaternalOrangeAlleles / (double) ITERATIONS;
        double maternalNonOrangeAllelesProportion = (double) numMaternalNonOrangeAlleles / (double) ITERATIONS;

        assertTrue(maternalOrangeAllelesProportion >= LOWER_BOUND_ALLELE_DISTRIBUTION && maternalOrangeAllelesProportion <= UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(maternalNonOrangeAllelesProportion >= LOWER_BOUND_ALLELE_DISTRIBUTION && maternalNonOrangeAllelesProportion <= UPPER_BOUND_ALLELE_DISTRIBUTION);
    }

    // verify maternal X alleles are inherited with approximately equal probability in sons
    @Test
    public void xLinkedInheritanceMaternalAlleleDistributionOfMaleOffspring() {
        int numMaternalOrangeAlleles = 0, numMaternalNonOrangeAlleles = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalOrangePaternalNonOrangeFemaleAllelePair, orangeMaleAllelePair, Sex.MALE, random);
            Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();

            if (maternalInheritedAllele == orangeAllele) {
                numMaternalOrangeAlleles++;
            } else if (maternalInheritedAllele == nonOrangeAllele) {
                numMaternalNonOrangeAlleles++;
            }
        }
        double maternalOrangeAllelesProportion = (double) numMaternalOrangeAlleles / (double) ITERATIONS;
        double maternalNonOrangeAllelesProportion = (double) numMaternalNonOrangeAlleles / (double) ITERATIONS;

        assertTrue(maternalOrangeAllelesProportion >= LOWER_BOUND_ALLELE_DISTRIBUTION && maternalOrangeAllelesProportion <= UPPER_BOUND_ALLELE_DISTRIBUTION);
        assertTrue(maternalNonOrangeAllelesProportion >= LOWER_BOUND_ALLELE_DISTRIBUTION && maternalNonOrangeAllelesProportion <= UPPER_BOUND_ALLELE_DISTRIBUTION);
    }

    // test gametes for heterozygous female
    @Test
    public void getPossibleGametesHeterozygousFemaleTest() {
        Map<Gene.Allele, Double> possibleAllelesMap = xLinkedInheritance.getPossibleGametes(maternalOrangePaternalNonOrangeFemaleAllelePair);
        assertTrue(possibleAllelesMap.containsKey(orangeAllele));
        assertTrue(possibleAllelesMap.containsKey(nonOrangeAllele));
        assertEquals(0.5, possibleAllelesMap.get(orangeAllele));
        assertEquals(0.5, possibleAllelesMap.get(nonOrangeAllele));
    }
    // test gametes for homozygous female
    @Test
    public void getPossibleGametesHomozygousFemaleTest() {
        AllelePair homozygousAllelePair = new AllelePair(gene, orangeAllele, orangeAllele);
        Map<Gene.Allele, Double> possibleAllelesMap = xLinkedInheritance.getPossibleGametes(homozygousAllelePair);
        assertTrue(possibleAllelesMap.containsKey(orangeAllele));
        assertFalse(possibleAllelesMap.containsKey(nonOrangeAllele));
        assertEquals(1.0, possibleAllelesMap.get(orangeAllele));
        assertTrue(possibleAllelesMap.size() == 1);
    }

    // test gametes for male
    @Test
    public void getPossibleGametesMaleTest() {
        Map<Gene.Allele, Double> possibleAllelesMap = xLinkedInheritance.getPossibleGametes(orangeMaleAllelePair);
        assertTrue(possibleAllelesMap.containsKey(NO_X_ALLELE));
        assertTrue(possibleAllelesMap.containsKey(orangeAllele));
        assertFalse(possibleAllelesMap.containsKey(nonOrangeAllele));
        assertEquals(0.5, possibleAllelesMap.get(NO_X_ALLELE));
        assertEquals(0.5, possibleAllelesMap.get(orangeAllele));
    }

    // test heterozygous female and affected male
    @Test
    public void getInheritanceDistributionHeterozygousFemaleAffectedMaleTest() {
        Map<AllelePair, Double> inheritanceDistributionMap = xLinkedInheritance.getInheritanceDistribution(gene, maternalOrangePaternalNonOrangeFemaleAllelePair, orangeMaleAllelePair);
        assertTrue(inheritanceDistributionMap.size() == 4);
        assertFalse(inheritanceDistributionMap.containsKey(maternalOrangePaternalNonOrangeFemaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(maternalNonOrangePaternalOrangeFemaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(homozygousOrangeFemaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(nonOrangeMaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(orangeMaleAllelePair));
        for (AllelePair allelePair : inheritanceDistributionMap.keySet()) {
            assertEquals(0.25, inheritanceDistributionMap.get(allelePair));
        }
    }

    // test homozygous female and male with opposite copy
    @Test
    public void getInheritanceDistributionHomozygousAffectedFemaleNonAffectedMaleTest() {
        Map<AllelePair, Double> inheritanceDistributionMap = xLinkedInheritance.getInheritanceDistribution(gene, homozygousOrangeFemaleAllelePair, nonOrangeMaleAllelePair);
        assertTrue(inheritanceDistributionMap.size() == 2);
        assertTrue(inheritanceDistributionMap.containsKey(maternalOrangePaternalNonOrangeFemaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(orangeMaleAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(nonOrangeMaleAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(homozygousOrangeFemaleAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(maternalOrangePaternalNonOrangeFemaleAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(orangeMaleAllelePair));
    }
    
    // test homozygous female and male with same copy
    @Test
    public void getInheritanceDistributionHomozygousAffectedFemaleAffectedMaleTest() {
        Map<AllelePair, Double> inheritanceDistributionMap = xLinkedInheritance.getInheritanceDistribution(gene, homozygousOrangeFemaleAllelePair, orangeMaleAllelePair);
        assertTrue(inheritanceDistributionMap.size() == 2);
        assertTrue(inheritanceDistributionMap.containsKey(homozygousOrangeFemaleAllelePair));
        assertTrue(inheritanceDistributionMap.containsKey(orangeMaleAllelePair));
        assertFalse(inheritanceDistributionMap.containsKey(nonOrangeMaleAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(homozygousOrangeFemaleAllelePair));
        assertEquals(0.5, inheritanceDistributionMap.get(orangeMaleAllelePair));
    }
}
