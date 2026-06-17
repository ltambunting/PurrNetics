package com.purrnetics.model;

import java.util.Random;

public interface InheritanceRule {
    AllelePair inherit(Gene gene, AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random); // assigns allele to offspring based on inheritance rule
}

