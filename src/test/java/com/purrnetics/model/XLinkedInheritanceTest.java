package com.purrnetics.model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XLinkedInheritanceTest {
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
    private AllelePair maternalAllelePair;
    private AllelePair paternalAllelePair;

    @BeforeEach
    public void setup() {
        random = new Random(67);
        trait = new Trait("Fur Colour");
        xLinkedInheritance = new XLinkedInheritance();
        completeDominance = new CompleteDominance();
        gene = new Gene("O", trait, xLinkedInheritance, completeDominance);
        orangeAllele = gene.addAllele("O", 1, ORANGE_TRAIT);
        nonOrangeAllele = gene.addAllele("o", 1, NON_ORANGE_TRAIT);
        maternalAllelePair = new AllelePair(gene, orangeAllele, nonOrangeAllele);
        // paternal X-linked chromosome: second allele is absent (hemizygous Y-side)
        paternalAllelePair = new AllelePair(gene, orangeAllele, null);
    }

    // verify daughters inherit a maternal X and father's X chromosome -> invariant
    @Test
    public void femaleOffspringInheritsMaternalXPaternalX() {
        AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.FEMALE, random);

        Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();
        Gene.Allele paternalInheritedAllele = offspringAllelePair.getPaternalAllele();

        assertTrue(maternalInheritedAllele == maternalAllelePair.getMaternalAllele() || maternalInheritedAllele == maternalAllelePair.getPaternalAllele());
        assertEquals(paternalInheritedAllele, paternalAllelePair.getMaternalAllele());
    }

    // verify sons inherit a maternal X and father's Y chromosme -> invariant
    @Test
    public void maleOffspringInheritsMaternalXPaternalY() {
        AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.MALE, random);

        Gene.Allele maternalInheritedAllele = offspringAllelePair.getMaternalAllele();
        Gene.Allele paternalInheritedAllele = offspringAllelePair.getPaternalAllele();

        assertTrue(maternalInheritedAllele == maternalAllelePair.getMaternalAllele() || maternalInheritedAllele == maternalAllelePair.getPaternalAllele());
        assertNull(paternalInheritedAllele);
    }

    // verify maternal X alleles are inherited with approximately equal probability in daughters
    @Test
    public void xLinkedInheritanceMaternalAlleleDistributionOfFemaleOffspring() {
        int numMaternalOrangeAlleles = 0, numMaternalNonOrangeAlleles = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.FEMALE, random);
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
            AllelePair offspringAllelePair = xLinkedInheritance.inherit(gene, maternalAllelePair, paternalAllelePair, Sex.MALE, random);
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
}
