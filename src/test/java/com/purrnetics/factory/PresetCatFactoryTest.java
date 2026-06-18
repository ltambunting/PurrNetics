package com.purrnetics.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purrnetics.model.AllelePair;
import com.purrnetics.model.Cat;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Sex;
import com.purrnetics.model.Trait;


public class PresetCatFactoryTest {
    private GeneFactory geneFactory;
    private PresetCatFactory catFactory;

    @BeforeEach
    public void setup() {
        geneFactory = new GeneFactory();
        catFactory = new PresetCatFactory(geneFactory);
    }

    @Test
    public void createFemaleTortieTest() {
        Cat cat = catFactory.createFemaleShortHairAgoutiTortieCat();
        assertEquals("Tortie Queen", cat.getName());
        assertEquals(Sex.FEMALE, cat.getSex());
        assertEquals("Mosaic", getOrangePhenotype(cat));
        assertDefaultAppearance(cat);
        AllelePair orangePair = getOrangeAllelePair(cat);
        assertEquals("O", orangePair.getMaternalAllele().getAlleleSymbol());
        assertEquals("o",orangePair.getPaternalAllele().getAlleleSymbol());
    }

    @Test
    public void createFemaleOrangeTest() {
        Cat cat = catFactory.createFemaleShortHairAgoutiOrangeCat();
        assertEquals("Orange fur", getOrangePhenotype(cat));
        assertDefaultAppearance(cat);
        AllelePair orangePair = getOrangeAllelePair(cat);
        assertEquals("O", orangePair.getMaternalAllele().getAlleleSymbol());
        assertEquals("O", orangePair.getPaternalAllele().getAlleleSymbol());
    }

    @Test
    public void createFemaleNonOrangeTest() {
        Cat cat = catFactory.createFemaleShortHairAgoutiNonOrangeCat();
        assertEquals("Non-orange fur", getOrangePhenotype(cat));
        assertDefaultAppearance(cat);
        AllelePair orangePair = getOrangeAllelePair(cat);
        assertEquals("o", orangePair.getMaternalAllele().getAlleleSymbol());
        assertEquals("o", orangePair.getPaternalAllele().getAlleleSymbol());
    }



    @Test
    public void createMaleOrangeTest() {
        Cat cat = catFactory.createMaleShortHairAgoutiOrangeCat();
        assertEquals(Sex.MALE, cat.getSex());
        assertEquals("Orange fur", getOrangePhenotype(cat));
        assertDefaultAppearance(cat);
        AllelePair orangePair = getOrangeAllelePair(cat);
        assertEquals("O", orangePair.getMaternalAllele().getAlleleSymbol());
        assertEquals(null, orangePair.getPaternalAllele());
    }



    @Test
    public void createMaleNonOrangeTest() {
        Cat cat = catFactory.createMaleShortHairAgoutiNonOrangeCat();
        assertEquals("Non-orange fur", getOrangePhenotype(cat));
        assertDefaultAppearance(cat);
        AllelePair orangePair = getOrangeAllelePair(cat);
        assertEquals("o", orangePair.getMaternalAllele().getAlleleSymbol());
    }



    private String getOrangePhenotype(Cat cat) {
        Gene orangeGene = findGene(cat, "O");
        Trait orangeTrait = orangeGene.getTrait();
        return cat.getPhenotype().getExpressedVariant(orangeTrait);
    }


    private AllelePair getOrangeAllelePair(Cat cat) {
        Gene orangeGene = findGene(cat, "O");
         return cat.getGenotype().getInheritedAlleles().get(orangeGene);
    }


    private Gene findGene(Cat cat, String symbol) {
        for (Gene gene : cat.getGenotype().getInheritedAlleles().keySet()) {

            if (gene.getSymbol().equals(symbol)) {
                return gene;
            }
        }
        return null;
    }

    private String getFurLengthPhenotype(Cat cat) {
        Gene furLengthGene = findGene(cat, "L");
        Trait furLengthTrait = furLengthGene.getTrait();
        return cat.getPhenotype().getExpressedVariant(furLengthTrait);
    }

    private String getAgoutiPhenotype(Cat cat) {
        Gene agoutiGene = findGene(cat, "A");
        Trait agoutiTrait = agoutiGene.getTrait();
        return cat.getPhenotype().getExpressedVariant(agoutiTrait);
    }

    private void assertDefaultAppearance(Cat cat) {
        assertEquals("Short hair", getFurLengthPhenotype(cat));
        assertEquals("Agouti fur", getAgoutiPhenotype(cat));
    }
}