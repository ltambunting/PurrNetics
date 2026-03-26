package model;

import java.util.Collections;
import java.util.Map;

public class Genotype {
    private final Map<Gene, AllelePair> inheritedAlleles; // represents the organism's inherited maternal and paternal copy at each gene locus

    public Genotype(Map<Gene, AllelePair> genotype) {
        this.inheritedAlleles = genotype;
    }

    public Map<Gene, AllelePair> getInheritedAlleles() {
        return Collections.unmodifiableMap(inheritedAlleles);
    }

    // EFFECT: gets inherited allele pair for this gene locus
    public AllelePair getAllelePair(Gene gene) {
        return this.inheritedAlleles.get(gene);
    }
}
