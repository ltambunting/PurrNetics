package com.purrnetics.model;

import java.util.Map;
import java.util.Random;

public interface InheritanceRule {
    AllelePair inherit(Gene gene, AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random); // assigns allele to offspring based on inheritance rule
    Map<Gene.Allele, Double> getPossibleGametes(AllelePair parentAllelePair);
    Map<AllelePair, Double> getInheritanceDistribution(Gene gene, AllelePair maternalAllelePair, AllelePair paternalAllelePair); // calculates probability of inheriting particular alleles
}

