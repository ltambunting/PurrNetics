package model;

import java.util.Random;

public interface InheritanceRule {
    AllelePair inherit(AllelePair maternal, AllelePair paternal, Random random); // assigns allele to offspring based on inheritance rule
    String resolvePhenotype(AllelePair genotype); // resolves phenotype from allele pair based on inheritance rule
}

