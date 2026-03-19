package model;

public interface InheritanceRule {
    AllelePair inherit(AllelePair maternal, AllelePair paternal); // assigns allele to offspring based on inheritance rule
    Trait resolvePhenotype(AllelePair genotype); // resolves phenotype from allele pair based on inheritance rule
}

