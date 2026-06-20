package com.purrnetics.model;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AutosomalInheritance implements InheritanceRule {

    // EFFECT: randomly assigns allele pair from maternal and paternal copies
    //         based on autosomal mode of inheritance
    // REQUIRES: maternal != null and paternal != null
    @Override
    public AllelePair inherit(Gene gene, AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        Gene.Allele maternalCopy = maternal.getRandomAllele(random);
        Gene.Allele paternalCopy = paternal.getRandomAllele(random);
        AllelePair offspringAllelePair = new AllelePair(gene, maternalCopy, paternalCopy);
        return offspringAllelePair;
    } 

    @Override
    public Map<Gene.Allele, Double> getPossibleGametes(AllelePair parentAllelePair) {
        Map<Gene.Allele, Double> possibleGameteDistributionMap = new HashMap<>();
        Gene.Allele maternalAllele = parentAllelePair.getMaternalAllele();
        Gene.Allele paternalAllele = parentAllelePair.getPaternalAllele();
        if (maternalAllele == paternalAllele) {
            possibleGameteDistributionMap.put(maternalAllele, 1.0);
        } else {
            possibleGameteDistributionMap.put(maternalAllele, 0.50);
            possibleGameteDistributionMap.put(paternalAllele, 0.50);
        }
        return possibleGameteDistributionMap;
    }
    

    // EFFECT: given maternal and paternal allele pairs, calculates probability of inheriting particular allele pair
    // REQUIRES: maternal != null and paternal != null
    @Override
    public Map<AllelePair, Double> getInheritanceDistribution(Gene gene, AllelePair maternal, AllelePair paternal, Sex offspringSex) {
        return null; // stub
    }
}
