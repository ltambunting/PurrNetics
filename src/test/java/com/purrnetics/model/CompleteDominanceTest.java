package com.purrnetics.model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompleteDominanceTest {
    private static final int ITERATIONS =1000;
    private static final String DOMINANT_TRAIT = "Short hair";
    private static final String RECESSIVE_TRAIT = "Long hair";
    private Random random;
    private Gene gene;
    private Trait trait;
    private InheritanceRule autosomalInheritance;
    private CompleteDominance completeDominance;
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
    public void trivialResolvePhenotypeMonohybrid() {
        // If offspring allele pair is Aa or aA, expect dominant trait
        AllelePair hybrid1 = new AllelePair(gene, recessiveAllele, dominantAllele);
        AllelePair hybrid2 = new AllelePair(gene, dominantAllele, recessiveAllele);
        assertEquals(DOMINANT_TRAIT, completeDominance.resolvePhenotype(hybrid1));
        assertEquals(DOMINANT_TRAIT, completeDominance.resolvePhenotype(hybrid2));
    }

    @Test
    public void trivialResolvePhenotypeHomozygousDominant() {
        // If offspring allele pair is AA, expect dominant trait
        AllelePair hybrid = new AllelePair(gene, dominantAllele, dominantAllele);
        assertEquals(DOMINANT_TRAIT, completeDominance.resolvePhenotype(hybrid));
    }

    @Test
    public void trivialResolvePhenotypeHomozygousRecessive() {
        // If offspring allele pair is aa, expect recessive trait
        AllelePair hybrid = new AllelePair(gene, recessiveAllele, recessiveAllele);
        assertEquals(RECESSIVE_TRAIT, completeDominance.resolvePhenotype(hybrid));
    }

    @Test
    public void AaAndaAAlwaysProduceSamePhenotypeTest() {
        // should both be dominant phenotype
        AllelePair allelePairAa = new AllelePair(gene, dominantAllele, recessiveAllele);
        AllelePair allelePairaA = new AllelePair(gene, recessiveAllele, dominantAllele);

        for (int i = 0; i < ITERATIONS; i++) {
            String phenotypeAa = completeDominance.resolvePhenotype(allelePairAa);
            String phenotypeaA = completeDominance.resolvePhenotype(allelePairaA);
            assertEquals(DOMINANT_TRAIT, phenotypeAa);
            assertEquals(DOMINANT_TRAIT, phenotypeaA);
        }
    }
    
}

