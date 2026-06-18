package com.purrnetics.factory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purrnetics.model.AutosomalInheritance;
import com.purrnetics.model.CompleteDominance;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Trait;
import com.purrnetics.model.XLinkedInheritance;
import com.purrnetics.model.XLinkedMosaicExpression;

public class GeneFactoryTest {
    private GeneFactory geneFactory;

    @BeforeEach
    public void setup() {
        this.geneFactory = new GeneFactory();
    }

    @Test
    public void createOrangeGeneTest() {
        Gene orangeGene = geneFactory.createOrangeGene();
        Trait orangeTrait = orangeGene.getTrait();
        Set<String> orangeTraitVariants = orangeTrait.getTraitVariants();

        assertEquals("O", orangeGene.getSymbol());
        assertTrue(orangeGene.getInheritanceRule() instanceof XLinkedInheritance);
        assertTrue(orangeGene.getExpressionRule() instanceof XLinkedMosaicExpression);

        assertEquals("Orange Fur", orangeTrait.getKey());
        assertEquals(Set.of("Orange fur","Non-orange fur", "Mosaic"), orangeTraitVariants);

        assertEquals(Set.of("O", "o"), getActualAlleleSymbols(orangeGene));
        assertEquals(Set.of("Orange fur", "Non-orange fur"), getActualTraitVariantsWithEachAllele(orangeGene));
    }

    @Test
    public void createFurLengthGeneTest() {
        Gene furLengthGene = geneFactory.createFurLengthGene();
        Trait furLengthTrait = furLengthGene.getTrait();
        Set<String> furLengthTraitVariants = furLengthTrait.getTraitVariants();

        assertEquals("L", furLengthGene.getSymbol());
        assertTrue(furLengthGene.getInheritanceRule() instanceof AutosomalInheritance);
        assertTrue(furLengthGene.getExpressionRule() instanceof CompleteDominance);

        assertEquals("Fur Length", furLengthTrait.getKey());
        assertEquals(Set.of("Short hair", "Long hair"), furLengthTraitVariants);

        assertEquals(Set.of("L", "l"), getActualAlleleSymbols(furLengthGene));
        assertEquals(Set.of("Short hair", "Long hair"), getActualTraitVariantsWithEachAllele(furLengthGene));
    }

    @Test
    public void createAgoutiFurGeneTest() {
        Gene agoutiGene = geneFactory.createAgoutiGene();
        Trait agoutiFurTrait = agoutiGene.getTrait();
        Set<String> agoutiFurTraitVariants = agoutiFurTrait.getTraitVariants();

        assertEquals("A", agoutiGene.getSymbol());
        assertTrue(agoutiGene.getInheritanceRule() instanceof AutosomalInheritance);
        assertTrue(agoutiGene.getExpressionRule() instanceof CompleteDominance);

        assertEquals("Agouti Fur", agoutiFurTrait.getKey());
        assertEquals(Set.of("Agouti fur", "Non-agouti fur"), agoutiFurTraitVariants);

        assertEquals(Set.of("A", "a"), getActualAlleleSymbols(agoutiGene));
        assertEquals(Set.of("Agouti fur", "Non-agouti fur"), getActualTraitVariantsWithEachAllele(agoutiGene));
    }

    private Set<String> getActualAlleleSymbols(Gene gene) {
        Set<String> actualAlleleSymbols = new HashSet<>();
        for (Gene.Allele allele : gene.getAlleles()) {
            actualAlleleSymbols.add(allele.getAlleleSymbol());
        }
        return actualAlleleSymbols;
    }

    private Set<String> getActualTraitVariantsWithEachAllele(Gene gene) {
        Set<String> actualVariantsFromAlleles = new HashSet<>();
        for (Gene.Allele allele : gene.getAlleles()) {
            actualVariantsFromAlleles.add(allele.getVariant());
        }
        return actualVariantsFromAlleles;
    }
}
