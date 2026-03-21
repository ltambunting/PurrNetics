package model;

import java.util.Random;

public interface InheritanceRule {
    AllelePair inherit(AllelePair maternal, AllelePair paternal, Random random); // assigns allele to offspring based on inheritance rule
    String resolvePhenotype(AllelePair allelePair); // resolves phenotype from allele pair at this offspring's gene locus based on inheritance rule
}

