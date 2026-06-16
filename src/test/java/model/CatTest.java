package model;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatTest {
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
    private Phenotype phenotype;
    private AllelePair allelePair1;
    private AllelePair allelePair2;
    private Map<Gene, AllelePair> genotypeMap;
    private Map<Trait, String> phenotypeMap;
    private Cat catMom;
    private Cat catDad;
    private Cat cat1;
    private ParentPair parentPair;

    @BeforeEach
    void setup() {
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

        phenotypeMap = new HashMap<>();
        phenotypeMap.put(trait1, TRAIT_VARIANT1_GENE1);
        phenotypeMap.put(trait2, TRAIT_VARIANT1_GENE2);

        phenotype = new Phenotype(phenotypeMap);

        catMom = new Cat("Lucy", Sex.FEMALE, null, genotype, phenotype);
        catDad = new Cat("Jotaro", Sex.MALE, null, genotype, phenotype);
        parentPair = new ParentPair(catMom, catDad);

        cat1 = new Cat("Jolyne", Sex.FEMALE, parentPair, genotype, phenotype);  
        
    }

    @Test
    void testGetters() {
        assertEquals("Jolyne", cat1.getName());
        assertEquals(Sex.FEMALE, cat1.getSex());
        assertEquals(parentPair, cat1.getParents());
        assertEquals(genotype, cat1.getGenotype());
        assertEquals(phenotype, cat1.getPhenotype());

    }

    @Test
    void testSetName() {
        cat1.setName("Irene");
        assertEquals("Irene", cat1.getName());
    }

}
