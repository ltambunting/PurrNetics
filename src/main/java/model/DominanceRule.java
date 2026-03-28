package model;

public interface DominanceRule {
    String resolvePhenotype(AllelePair allelePair); // resolves phenotype from allele pair at this offspring's gene locus based on dominance rule
}
