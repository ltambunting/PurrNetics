package com.purrnetics.model;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenotypeTest {
    private static final String TRAIT_VARIANT1_GENE1 = "Short hair";
    private static final String TRAIT_VARIANT2_GENE1 = "Long hair";
    private static final String TRAIT_VARIANT1_GENE2 = "Orange fur";
    private static final String TRAIT_VARIANT2_GENE2 = "Black fur";
    private Gene gene1;
    private Gene gene2;
    private Trait trait1;
    private Trait trait2;
    private InheritanceRule autosomalInheritance;
    private ExpressionRule completeDominance;
    private Gene.Allele allele1Gene1;
    private Gene.Allele allele2Gene1;
    private Gene.Allele allele1Gene2;
    private Gene.Allele allele2Gene2;
    private Genotype genotype;
    private AllelePair allelePair1;
    private AllelePair allelePair2;
    private Map<Gene, AllelePair> genotypeMap;

    @BeforeEach
    public void setup() {
        autosomalInheritance = new AutosomalInheritance();
        completeDominance = new CompleteDominance();
        trait1 = new Trait("coatLength");
        trait1.addTraitVariant(TRAIT_VARIANT1_GENE1);
        trait1.addTraitVariant(TRAIT_VARIANT2_GENE1);
        trait2 = new Trait("coatColour");
        trait2.addTraitVariant(TRAIT_VARIANT1_GENE2);
        trait2.addTraitVariant(TRAIT_VARIANT2_GENE2);
        
        gene1 = new Gene("L", trait1, autosomalInheritance, completeDominance);
        allele1Gene1 = gene1.addAllele("L^d", 2, TRAIT_VARIANT1_GENE1);
        allele2Gene1 = gene1.addAllele("L^r", 0, TRAIT_VARIANT2_GENE1);
        allelePair1 = new AllelePair(gene1, allele1Gene1, allele2Gene1);

        gene2 = new Gene("C", trait2, autosomalInheritance, completeDominance);
        allele1Gene2 = gene2.addAllele("C^d", 1, TRAIT_VARIANT1_GENE2);
        allele2Gene2 = gene2.addAllele("C^r", 0, TRAIT_VARIANT2_GENE2);
        allelePair2 = new AllelePair(gene2, allele1Gene2, allele2Gene2);

        genotypeMap = new HashMap<>();
        genotypeMap.put(gene1, allelePair1);
        genotypeMap.put(gene2, allelePair2);

        genotype = new Genotype(genotypeMap);
    }

    @Test
    public void getInheritedAllelesTest() {
        assertEquals(genotypeMap, genotype.getInheritedAlleles());
    }

    @Test
    public void getAllelePairTestMultipleGenes() {
        assertEquals(allelePair1, genotype.getAllelePair(gene1));
        assertEquals(allelePair2, genotype.getAllelePair(gene2));
    }
}
