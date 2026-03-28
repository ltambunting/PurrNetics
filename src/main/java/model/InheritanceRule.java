package model;

import java.util.Random;

public interface InheritanceRule {
    AllelePair inherit(AllelePair maternal, AllelePair paternal, Random random); // assigns allele to offspring based on inheritance rule
}

