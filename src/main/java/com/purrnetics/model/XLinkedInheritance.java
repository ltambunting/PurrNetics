package com.purrnetics.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles X-linked inheritance.
 *
 * Males are hemizygous for X-linked loci and therefore inherit only one X allele.
 * To maintain a uniform AllelePair representation, the second allele position is
 * treated as structurally absent (Y-side) rather than modeled as a genetic allele.
 *
 * The Y chromosome does not encode an allele for X-linked loci and is not used in
 * phenotype determination. Note: null is used intentionally to represent absence rather than unknown data.
 */
public class XLinkedInheritance implements InheritanceRule {
    private static final Gene.Allele NO_X_ALLELE = null;

    @Override
    public AllelePair inherit(Gene gene, AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        Gene.Allele maternalInheritedAllele = null;
        Gene.Allele paternalInheritedAllele = null;
        if (offspringSex == Sex.FEMALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = paternal.getMaternalAllele();
        } else if (offspringSex == Sex.MALE) {
            maternalInheritedAllele = maternal.getRandomAllele(random);
            paternalInheritedAllele = NO_X_ALLELE;

        }
        return new AllelePair(gene, maternalInheritedAllele, paternalInheritedAllele);
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

    @Override
    public Map<AllelePair, Double> getInheritanceDistribution(Gene gene, AllelePair maternalAllelePair, AllelePair paternalAllelePair) {
        Map<Gene.Allele, Double> possibleMaternalGameteDistributionMap = getPossibleGametes(maternalAllelePair);
        Map<Gene.Allele, Double> possiblePaternalGameteDistributionMap = getPossibleGametes(paternalAllelePair);
        Map<AllelePair, Double> inheritanceDistributionMap = new HashMap<>();
        for (Gene.Allele maternalAllele : possibleMaternalGameteDistributionMap.keySet()) {
            for (Gene.Allele paternalAllele : possiblePaternalGameteDistributionMap.keySet()) {
                AllelePair allelePair = new AllelePair(gene, maternalAllele, paternalAllele);
                if (!inheritanceDistributionMap.containsKey(allelePair)) {
                    Double probability = possibleMaternalGameteDistributionMap.get(maternalAllele) * 
                        possiblePaternalGameteDistributionMap.get(paternalAllele);
                    inheritanceDistributionMap.put(allelePair, probability);
                }
            }
        }
        return inheritanceDistributionMap;
    }

    

}
