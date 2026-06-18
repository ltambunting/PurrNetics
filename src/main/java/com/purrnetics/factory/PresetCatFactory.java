package com.purrnetics.factory;

import java.util.HashMap;
import java.util.Map;

import com.purrnetics.model.AllelePair;
import com.purrnetics.model.Cat;
import com.purrnetics.model.Gene;
import com.purrnetics.model.Genotype;
import com.purrnetics.model.Phenotype;
import com.purrnetics.model.Sex;
import com.purrnetics.model.Trait;

public class PresetCatFactory {
    private static final String ORANGE_ALLELE_SYMBOL = "O";
    private static final String NON_ORANGE_ALLELE_SYMBOL = "o";
    private static final String AGOUTI_ALLELE_SYMBOL = "A";
    private static final String NON_AGOUTI_ALLELE_SYMBOL = "a";
    private static final String SHORT_HAIR_ALLELE_SYMBOL = "L";
    private static final String LONG_HAIR_ALLELE_SYMBOL = "l";
    private static final String MOSAIC_STRING = "Mosaic";
    private final GeneFactory geneFactory;
    private final Gene orangeGene;
    private final Gene furLengthGene;
    private final Gene agoutiFurGene;
    private final Trait agoutiTrait;
    private final Trait furLengthTrait;
    private final AllelePair heterozygousShortHairAllelePair;
    private final AllelePair heterozygousAgoutiAllelePair;

    public PresetCatFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
        this.orangeGene = geneFactory.createOrangeGene();
        this.furLengthGene = geneFactory.createFurLengthGene();
        this.agoutiFurGene = geneFactory.createAgoutiGene();
        this.furLengthTrait = furLengthGene.getTrait();
        this.agoutiTrait = agoutiFurGene.getTrait();
        this.heterozygousShortHairAllelePair = new AllelePair(furLengthGene, furLengthGene.getAlleleBySymbol(SHORT_HAIR_ALLELE_SYMBOL), furLengthGene.getAlleleBySymbol(LONG_HAIR_ALLELE_SYMBOL));
        this.heterozygousAgoutiAllelePair = new AllelePair(agoutiFurGene, agoutiFurGene.getAlleleBySymbol(AGOUTI_ALLELE_SYMBOL), agoutiFurGene.getAlleleBySymbol(NON_AGOUTI_ALLELE_SYMBOL));
    }

    public Cat createFemaleShortHairAgoutiTortieCat() {
        AllelePair allelePair = new AllelePair(orangeGene, orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL), orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL));
        Map<Gene, AllelePair> genotypeMap = generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(orangeGene, allelePair);
        Map<Trait, String> phenotypeMap = generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(orangeGene.getTrait(), MOSAIC_STRING);
        Cat cat = new Cat("Tortie Queen", Sex.FEMALE, null, new Genotype(genotypeMap), new Phenotype(phenotypeMap));
        return cat;
    }

    public Cat createFemaleShortHairAgoutiOrangeCat() {
        AllelePair allelePair = new AllelePair(orangeGene, orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL), orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL));
        Map<Gene, AllelePair> genotypeMap = generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(orangeGene, allelePair);
        Map<Trait, String> phenotypeMap = generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(orangeGene.getTrait(), orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL).getVariant());
        Cat cat = new Cat("Tiger Queen", Sex.FEMALE, null, new Genotype(genotypeMap), new Phenotype(phenotypeMap));
        return cat;
    }

    public Cat createFemaleShortHairAgoutiNonOrangeCat() {
        AllelePair allelePair = new AllelePair(orangeGene, orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL), orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL));
        Map<Gene, AllelePair> genotypeMap = generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(orangeGene, allelePair);
        Map<Trait, String> phenotypeMap = generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(orangeGene.getTrait(), orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL).getVariant());
        Cat cat = new Cat("Tabby Queen", Sex.FEMALE, null, new Genotype(genotypeMap), new Phenotype(phenotypeMap));
        return cat;
    }

    public Cat createMaleShortHairAgoutiOrangeCat() {
        AllelePair allelePair = new AllelePair(orangeGene, orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL), null);
        Map<Gene, AllelePair> genotypeMap = generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(orangeGene, allelePair);
        Map<Trait, String> phenotypeMap = generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(orangeGene.getTrait(), orangeGene.getAlleleBySymbol(ORANGE_ALLELE_SYMBOL).getVariant());
        Cat cat = new Cat("Tiger Tom", Sex.MALE, null, new Genotype(genotypeMap), new Phenotype(phenotypeMap));
        return cat;
    }

    public Cat createMaleShortHairAgoutiNonOrangeCat() {
        AllelePair allelePair = new AllelePair(orangeGene, orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL), null);
        Map<Gene, AllelePair> genotypeMap = generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(orangeGene, allelePair);
        Map<Trait, String> phenotypeMap = generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(orangeGene.getTrait(), orangeGene.getAlleleBySymbol(NON_ORANGE_ALLELE_SYMBOL).getVariant());
        Cat cat = new Cat("Tabby Tom", Sex.MALE, null, new Genotype(genotypeMap), new Phenotype(phenotypeMap));
        return cat;
    }

    private Map<Gene, AllelePair> generateHeterozygousGenotypeMapAgoutiShortHairLociAndInputLocus(Gene gene, AllelePair allelePair) {
        Map<Gene, AllelePair> genotypeMap = new HashMap<>();
        genotypeMap.put(agoutiFurGene, heterozygousAgoutiAllelePair);
        genotypeMap.put(furLengthGene, heterozygousShortHairAllelePair);
        genotypeMap.put(gene, allelePair);
        return genotypeMap;
    }

    private Map<Trait, String> generateHeterozygousPhenotypeMapAgoutiShortHairLociandInputLocus(Trait trait, String variant) {
        Map<Trait, String> phenotypeMap = new HashMap<>();
        phenotypeMap.put(agoutiTrait, agoutiFurGene.getAlleleBySymbol(AGOUTI_ALLELE_SYMBOL).getVariant());
        phenotypeMap.put(furLengthTrait, furLengthGene.getAlleleBySymbol(SHORT_HAIR_ALLELE_SYMBOL).getVariant());
        phenotypeMap.put(trait, variant);
        return phenotypeMap;
    }
}
