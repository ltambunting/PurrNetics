package model;

import java.util.Random;

/**
 * Models inheritance of X-linked loci
 * 
 * Note: male offspring are represented using a special Y placeholder allele
 * to preserve compatability with the AllelePair-based genotype model. 
 * In other words, it indicates the absence of a corresponding X-linked allele on 
 * the Y chromosome in XY cats.
 */
public class XLinkedInheritance implements InheritanceRule {

    @Override
    public AllelePair inherit(AllelePair maternal, AllelePair paternal, Sex offspringSex, Random random) {
        return null; //stub
    }

}
